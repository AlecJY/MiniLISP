package com.alebit.minilisp.exceptions;

import org.antlr.v4.runtime.Token;

public class FunctionArgumentsNotMatchException extends RuntimeException {
    public FunctionArgumentsNotMatchException(int need, int found, Token token) {
        System.err.println("line " + token.getLine() + ":" + token.getCharPositionInLine() + " Function arguments it not match. Expect " + need +", found " + found);
        System.exit(1);
    }
}
