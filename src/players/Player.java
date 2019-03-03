package players;

import assets.Apple;
import assets.Asset;
import assets.IllegalAsset;
import assets.LegalAsset;
import gameplay.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Abstract class created for instantiating a player.
 * <p>
 * An object of type {@code Player} contains fields for the general properties of every player in
 * the game.
 * </p>
 * @author Teodor-Adrian Mirea, 323CA
 */
public abstract class Player {
    /** The maximum number of assets that a player can put in his bag. */
    protected static final int MAXIMUM_ASSETS_IN_BAG = 5;
    // The maximum number of assets that a player can hold in hand.
    private static final int MAXIMUM_ASSETS_IN_HAND = 6;
    // Amount of money that every player has at the beginning of the game.
    private static final int INITIAL_MONEY = 50;

    /** List containing the assets that the player holds in hand. */
    protected List<Asset> assetsInHand;
    /** List containing the assets that the player puts in his bag. */
    protected List<Asset> assetsInBag;
    /** List containing the assets that the player has on his stand. */
    protected List<Asset> assetsOnStand;
    /** The asset that the player declares when he says what type of assets is in his bag. */
    protected Asset declaredAssets;
    /** The amount of money the player has. */
    protected int money;
    /** The bribe the player offers with his bag. */
    protected int bribe;
    /** Total points of the player. */
    protected int points;
    private int profit;
    private int kingQueenBonus;

    /**
     * Constructs a newly allocated {@code Player} object and sets the initial values for all
     * fields.
     */
    protected Player() {
        assetsInHand = new ArrayList<Asset>();
        assetsInBag = new ArrayList<Asset>();
        assetsOnStand = new ArrayList<Asset>();
        money = INITIAL_MONEY;
    }

    /**
     * Returns the {@link #points} field.
     * @return the points value of the player
     */
    public final int getPoints() {
        return points;
    }

//    Methods for creating the bag and declaring the assets in bag.

    /**
     * Moves the selected assets based on the player's strategy from his hand into his bag.
     */
    public final void loadAssetsInBagAndDeclare() {
        // Getting the number of occurrences of every legal asset the player has in hand and the
        // profit of every illegal asset the player has in hand.
        TreeMap<Integer, Asset> occurrencesOfLegalAssets = computeOccurrencesOfLegalAssets();
        TreeMap<Integer, Asset> profitsOfIllegalAssets = computeProfitsOfIllegalAssets();

        // Selecting which assets to transfer from hand into bag.
        List<Asset> assetsToMove = setAssetsToMove(occurrencesOfLegalAssets,
                profitsOfIllegalAssets);

        moveAssetsFromHandIntoBag(assetsToMove);
    }

    // Returns a TreeMap containing for every number of occurrences the legal asset with the
    // greatest profit the player has in his hand.
    private TreeMap<Integer, Asset> computeOccurrencesOfLegalAssets() {
        // Every entry in the TreeMap has the Key as number of occurrences in hand for every legal
        // asset in the player's hand and the Value as corresponding asset.
        TreeMap<Integer, Asset> occurrencesOfLegalAssets = new TreeMap<Integer, Asset>();

        for (Asset asset : assetsInHand) {
            if (!asset.isIllegal()) {
                // If the legal asset has not been added in the TreeMap.
                if (!occurrencesOfLegalAssets.containsValue(asset)) {
                    // Compute the number of occurrences for this legal asset in the player's hand.
                    Integer occurrencesOfAsset = getOccurrencesOfAsset(asset);

                    // If there is another legal asset with the same number of occurrences.
                    if (occurrencesOfLegalAssets.containsKey(occurrencesOfAsset)) {
                        // If the profit for the newer legal asset is greater that the profit for
                        // the older legal asset, replace the entry with the newer legal asset and
                        // its number of occurrences.
                        // If the newer profit is equal with the old one, the old legal asset
                        // remains in place, so the player will put in bag the first occurrence
                        // of a legal asset with the same profit as another.
                        if (occurrencesOfLegalAssets.get(occurrencesOfAsset).getProfit()
                                < asset.getProfit()) {
                            occurrencesOfLegalAssets.replace(occurrencesOfAsset, asset);
                        }
                    // Else, if there is not another legal asset with the same number of
                    // occurrences add the current pair of (occurrence, legal asset).
                    } else {
                        occurrencesOfLegalAssets.put(occurrencesOfAsset, asset);
                    }
                }
            }
        }

        return occurrencesOfLegalAssets;
    }

    // Returns a TreeMap containing the profit for every type of illegal asset the player has in his
    // hand.
    private TreeMap<Integer, Asset> computeProfitsOfIllegalAssets() {
        // Every entry in the TreeMap has the Key as profit for every illegal asset in the
        // player's hand and the Value as the illegal asset with that profit value.
        TreeMap<Integer, Asset> profitsOfIllegalAssets = new TreeMap<Integer, Asset>();

        for (Asset asset : assetsInHand) {
            if (asset.isIllegal()) {
                // If the illegal asset has not been added in the TreeMap.
                if (!profitsOfIllegalAssets.containsValue(asset)) {
                    profitsOfIllegalAssets.put(asset.getProfit(), asset);
                }
            }
        }

        return profitsOfIllegalAssets;
    }

