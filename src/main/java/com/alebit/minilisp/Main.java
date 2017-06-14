package com.alebit.minilisp;

import com.alebit.minilisp.LISPLexer;
import com.alebit.minilisp.LISPParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*if (args.length != 1) {
            // System.err.println("Wrong arguments!");
            // System.err.println("Usage: MiniLISP <LISPFILE>");
            System.out.println("error");
            System.exit(1);
        }*/
        // try {
            String lispProgram = new String();
            Scanner scn = new Scanner(System.in);
            while (scn.hasNext()) {
                lispProgram += "\n" + scn.nextLine();
            }
            LISPLexer lexer = new LISPLexer(CharStreams.fromString(lispProgram));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LISPParser parser = new LISPParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(new LISPErrorListener());
            ParseTree tree = parser.prog();
            LISPVisitor visitor = new LISPVisitor();
            visitor.visit(tree);
        /*} catch (IOException e) {
            // System.err.println("File not found: " + args[0]);
            System.out.println("error");
            System.exit(1);
        }*/
    }
}
