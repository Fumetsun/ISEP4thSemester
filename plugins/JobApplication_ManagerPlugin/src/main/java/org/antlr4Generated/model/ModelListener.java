// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/Model.g4 by ANTLR 4.13.1
package org.antlr4Generated.model;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ModelParser}.
 */
public interface ModelListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ModelParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(ModelParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(ModelParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#headliner}.
	 * @param ctx the parse tree
	 */
	void enterHeadliner(ModelParser.HeadlinerContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#headliner}.
	 * @param ctx the parse tree
	 */
	void exitHeadliner(ModelParser.HeadlinerContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(ModelParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(ModelParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#single_choice_question}.
	 * @param ctx the parse tree
	 */
	void enterSingle_choice_question(ModelParser.Single_choice_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#single_choice_question}.
	 * @param ctx the parse tree
	 */
	void exitSingle_choice_question(ModelParser.Single_choice_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#multiple_choice_question}.
	 * @param ctx the parse tree
	 */
	void enterMultiple_choice_question(ModelParser.Multiple_choice_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#multiple_choice_question}.
	 * @param ctx the parse tree
	 */
	void exitMultiple_choice_question(ModelParser.Multiple_choice_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#integer_question}.
	 * @param ctx the parse tree
	 */
	void enterInteger_question(ModelParser.Integer_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#integer_question}.
	 * @param ctx the parse tree
	 */
	void exitInteger_question(ModelParser.Integer_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#decimal_question}.
	 * @param ctx the parse tree
	 */
	void enterDecimal_question(ModelParser.Decimal_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#decimal_question}.
	 * @param ctx the parse tree
	 */
	void exitDecimal_question(ModelParser.Decimal_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#numerical_choice_question}.
	 * @param ctx the parse tree
	 */
	void enterNumerical_choice_question(ModelParser.Numerical_choice_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#numerical_choice_question}.
	 * @param ctx the parse tree
	 */
	void exitNumerical_choice_question(ModelParser.Numerical_choice_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#true_false_question}.
	 * @param ctx the parse tree
	 */
	void enterTrue_false_question(ModelParser.True_false_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#true_false_question}.
	 * @param ctx the parse tree
	 */
	void exitTrue_false_question(ModelParser.True_false_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#date_question}.
	 * @param ctx the parse tree
	 */
	void enterDate_question(ModelParser.Date_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#date_question}.
	 * @param ctx the parse tree
	 */
	void exitDate_question(ModelParser.Date_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#time_question}.
	 * @param ctx the parse tree
	 */
	void enterTime_question(ModelParser.Time_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#time_question}.
	 * @param ctx the parse tree
	 */
	void exitTime_question(ModelParser.Time_questionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ModelParser#text_question}.
	 * @param ctx the parse tree
	 */
	void enterText_question(ModelParser.Text_questionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ModelParser#text_question}.
	 * @param ctx the parse tree
	 */
	void exitText_question(ModelParser.Text_questionContext ctx);
}