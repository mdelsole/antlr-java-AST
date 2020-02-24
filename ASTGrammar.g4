grammar ASTGrammar;


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
arith_expr returns[double d]:
       '(' e=arith_expr ')' { $d = $e.d; }
       | el=arith_expr '*' er=arith_expr { $d = $el.d * $er.d; }
       | el=arith_expr '/' er=arith_expr { $d = $el.d / $er.d; }
       | el=arith_expr '+' er=arith_expr { $d = $el.d + $er.d; }
       | el=arith_expr '-' er=arith_expr { $d = $el.d - $er.d; }
       // Base
       | REAL { $d = Double.valueOf($REAL.text); }
       // Variable names
       | NAME { $d = arithVars.get($NAME.text); }
       // Special expressions
       | spcl_math_expr {$d = $spcl_math_expr.d; }
       ;

/***** Boolean/logical Expressions *****/

bool_expr returns [boolean b]:
    el=bool_expr AND er=bool_expr { $b = ((($el.b != false ? true : false) && ($er.b != false ? true : false)) ? true : false); }
    | el=bool_expr OR er=bool_expr { $b = ((($el.b != false ? true : false) || ($er.b != false ? true : false)) ? true : false); }
    | NOT el=bool_expr { $b = (!($el.b != false ? true : false) ? true : false); }
    // Base
    | BOOL { $b = Boolean.parseBoolean($BOOL.text); }
    // Variable names
    | NAME { $b = boolVars.get($NAME.text); }
    ;

/***** Decision Making (if-then-else, case) *****/

if_block: IF condition THEN statement (ELSE IF bool_expr THEN statement)* (ELSE statement)?;

condition returns [boolean b]:
    el=bool_expr '=' er=bool_expr { $b = (($el.b == $er.b) ? true : false); }
    | el=bool_expr NOT '=' er=bool_expr { $b = (($el.b == $er.b) ? true : false); }
    | BOOL { $b = Boolean.parseBoolean($BOOL.text);}
    | NAME { $b = Boolean.parseBoolean($NAME.text); }
    ;

// TODO: Case

case_statement: CASE condition OF statement_list  SMCOLN  (SMCOLN ELSE statements)? END;

/***** Special Expressions: Readln, Writeln, sqrt, sin, cos, ln, exp *****/

// Readln, Writeln
readln: READLN '('NAME')' SMCOLN{
    if( scanner.hasNextDouble()){
        double dou = scanner.nextDouble();
        arithVars.put($NAME.text, dou);
    }
    else if (scanner.hasNextBoolean()){
        boolean bo = scanner.nextBoolean();
        boolVars.put($NAME.text, bo);
    }
    else{
        System.out.println("NOT VALID");
    }
};

writeln: WRITELN '('spcl_math_expr')' SMCOLN {
    if (arithVars.containsKey($spcl_math_expr.text.substring($spcl_math_expr.text.length()-2,$spcl_math_expr.text.length()-1))){
        //System.out.println($spcl_math_expr.text.substring(0,4));
        if ( $spcl_math_expr.text.substring(0,4).equals("sqrt") )
            System.out.println($spcl_math_expr.d);
        else if( $spcl_math_expr.text.substring(0,3).equals("sin") )
            System.out.println($spcl_math_expr.d);
        else if( $spcl_math_expr.text.substring(0,3).equals("cos") )
            System.out.println($spcl_math_expr.d);
        else if( $spcl_math_expr.text.substring(0,2).equals("ln") )
            System.out.println($spcl_math_expr.d);
        else if( $spcl_math_expr.text.substring(0,3).equals("exp"))
            System.out.println($spcl_math_expr.d);
    }
} | WRITELN '('NAME')' SMCOLN {
      if (arithVars.containsKey($NAME.text)){
          System.out.println(arithVars.get($NAME.text));
      }
      else if (boolVars.containsKey($NAME.text)){
          System.out.println(boolVars.get($NAME.text));
      }
      else{
          System.out.println($NAME.text);
          System.out.println("DODDO");
      }
} | WRITELN '('REAL')' SMCOLN {
       System.out.println($REAL.text);
} | WRITELN '('BOOL')' SMCOLN {
       System.out.println($BOOL.text);
};

// For sqrt, sin, cos, ln, and exp
spcl_math_expr returns [double d]:
    expr=SQRT '(' contents=arith_expr ')' { $d = Math.sqrt($contents.d); }
    | expr=SIN '(' contents=arith_expr ')' { $d = Math.sin($contents.d); }
    | expr=COS '(' contents=arith_expr ')' { $d = Math.cos($contents.d); }
    | expr=LN '(' contents=arith_expr ')' { $d = Math.log($contents.d); }
    | expr=EXP '(' contents=arith_expr ')' { $d = Math.exp($contents.d); }
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