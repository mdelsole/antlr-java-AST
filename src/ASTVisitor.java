
import java.util.*;
import java.lang.Math;


// Our custom visitor extends the base visitor
public class ASTVisitor extends pascalBaseVisitor<Data>{


    /*************** Our old @members, modified to utilize generic types ***************/


    // Our "scope"; each level of the stack will store the local variables for that "scope"
    Stack<HashMap<String, Data>> localVars = new Stack<>();
    // Global vars will be the variables at the lowest scope level
    HashMap<String, Data> globalVars = new HashMap<>();

    // Keep a map of all the user-defined functions
    HashMap<String, pascalParser.FunctionDeclarationContext> functions = new HashMap<>();
    // Keep a map of all the user-defined procedures
    HashMap<String, pascalParser.ProcedureDeclarationContext> procedures = new HashMap<>();
    // Storing variables names
    List<String> procedureVars = new ArrayList<>();

    // Break/continue. Excuse the stupid names, but both are reserved words, so something must be done...
    boolean break_or_nah = false;
    boolean continue_or_nah = false;


    /*************** Implementing the abstract methods of pascalBaseVisitor ***************/


    @Override
    public Data visitStart(pascalParser.StartContext ctx) {
        // Top level scope holds the global variables
        localVars.push(globalVars);
        System.out.println("Running " + ctx.program().NAME());
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
            //System.out.println("Declared Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                //System.out.println("Declared Name: " + vNames[i] + ", Value: " + value);
            }
        }

        //System.out.println("Local declared variables: " + localVars.peek());


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
            //System.out.println("Initialized Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                //System.out.println("Initialized Name: " + vNames[i] + ", Value: " + value);
            }
        }

        //System.out.println("Local initialized variables: " + localVars.peek());

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
            //System.out.println("Assigned Name: " + varName + ", Value: " + value);
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
                //System.out.println("Assigned Name: " + vNames[i] + ", Value: " + value);
            }
        }

        //System.out.println("Local initialized variables: " + localVars.peek());

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
        //System.out.println("Contents: " + contents);

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
        //System.out.println("Retrieved value: " + value);

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

        //System.out.println("Le: " + leftElement.toString());
        //System.out.println("Re: " + rightElement.toString());

        Data result = null;

        if (ctx.op.getType() == pascalParser.EQU){
            result = new Data(leftElement.toDouble().equals(rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.NEQU){
            result = new Data(!(leftElement.toDouble().equals(rightElement.toDouble())));
        }
        else if (ctx.op.getType() == pascalParser.GRT){
            result = new Data(leftElement.toDouble() > (rightElement.toDouble()));
        }
        else if (ctx.op.getType() == pascalParser.GRTEQ){
            result = new Data(leftElement.toDouble() >= (rightElement.toDouble()));
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
        //System.out.println("Retrieved value: " + value);

        return value;
    }

    // Base case, returning the boolean value itself
    @Override
    public Data visitBoolValueElement(pascalParser.BoolValueElementContext ctx) {
        Boolean elementValue = Boolean.valueOf(ctx.getText());
        Data element = new Data(elementValue);

        return element;
    }


    /*************** If-then-else, case statements ***************/


    @Override
    public Data visitIfBlock(pascalParser.IfBlockContext ctx) {
        // Whether or not we need to execute the else at the end
        boolean complete = false;

        // Loop through the if statement, execute it if the condition evaluates to true
        for (int i = 0; i < ctx.conditional().size(); i++) {
            if (this.visit(ctx.conditional(i).logicExpr()).toBoolean().equals(true)){
                for (int j = 0; j < ctx.conditional(i).statement().size(); j++) {
                    this.visit(ctx.conditional(i).statement(j));
                }

                complete = true;
                break;
            }
        }

        // Execute the else, if nothing else was true
        if (!complete){
            this.visit(ctx.statement());

        }

        return null;
    }

    @Override
    public Data visitCaseBlock(pascalParser.CaseBlockContext ctx) {
        // Whether or not we need to execute the else at the end
        boolean complete = false;

        Data compareValue = this.visit(ctx.varValue());

        // Loop through the if statement, execute it if the condition evaluates to true
        for (int i = 0; i < ctx.caseStatement().size(); i++) {
            Data value = this.visit(ctx.caseStatement(i).varValue());
            //System.out.println("Value: " + value);
            //System.out.println("Compare value: " + compareValue);
            //System.out.println("Eval: " + (value.equals(compareValue)));
            if (value.equals(compareValue)){
                this.visit(ctx.caseStatement(i).statement());
                complete = true;
                break;
            }
        }

        // Execute the else, if nothing else was true
        if (!complete){
            this.visit(ctx.statement());
        }

        return null;
    }

    /*************** Writeln, readln ***************/


    @Override
    public Data visitWriteln(pascalParser.WritelnContext ctx) {

        StringBuilder result = new StringBuilder();
        // Loop through the if statement, execute it if the condition evaluates to true
        for (int i = 0; i < ctx.writeContent().size(); i++) {
            result.append(this.visit(ctx.writeContent(i)).toString());
        }
        System.out.println(result);
        return null;
    }

    // Print a variable
    @Override
    public Data visitWriteVar(pascalParser.WriteVarContext ctx) {
        return this.visit(ctx.varValue());
    }

    // Print text
    @Override
    public Data visitWriteText(pascalParser.WriteTextContext ctx) {
        String text = ctx.TEXT().getText();
        // Strip it of the quotes
        text = text.substring(1, text.length()-1);
        return new Data(text);
    }

    public Data visitReadln(pascalParser.ReadlnContext ctx){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");

        if(scanner.hasNextDouble()){
            double dou = scanner.nextDouble();
            String name = ctx.NAME().getText();
            Data value = new Data(dou);
            localVars.peek().put(name, value);
            //System.out.println("Assigned Name: " + name + ", Value: " + value);
        }
        else if (scanner.hasNextBoolean()){
            boolean bo = scanner.nextBoolean();
            String name = ctx.NAME().getText();
            Data value = new Data(bo);
            localVars.peek().put(name, value);
            //System.out.println("Assigned Name: " + name + ", Value: " + value);
        }
        else{
            System.out.println("NOT A VALID INPUT");
        }
        return null;
    }

    /*************** Loops: While and For ***************/


    @Override
    public Data visitWhileLoop(pascalParser.WhileLoopContext ctx) {

        // Make a new scope for within the while loop
        HashMap<String, Data> newScope = new HashMap<String, Data>();
        // Make sure the new scope has access to the global variables too by using putAll()
        newScope.putAll(localVars.peek());
        localVars.push(newScope);

        Boolean condition = this.visit(ctx.logicExpr()).toBoolean();
        while (condition){
            if (!break_or_nah) {
                if (!continue_or_nah) {
                    this.visit(ctx.programBlock());
                    // Re-evaluate the condition to determine if we continue
                    condition = this.visit(ctx.logicExpr()).toBoolean();
                }
                else{
                    // Re-evaluate the condition to determine if we continue
                    condition = this.visit(ctx.logicExpr()).toBoolean();
                }
            }
            else{
                break;
            }
        }

        // Reset break/continue
        break_or_nah = false;
        continue_or_nah = false;

        // Once we're done with our loop, return to the original scope
        localVars.pop();

        return null;
    }

    @Override
    public Data visitForLoop(pascalParser.ForLoopContext ctx) {

        // Make a new scope for within the while loop
        HashMap<String, Data> newScope = new HashMap<String, Data>();
        // Make sure the new scope has access to the global variables too by using putAll()
        newScope.putAll(localVars.peek());
        localVars.push(newScope);

        Data tempVar = this.visit(ctx.forVar());

        Double cond = this.visit(ctx.mathExpr()).toDouble();

        for(double i = tempVar.toDouble(); i <= cond; i++){
            if (!break_or_nah) {
                if (!continue_or_nah) {
                    this.visit(ctx.programBlock());
                    tempVar.add1();
                }
                else{
                    tempVar.add1();
                }
            }
            else{
                break;
            }
        }

        // Reset break/continue
        break_or_nah = false;
        continue_or_nah = false;

        // Once we're done with our loop, return to the original scope
        localVars.pop();

        return null;
    }

    @Override
    public Data visitForVar(pascalParser.ForVarContext ctx) {
        String id = ctx.NAME().getText();
        Data value = this.visit(ctx.mathExpr());

        localVars.peek().put(id, value);
        return value;
    }

    @Override
    public Data visitBreakd(pascalParser.BreakdContext ctx) {
        break_or_nah = true;
        return null;
    }

    @Override
    public Data visitContinued(pascalParser.ContinuedContext ctx) {
        continue_or_nah = true;
        return null;
    }

    /*************** User-defined functions ***************/


    // Declare a function
    @Override
    public Data visitFunctionDeclaration(pascalParser.FunctionDeclarationContext ctx) {
        String functionName = ctx.NAME().getText();
        // Put the function and its context in our store of functions
        functions.put(functionName, ctx);
        return null;
    }

    // Call a function
    @Override
    public Data visitFunctionCall(pascalParser.FunctionCallContext ctx) {

        // Make a new scope for within the function
        HashMap<String, Data> newScope = new HashMap<String, Data>();
        // Make sure the new scope has access to the global variables too by using putAll()
        newScope.putAll(localVars.peek());
        localVars.push(newScope);

        //System.out.println("Function: " + ctx.NAME().getText());
        // Get the context of the function
        pascalParser.FunctionDeclarationContext function = functions.get(ctx.NAME().getText());
        // Get the name of the function
        String functionName = ctx.NAME().getText();
        // Keep track of where we are in the list of parameters
        int position = 0;

        for (int i = 0; i < function.parameterList().parameterSet().size(); i++){
            String varName = function.parameterList().parameterSet(i).varNameList().getText();
            //System.out.println("VarName: " + varName);
            String[] vNames;
            if (varName.contains(",")) {
                vNames = parseString(varName);
            }
            else{
                vNames = null;
            }

            // Place the variable name and its data value into this scope's variables (i.e. localVars)
            if (vNames == null) {
                //System.out.println("I-sing: " + position);
                //System.out.println("VarName: " + varName);

                Data val = this.visit(ctx.parameterCallList().varValue(position));
                //System.out.println("Added: " + val);
                // peek() gets us the top element, i.e. current scope
                localVars.peek().put(varName, val);
                position += 1;
            }
            else{
                for (int k = 0; k < vNames.length; k++){
                    //System.out.println("Vnames: " + vNames[k]);
                    Data val = this.visit(ctx.parameterCallList().varValue(position));
                    //System.out.println("Added2: " + val);
                    // peek() gets us the top element, i.e. current scope
                    localVars.peek().put(vNames[k], val);
                    //System.out.println("Table: " + localVars.peek());
                    position +=1;
                    //System.out.println("Position: " + position);
                }
            }
        }

        this.visitChildren(function);
        Data returnVal = localVars.peek().get(functionName);
        System.out.println("Return: " + returnVal);

        // Move back down to lower scope
        localVars.pop();

        return returnVal;
    }

    // Declare a procedure
    @Override
    public Data visitProcedureDeclaration(pascalParser.ProcedureDeclarationContext ctx) {
        String procedureName = ctx.NAME().getText();

        // Put the function and its context in our store of functions
        procedures.put(procedureName, ctx);
        return null;
    }

    @Override
    public Data visitProcedureCall(pascalParser.ProcedureCallContext ctx) {
        // Make a new scope for within the function
        HashMap<String, Data> newScope = new HashMap<String, Data>();
        // Make sure the new scope has access to the global variables too by using putAll()
        newScope.putAll(localVars.peek());
        localVars.push(newScope);

        // Get the names of the variables that will be updated at the end of the procedure
        List<String> procNames = new ArrayList<>();
        String [] pNames;
        String pName = "";
        for (int i = 0 ; i < ctx.parameterCallList().varValue().size(); i++) {
            pName = ctx.parameterCallList().varValue(i).getText();
            procNames.add(pName);
        }
        //System.out.println("Varnames: " + varNames);
        for (int i = 0; i < procNames.size(); i++) {
            if (procNames.get(i).contains(",")) {
                pNames = parseString(procNames.get(i));
                for (int j = 0; j < pNames.length; j++){
                    procedureVars.add(pNames[j]);
                }
            } else {
                procedureVars.add(procNames.get(i));
            }
        }
        //System.out.println("ProcedureVars: " + procedureVars);

        // Get the names of variables we have manipulated here
        List<String> usedVars = new ArrayList<>();

        // Get the context of the function
        pascalParser.ProcedureDeclarationContext procedure = procedures.get(ctx.NAME().getText());

        int position = 0;

        for (int i = 0; i < procedure.parameterList().parameterSet().size(); i++){
            String varName = procedure.parameterList().parameterSet(i).varNameList().getText();
            //System.out.println("VarName: " + varName);
            String[] vNames;
            if (varName.contains(",")) {
                vNames = parseString(varName);
            }
            else{
                vNames = null;
            }

            // Place the variable name and its data value into this scope's variables (i.e. localVars)
            if (vNames == null) {
                //System.out.println("I-sing: " + position);
                //System.out.println("VarName: " + varName);
                Data val = this.visit(ctx.parameterCallList().varValue(position));
                //System.out.println("Added: " + val);

                // peek() gets us the top element, i.e. current scope
                localVars.peek().put(varName, val);
                usedVars.add(varName);
                position += 1;

            }
            else{
                for (int k = 0; k < vNames.length; k++){
                    //System.out.println("Vnames: " + vNames[k]);
                    Data val = this.visit(ctx.parameterCallList().varValue(position));
                    //System.out.println("Added2: " + val);
                    // peek() gets us the top element, i.e. current scope
                    localVars.peek().put(vNames[k], val);
                    usedVars.add(vNames[k]);
                    //System.out.println("Table: " + localVars.peek());
                    position +=1;

                }
            }
        }

        this.visitChildren(procedure);

        // Move back down and transfer over any variables as necessary
        HashMap<String, Data> current = localVars.pop();
        HashMap<String, Data> parent = localVars.peek();

        //System.out.println(Arrays.asList(current));
        //System.out.println(Arrays.asList(procedureVars));
        //System.out.println(usedVars);

        // Go through and figure out which global variables to update
        for (int i = 0; i < procedureVars.size(); i++){
            if (parent.containsKey(procedureVars.get(i))){
                Data value = current.get(usedVars.get(i));
                parent.put(procedureVars.get(i), value);
            }
        }

        procedureVars.clear();

        return null;
    }

    /*************** Utility methods ***************/


    // Separate the variable name list into usable names
    private String [] parseString(String variable_list){
        return variable_list.split("\\s*,\\s*");
    }
}
