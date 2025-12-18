package org.antlr4Impl.model;

import org.antlr4Generated.model.ModelBaseListener;
import org.antlr4Generated.model.ModelParser;
import org.core.model.question.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides an implementation of {@link ModelBaseListener},
 * implementing only the methods that have a functional implementation in {@link ModelBaseListener}.
 */
public class InterviewListenerImpl extends ModelBaseListener {
    List<Question> questions = new ArrayList<>();

    public List<Question> getQuestions() {
        return questions;
    }
    @Override
    public void enterSingle_choice_question(ModelParser.Single_choice_questionContext ctx) {
        questions.add(new SingleChoiceQuestion(ctx.PROMPT().toString() + ctx.SQUES().toString(), ctx.SINGLE_CHOICE_ANS().getText()));
    }

    @Override
    public void enterMultiple_choice_question(ModelParser.Multiple_choice_questionContext ctx) {
        questions.add(new MultChoiceQuestion(ctx.PROMPT().toString()+ ctx.MQUES().toString(), ctx.MULT_CHOICE_ANS().getText()));
    }

    @Override
    public void enterInteger_question(ModelParser.Integer_questionContext ctx) {
        questions.add(new IntQuestion(ctx.PROMPT().toString() + ctx.IQUES().toString(), ctx.INT_ANS().getText()));
    }

    @Override
    public void enterDecimal_question(ModelParser.Decimal_questionContext ctx) {
        questions.add(new DeciQuestion(ctx.PROMPT().toString()+  ctx.DQUES().toString(), ctx.DECI_ANS().getText()));
    }

    @Override
    public void enterNumerical_choice_question(ModelParser.Numerical_choice_questionContext ctx) {
        questions.add(new NumericQuestion(ctx.PROMPT().toString() +ctx.NQUES().toString(), ctx.NUM_ANS().getText()));
    }

    @Override
    public void enterTrue_false_question(ModelParser.True_false_questionContext ctx) {
        questions.add(new TrueFalseQuestion(ctx.PROMPT().toString()+  ctx.TFQUES().toString(), ctx.TRUE_FALSE_ANS().getText()));
    }

    @Override
    public void enterDate_question(ModelParser.Date_questionContext ctx) {
        questions.add(new DateQuestion(ctx.PROMPT().toString() + ctx.DATEQUES().toString(), ctx.DATE_DAY().getText()));
    }

    @Override
    public void enterTime_question(ModelParser.Time_questionContext ctx) {
        questions.add(new TimeQuestion(ctx.PROMPT().toString()+  ctx.TIMEQUES().toString(), ctx.TIME().getText()));
    }

    @Override
    public void enterText_question(ModelParser.Text_questionContext ctx) {
        questions.add(new ShortTextQuestion(ctx.PROMPT().toString()+ ctx.TEXTQUES().toString(), ctx.TEXT_ANS().getText()));
    }
}
