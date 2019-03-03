package players;

import assets.Apple;
import assets.Asset;
import gameplay.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class created for instantiating a player which uses the bribed strategy.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Bribed extends Player {
    private static final int NORMAL_BRIBE = 5;
    private static final int DOUBLE_BRIBE = 10;
    @Override
    protected List<Asset> setAssetsToMove(final TreeMap<Integer, Asset> frequenciesOfLegalAssets,
                                          final TreeMap<Integer, Asset> profitsOfIllegalAssets) {
        List<Asset> assetsToMove = new ArrayList<Asset>();

        // If the player using bribed strategy has no illegal assets, he uses the basic strategy
        // for selecting which assets to move from hand in bag.
        if (profitsOfIllegalAssets.isEmpty()) {
            return basicAssetsToMove(frequenciesOfLegalAssets, profitsOfIllegalAssets);
        }

        // If this point is reached, there is at least one illegal asset in player's hand.
        // Select the one with the greatest profit.
        Map.Entry<Integer, Asset> mostProfitableIllegalAsset = profitsOfIllegalAssets.lastEntry();

        // Add illegal assets until there are no more.
        while (mostProfitableIllegalAsset != null) {
            // Get the asset with the greatest profit.
            Asset assetToMove = mostProfitableIllegalAsset.getValue();

            // Select all the occurrences of the asset to move.
            for (Asset asset : assetsInHand) {
                // Since there is a single instance of any Asset (Singleton Pattern), testing the
                // equality of references is correct.
                if (asset.equals(assetToMove)) {
                    assetsToMove.add(asset);
                }
            }

            // Delete the actual greatest profit entry because it has been selected for moving
            // and select the next greatest profit entry.
            profitsOfIllegalAssets.remove(mostProfitableIllegalAsset.getKey());
            mostProfitableIllegalAsset = profitsOfIllegalAssets.lastEntry();
        }

        // If there are more than 5 (=MAXIMUM_ASSETS_IN_BAG) assets in bag, remove the last one
        // because the maximum number of possible assets in bag cannot be greater that the maximum
        // number of assets in hand = 6.
        if (assetsToMove.size() > MAXIMUM_ASSETS_IN_BAG) {
            assetsToMove.remove(MAXIMUM_ASSETS_IN_BAG);
        }

        bribe = (assetsToMove.size() <= 2 ? NORMAL_BRIBE : DOUBLE_BRIBE);

        // If the player doesn't have enough money to give bribe, he uses the basic strategy for
        // selecting which assets to move from hand in bag.
        if (bribe > money) {
            bribe = 0;
            return basicAssetsToMove(frequenciesOfLegalAssets, profitsOfIllegalAssets);
        }

        // Subtract bribe from player's money and declare that in bag are Apples.
        money -= bribe;
        declaredAssets = Apple.getInstance();

        return assetsToMove;
    }

    @Override
    public void inspect(final List<Player> merchants) {
        for (Player merchant : merchants) {
            // The bribed strategy player controls his neighbors.
            if (isNeighborWith(merchant)) {
                controlBag(merchant);
            }
        }
    }

    // Returns if a player is or not neighbor with the current player.
    private boolean isNeighborWith(final Player merchant) {
        List<Player> players = Game.getPlayers();

        // If the player is first on the left side or right side, he is neighbor.
        if (((players.indexOf(merchant) + 1) % players.size() == players.indexOf(this))
                || ((players.indexOf(merchant) - 1 + players.size()) % players.size()
                    == players.indexOf(this))) {
            return true;
        }

        return false;
    }

    @Override
    public void printPoints() {
        System.out.println("BRIBED: " + points);
    }
}
