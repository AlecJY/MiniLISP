package com.alebit.minilisp.exceptions;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

public class UnexpectedTypeException extends RuntimeException {
    public UnexpectedTypeException(Class need, Class found, Token token) {
        String needName = need.getSimpleName();
        String foundName = found.getSimpleName();
        if (need == Integer.class) {
            needName = "Number";
        }
        if (found == Integer.class) {
            foundName = "Number";
        }
        System.err.println("line " + token.getLine() + ":" + token.getCharPositionInLine() + " Unexpected type " + foundName + ". Expect " + needName);
        System.exit(1);
    }
}
