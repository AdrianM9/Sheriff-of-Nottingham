package assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Class using the Singleton Pattern for instantiating a Barrel asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Barrel extends IllegalAsset {
    private static Barrel instance = new Barrel();
    private static final int PROFIT = 7;
    private static final int PENALTY = 4;
    private static final int NUMBER_OF_BONUS_ASSETS = 2;

    private Barrel() {
        profit = PROFIT;
        penalty = PENALTY;
        bonusAssets = createBonusAssets();
    }

    /**
     * Returns an object of type {@code Barrel} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Barrel
     */
    public static Barrel getInstance() {
        return instance;
    }

    // Method used for creating the list containing the legal assets taken for owning the current
    // illegal asset. In this case, there are 2 (=NUMBER_OF_BONUS_ASSETS) Bread assets.
    private static List<LegalAsset> createBonusAssets() {
        List<LegalAsset> bonusAssets = new ArrayList<LegalAsset>();

        for (int i = 0; i < NUMBER_OF_BONUS_ASSETS; i++) {
            bonusAssets.add(Bread.getInstance());
        }

        return bonusAssets;
    }
}
