# antlr-java-AST
Implementation of a custom programming language in antlr + java using abstract syntax trees

## About:
In this project we use a java-based "visitor pattern" (visitor for short) to traverse the abstract syntax tree generated by our ANTLR grammar.

## Running:
1. Generate the proper java files from ANTLR grammar. Run the following command with the ```-visitor``` flag, to generate a visitor in addition to the rest of the files:
```
antlr4 pascal.g4 -visitor
```
2. Compile generated java files:
```
javac pascal*.java
```
## Notes

### What is a visitor?

A visitor is simply the action implementer for our parse tree. It has a method for each branch, containing what actions to perform for that branch.

Our base visitor, generated by the command ```-visitor``` above, is an *interface*; it is generated with empty methods for handling each branch. It's up to us to implement what actions they do in our ASTVisitor file.

### What is ```ctx```?

The ctx argument is an instance of a specific class context for the node that we are entering/exiting. 

Our visitor visits each branch of the parse tree. Upon reaching a branch, the method for handling that branch takes in the branch as a *context*, named ```ctx``` for short. This ```ctx``` is how we access the branch's elements and do actions on it.

Let's take the branch ```varDeclaration``` for example. As our visitor goes through the parse tree, it will come across the branch ```varDeclaration``` where we declare our variables. This will trigger the visitor's ```visitVarDeclaration``` method. The input parameter to this method is ```pascalParser.VarDeclarationContext ctx```, which is our current *context*.

What is meant by context? It means all of the parse elements of the current branch. It defines what we can access. In the case of the ```varDeclaration``` branch, this could be ```vNameList()```, ```BOOLEANTYPE()```, or even ```COLON()```. 

By accessing the elements of our current *context*, we can perform actions on them. For example, I could do ```ctx.vNameList().getText()``` to get the text of the variable names.

## To-Do:

- [x] Implement visitor
- [x] Program name
- [x] Variable declarations
- [x] Main program block
- [x] Arithmetic expressions (with variables)
- [ ] Negative numbers
- [x] Boolean expressions (with variables)
- [x] If-then-else statements
- [ ] Case statements
- [x] Special math expressions (sqrt, sin, cos, ln, exp)
- [ ] Readln
- [x] Writeln
- [x] Variable assignment
- [x] Static scoping
- [x] Global variables vs. local variables
- [x] Scope chaining and position tracking
- [x] While loops
- [ ] For loops
- [ ] Break/continue in loops
- [ ] User defined functions
