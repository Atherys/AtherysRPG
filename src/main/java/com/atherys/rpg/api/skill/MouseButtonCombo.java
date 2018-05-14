package com.atherys.rpg.api.skill;

/**
 * Represents a 4-size combination of mouse buttons, pressed one after another in order.
 */
public final class MouseButtonCombo {

    public enum MouseButton {
        LEFT,
        RIGHT;

        /**
         * Interprets the boolean as a MouseButton:<br>
         * true is MouseButton.LEFT<br>
         * false is MouseButton.RIGHT
         *
         * @param mbtn The boolean to be interpreted
         * @return The MouseButton
         */
        public static MouseButton interpret(boolean mbtn) {
            return mbtn ? MouseButton.LEFT : MouseButton.RIGHT;
        }
    }

    private MouseButton[] combo;

}
