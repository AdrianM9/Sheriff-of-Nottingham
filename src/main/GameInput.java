package main;

import java.util.List;

/**
 * The class offers instances which contains a list with assets ids and other list with players.
 * @author POO team (ACS)
 */
public final class GameInput {
    // The list containing the deck of assets ids.
    private final List<Integer> mAssetOrder;
    // The list containing the strategy of every player.
    private final List<String> mPlayersOrder;

    /**
     * Constructs a newly allocated {@code GameInput} object and sets the initial values for all
     * fields.
     */
    public GameInput() {
        mAssetOrder = null;
        mPlayersOrder = null;
    }

    /**
     * Constructs a newly allocated {@code GameInput} object and sets the specified values for all
     * fields.
     * @param assets list of assets ids to be setted as part of the new object
     * @param players list of strategy of every player to be setted as part of the new object
     */
    public GameInput(final List<Integer> assets, final List<String> players) {
        mAssetOrder = assets;
        mPlayersOrder = players;
    }

    /**
     * Returns the assets ids list.
     * @return the list containing the assets ids
     */
    public List<Integer> getAssetIds() {
        return mAssetOrder;
    }

    /**
     * Returns the players list (their strategies of playing).
     * @return the list containing the strategy for every player
     */
    public List<String> getPlayerNames() {
        return mPlayersOrder;
    }

    public boolean isValidInput() {
        boolean membersInstantiated = mAssetOrder != null && mPlayersOrder != null;
        boolean membersNotEmpty = mAssetOrder.size() > 0 && mPlayersOrder.size() > 0;

        return membersInstantiated && membersNotEmpty;
    }
}
