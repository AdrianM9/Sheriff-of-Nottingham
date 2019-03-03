package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gameplay.Game;

/**
 * The main class of the project. It reads the input and generates an {@link GameInput} object which
 * has one list containing id's of assets in deck and another one containing the strategies used by
 * players.
 * @author POO team (ACS)
 * @author Teodor-Adrian Mirea, 323CA
 */
public final class Main {

    private Main() {
    }

    //Inner class used for reading the input.
    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    /**
     * Main method of the class and the starting point of the project. Calls the specific methods
     * for the {@link Game} object in order to play the game for the given input.
     * @param args arguments taken from input
     */
    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        Game game = new Game(gameInput);

        game.initialize();
        game.playRounds();
        game.finish();
    }
}
