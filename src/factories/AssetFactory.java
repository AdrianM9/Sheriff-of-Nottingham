package factories;

import assets.Asset;
import assets.AssetsIds;
import assets.Apple;
import assets.Barrel;
import assets.Bread;
import assets.Cheese;
import assets.Chicken;
import assets.Pepper;
import assets.Silk;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which implements the IFactory interface and uses the Factory and Singleton Patterns for
 * creating objects of type {@link Asset}.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class AssetFactory implements IFactory<Asset, AssetsIds> {
    private static AssetFactory instance = new AssetFactory();

    /**
     * Returns an object of type {@code AssetFactory} representing the only one instance of this
     * class type.
     * @return the only one instance of the type AssetFactory
     */
    public static AssetFactory getInstance() {
        return instance;
    }

    /**
     * Returns an object of type {@link Asset}.
     * @param id parameter which will be used for choosing which object to create and return
     * @return an {@link Asset} object based on the id of asset given as parameter
     */
    @Override
    public Asset createElement(final AssetsIds id) {
        switch (id) {
            case APPLE_ID: return Apple.getInstance();
            case CHEESE_ID: return Cheese.getInstance();
            case BREAD_ID: return Bread.getInstance();
            case CHICKEN_ID: return Chicken.getInstance();
            case SILK_ID: return Silk.getInstance();
            case PEPPER_ID: return Pepper.getInstance();
            case BARREL_ID: return Barrel.getInstance();
            default: return null;
        }
    }

    /**
     * Creates a list of {@link Asset} objects.
     * <p>
     * The method uses the {@link #createElement(AssetsIds)} method for adding {@code Asset} objects
     * in the list.
     * </p>
     * @param assetsIds - list containing the ids of the assets
     * @return a list containing objects of type {@link Asset}
     */
    public List<Asset> createListAssets(final List<Integer> assetsIds) {
        List<Asset> assets = new ArrayList<Asset>();

        for (int i = 0; i < assetsIds.size(); i++) {
            assets.add(createElement(AssetsIds.getValue(assetsIds.get(i))));
        }

        return assets;
    }
}
