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

exprs   : LPARAM expr RPARAM #BasicExprs
        | NUMBER #NumExprs
        | bool #BoolExprs
        | ID #VarExprs
        ;

expr    : uArgNumOp exprs exprs+ #UOpExpr
        | twoArgNumOp exprs exprs #TwoOpExpr
        | uArgLogOp exprs exprs+ #ULogExpr
        | oneArgLogOp exprs #OneLogExpr
        | PRINTNUM exprs #PNumExpr
        | PRINTBOOL exprs #PBExpr
        | FUNC LPARAM ID* RPARAM def* exprs #FuncExpr
        | LPARAM FUNC LPARAM ID* RPARAM def* exprs RPARAM exprs* #FuncWFuncCallExpr
        | ID exprs* #FuncCallExpr
        | IF exprs exprs exprs #IfExpr
        ;

def     : LPARAM DEFINE ID exprs RPARAM;

uArgNumOp   : op=PLUS
            | op=MULTIPLY
            | op=EQUAL
            ;

twoArgNumOp : op=MINUS
            | op=DIVIDE
            | op=MODULUS
            | op=GREATER
            | op=SMALLER
            ;

uArgLogOp   : op=AND
            | op=OR
            ;

oneArgLogOp : NOT;

bool    : op=TRUE
        | op=FALSE
        ;
