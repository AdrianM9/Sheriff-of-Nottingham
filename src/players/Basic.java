package players;

import assets.Asset;

import java.util.List;
import java.util.TreeMap;

/**
 * Class created for instantiating a player which uses the basic strategy.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Basic extends Player {
    @Override
    protected List<Asset> setAssetsToMove(final TreeMap<Integer, Asset> frequenciesOfLegalAssets,
                                          final TreeMap<Integer, Asset> profitsOfIllegalAssets) {
        // Since this is the basic player, he is using the basic strategy for selecting assets to
        // move from his hand in his bag.
        return basicAssetsToMove(frequenciesOfLegalAssets, profitsOfIllegalAssets);
    }

    @Override
    public void inspect(final List<Player> merchants) {
        // The basic player controls every merchant.
        for (Player merchant : merchants) {
            controlBag(merchant);
        }
    }

    @Override
    public void printPoints() {
        System.out.println("BASIC: " + points);
    }
}
