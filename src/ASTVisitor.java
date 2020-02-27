
import java.util.HashMap;
import java.util.Stack;
import java.util.List;
import java.lang.Math;


// Our custom visitor extends the base visitor
public class ASTVisitor extends pascalBaseVisitor<Data>{


    /*************** Our old @members, modified to utilize generic types ***************/


    // Global vars will be the variables at the lowest scope level
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


    /*************** Variable block ***************/


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

    // Variable assignment using ':=' (mostly same code as above)
    @Override
    public Data visitVarAssignment(pascalParser.VarAssignmentContext ctx) {
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
            System.out.println("Assigned Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                System.out.println("Assigned Name: " + vNames[i] + ", Value: " + value);
            }
        }

        System.out.println("Local initialized variables: " + localVars.peek());

        return value;
    }

    /*************** Arithmetic expressions ***************/


    // Handle *, /, +, -
    @Override
    public Data visitArithExpr(pascalParser.ArithExprContext ctx) {
        Data leftElement = this.visit(ctx.el);
        Data rightElement = this.visit(ctx.er);
        Data result = null;

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
            System.out.println("Unknown arithmetic operation");
        }

        return result;
    }

    // Handle sqrt, sin, cos, ln, exp
    @Override
    public Data visitArithSpclExpr(pascalParser.ArithSpclExprContext ctx) {
        String expression = ctx.expr.getText();
        Data contents = this.visit(ctx.contents);
        Data result = null;
        System.out.println("Contents: " + contents);

        if (expression.equals("sqrt")){
            result = new Data(Math.sqrt(contents.toDouble()));
        }
        else if (expression.equals("sin")){
            result = new Data(Math.sin(contents.toDouble()));
        }
        else if (expression.equals("cos")){
            result = new Data(Math.cos(contents.toDouble()));
        }
        else if (expression.equals("ln")){
            result = new Data(Math.log(contents.toDouble()));
        }
        else if (expression.equals("exp")){
            result = new Data(Math.exp(contents.toDouble()));
        }
        else{
            System.out.println("Unknown special operation");
        }
        return result;
    }

    // Handle parentheses
    @Override
    public Data visitArithExprElement(pascalParser.ArithExprElementContext ctx) {
        return this.visit(ctx.mathExpr());
    }

    // Handle variables
    @Override
    public Data visitArithVarElement(pascalParser.ArithVarElementContext ctx) {
        String varName = ctx.getText();

        // Retrieve the value using the variable name as the key
        Data value = localVars.peek().get(varName);
        System.out.println("Retrieved value: " + value);

        return value;
    }

    // Base case, returning the double value itself
    @Override
    public Data visitArithValueElement(pascalParser.ArithValueElementContext ctx) {
        Double elementValue = Double.valueOf(ctx.getText());
        Data element = new Data(elementValue);

        return element;
    }


    /*************** Boolean expressions ***************/


    // Handle 'and', 'or'
    @Override
    public Data visitBoolExpr(pascalParser.BoolExprContext ctx) {
        Data leftElement = this.visit(ctx.el);
        Data rightElement = this.visit(ctx.er);

        Data result = null;

        if (ctx.op.getType() == pascalParser.AND){
            result = new Data(leftElement.toBoolean() && rightElement.toBoolean());
        }
        else if (ctx.op.getType() == pascalParser.OR){
            result = new Data(leftElement.toBoolean() || rightElement.toBoolean());
        }
        else{
            System.out.println("Unknown boolean operation");
        }

        return result;
    }

    // Handle 'not'
    @Override
    public Data visitBoolNotExpr(pascalParser.BoolNotExprContext ctx) {
        Data element = this.visit(ctx.el);
        Data result = new Data(!element.toBoolean());
        return result;
    }

    // Handle arithmetic equality
    @Override
    public Data visitBoolArithEquality(pascalParser.BoolArithEqualityContext ctx) {
        Data leftElement = this.visit(ctx.el);
        Data rightElement = this.visit(ctx.er);

        Data result = null;

        if (ctx.op.getType() == pascalParser.EQU){
            result = new Data(leftElement.toDouble().equals(rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.NEQU){
            result = new Data(!(leftElement.toDouble().equals(rightElement.toDouble())));
        }
        else if (ctx.op.getType() == pascalParser.GRT){
            result = new Data(leftElement.toDouble() >= (rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.GRTEQ){
            result = new Data(leftElement.toDouble() > (rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.LST){
            result = new Data(leftElement.toDouble() < (rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.LSTEQ){
            result = new Data(leftElement.toDouble() <= (rightElement.toDouble()));
        }
        else{
            System.out.println("Unknown arithmetic equality operation");
        }

        return result;
    }

    // Handle parentheses
    @Override
    public Data visitBoolExprElement(pascalParser.BoolExprElementContext ctx) {
        return this.visit(ctx.logicExpr());
    }

    // Handle variables
    @Override
    public Data visitBoolVarElement(pascalParser.BoolVarElementContext ctx) {
        String varName = ctx.getText();

        // Retrieve the value using the variable name as the key
        Data value = localVars.peek().get(varName);
        System.out.println("Retrieved value: " + value);

        return value;
    }

    // Base case, returning the boolean value itself
    @Override
    public Data visitBoolValueElement(pascalParser.BoolValueElementContext ctx) {
        Boolean elementValue = Boolean.valueOf(ctx.getText());
        Data element = new Data(elementValue);

        return element;
    }


    /*************** Decision Making (if-then-else, case) ***************/


    @Override
    public Data visitIfBlock(pascalParser.IfBlockContext ctx) {


        return null;
    }

    /*************** Utility methods ***************/


    // Separate the variable name list into usable names
    private String [] parseString(String variable_list){
        return variable_list.split("\\s*,\\s*");
    }
}
