package org.core.model.question;

import org.core.SymbolTable;

import java.util.Map;

public class TrueFalseQuestion implements Question{

    private String questionPrompt;

    private String questionAnswer;



    public TrueFalseQuestion(){
        this.questionAnswer = "";
        this.questionPrompt = "";
    }

    public TrueFalseQuestion(String questionPrompt, String questionAnswer){
        this.questionAnswer=questionAnswer;
        this.questionPrompt=questionPrompt;
    }


    public int getAnswerRating(){
        Map<String,Integer> answer = SymbolTable.getInstance().getSymbol(this.getQuestionPrompt());

        if(!answer.keySet().contains(this.getQuestionAnswer())){
            return 0;
        }else{
            return answer.get(this.getQuestionAnswer());
        }
    }

    @Override
    public String getQuestionPrompt() {
        return this.questionPrompt;
    }

    @Override
    public String getQuestionAnswer() {
        return this.questionAnswer;
    }


}
