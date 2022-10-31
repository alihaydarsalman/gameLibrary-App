package com.turkcell.gamelibrary.system.core.responseTypes;

public class SuccessResult extends Result{

    public SuccessResult(String message) {
        super(true, message);

    }

    public SuccessResult() {
        super(true);
    }
}
