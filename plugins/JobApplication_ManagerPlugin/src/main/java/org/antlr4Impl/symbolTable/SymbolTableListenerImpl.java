package org.antlr4Impl.symbolTable;

import org.antlr4Generated.symbolTable.SymbolTableBaseListener;
import org.antlr4Generated.symbolTable.SymbolTableParser;

import java.util.HashMap;
import java.util.Map;

public class SymbolTableListenerImpl extends SymbolTableBaseListener {
    private Map<String, Map<String, Integer>> table = new HashMap<>();

    private String jobName;

    public Map<String, Map<String, Integer>> getTable() {
        return table;
    }

    public String getJobName() {return jobName;}

    @Override public void enterQuestion(SymbolTableParser.QuestionContext ctx) {
        Map<String,Integer> map = new HashMap<>();
        String[] elements;
        String answer;

        for(int i=0; i < ctx.answer().size();i++){
            answer = ctx.answer(i).getText().toString();
            answer = answer.replaceAll("\\|", "");

            if(ctx.QUEST_TYPE_SYMBOL().toString().contains("<NUMANS>")){
                map.put(answer.split("_")[0].replace(":","-"), Integer.parseInt(answer.split("_")[1]));
            }else{
                map.put(answer.split("_")[0], Integer.parseInt(answer.split("_")[1]));
            }


        }


        table.put(ctx.TEXT().toString() + " " + ctx.QUEST_TYPE_SYMBOL().toString(), map);


    }

    @Override
    public void enterHeadliner(SymbolTableParser.HeadlinerContext ctx) {
        this.jobName = ctx.TEXT().toString();
    }
}
