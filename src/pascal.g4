grammar pascal;

// See https://stackoverflow.com/questions/7527232/antlr-header-parser-superclass-option-and-basic-file-io-java
@header {
    import java.lang.Math;
    import java.util.HashMap;
    import java.util.Scanner;
    import java.util.Map;
}

@members {
    Map<String, Double> arithVars = new HashMap<String, Double>();
    Map<String, Boolean> boolVars = new HashMap<String, Boolean>();
    Scanner scanner = new Scanner(System.in);
    // Separate the variable name list into usable names
    public String [] parseString(String variable_list){
        String[] values = variable_list.split("\\s*,\\s*");
        return values;
    }
}

/*************** Parser rules (putting input into tree) ***************/


/***** Program *****/

start: program EOF;

program: PROGRAM NAME SMCOLN (variableBlock)? programBlock;

/***** Variables *****/

variableBlock: VAR varDec+;
varDec:
    varNameList COLON type=(BOOLEANTYPE | REALTYPE) '=' varValue SMCOLN         #varInitialization
    | varNameList COLON type=(BOOLEANTYPE | REALTYPE) SMCOLN                    #varDeclaration
    ;

varValue: (mathExpr | logicExpr);
varNameList: NAME (COMMA NAME)*;

varAssignment: varNameList ':=' varValue SMCOLN;

/***** Main program block *****/

programBlock: BEGIN statementList END SMCOLN;
statementList: statement | statement SMCOLN statementList;
statement: (programBlock | ifBlock | caseStatement | writeln | readln | varAssignment)+;
statements: statement (SMCOLN statement)*;

/***** Basic arithmetic expressions with variables *****/

mathExpr:
   el=mathExpr op=(MULT | DIV | PLUS | MINUS) er=mathExpr                       #arithExpr
   | expr=(SQRT|SIN|COS|LN|EXP) '(' contents=mathExpr ')'                       #arithSpclExpr
   // Base
   | mathElement                                                                #arithElement
   ;

mathElement:
   '(' mathExpr ')'                                                             #arithExprElement
   | REAL                                                                       #arithValueElement
   | NAME                                                                       #arithVarElement;

/***** Boolean/logical expressions with variables *****/

logicExpr:
    el=logicExpr op=(AND | OR) er=logicExpr                                     #boolExpr
    | NOT el=logicExpr                                                          #boolNotExpr
    | el=mathExpr op=(EQU | NEQU | GRT | GRTEQ | LST | LSTEQ) er=mathExpr       #boolArithEquality
    // Base
    | logicElement                                                              #boolElement
    ;

logicElement:
   '(' logicExpr ')'                                                            #boolExprElement
   | BOOL                                                                       #boolValueElement
   | NAME                                                                       #boolVarElement;


/***** Decision Making (if-then-else, case) *****/

ifBlock: IF conditional (ELSE IF conditional)* (ELSE statement)?;

conditional:
    logicExpr THEN statement;

// TODO: Case

caseStatement: CASE logicExpr OF statementList  SMCOLN  (SMCOLN ELSE statements)? END;

/***** Special Expressions: Readln, Writeln, sqrt, sin, cos, ln, exp *****/

// Readln, Writeln
readln: READLN '('NAME')' SMCOLN;

writeln:
         WRITELN '('NAME')' SMCOLN
         | WRITELN '('REAL')' SMCOLN
         | WRITELN '('BOOL')' SMCOLN
         ;


//whileLoop: WHILE '('? condition ')' DO loopBlock;


/*************** Lexer rules (breaking up the input). Must be uppercase names! ***************/


/*
Ordering rule is: Most specific to least specific. DO NOT BREAK THIS RULE! Very bad things will happen if you do.
*/

PROGRAM: 'program';
VAR: 'VAR';
VASSIGN: ':=';
BEGIN: 'BEGIN';
END: 'END';
BOOLEANTYPE: 'boolean';
REALTYPE: 'real';

READLN: 'readln';
WRITELN: 'writeln';


COMMENT1: '(*' .*? '*)' -> skip;
COMMENT2: '{' .*? '}' -> skip;

BOOL: 'true' | 'false';
PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';

EQU: '=';
NEQU: '<>';
GRT: '>';
GRTEQ: '>=';
LST: '<';
LSTEQ: '<=';

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

REAL: [0-9]+('.'[0-9]+)?;
INT: [0-9]+;

NAME: [a-zA-Z][a-zA-Z0-9_]*;

// Could mess things up?
SPACE : [ \n\r\t] -> skip;
SMCOLN: ';';
COMMA: ',';
COLON: ':';

TEXT: .;