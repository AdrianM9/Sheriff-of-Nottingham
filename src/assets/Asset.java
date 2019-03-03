package assets;

/**
 * Abstract class created for instantiating an asset. An object of type {@code Asset} contains
 * fields for the general properties of every asset in the game.
 * @author Teodor-Adrian Mirea, 323CA
 */
public abstract class Asset {
    /** The profit of the asset. */
    protected int profit;
    /** The penalty of the asset. */
    protected int penalty;
    /** A boolean variable representing if an asset is legal or illegal. */
    protected boolean illegalAsset;

    /** Returns the {@link #profit} field.
     * @return the profit value of the asset
     */
    public final int getProfit() {
        return profit;
    }

    /** Returns the {@link #penalty} field.
     * @return the penalty value of the asset
     */
    public final int getPenalty() {
        return penalty;
    }

    /**
     * Method for testing if an asset is legal or illegal.
     * @return the illegalAsset value of the asset
     */
    public final boolean isIllegal() {
        return illegalAsset;
    }

}
