package assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Class using the Singleton Pattern for instantiating a Silk asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Silk extends IllegalAsset {
    private static Silk instance = new Silk();
    private static final int PROFIT = 9;
    private static final int PENALTY = 4;
    private static final int NUMBER_OF_BONUS_ASSETS = 3;

    private Silk() {
        profit = PROFIT;
        penalty = PENALTY;
        bonusAssets = createBonusAssets();
    }

    /**
     * Returns an object of type {@code Silk} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Silk
     */
    public static Silk getInstance() {
        return instance;
    }

    // Method used for creating the list containing the legal assets taken for owning the current
    // illegal asset. In this case, there are 3 (=NUMBER_OF_BONUS_ASSETS) Cheese assets.
    private static List<LegalAsset> createBonusAssets() {
        List<LegalAsset> bonusAssets = new ArrayList<LegalAsset>();

        for (int i = 0; i < NUMBER_OF_BONUS_ASSETS; i++) {
            bonusAssets.add(Cheese.getInstance());
        }

        return bonusAssets;
    }
}
