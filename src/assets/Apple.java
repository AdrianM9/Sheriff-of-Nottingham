package assets;

/**
 * Class using the Singleton Pattern for instantiating an Apple asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Apple extends LegalAsset {
    private static Apple instance = new Apple();
    private static final int PROFIT = 2;
    private static final int PENALTY = 2;
    private static final int KING_BONUS = 20;
    private static final int QUEEN_BONUS = 10;

    private Apple() {
        profit = PROFIT;
        penalty = PENALTY;
        kingBonus = KING_BONUS;
        queenBonus = QUEEN_BONUS;
    }

    /**
     * Returns an object of type {@code Apple} representing the only one instance of this class
     * type.
     * @return the only one instance of the type Apple
     */
    public static Apple getInstance() {
        return instance;
    }
}
