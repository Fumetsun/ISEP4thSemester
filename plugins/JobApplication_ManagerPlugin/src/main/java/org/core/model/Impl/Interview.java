package org.core.model.Impl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr4Impl.model.InterviewListenerImpl;
import org.core.SymbolTable;
import org.core.model.Model;
import org.core.model.question.Question;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Interface representing an interview.
 */
public class Interview implements Model {



    private ParseTree tree;
    private List<Question> questions;


    public Interview(ParseTree tree) throws IOException {
        this.tree = tree;
    }

    public Interview(){}



    public void parseQuestions() throws IOException {
        InterviewListenerImpl listener = new InterviewListenerImpl();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(listener, tree);
        this.questions = listener.getQuestions();

    }

    @Override
    public int assessAnswers() throws IOException {
        parseQuestions();
        int score=0;
        for(Question q : this.questions){
            score += q.getAnswerRating();
        }

        return score;
    }

    public void generateTemplate(String outDir){
        int n = 1;
        Iterable<String> questions = SymbolTable.getInstance().getQuestions();

        if(!outDir.endsWith("/")){
            outDir+= "/";
        }

        try {
            FileWriter fileWriter = new FileWriter(outDir + SymbolTable.getInstance().getJobName() + "InterviewGeneratedTemplate.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write content to the file
            bufferedWriter.write("-" + SymbolTable.getInstance().getJobName() + " Interview Model-\n\n");
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
            bufferedWriter.write("-" + SymbolTable.getInstance().getJobName() + " Interview Model-\n\n");
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
