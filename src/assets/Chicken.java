package assets;

/**
 * Class using the Singleton Pattern for instantiating a Chicken asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Chicken extends LegalAsset {
    private static Chicken instance = new Chicken();
    private static final int PROFIT = 4;
    private static final int PENALTY = 2;
    private static final int KING_BONUS = 10;
    private static final int QUEEN_BONUS = 5;

    private Chicken() {
        profit = PROFIT;
        penalty = PENALTY;
        kingBonus = KING_BONUS;
        queenBonus = QUEEN_BONUS;
    }

    /**
     * Returns an object of type {@code Chicken} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Chicken
     */
    public static Chicken getInstance() {
        return instance;
    }
}
