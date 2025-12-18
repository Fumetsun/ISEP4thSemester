// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/SymbolTable.g4 by ANTLR 4.13.1
package org.antlr4Generated.symbolTable;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SymbolTableParser}.
 */
public interface SymbolTableListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SymbolTableParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(SymbolTableParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymbolTableParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(SymbolTableParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymbolTableParser#headliner}.
	 * @param ctx the parse tree
	 */
	void enterHeadliner(SymbolTableParser.HeadlinerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymbolTableParser#headliner}.
	 * @param ctx the parse tree
	 */
	void exitHeadliner(SymbolTableParser.HeadlinerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymbolTableParser#rules}.
	 * @param ctx the parse tree
	 */
	void enterRules(SymbolTableParser.RulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymbolTableParser#rules}.
	 * @param ctx the parse tree
	 */
	void exitRules(SymbolTableParser.RulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymbolTableParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(SymbolTableParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymbolTableParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(SymbolTableParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SymbolTableParser#answer}.
	 * @param ctx the parse tree
	 */
	void enterAnswer(SymbolTableParser.AnswerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SymbolTableParser#answer}.
	 * @param ctx the parse tree
	 */
	void exitAnswer(SymbolTableParser.AnswerContext ctx);
}