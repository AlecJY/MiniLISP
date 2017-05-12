lexer grammar LISPLexer;

@header {
   package com.alebit.minilisp;
}

LPARAM  : '(';
RPARAM  : ')';

// Numerical

PLUS    : '+';
MINUS   : '-';
MULTIPLY: '*';
DIVIDE  : '/';
MODULUS : 'mod';
GREATER : '>';
SMALLER : '<';
EQUAL   : '=';

// Logical

AND : 'and';
OR  : 'or';
NOT : 'not';

// Other
DEFINE  : 'define';
FUNC    : 'fun';
IF      : 'if';


PRINTNUM    : 'print-num';
PRINTBOOL   : 'print-bool';

NUMBER      : '0'
            | '1'..'9'  DIGIT*
            | '-' '1'..'9'  DIGIT*
            ;

ID          : LETTER (LETTER | DIGIT | '-')*;

BOOL        : TRUE
            | FALSE
            ;

TRUE        : '#t';
FALSE       : '#f';

SEPARATOR   : [ \t\n\r] -> skip;
LETTER      : [a-z];
DIGIT       : [0-9];