package com.alebit.minilisp.exceptions;

import org.antlr.v4.runtime.tree.ParseTree;

public class UnexpectedTypeException extends RuntimeException {
    public UnexpectedTypeException(ParseTree ctx,  String message) {

    }
}
