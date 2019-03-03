package gameplay;

import assets.Asset;
import assets.AssetsIds;
import assets.LegalAsset;
import factories.AssetFactory;
import factories.PlayerFactory;
import main.GameInput;
import players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Class created for instantiating a game. The objects of type {@code Game} offers the posibility to
 * start, play and end a game of Sheriff of Nottingham.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Game {
    private static int numberOfRound;
    // List containing the assets in deck in the current game.
    private static List<Asset> assets;
    // List containing the players of the current game.
    private static List<Player> players;

    /**
     * Constructs a newly allocated {@code Game} object and sets the specified values for all
     * fields.
     * @param gameInput the {@link GameInput} object containing the two lists with ids of assets and
     *                  strategies of players
     */
    public Game(final GameInput gameInput) {
        assets = AssetFactory.getInstance().createListAssets(gameInput.getAssetIds());
        players = PlayerFactory.getInstance().createListPlayers((gameInput.getPlayerNames()));
    }

    /**
     * Returns the current deck of assets ids.
     * @return the list containing the assets ids (the deck) in the current game
     */
    public static List<Asset> getAssets() {
        return assets;
    }

    /**
     * Returns the current players list.
     * @return the list containing the players in the current game
     */
    public static List<Player> getPlayers() {
        return players;
    }

    /**
     * Initializes the game. Completes the assets in hand for every player.
     */
    public void initialize() {
        for (Player player : players) {
            player.completeAssetsInHand(assets);
        }
    }

    /**
     * Starts a number of rounds equal to double the number of players.
     */
    public void playRounds() {
        for (int round = 0; round < players.size() * 2; round++) {
            Round.start();
        }
    }

    /**
     * Ends a game after computing the total points for every player and sorting them descending
     * by their points.
     */
    public void finish() {
        // Get the number of required number of assets of every type for reaching King's or Queen's
        // Bonus.
        List<List<Integer>> numberOfAssetsForKingQueenBonus =
                computeNumberOfAssetsForKingQueenBonus();

        // Add the King's or Queen's Bonus after deciding for which type of assets the player must
        // receive the bonus and compute the final points for every player.
        for (Player player : players) {
            // List containing the assets for which the current player gets the King's Bonus.
            List<Asset> kingForAssets = new ArrayList<Asset>();
            // List containing the assets for which the current player gets the Queen's Bonus.
            List<Asset> queenForAssets = new ArrayList<Asset>();

            for (int id = AssetsIds.APPLE_ID.getId(); id <= AssetsIds.CHICKEN_ID.getId(); id++) {
                Asset asset = AssetFactory.getInstance().createElement(AssetsIds.getValue(id));
                // If the player has the same number of assets of a type with the maximum in the
                // game, the asset is added to the kingForAssets list.
                if (player.getCountOfAsset(asset)
                        == numberOfAssetsForKingQueenBonus.get(id).get(0)) {
                    kingForAssets.add(asset);
                // If the player has the same number of assets of a type with the second maximum in
                // the game, the asset is added to the queenForAssets list.
                } else if (player.getCountOfAsset(asset)
                        == numberOfAssetsForKingQueenBonus.get(id).get(1)) {
                    queenForAssets.add(asset);
                }
            }

            // Adding the King's and Queen's Bonuses and computing the final points.
            player.calculatePoints(kingForAssets, queenForAssets);
        }

        // Sort the players using lambda expression.
        players.sort((p1, p2) -> p2.getPoints() - p1.getPoints());

        // Print the final ranking.
        for (Player player : players) {
            player.printPoints();
        }
    }

    // Returns a list of lists of Integers designed with this logic:
    // - every item in the grand list refers to an legal asset, the first item refers to Apple
    //      (because it has the id 0), the next item refers to Cheese(id 1) and the other two
    //      refers to Bread(id 2) and Chicken(id 3);
    // - the first item in the "child" list means the maximum number of occurrences for the type of
    //      legal asset corresponding in the grand list;
    // - the second item in the "child" list means the second maximum number of occurrences for the
    //      type of legal asset corresponding in the grand list.
    //
    // Example:
    //      Apple: 6, 3     ---> grandList.get(0) = Apple,   Apple.get(0) = 6, Apple.get(1) = 3
    //      Cheese: 3, 1    ---> grandList.get(1) = Cheese,  Cheese.get(0) = 3, Cheese.get(1) = 1
    //      Bread: 5, 0     ---> grandList.get(2) = Bread,   Bread.get(0) = 5, Bread.get(1) = 0
    //      Chicken 0, -1   ---> grandList.get(3) = Chicken, Chicken.get(0) = 0, Chicken.get(1) = -1
    // So, in the current game, the maximum number of occurrences of Cheese on all stands is 3 and
    // the second maximum is 1. For Chicken, the maximum number of occurrences is 0, because no one
    // has Chicken on his stand, and the second one is -1 for not offering the both bonuses.
    private List<List<Integer>> computeNumberOfAssetsForKingQueenBonus() {
        List<Asset> legalAssets = LegalAsset.getLegalAssets();
        List<List<Integer>> numberOfAssetsForKingQueenBonus = new ArrayList<List<Integer>>();

        for (Asset asset : legalAssets) {
            List<Integer> kingQueenBonuses = new ArrayList<>();
            // TreeSet used for keeping every number of occurrences of an asset for all stands.
            TreeSet<Integer> countsSpecifiedAsset = new TreeSet<>();

            for (Player player : players) {
                // Adding the number of occurrences of the current asset the current player has
                // on stand.
                countsSpecifiedAsset.add(player.getCountOfAsset(asset));
            }

            // Add the maximum number of occurrences of the current asset on a stand as the
            // required number of occurrences of King's Bonus.
            kingQueenBonuses.add(countsSpecifiedAsset.pollLast());

            // If there is a second maximum of number of occurrences of the current asset on a
            // stand, add it as the required number of occurrences of Queen's Bonus.
            if (!countsSpecifiedAsset.isEmpty()) {
                kingQueenBonuses.add(countsSpecifiedAsset.pollLast());
            // If there is not a second maximum, set the value of required number of occurrences of
            // Queen's Bonus to -1, so there won't be a player who will get this bonus because no
            // one can have a negative number of assets on their stand.
            } else {
                kingQueenBonuses.add(-1);
            }

            // Add in the grand list the required numbers of assets for the two bonuses.
            numberOfAssetsForKingQueenBonus.add(kingQueenBonuses);
        }

        return numberOfAssetsForKingQueenBonus;
    }

    /**
     * Static inner class used for playing rounds in the game.
     * @author Teodor-Adrian Mirea, 323CA
     */
    public static class Round {
        // Field used to have a quick access on the Player object which is currently sheriff.
        private static Player currentSheriff;

        /**
         * Plays a round by going through all stages.
         */
        public static void start() {
            numberOfRound++;

            initializeRound();
            loadMerchantBagAndDeclaration();
            inspection();
            endOfRound();
        }

        // Computes which player is going to be the sheriff this round.
        private static void initializeRound() {
            currentSheriff = players.get((numberOfRound - 1) % players.size());
        }

        // Moving the assets from hand in bag and declaring the assets in bag for every player.
        private static void loadMerchantBagAndDeclaration() {
            for (Player player : players) {
                if (player.equals(currentSheriff)) {
                    continue;
                }
                player.loadAssetsInBagAndDeclare();
            }
        }

        // Inspecting the bags of merchants.
        private static void inspection() {
            List<Player> merchants = new ArrayList<Player>(players);

            merchants.remove(currentSheriff);
            currentSheriff.inspect(merchants);
        }

        // Ends the round by moving the remaining assets in bag on stand and completing the set of
        // assets in hand for every player.
        private static void endOfRound() {
            for (Player player : players) {
                player.loadAssetsFromBagOnStand();
                player.completeAssetsInHand(assets);
            }

        }

        /**
         * Adds all the assets in the list given as parameter at the end of the deck.
         * @param assetsToAdd list with the assets which will be added in deck
         */
        public static void addAssetsInDeck(final List<Asset> assetsToAdd) {
            for (Asset asset : assetsToAdd) {
                assets.add(asset);
            }
        }


    }

}
