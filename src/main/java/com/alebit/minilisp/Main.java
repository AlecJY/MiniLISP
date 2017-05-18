package com.alebit.minilisp;

import com.alebit.minilisp.LISPLexer;
import com.alebit.minilisp.LISPParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            LISPLexer lexer = new LISPLexer(CharStreams.fromFileName(args[0]));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LISPParser parser = new LISPParser(tokens);
            ParseTree tree = parser.prog();
            LISPVisitor visitor = new LISPVisitor();
            visitor.visit(tree);

        } catch (IOException e) {

        }
    }
}
