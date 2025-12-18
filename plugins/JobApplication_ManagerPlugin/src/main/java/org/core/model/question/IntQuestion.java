package org.core.model.question;

import org.core.SymbolTable;

import java.util.Map;

public class IntQuestion implements Question{
    private String questionPrompt;

    private String questionAnswer;



    public IntQuestion(){
        this.questionAnswer = "";
        this.questionPrompt = "";
    }

    public IntQuestion(String questionPrompt, String questionAnswer){
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
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    @Override
    public String getQuestionPrompt() {
        return questionPrompt;
    }
}
