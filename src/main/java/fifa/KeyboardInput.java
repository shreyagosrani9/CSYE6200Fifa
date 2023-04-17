package fifa;

import javafx.scene.input.KeyCode;

public class KeyboardInput {

    private Player[] players = new Player[3];

    private KeyCode[][] keys = new KeyCode[3][5];

    private Logic logic;

    public KeyboardInput(Logic logic, Player[] players) {
        this.logic = logic;
        this.players = players;
        for (int i = 0; i < 3; i++) {
            fillKeyCodeArray(i); 
        }
    }

    public void setInputOnKeyPressed(KeyCode code) {
        for (int i = 0; i < 3; i++) {
            Player player = players[i]; 
            // Removed redundant array access

            player.none = true;

            if (code == keys[i][0]) { // UP
                player.goNorth = true;
                player.none = false;
            }

            if (code == keys[i][1]) { // DOWN
                player.goSouth = true;
                player.none = false;
            }

            if (code == keys[i][2]) { // LEFT
                player.goWest = true;
                player.none = false;
            }

            if (code == keys[i][3]) { // RIGHT
                player.goEast = true;
                player.none = false;
            }

            if (code == keys[i][4]) { // SHOOT
                player.shooting = true;
                player.none = false;
            }

            if (code == KeyCode.P) {
                logic.displayPause();
            }
        }
    }

    public void setInputOnKeyReleased(KeyCode code) {
        for (int i = 0; i < 3; i++) {
            Player player = players[i]; 
            // Removed redundant array access

            player.none = true;

            if (code == keys[i][0]) { // UP
                player.goNorth = false;
            }

            if (code == keys[i][1]) { // DOWN
                player.goSouth = false;
            }

            if (code == keys[i][2]) { // LEFT
                player.goWest = false;
            }

            if (code == keys[i][3]) { // RIGHT
                player.goEast = false;
            }

            if (code == keys[i][4]) { // SHOOT
                player.shooting = false;
            }
        }
    }

    private void fillKeyCodeArray(int i) { 
    	// Changed method name from "fillKeycodeArray" to "fillKeyCodeArray"
        switch (i) {
            case 0:
                keys[i][0] = KeyCode.UP;
                keys[i][1] = KeyCode.DOWN;
                keys[i][2] = KeyCode.LEFT;
                keys[i][3] = KeyCode.RIGHT;
                keys[i][4] = KeyCode.ENTER;
                break;
            case 1:
                keys[i][0] = KeyCode.W;
                keys[i][1] = KeyCode.S;
                keys[i][2] = KeyCode.A;
                keys[i][3] = KeyCode.D;
                keys[i][4] = KeyCode.CONTROL;
                break;
            case 2:
                keys[i][0] = KeyCode.I;
                keys[i][1] = KeyCode.K;
                keys[i][2] = KeyCode.J;
                keys[i][3] = KeyCode.L;
                keys[i][4] = KeyCode.SPACE;
                break;
        }
    }
}
