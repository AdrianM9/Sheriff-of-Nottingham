package assets;

import java.util.ArrayList;
import java.util.List;

/**
 * Class using the Singleton Pattern for instantiating a Pepper asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Pepper extends IllegalAsset {
    private static Pepper instance = new Pepper();
    private static final int PROFIT = 8;
    private static final int PENALTY = 4;
    private static final int NUMBER_OF_BONUS_ASSETS = 2;

    private Pepper() {
        profit = PROFIT;
        penalty = PENALTY;
        bonusAssets = createBonusAssets();
    }

    /**
     * Returns an object of type {@code Pepper} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Pepper
     */
    public static Pepper getInstance() {
        return instance;
    }

    // Method used for creating the list containing the legal assets taken for owning the current
    // illegal asset. In this case, there are 2 (=NUMBER_OF_BONUS_ASSETS) Chicken assets.
    private static List<LegalAsset> createBonusAssets() {
        List<LegalAsset> bonusAssets = new ArrayList<LegalAsset>();

        for (int i = 0; i < NUMBER_OF_BONUS_ASSETS; i++) {
            bonusAssets.add(Chicken.getInstance());
        }

        return bonusAssets;
    }
}
