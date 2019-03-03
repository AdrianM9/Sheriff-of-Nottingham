package assets;

/**
 * Class using the Singleton Pattern for instantiating a Cheese asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Cheese extends LegalAsset {
    private static Cheese instance = new Cheese();
    private static final int PROFIT = 3;
    private static final int PENALTY = 2;
    private static final int KING_BONUS = 15;
    private static final int QUEEN_BONUS = 10;

    private Cheese() {
        profit = PROFIT;
        penalty = PENALTY;
        kingBonus = KING_BONUS;
        queenBonus = QUEEN_BONUS;
    }

    /**
     * Returns an object of type {@code Cheese} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Cheese
     */
    public static Cheese getInstance() {
        return instance;
    }
}
