package players;

import assets.Asset;

import java.util.List;
import java.util.TreeMap;

/**
 * Class created for instantiating a player which uses the greedy strategy.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Greedy extends Player {
    // Value used to identify in which rounds the player should add in bag an illegal asset (if he
    // has) next to the legal ones.
    private int numberOfRoundAsMerchant = 0;

    @Override
    protected List<Asset> setAssetsToMove(final TreeMap<Integer, Asset> frequenciesOfLegalAssets,
                                          final TreeMap<Integer, Asset> profitsOfIllegalAssets) {
        // In every round, the greedy player applies the basic strategy to select the assets he
        // moves from hand in bag.
        List<Asset> assetsToMove = basicAssetsToMove(frequenciesOfLegalAssets,
                profitsOfIllegalAssets);

        numberOfRoundAsMerchant++;

        // If the round is even, there are illegal assets and the bag is not full, the greedy
        // player adds the illegal asset with the greatest profit.
        if ((isEvenRound()) && (!profitsOfIllegalAssets.isEmpty())
                && (assetsToMove.size() < MAXIMUM_ASSETS_IN_BAG)) {
            assetsToMove.add(profitsOfIllegalAssets.lastEntry().getValue());
        }

        return assetsToMove;
    }

    @Override
    public void inspect(final List<Player> merchants) {
        for (Player merchant : merchants) {
            // The greedy player controls every merchant who doesn't offer bribe.
            if (merchant.bribe == 0) {
                controlBag(merchant);
            } else {
                acceptBribe(merchant);
            }
        }
    }

    @Override
    public void printPoints() {
        System.out.println("GREEDY: " + points);
    }

    // Returns if a round is even from the ones in which the greedy player played as merchant.
    private boolean isEvenRound() {
        return (numberOfRoundAsMerchant % 2 == 0);
    }
}
