package org.core;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr4Generated.symbolTable.SymbolTableLexer;
import org.antlr4Generated.symbolTable.SymbolTableParser;
import org.antlr4Impl.symbolTable.SymbolTableListenerImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private static SymbolTable instance;
    private Map<String, Map<String, Integer>> table;

    private String jobName;

    private SymbolTable() {
        // Initialize the symbol table as needed
        table = new HashMap<>();
        jobName = "";
    }

    public static SymbolTable getInstance() {
        if (instance == null) {
            instance = new SymbolTable();
        }
        return instance;
    }

    public void addSymbol(String name, Map<String, Integer> value) {
        table.put(name, value);
    }

    public Map<String, Integer> getSymbol(String name) {
        name = name.substring(name.indexOf("-")+2).trim();
        return table.get(name);
    }

    public boolean containsSymbol(String name) {
        return table.containsKey(name);
    }

    public void removeSymbol(String name) {
        table.remove(name);
    }

    public void importTableFromFile(String filePath) throws IOException {

        SymbolTableLexer lexer = new SymbolTableLexer(CharStreams.fromFileName(filePath));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SymbolTableParser parser = new SymbolTableParser(tokens);

        SymbolTableListenerImpl listener = new SymbolTableListenerImpl();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(listener, parser.start());

        setTable(listener.getTable());
        setJobName(listener.getJobName());
    }




    private void importSCANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,3), Integer.parseInt(elements[2].substring(4)));
        this.addSymbol(elements[1]+" " + elements[0], ans);
    }

    private void importTFANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf("-")), Integer.parseInt(elements[2].substring(elements[2].indexOf("-")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }

    private void importMCANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();

        for (int i = 2; i < elements.length; i++) {
            ans.put(elements[i].split("-")[0], Integer.parseInt(elements[i].split("-")[1]));
        }

        table.put(elements[1]+ " " + elements[0], ans);

    }

    private void importTEXTANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();

        for (int i = 2; i < elements.length; i++) {
            ans.put(elements[i].split("-")[0], Integer.parseInt(elements[i].split("-")[1]));
        }

        table.put(elements[1]+ " " + elements[0], ans);

    }

    private void importINTANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf("-")), Integer.parseInt(elements[2].substring(elements[2].indexOf("-")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }


    private void importDECANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf("-")), Integer.parseInt(elements[2].substring(elements[2].indexOf("-")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }

    private void importDATEANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf("-")), Integer.parseInt(elements[2].substring(elements[2].indexOf("-")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }

    private void importTIMEANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf("-")), Integer.parseInt(elements[2].substring(elements[2].indexOf("-")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }

    private void importNUMANS(String[] elements){
        HashMap<String, Integer> ans = new HashMap<>();
        ans.put(elements[2].substring(0,elements[2].indexOf(":")), Integer.parseInt(elements[2].substring(elements[2].indexOf(":")+1)));
        this.addSymbol(elements[1]+ " " + elements[0], ans);
    }

    public void setTable(Map<String, Map<String, Integer>> table) {
        this.table = table;
    }

    public Map<String, Map<String, Integer>> getTable() {
        return table;
    }

    public Iterable<String> getQuestions(){
        return this.table.keySet();
    }


    public String getJobName() {
        return jobName;
    }

    private void setJobName(String jobName){
        this.jobName = jobName;
    }
}
