// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/Model.g4 by ANTLR 4.13.1
package org.antlr4Generated.model;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ModelParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ModelVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ModelParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(ModelParser.StartContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#headliner}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeadliner(ModelParser.HeadlinerContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(ModelParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#single_choice_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingle_choice_question(ModelParser.Single_choice_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#multiple_choice_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiple_choice_question(ModelParser.Multiple_choice_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#integer_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger_question(ModelParser.Integer_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#decimal_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecimal_question(ModelParser.Decimal_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#numerical_choice_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumerical_choice_question(ModelParser.Numerical_choice_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#true_false_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue_false_question(ModelParser.True_false_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#date_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDate_question(ModelParser.Date_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#time_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTime_question(ModelParser.Time_questionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ModelParser#text_question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitText_question(ModelParser.Text_questionContext ctx);
}