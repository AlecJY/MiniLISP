package com.alebit.minilisp;

import com.alebit.minilisp.LISPLexer;
import com.alebit.minilisp.LISPParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Wrong arguments!");
            System.err.println("Usage: MiniLISP <LISPFILE>");
            System.exit(1);
        }
        try {
            LISPLexer lexer = new LISPLexer(CharStreams.fromFileName(args[0]));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LISPParser parser = new LISPParser(tokens);
            parser.addErrorListener(new LISPErrorListener());
            ParseTree tree = parser.prog();
            LISPVisitor visitor = new LISPVisitor();
            visitor.visit(tree);
        } catch (IOException e) {
            System.err.println("File not found: " + args[0]);
            System.exit(1);
        }
        System.out.println();
    }
}
