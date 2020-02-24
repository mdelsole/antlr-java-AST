import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import java.io.FileInputStream;



public class Main {
    public static void main(String [] args) throws Exception{

        if (args.length == 0) {
            args = new String[]{"test.txt"};
        }

        System.out.println("parsing: " + args[0]);

        // Set args[0] as the input to our lexer
        pascalLexer lexer = new pascalLexer(new ANTLRFileStream(args[0]));
        // Set the lexed file as input to our parser
        pascalParser parser = new pascalParser(new CommonTokenStream(lexer));
        // Create a parse tree. Program() is our entry point
        ParseTree tree = parser.program();
        // Our visitor (does the actions as tree is traversed)
        PascalVisitor visitor = new PascalVisitor();
        // We need to implement this:
        // visitor.visit(tree);

    }
}
