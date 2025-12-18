package org.core.model.question;

import org.core.SymbolTable;

import java.util.Map;

public class DateQuestion implements Question{
    private String questionPrompt;

    private String questionAnswer;



    public DateQuestion(){
        this.questionAnswer = "";
        this.questionPrompt = "";
    }

    public DateQuestion(String questionPrompt, String questionAnswer){
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

    /*public int getAnswerRating(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Map<String,Integer> answer = SymbolTable.getInstance().getSymbol(this.getQuestionPrompt());

        boolean result = true;

        String eval = answer.keySet().stream().findFirst().get();
        eval = eval.substring(eval.length()-1);
        Date dateExpected;
        Date dateActual;
        try {
            dateExpected = dateFormat.parse(answer.keySet().stream().findFirst().get().substring(0,answer.keySet().stream().findFirst().get().length()-1));
            dateActual = dateFormat.parse(this.getQuestionAnswer().substring(0,this.getQuestionAnswer().length()-1));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        switch(eval){
            case "G":
                result = dateActual.compareTo(dateExpected)>0;
                break;
            case "L":
                result = dateActual.compareTo(dateExpected)<0;
                break;
            case "E":
                result = dateActual.compareTo(dateExpected)==0;
                break;
        }

        if(result){
            return answer.get(this.getQuestionAnswer());
        }else{
            return 0;
        }
    }*/

    @Override
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    @Override
    public String getQuestionPrompt() {
        return questionPrompt;
    }
}
