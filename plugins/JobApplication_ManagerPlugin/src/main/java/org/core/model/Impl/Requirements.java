package org.core.model.Impl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr4Impl.model.RequirementsVisitorImpl;
import org.core.SymbolTable;
import org.core.model.Model;
import org.core.model.question.Question;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Requirements implements Model {


    private ParseTree tree;


    public Requirements(ParseTree tree) {
        this.tree = tree;
    }

    public Requirements(){
    }

    @Override
    public int assessAnswers() {
        ParseTree childTree;


        int answer=0, count=1;
        RequirementsVisitorImpl visitor = new RequirementsVisitorImpl();


        do{
            childTree = this.tree.getChild(count);
            Question q = visitor.visit(childTree);
            answer = visitor.visit(childTree).getAnswerRating();

            if(answer == 0){
                return 0;
            }


            count++;
        }while( this.tree.getChild(count) != null );

        return 1;
    }

    public void generateTemplate(String outDir){
        int n = 1;
        Iterable<String> questions = SymbolTable.getInstance().getQuestions();

        if(!outDir.endsWith("/")){
            outDir+= "/";
        }

        try {
            FileWriter fileWriter = new FileWriter(outDir + SymbolTable.getInstance().getJobName() + "RequirementsGeneratedTemplate.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write content to the file
            bufferedWriter.write("-" + SymbolTable.getInstance().getJobName() + " Requirements Model-\n\n");
            for(String question : questions){
                bufferedWriter.write(n + "- " + question + "\n");
                bufferedWriter.write("R: \n\n");


                n++;
            }


            bufferedWriter.close(); // Close the BufferedWriter
            System.out.println("Template has been exported to " + outDir);
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }


    }

    public void generateTemplate(){
        int n = 1;
        Iterable<String> questions = SymbolTable.getInstance().getQuestions();


        try {
            FileWriter fileWriter = new FileWriter(SymbolTable.getInstance().getJobName() + "GeneratedTemplate.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write content to the file
            bufferedWriter.write("-" + SymbolTable.getInstance().getJobName() + " Requirements Model-\n\n");
            for(String question : questions){
                bufferedWriter.write(n + "- " + question + "\n");
                bufferedWriter.write("R: \n\n");


                n++;
            }

            bufferedWriter.close(); // Close the BufferedWriter
            System.out.println("Template has been exported to local directory");
        } catch (IOException e) {
            e.printStackTrace(); // Handle any IO exceptions
        }


    }

}
