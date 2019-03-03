package assets;

/**
 * This enum contains enum constants used for associating every asset the corresponding id.
 * @author Teodor-Adrian Mirea, 323CA
 */
public enum AssetsIds {
    /**
     * The singleton instance for an unrecognized asset. This has the numeric value of {@code -1}.
     * <p>
     * This enum constant is only used when returning an instance if the id specified is not equal
     * with the id of another constant in the enum.
     * </p>
     */
    DEFAULT_ID(-1),
    /**
     * The singleton instance for the Apple id.
     * This has the numeric value of {@code 0}.
     */
    APPLE_ID(0),
    /**
     * The singleton instance for the Cheese id.
     * This has the numeric value of {@code 1}.
     */
    CHEESE_ID(1),
    /**
     * The singleton instance for the Bread id.
     * This has the numeric value of {@code 2}.
     */
    BREAD_ID(2),
    /**
     * The singleton instance for the Chicken id.
     * This has the numeric value of {@code 3}.
     */
    CHICKEN_ID(3),
    /**
     * The singleton instance for the Silk id.
     * This has the numeric value of {@code 10}.
     */
    SILK_ID(10),
    /**
     * The singleton instance for the Pepper id.
     * This has the numeric value of {@code 11}.
     */
    PEPPER_ID(11),
    /**
     * The singleton instance for the Barrel id.
     * This has the numeric value of {@code 12}.
     */
    BARREL_ID(12);


    private int id;

    AssetsIds(final int id) {
        this.id = id;
    }

    /**
     * Returns the id field for an enum constant of this type.
     * @return the id field for the current enum constant
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the enum constant of this type with the specified id.
     * @param id the id of the enum constant to be returned.
     * @return the enum constant with the specified id
     */
    public static AssetsIds getValue(final int id) {
        for (AssetsIds iterator : AssetsIds.values()) {
            if (iterator.id == id) {
                return iterator;
            }
        }
        return DEFAULT_ID;
    }
}
