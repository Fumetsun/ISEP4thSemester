package org.antlr4Impl.model;

import org.antlr4Generated.model.ModelBaseVisitor;
import org.antlr4Generated.model.ModelParser;
import org.core.model.question.*;

public class RequirementsVisitorImpl extends ModelBaseVisitor<Question> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitStart(ModelParser.StartContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitHeadliner(ModelParser.HeadlinerContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitQuestion(ModelParser.QuestionContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitSingle_choice_question(ModelParser.Single_choice_questionContext ctx) {
        return (new SingleChoiceQuestion(ctx.PROMPT().toString() + ctx.SQUES().toString(), ctx.SINGLE_CHOICE_ANS().getText()));
         }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitMultiple_choice_question(ModelParser.Multiple_choice_questionContext ctx) {
        return (new MultChoiceQuestion(ctx.PROMPT().toString() + ctx.MQUES().toString(), ctx.MULT_CHOICE_ANS().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitInteger_question(ModelParser.Integer_questionContext ctx) {
        return (new IntQuestion(ctx.PROMPT().toString() + ctx.IQUES().toString(), ctx.INT_ANS().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitDecimal_question(ModelParser.Decimal_questionContext ctx) {
        return (new DeciQuestion(ctx.PROMPT().toString()+  ctx.DQUES().toString(), ctx.DECI_ANS().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitNumerical_choice_question(ModelParser.Numerical_choice_questionContext ctx) {
        return (new NumericQuestion(ctx.PROMPT().toString() + ctx.NQUES().toString(), ctx.NUM_ANS().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitTrue_false_question(ModelParser.True_false_questionContext ctx) {
        return (new TrueFalseQuestion(ctx.PROMPT().toString() + ctx.TFQUES().toString(), ctx.TRUE_FALSE_ANS().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitDate_question(ModelParser.Date_questionContext ctx) {
        return (new DateQuestion(ctx.PROMPT().toString() + ctx.DATEQUES().toString(), ctx.DATE_DAY().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitTime_question(ModelParser.Time_questionContext ctx) {
        return (new TimeQuestion(ctx.PROMPT().toString() + ctx.TIMEQUES().toString(), ctx.TIME().getText()));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public Question visitText_question(ModelParser.Text_questionContext ctx) {
        return (new ShortTextQuestion(ctx.PROMPT().toString() + ctx.TEXTQUES().toString(), ctx.TEXT_ANS().getText()));
    }
}
