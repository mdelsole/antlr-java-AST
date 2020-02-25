
import java.util.HashMap;
import java.util.Stack;



// Our custom visitor extends the base visitor
public class ASTVisitor extends pascalBaseVisitor<Data>{

    /***** Our old @members, modified to utilize generic types *****/

    HashMap<String, Data> globalVars = new HashMap<String, Data>();
    // Our "scope"; each level of the stack will store the local variables for that "scope"
    Stack<HashMap<String, Data>> localVars = new Stack<HashMap<String, Data>>();

    /***** Implementing the abstract methods of pascalBaseVisitor *****/

    @Override
    public Data visitStart(pascalParser.StartContext ctx) {
        // Top level scope holds the global variables
        localVars.push(globalVars);
        return visitChildren(ctx);
    }

    @Override
    public Data visitVarDeclaration(pascalParser.VarDeclarationContext ctx) {
        String varName = ctx.vNameList().getText();

        // Handle case where multiple variable names are passed in
        String[] vNames;
        if (varName.contains(",")) {
            vNames = parseString(varName);
        }
        else{
            vNames = null;
        }

        // Determine the type of the Data
        Data value = null;
        if (ctx.type.getType() == pascalParser.BOOLEANTYPE){
            value = new Data(Boolean.parseBoolean(ctx.varType().getText()));
        }
        else if (ctx.type.getType() == pascalParser.REALTYPE){
            value = new Data(Double.parseDouble(ctx.varType().getText()));
        }

        // Place the variable name and its data value into this scope's variables (i.e. localVars
        if (vNames == null) {
            // peek() gets us the top element, i.e. current scope
            localVars.peek().put(varName, value);
            System.out.println("Local variables: " + localVars.peek());
        }
        else{
            for (int i = 0; i < vNames.length; i++){
                localVars.peek().put(vNames[i], value);
            }
            System.out.println("Local variables: " + localVars.peek());
        }

        return value;
    }


    /***** Utility methods *****/


    // Separate the variable name list into usable names
    public String [] parseString(String variable_list){
        String[] values = variable_list.split("\\s*,\\s*");
        return values;
    }
}
