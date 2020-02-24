
import java.lang.Math;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

// Our custom visitor extends the base visitor
public class ASTVisitor extends pascalBaseVisitor{

    /***** Our old @members *****/

    Map<String, Double> arithVars = new HashMap<String, Double>();
    Map<String, Boolean> boolVars = new HashMap<String, Boolean>();
    Scanner scanner = new Scanner(System.in);
    // Separate the variable name list into usable names
    public String [] parseString(String variable_list){
        String[] values = variable_list.split("\\s*,\\s*");
        return values;
    }

    /***** Implement the abstract methods of pascalBaseVisitor *****/


}
