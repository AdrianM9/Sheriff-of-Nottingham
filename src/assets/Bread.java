package assets;

/**
 * Class using the Singleton Pattern for instantiating a Bread asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Bread extends LegalAsset {
    private static Bread instance = new Bread();
    private static final int PROFIT = 4;
    private static final int PENALTY = 2;
    private static final int KING_BONUS = 15;
    private static final int QUEEN_BONUS = 10;

    private Bread() {
        profit = PROFIT;
        penalty = PENALTY;
        kingBonus = KING_BONUS;
        queenBonus = QUEEN_BONUS;
    }

    /**
     * Returns an object of type {@code Bread} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Bread
     */
    public static Bread getInstance() {
        return instance;
    }
}
