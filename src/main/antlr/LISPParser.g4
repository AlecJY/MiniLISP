parser grammar LISPParser;

@header {
   package com.alebit.minilisp;
}

options {
    language = Java;
    tokenVocab = LISPLexer;
}

prog    : stat+;

stat    : exprs
        | def
        ;

exprs   : LPARAM expr RPARAM
        | NUMBER
        | BOOL
        | ID
        ;

expr    : uArgNumOp exprs exprs+
        | twoArgNumOp exprs exprs
        | uArgLogOp exprs exprs+
        | oneArgLogOp exprs
        | PRINTNUM exprs
        | PRINTBOOL exprs
        | FUNC LPARAM ID* RPARAM def* exprs
        | LPARAM FUNC LPARAM ID* RPARAM exprs RPARAM exprs*
        | ID exprs*
        | IF exprs exprs exprs
        ;

def     : LPARAM DEFINE ID exprs RPARAM;

uArgNumOp   : PLUS
            | MULTIPLY
            | EQUAL
            ;

twoArgNumOp : MINUS
            | DIVIDE
            | MODULUS
            | GREATER
            | SMALLER
            ;

uArgLogOp   : AND
            | OR
            ;

oneArgLogOp : NOT;
