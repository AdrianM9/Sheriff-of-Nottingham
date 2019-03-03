package assets;

import factories.AssetFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class created for instantiating an legal asset. An object of type {@code LegalAsset}
 * contains fields for the specific properties of a legal asset.
 * @author Teodor-Adrian Mirea, 323CA
 */
public abstract class LegalAsset extends Asset {
    /** The King's Bonus of the asset. */
    protected int kingBonus;
    /** The Queen's Bonus of the asset. */
    protected int queenBonus;

    /**
     * Constructs a newly allocated {@code LegalAsset} object by constructing first an {@code Asset}
     * object and assigning the false value for the {@link #illegalAsset} value.
     */
    protected LegalAsset() {
        illegalAsset = false;
    }

    /**
     * Getter for the {@link #kingBonus} field.
     * @return the King's Bonus of the asset
     */
    public final int getKingBonus() {
        return kingBonus;
    }

    /**
     * Getter for the {@link #queenBonus} field.
     * @return the Queen's Bonus of the asset
     */
    public final int getQueenBonus() {
        return queenBonus;
    }

    /**
     * Method which returns an object of type {@code List<Asset>} containing an instance of
     * every legal asset.
     * @return a list containing instances of every legal asset
     */
    public static List<Asset> getLegalAssets() {
        List<Asset> legalAssets = new ArrayList<Asset>();
        AssetFactory assetFactory = AssetFactory.getInstance();

        for (int id = AssetsIds.APPLE_ID.getId(); id <= AssetsIds.CHICKEN_ID.getId(); id++) {
            legalAssets.add(assetFactory.createElement(AssetsIds.getValue(id)));
        }

        return legalAssets;
    }
}
