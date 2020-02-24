grammar pascal;


/*************** Parser rules (putting input into tree) ***************/


/***** Program *****/

program: PROGRAM NAME SMCOLN (variable)? program_block EOF;

/***** Variable declarations *****/

variable: VAR varDeclaration+;
varDeclaration: vNameList COLON VTYPE '=' BOOL SMCOLN
    | vNameList COLON VTYPE '=' REAL SMCOLN
    | vNameList COLON VTYPE '=' arith_expr SMCOLN
    | vNameList COLON VTYPE '=' bool_expr SMCOLN;

vNameList: NAME (COMMA NAME)*;

/***** Main program block *****/

program_block: BEGIN statement_list END SMCOLN;
statement_list: statement | statement SMCOLN statement_list;
statement: (program_block | if_block | case_statement | writeln | readln)+;
statements: statement (SMCOLN statement)*;


/***** Basic arithmetic expressions with variables *****/

// For 'real' (double) variables
arith_expr:
       '(' e=arith_expr ')'
       | el=arith_expr '*' er=arith_expr
       | el=arith_expr '/' er=arith_expr
       | el=arith_expr '+' er=arith_expr
       | el=arith_expr '-' er=arith_expr
       // Base
       | REAL
       // Variable names
       | NAME
       // Special expressions
       | spcl_math_expr
       ;

/***** Boolean/logical Expressions *****/

bool_expr:
    el=bool_expr AND er=bool_expr
    | el=bool_expr OR er=bool_expr
    | NOT el=bool_expr
    // Base
    | BOOL
    // Variable names
    | NAME
    ;

/***** Decision Making (if-then-else, case) *****/

if_block: IF condition THEN statement (ELSE IF bool_expr THEN statement)* (ELSE statement)?;

condition:
    el=bool_expr '=' er=bool_expr
    | el=bool_expr NOT '=' er=bool_expr
    | BOOL
    | NAME
    ;

// TODO: Case

case_statement: CASE condition OF statement_list  SMCOLN  (SMCOLN ELSE statements)? END;

/***** Special Expressions: Readln, Writeln, sqrt, sin, cos, ln, exp *****/

// Readln, Writeln
readln: READLN '('NAME')' SMCOLN;

writeln: WRITELN '('spcl_math_expr')' SMCOLN
         | WRITELN '('NAME')' SMCOLN
         | WRITELN '('REAL')' SMCOLN
         | WRITELN '('BOOL')' SMCOLN
         ;

// For sqrt, sin, cos, ln, and exp
spcl_math_expr:
    expr=SQRT '(' contents=arith_expr ')'
    | expr=SIN '(' contents=arith_expr ')'
    | expr=COS '(' contents=arith_expr ')'
    | expr=LN '(' contents=arith_expr ')'
    | expr=EXP '(' contents=arith_expr ')'
    ;


/*************** Lexer rules (breaking up the input). Must be uppercase names! ***************/


/*
Ordering rule is: Most specific to least specific. DO NOT BREAK THIS RULE! Very bad things will happen if you do.
*/

PROGRAM: 'program';
VAR: 'VAR';
VTYPE: 'boolean' | 'real';
VASSIGN: ':=';
BEGIN: 'BEGIN';
END: 'END';


READLN: 'readln';
WRITELN: 'writeln';


COMMENT1: '(*' .*? '*)' -> skip;
COMMENT2: '{' .*? '}' -> skip;

BOOL: 'true' | 'false';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';

AND : 'and' ;
OR : 'or' ;
NOT : 'not' ;


SQRT : 'sqrt' ;
EXP : 'exp';
COS : 'cos';
SIN : 'sin';
LN : 'ln';

IF : 'if';
ELSE : 'else';
THEN : 'then';
CASE : 'case';
OF : 'of';

REAL: [-]?[0-9]+('.'[0-9]+)?;
INT: [0-9]+;

NAME: [a-zA-Z][a-zA-Z0-9_]*;

// Could mess things up?
SPACE : [ \n\r\t] -> skip;
SMCOLN: ';';
COMMA: ',';
COLON: ':';

TEXT: .;