    /**
     * Sets which assets to be moved from hand into bag.
     * @param occurrencesOfLegalAssets a TreeMap containing for every number of occurrences the
     *                                 legal asset with the greatest profit the player has in his
     *                                 hand
     * @param profitsOfIllegalAssets a TreeMap containing the profit for every type of illegal
     *                               asset the player has in his hand
     * @return a list containing the assets which are going to be moved from hand in bag
     */
    protected abstract List<Asset> setAssetsToMove(TreeMap<Integer, Asset> occurrencesOfLegalAssets,
                                                   TreeMap<Integer, Asset> profitsOfIllegalAssets);

    /**
     * Computes the basic strategy of selecting assets to move from hand into bag.
     * @param occurrencesOfLegalAssets a TreeMap containing for every number of occurrences the
     *                                 legal asset with the greatest profit the player has in his
     *                                 hand
     * @param profitsOfIllegalAssets a TreeMap containing the profit for every type of illegal
     *                               asset the player has in his hand
     * @return a list containing the assets which are going to be moved from hand into bag
     */
    protected final List<Asset> basicAssetsToMove(final TreeMap<Integer, Asset>
                                                          occurrencesOfLegalAssets,
                                                  final TreeMap<Integer, Asset>
                                                          profitsOfIllegalAssets) {
        List<Asset> assetsToMove = new ArrayList<Asset>();

        // If there are legal assets.
        if (!occurrencesOfLegalAssets.isEmpty()) {
            // Take the legal asset with the greatest number of occurrences.
            // lastEntry() return the map entry (Key, Value) pair with the greatest key.
            Asset assetToMove = occurrencesOfLegalAssets.lastEntry().getValue();
            declaredAssets = assetToMove;

            // Select all the occurrences of the asset to move.
            for (Asset asset : assetsInHand) {
                // Since there is a single instance of any Asset (Singleton Pattern), testing the
                // equality of references is correct.
                if (asset.equals(assetToMove)) {
                    assetsToMove.add(asset);
                }
            }

            // If there are more than 5 (=MAXIMUM_ASSETS_IN_BAG) assets in bag, remove the last one
            // because the maximum number of possible assets in bag cannot be greater that the
            // maximum number of assets in hand = 6.
            if (assetsToMove.size() > MAXIMUM_ASSETS_IN_BAG) {
                assetsToMove.remove(MAXIMUM_ASSETS_IN_BAG);
            }
        // If the player doesn't have any legal asset.
        } else {
            // Take the illegal asset with the greatest profit.
            // lastEntry() return the map entry (Key, Value) pair with the greatest key.
            Asset assetToMove = profitsOfIllegalAssets.lastEntry().getValue();
            // Declare that in bag are Apples.
            declaredAssets = Apple.getInstance();
            // Add only one illegal asset.
            assetsToMove.add(assetToMove);
        }

        return assetsToMove;
    }

    // Moves the assets from player's hand into his bag.
    private void moveAssetsFromHandIntoBag(final List<Asset> assetsToMove) {
        for (Asset asset : assetsToMove) {
            assetsInHand.remove(asset);
            assetsInBag.add(asset);
        }
    }

    // Returns the number of occurrences for a reference asset, named refAsset.
    private Integer getOccurrencesOfAsset(final Asset refAsset) {
        Integer occurrences = 0;

        for (Asset asset : assetsInHand) {
            // Since there is a single instance of any Asset (Singleton Pattern), testing the
            // equality of references is correct.
            if (asset.equals(refAsset)) {
                occurrences++;
            }
        }

        return occurrences;
    }

//    Methods for inspecting the bags of merchants.

    /**
     * For every merchant chooses if his bag should be controlled or not.
     * @param merchants list of merchants the sheriff is going to choose which bags to control or
     *                  not
     */
    public abstract void inspect(List<Player> merchants);

