package players;

import assets.Asset;

import java.util.List;
import java.util.TreeMap;

/**
 * Class created for instantiating a player which uses the wizard strategy.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Wizard extends Player {
    @Override
    protected List<Asset> setAssetsToMove(final TreeMap<Integer, Asset> frequenciesOfLegalAssets,
                                          final TreeMap<Integer, Asset> profitsOfIllegalAssets) {
        // The wizard player doesn't risk, so he plays the basic strategy which assures him the
        // lowest chances of paying penalties.
        return basicAssetsToMove(frequenciesOfLegalAssets, profitsOfIllegalAssets);
    }

    @Override
    public void inspect(final List<Player> merchants) {
        for (Player merchant : merchants) {
            // The wizard player controls every player which offered bribe because they are suspect
            // and he thinks that the players who put over 2 assets in bag are lying.
            if (merchant.bribe > 0 || merchant.assetsInBag.size() > 2) {
                controlBag(merchant);
            }
        }
    }

    @Override
    public void printPoints() {
        System.out.println("WIZARD: " + points);
    }
}
