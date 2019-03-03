package factories;

import players.Player;
import players.Basic;
import players.Bribed;
import players.Greedy;
import players.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which implements the IFactory interface and uses the Factory and Singleton Patterns for
 * creating objects of type {@link Player}.
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class PlayerFactory implements IFactory<Player, String> {
    private static PlayerFactory instance = new PlayerFactory();
    private static final String BASIC = "basic";
    private static final String GREEDY = "greedy";
    private static final String BRIBED = "bribed";
    private static final String WIZARD = "wizard";

    /**
     * Returns an object of type {@code PlayerFactory} representing the only one instance of this
     * class type.
     * @return the only one instance of the type PlayerFactory
     */
    public static PlayerFactory getInstance() {
        return instance;
    }

    /**
     * Creates and returns an object of type {@link Player}.
     * @param type parameter which will be used for choosing which object to create and return
     * @return an {@link Player} object based on the type of player given as parameter
     */
    @Override
    public Player createElement(final String type) {
        switch (type) {
            case BASIC: return new Basic();
            case GREEDY: return new Greedy();
            case BRIBED: return new Bribed();
            case WIZARD: return new Wizard();
            default: return null;
        }
    }

    /**
     * Creates a list of {@link Player} objects.
     * <p>
     * The method uses the {@link #createElement(String)} method for adding {@code Player} objects
     * in the list.
     * </p>
     * @param playersOrder list containing the players (actually their type of playing)
     * @return a list containing objects of type {@link Player}
     */
    public List<Player> createListPlayers(final List<String> playersOrder) {
        List<Player> players = new ArrayList<Player>();

        for (String type : playersOrder) {
            players.add(createElement(type));
        }

        return players;
    }
}
