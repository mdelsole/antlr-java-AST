
import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.lang.Math;


// Our custom visitor extends the base visitor
public class ASTVisitor extends pascalBaseVisitor<Data>{


    /*************** Our old @members, modified to utilize generic types ***************/


    HashMap<String, Data> globalVars = new HashMap<String, Data>();
    // Our "scope"; each level of the stack will store the local variables for that "scope"
    Stack<HashMap<String, Data>> localVars = new Stack<HashMap<String, Data>>();


    /*************** Implementing the abstract methods of pascalBaseVisitor ***************/


    @Override
    public Data visitStart(pascalParser.StartContext ctx) {
        // Top level scope holds the global variables
        localVars.push(globalVars);
        return visitChildren(ctx);
    }

    /***** Variable block *****/

    // Where vars are just declared, but not initialized with a value
    @Override
    public Data visitVarDeclaration(pascalParser.VarDeclarationContext ctx) {
        // Get the variable name list
        String varName = ctx.varNameList().getText();

        // Handle case where multiple variable names are passed in
        String[] vNames;
        if (varName.contains(",")) {
            vNames = parseString(varName);
        }
        else{
            vNames = null;
        }

        // Determine the type of the Data, set to default value
        Data value = null;
        if (ctx.type.getType() == pascalParser.BOOLEANTYPE){
            value = new Data(false);
        }
        else if (ctx.type.getType() == pascalParser.REALTYPE){
            value = new Data(0.0);
        }

        // Place the variable name and its data value into this scope's variables (i.e. localVars)
        if (vNames == null) {
            // peek() gets us the top element, i.e. current scope
            localVars.peek().put(varName, value);
            System.out.println("Declared Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                System.out.println("Declared Name: " + vNames[i] + ", Value: " + value);
            }
        }

        System.out.println("Local declared variables: " + localVars.peek());


        return value;
    }

    // Where vars are both declared AND initialized
    @Override
    public Data visitVarInitialization(pascalParser.VarInitializationContext ctx) {
        // Get the variable name list
        String varName = ctx.varNameList().getText();

        // Handle case where multiple variable names are passed in
        String[] vNames;
        if (varName.contains(",")) {
            vNames = parseString(varName);
        }
        else{
            vNames = null;
        }

        // Retrieve the value to set these variable(s) equal to
        Data value = this.visit(ctx.varValue());

        // Place the variable name and its data value into this scope's variables (i.e. localVars)
        if (vNames == null) {
            // peek() gets us the top element, i.e. current scope
            localVars.peek().put(varName, value);
            System.out.println("Initialized Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                System.out.println("Initialized Name: " + vNames[i] + ", Value: " + value);
            }
        }

        System.out.println("Local initialized variables: " + localVars.peek());

        return value;
    }

    /***** Math expressions *****/

    // Handles *, /, +, -
    @Override
    public Data visitArithExpr(pascalParser.ArithExprContext ctx) {
        Data leftElement = this.visit(ctx.el);
        Data rightElement = this.visit(ctx.er);
        Data result = null;
        System.out.println("le: " + leftElement);
        System.out.println("re: " + rightElement);


        if (ctx.op.getType() == pascalParser.MULT){
            result = new Data(leftElement.toDouble() * rightElement.toDouble());
        }
        else if (ctx.op.getType() == pascalParser.DIV){
            result = new Data(leftElement.toDouble() / rightElement.toDouble());
        }
        else if (ctx.op.getType() == pascalParser.PLUS){
            result = new Data(leftElement.toDouble() + rightElement.toDouble());
        }
        else if (ctx.op.getType() == pascalParser.MINUS){
            result = new Data(leftElement.toDouble() - rightElement.toDouble());
        }
        else{
            System.out.println("Unknown operation");
        }
        System.out.println("Result: " + result);

        return result;
    }

    // Handles sqrt, sin, cos, ln, exp
    @Override
    public Data visitSpclExpr(pascalParser.SpclExprContext ctx) {
        String expression = ctx.expr.getText();
        Data contents = this.visit(ctx.contents);
        Data result = null;
        System.out.println("Contents: " + contents);

        if (expression.equals("sin")){
            //result = new Data(Math.sin(contents.toDouble()));
        }
        return result;
    }

    // Base case, returning double value itself
    @Override
    public Data visitRealElement(pascalParser.RealElementContext ctx) {
        Double elementValue = Double.valueOf(ctx.getText());
        Data element = new Data(elementValue);

        return element;
    }

    /***** Boolean expressions *****/

    // Base case, returning boolean value itself
    @Override
    public Data visitBoolElement(pascalParser.BoolElementContext ctx) {
        Boolean elementValue = Boolean.valueOf(ctx.getText());
        Data element = new Data(elementValue);

        return element;
    }

    /***** Utility methods *****/


    // Separate the variable name list into usable names
    private String [] parseString(String variable_list){
        return variable_list.split("\\s*,\\s*");
    }
}
