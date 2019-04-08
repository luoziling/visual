package com.wzb.visual.exception;

public class SatsukiWebException extends Exception {
    public final SatsukiWebError satsukiWebError;

    public SatsukiWebException(SatsukiWebError satsukiWebError) {
        this.satsukiWebError = satsukiWebError;
    }
}
