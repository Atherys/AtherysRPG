package com.atherys.rpg.api.skill;

import com.google.gson.annotations.Expose;

/**
 * Represents a 4-size combination of mouse buttons, pressed one after another in order.
 */
public final class MouseButtonCombo {

    public static final MouseButtonCombo EMPTY = new MouseButtonCombo();

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

    @Expose private MouseButton[] combo = new MouseButton[0];

    public MouseButtonCombo(MouseButton... combo) {
        this.combo = combo;
    }

    public MouseButtonCombo(MouseButton button1, MouseButton button2, MouseButton button3, MouseButton button4) {
        this.combo = new MouseButton[4];
        combo[0] = button1;
        combo[1] = button2;
        combo[2] = button3;
        combo[3] = button4;
    }

    public MouseButton[] getCombo() {
        return combo;
    }

    public boolean isEmpty() {
        return combo.length == 0;
    }
}
