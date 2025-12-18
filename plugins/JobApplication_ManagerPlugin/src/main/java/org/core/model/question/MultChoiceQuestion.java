package org.core.model.question;

import org.core.SymbolTable;

import java.util.Map;

public class MultChoiceQuestion implements Question{

    private String questionPrompt;

    private String questionAnswer;



    public MultChoiceQuestion(){
        this.questionAnswer = "";
        this.questionPrompt = "";
    }

    public MultChoiceQuestion(String questionPrompt, String questionAnswer){
        this.questionAnswer=questionAnswer;
        this.questionPrompt=questionPrompt;
    }



    public int getAnswerRating(){
        Map<String,Integer> answer = SymbolTable.getInstance().getSymbol(this.getQuestionPrompt());

        int score=0;


        for(String a : answer.keySet()){

            if(getQuestionAnswer().contains(a)){
                score += answer.get(a);
            }

        }

        return score;
    }

    @Override
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    @Override
    public String getQuestionPrompt() {
        return questionPrompt;
    }
}
