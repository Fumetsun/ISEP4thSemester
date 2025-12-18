package org.core;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr4Generated.model.ModelLexer;
import org.antlr4Generated.model.ModelParser;
import org.core.model.Impl.Interview;
import org.core.model.Impl.Requirements;
import org.core.model.Model;

public class AnswerAssessmentModel {

    public static void main(String[] args) throws IOException {
        Model model = null;

        if (args.length > 4) {
            System.out.println(
                    "Usage: java -jar YourClassName <-template | -evaluate | -validate> <-requirements | -interview> <symbol table path> <file location>");
            System.exit(-1);
            // return;
        }


        CommonTokenStream tokens;
        ModelLexer lexer;
        ModelParser parser;

        String arg = args[0];



        if (arg.contains("-template")) {
            SymbolTable.getInstance().importTableFromFile(args[2]);
            SymbolTable.getInstance();
            switch (args[1]) {
                case "-requirements":
                    model = new Requirements();
                    break;
                case "-interview":
                    model = new Interview();
                    break;
            }
            if (args.length > 3) {
                model.generateTemplate(args[3]);
            } else {
                model.generateTemplate();
            }
        } else if (arg.contains("-evaluate")) {
            SymbolTable.getInstance().importTableFromFile(args[2]);
            SymbolTable.getInstance();
            lexer = new ModelLexer(CharStreams.fromFileName(args[3]));
            tokens = new CommonTokenStream(lexer);
            parser = new ModelParser(tokens);
            switch (args[1]) {
                case "-requirements":
                    model = new Requirements(parser.start());
                    break;
                case "-interview":
                    model = new Interview(parser.start());
                    break;
            }
            if (args[1].contains("-interview")) {
                try {
                    System.exit(model.assessAnswers());
                    // System.out.println(model.assessAnswers());
                } catch (IOException e) {
                    System.exit(-1);
                    // throw new RuntimeException(e.getMessage());
                }
            } else {
                if (model.assessAnswers() != 0) {
                    System.exit(1);
                    // System.out.println(1);
                } else {
                    System.exit(0);
                    // System.out.println(0);
                }
            }
        } else if (arg.contains("-validate")) {
            try {
                lexer = new ModelLexer(CharStreams.fromFileName(args[1]));
                tokens = new CommonTokenStream(lexer);
                parser = new ModelParser(tokens);
                model = new Interview(parser.start());
                System.exit(1);
            } catch (Exception e) {
                System.exit(-1);
            }
        } else {
            System.out.println("Invalid argument. Please use <-template | -evaluate | -validate>");
            System.exit(-1);
        }
    }

}