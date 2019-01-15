package com.atherys.rpg.api.exception;

import org.spongepowered.api.text.Text;

public class CastException extends Exception {

    private Text error;

    public CastException(Text error) {
        this.error = error;
    }

}