    /**
     * Controls the merchant's bag and pays him the corresponding amount of money if the merchant is
     * not a liar, or the merchant pays sheriff the corresponding amount of money if he lied.
     * @param merchant the merchant who has the bag controlled
     */
    protected final void controlBag(final Player merchant) {
        List<Asset> assetsInMerchantsBag = merchant.assetsInBag;
        Asset declaredMerchantsAssets = merchant.declaredAssets;
        List<Asset> assetsToConfiscate = new ArrayList<Asset>();
        boolean liarMerchant = false;

        // Since the bag is controlled, the bribe is refused.
        merchant.withdrawBribe();

        for (Asset asset : assetsInMerchantsBag) {
            // Since there is a single instance of any Asset (Singleton Pattern), testing the
            // equality of references is correct.
            if (!asset.equals(declaredMerchantsAssets)) {
                liarMerchant = true;
                // Merchant pays sheriff the penalty of asset because he lied.
                this.takeMoney(merchant.giveMoney(asset.getPenalty()));
                // The non-declared asset is taken from merchant's bag.
                assetsToConfiscate.add(asset);
            }
        }

        // If the merchant lied, remove the confiscated assets and add them at the end of deck.
        if (liarMerchant) {
            merchant.removeAssets(assetsToConfiscate);
            Game.Round.addAssetsInDeck(assetsToConfiscate);
        // Else, if the merchant didn't lie, the sheriff pays merchant the penalty for every asset
        // in his bag.
        } else {
            merchant.takeMoney(this.giveMoney(assetsInMerchantsBag.size()
                                                * declaredMerchantsAssets.getPenalty()));
        }
    }

    // Taking back the bribe player offered with his bag in case the sheriff refuses the bribe.
    private void withdrawBribe() {
        money += bribe;
        bribe = 0;
    }

    // Taking the amount money given by merchant for current player (which is sheriff).
    private void takeMoney(final int moneyToTake) {
        money += moneyToTake;
    }

    // Giving the amount of money the player has to lose because he lied.
    private int giveMoney(final int moneyToGive) {
        money -= moneyToGive;
        return moneyToGive;
    }

    // Removes assets from merchant's bag if he put them without declaring.
    private void removeAssets(final List<Asset> assetsToRemove) {
        for (Asset asset : assetsToRemove) {
            assetsInBag.remove(asset);
        }
    }

    /**
     * Adds the bribe from the merchant to the amount of money the current player (which is sheriff)
     * has.
     * @param merchant the player from whom the bribe is collected
     */
    protected final void acceptBribe(final Player merchant) {
        money += merchant.bribe;
        merchant.bribe = 0;
    }

//    Methods for moving the remained assets in bag on stand and for completing the assets in hand.

    /**
     * Moves the player's assets from his bag on his stand. For every illegal asset, the bonus legal
     * corresponding assets are added on stand.
     */
    public final void loadAssetsFromBagOnStand() {
        for (Asset asset : assetsInBag) {
            assetsOnStand.add(asset);

            if (asset.isIllegal()) {
                assetsOnStand.addAll(((IllegalAsset) asset).getBonusAssets());
            }
        }

        assetsInBag.clear();
    }

    /**
     * Completes the set of assets in hand until it reaches 6 (=MAXIMUM_ASSETS_IN_HAND).
     * @param assetsInDeck list of the assets available for all players for taking assets (the deck)
     */
    public final void completeAssetsInHand(final List<Asset> assetsInDeck) {
        while (assetsInHand.size() < MAXIMUM_ASSETS_IN_HAND) {
            assetsInHand.add(assetsInDeck.remove(0));
        }
    }

//    Methods for computing total points at the end of the game.

    /**
     * Computes the count of an asset on player's stand.
     * @param assetToCount the asset which is counted
     * @return the count of the {@link Asset} object given as parameter
     */
    public final int getCountOfAsset(final Asset assetToCount) {
        int counter = 0;

        for (Asset asset : assetsOnStand) {
            // Since there is a single instance of any Asset (Singleton Pattern), testing the
            // equality of references is correct.
            if (asset.equals(assetToCount)) {
                counter++;
            }
        }

        return counter;
    }

    /**
     * Computes the player's total points.
     * <p>
     * The methos sums player's money, the profit reached from the assets on his stand and the King
     * Bonus and Queen Bonus for the most occurrences of an asset, respectively the second most
     * occurrences of an asset.
     * </p>
     * @param kingForAssets list containing the {@link Asset} objects the player has the most
     *                      number of occurrences among all players
     * @param queenForAssets list containing the {@link Asset} objects the player has the most
     *                       second number of occurrences among all players
     */
    public final void calculatePoints(final List<Asset> kingForAssets,
                                         final List<Asset> queenForAssets) {
        calculateProfit();
        addKingBonus(kingForAssets);
        addQueenBonus(queenForAssets);

        points = money + profit + kingQueenBonus;
    }

    // Method sums the profit for every asset on stand.
    private void calculateProfit() {
        for (Asset asset : assetsOnStand) {
            profit += asset.getProfit();
        }
    }

    // Method adds the King's Bonus for every asset the player has the most number of occurrences
    // among all players.
    private void addKingBonus(final List<Asset> kingForAssets) {
        for (Asset asset : kingForAssets) {
            kingQueenBonus += ((LegalAsset) asset).getKingBonus();
        }
    }

    // Method adds the Queen's Bonus for every asset the player has the most second number of
    // occurrences among all players.
    private void addQueenBonus(final List<Asset> queenForAssets) {
        for (Asset asset : queenForAssets) {
            kingQueenBonus += ((LegalAsset) asset).getQueenBonus();
        }
    }

    /**
     * Prints the type of player and his points.
     */
    public abstract void printPoints();
}
