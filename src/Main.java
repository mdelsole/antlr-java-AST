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

        //pascalLexer lexer = new pascalLexer(new ANTLRFileStream(args[0]));

    }
}
