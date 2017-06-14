package com.alebit.minilisp.exceptions;

import org.antlr.v4.runtime.Token;

public class VariableNotFoundException extends RuntimeException {
    public VariableNotFoundException(String name, Token token) {
        // System.err.println("line " + token.getLine() + ":" + token.getCharPositionInLine() + " variable '" + name + "' not found");
        System.out.println("error");
        System.exit(1);
    }
}
