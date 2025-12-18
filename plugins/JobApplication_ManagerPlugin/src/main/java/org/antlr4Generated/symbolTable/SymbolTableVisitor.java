// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/SymbolTable.g4 by ANTLR 4.13.1
package org.antlr4Generated.symbolTable;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SymbolTableParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SymbolTableVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SymbolTableParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(SymbolTableParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymbolTableParser#headliner}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeadliner(SymbolTableParser.HeadlinerContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymbolTableParser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(SymbolTableParser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymbolTableParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(SymbolTableParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SymbolTableParser#answer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnswer(SymbolTableParser.AnswerContext ctx);
}