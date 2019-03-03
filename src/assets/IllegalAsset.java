package assets;

import java.util.List;

/**
 * Abstract class created for instantiating an illegal asset. An object of type {@code IllegalAsset}
 * contains fields for the specific properties of an illegal asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public abstract class IllegalAsset extends Asset {
    /** The list of legal bonus assets that an illegal asset offers to the player.*/
    protected List<LegalAsset> bonusAssets;

    /**
     * Constructs a newly allocated {@code IlegalAsset} object by constructing first an {@code
     * Asset} object and assigning the true value for the {@link #illegalAsset} value.
     */
    protected IllegalAsset() {
        illegalAsset = true;
    }

    /**
     * Method which returns an object of type {@code List<Asset>} containing the legal assets that
     * the current type of illegal assets gives the player if he owns this illegal asset.
     * @return a list containing the legal bonus assets that this card gives
     */
    public final List<LegalAsset> getBonusAssets() {
        return bonusAssets;
    }
}
