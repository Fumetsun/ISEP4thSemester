// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/Model.g4 by ANTLR 4.13.1
package org.antlr4Generated.model;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ModelParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, SQUES=4, MQUES=5, IQUES=6, DQUES=7, NQUES=8, TFQUES=9, 
		DATEQUES=10, TIMEQUES=11, TEXTQUES=12, PROMPT=13, ANS_PROMPT=14, MULT_CHOICE_ANS=15, 
		SINGLE_CHOICE_ANS=16, INT_ANS=17, DECI_ANS=18, NUM_ANS=19, TRUE_FALSE_ANS=20, 
		TIME=21, DATE_DAY=22, TEXT_ANS=23, LETTER=24, WS=25, NEWLINE=26, WORD=27;
	public static final int
		RULE_start = 0, RULE_headliner = 1, RULE_question = 2, RULE_single_choice_question = 3, 
		RULE_multiple_choice_question = 4, RULE_integer_question = 5, RULE_decimal_question = 6, 
		RULE_numerical_choice_question = 7, RULE_true_false_question = 8, RULE_date_question = 9, 
		RULE_time_question = 10, RULE_text_question = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"start", "headliner", "question", "single_choice_question", "multiple_choice_question", 
			"integer_question", "decimal_question", "numerical_choice_question", 
			"true_false_question", "date_question", "time_question", "text_question"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'-'", "' Interview Model-'", "' Requirements Model-'", "'<SCANS>'", 
			"'<MCANS>'", "'<INTANS>'", "'<DECANS>'", "'<NUMANS>'", "'<TFANS>'", "'<DATEANS>'", 
			"'<TIMEANS>'", "'<TEXTANS>'", null, "'R: '"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "SQUES", "MQUES", "IQUES", "DQUES", "NQUES", 
			"TFQUES", "DATEQUES", "TIMEQUES", "TEXTQUES", "PROMPT", "ANS_PROMPT", 
			"MULT_CHOICE_ANS", "SINGLE_CHOICE_ANS", "INT_ANS", "DECI_ANS", "NUM_ANS", 
			"TRUE_FALSE_ANS", "TIME", "DATE_DAY", "TEXT_ANS", "LETTER", "WS", "NEWLINE", 
			"WORD"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Model.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ModelParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StartContext extends ParserRuleContext {
		public HeadlinerContext headliner() {
			return getRuleContext(HeadlinerContext.class,0);
		}
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			headliner();
			setState(26); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(25);
				question();
				}
				}
				setState(28); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==PROMPT );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HeadlinerContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(ModelParser.WORD, 0); }
		public HeadlinerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_headliner; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterHeadliner(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitHeadliner(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitHeadliner(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeadlinerContext headliner() throws RecognitionException {
		HeadlinerContext _localctx = new HeadlinerContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_headliner);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__0);
			setState(31);
			match(WORD);
			setState(32);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==T__2) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public Single_choice_questionContext single_choice_question() {
			return getRuleContext(Single_choice_questionContext.class,0);
		}
		public Multiple_choice_questionContext multiple_choice_question() {
			return getRuleContext(Multiple_choice_questionContext.class,0);
		}
		public Integer_questionContext integer_question() {
			return getRuleContext(Integer_questionContext.class,0);
		}
		public Decimal_questionContext decimal_question() {
			return getRuleContext(Decimal_questionContext.class,0);
		}
		public Numerical_choice_questionContext numerical_choice_question() {
			return getRuleContext(Numerical_choice_questionContext.class,0);
		}
		public True_false_questionContext true_false_question() {
			return getRuleContext(True_false_questionContext.class,0);
		}
		public Date_questionContext date_question() {
			return getRuleContext(Date_questionContext.class,0);
		}
		public Time_questionContext time_question() {
			return getRuleContext(Time_questionContext.class,0);
		}
		public Text_questionContext text_question() {
			return getRuleContext(Text_questionContext.class,0);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_question);
		try {
			setState(43);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				single_choice_question();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(35);
				multiple_choice_question();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(36);
				integer_question();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(37);
				decimal_question();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(38);
				numerical_choice_question();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(39);
				true_false_question();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(40);
				date_question();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(41);
				time_question();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(42);
				text_question();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Single_choice_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode SQUES() { return getToken(ModelParser.SQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode SINGLE_CHOICE_ANS() { return getToken(ModelParser.SINGLE_CHOICE_ANS, 0); }
		public Single_choice_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_single_choice_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterSingle_choice_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitSingle_choice_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitSingle_choice_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Single_choice_questionContext single_choice_question() throws RecognitionException {
		Single_choice_questionContext _localctx = new Single_choice_questionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_single_choice_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			match(PROMPT);
			setState(46);
			match(SQUES);
			setState(47);
			match(ANS_PROMPT);
			setState(48);
			match(SINGLE_CHOICE_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Multiple_choice_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode MQUES() { return getToken(ModelParser.MQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode MULT_CHOICE_ANS() { return getToken(ModelParser.MULT_CHOICE_ANS, 0); }
		public Multiple_choice_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiple_choice_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterMultiple_choice_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitMultiple_choice_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitMultiple_choice_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multiple_choice_questionContext multiple_choice_question() throws RecognitionException {
		Multiple_choice_questionContext _localctx = new Multiple_choice_questionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_multiple_choice_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(PROMPT);
			setState(51);
			match(MQUES);
			setState(52);
			match(ANS_PROMPT);
			setState(53);
			match(MULT_CHOICE_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Integer_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode IQUES() { return getToken(ModelParser.IQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode INT_ANS() { return getToken(ModelParser.INT_ANS, 0); }
		public Integer_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_integer_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterInteger_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitInteger_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitInteger_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Integer_questionContext integer_question() throws RecognitionException {
		Integer_questionContext _localctx = new Integer_questionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_integer_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(PROMPT);
			setState(56);
			match(IQUES);
			setState(57);
			match(ANS_PROMPT);
			setState(58);
			match(INT_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Decimal_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode DQUES() { return getToken(ModelParser.DQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode DECI_ANS() { return getToken(ModelParser.DECI_ANS, 0); }
		public Decimal_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decimal_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterDecimal_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitDecimal_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitDecimal_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Decimal_questionContext decimal_question() throws RecognitionException {
		Decimal_questionContext _localctx = new Decimal_questionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_decimal_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(PROMPT);
			setState(61);
			match(DQUES);
			setState(62);
			match(ANS_PROMPT);
			setState(63);
			match(DECI_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Numerical_choice_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode NQUES() { return getToken(ModelParser.NQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode NUM_ANS() { return getToken(ModelParser.NUM_ANS, 0); }
		public Numerical_choice_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numerical_choice_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterNumerical_choice_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitNumerical_choice_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitNumerical_choice_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Numerical_choice_questionContext numerical_choice_question() throws RecognitionException {
		Numerical_choice_questionContext _localctx = new Numerical_choice_questionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_numerical_choice_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			match(PROMPT);
			setState(66);
			match(NQUES);
			setState(67);
			match(ANS_PROMPT);
			setState(68);
			match(NUM_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class True_false_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode TFQUES() { return getToken(ModelParser.TFQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode TRUE_FALSE_ANS() { return getToken(ModelParser.TRUE_FALSE_ANS, 0); }
		public True_false_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_true_false_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterTrue_false_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitTrue_false_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitTrue_false_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final True_false_questionContext true_false_question() throws RecognitionException {
		True_false_questionContext _localctx = new True_false_questionContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_true_false_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(PROMPT);
			setState(71);
			match(TFQUES);
			setState(72);
			match(ANS_PROMPT);
			setState(73);
			match(TRUE_FALSE_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Date_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode DATEQUES() { return getToken(ModelParser.DATEQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode DATE_DAY() { return getToken(ModelParser.DATE_DAY, 0); }
		public Date_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterDate_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitDate_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitDate_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Date_questionContext date_question() throws RecognitionException {
		Date_questionContext _localctx = new Date_questionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_date_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(PROMPT);
			setState(76);
			match(DATEQUES);
			setState(77);
			match(ANS_PROMPT);
			setState(78);
			match(DATE_DAY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Time_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode TIMEQUES() { return getToken(ModelParser.TIMEQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode TIME() { return getToken(ModelParser.TIME, 0); }
		public Time_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterTime_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitTime_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitTime_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Time_questionContext time_question() throws RecognitionException {
		Time_questionContext _localctx = new Time_questionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_time_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(PROMPT);
			setState(81);
			match(TIMEQUES);
			setState(82);
			match(ANS_PROMPT);
			setState(83);
			match(TIME);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Text_questionContext extends ParserRuleContext {
		public TerminalNode PROMPT() { return getToken(ModelParser.PROMPT, 0); }
		public TerminalNode TEXTQUES() { return getToken(ModelParser.TEXTQUES, 0); }
		public TerminalNode ANS_PROMPT() { return getToken(ModelParser.ANS_PROMPT, 0); }
		public TerminalNode TEXT_ANS() { return getToken(ModelParser.TEXT_ANS, 0); }
		public Text_questionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_text_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).enterText_question(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ModelListener ) ((ModelListener)listener).exitText_question(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ModelVisitor ) return ((ModelVisitor<? extends T>)visitor).visitText_question(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Text_questionContext text_question() throws RecognitionException {
		Text_questionContext _localctx = new Text_questionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_text_question);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(PROMPT);
			setState(86);
			match(TEXTQUES);
			setState(87);
			match(ANS_PROMPT);
			setState(88);
			match(TEXT_ANS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001b[\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0001\u0000\u0004\u0000\u001b\b\u0000\u000b\u0000\f\u0000\u001c"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0003\u0002,\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0000\u0000\f\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0000\u0001\u0001\u0000\u0002\u0003W\u0000\u0018"+
		"\u0001\u0000\u0000\u0000\u0002\u001e\u0001\u0000\u0000\u0000\u0004+\u0001"+
		"\u0000\u0000\u0000\u0006-\u0001\u0000\u0000\u0000\b2\u0001\u0000\u0000"+
		"\u0000\n7\u0001\u0000\u0000\u0000\f<\u0001\u0000\u0000\u0000\u000eA\u0001"+
		"\u0000\u0000\u0000\u0010F\u0001\u0000\u0000\u0000\u0012K\u0001\u0000\u0000"+
		"\u0000\u0014P\u0001\u0000\u0000\u0000\u0016U\u0001\u0000\u0000\u0000\u0018"+
		"\u001a\u0003\u0002\u0001\u0000\u0019\u001b\u0003\u0004\u0002\u0000\u001a"+
		"\u0019\u0001\u0000\u0000\u0000\u001b\u001c\u0001\u0000\u0000\u0000\u001c"+
		"\u001a\u0001\u0000\u0000\u0000\u001c\u001d\u0001\u0000\u0000\u0000\u001d"+
		"\u0001\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0001\u0000\u0000\u001f"+
		" \u0005\u001b\u0000\u0000 !\u0007\u0000\u0000\u0000!\u0003\u0001\u0000"+
		"\u0000\u0000\",\u0003\u0006\u0003\u0000#,\u0003\b\u0004\u0000$,\u0003"+
		"\n\u0005\u0000%,\u0003\f\u0006\u0000&,\u0003\u000e\u0007\u0000\',\u0003"+
		"\u0010\b\u0000(,\u0003\u0012\t\u0000),\u0003\u0014\n\u0000*,\u0003\u0016"+
		"\u000b\u0000+\"\u0001\u0000\u0000\u0000+#\u0001\u0000\u0000\u0000+$\u0001"+
		"\u0000\u0000\u0000+%\u0001\u0000\u0000\u0000+&\u0001\u0000\u0000\u0000"+
		"+\'\u0001\u0000\u0000\u0000+(\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000"+
		"\u0000+*\u0001\u0000\u0000\u0000,\u0005\u0001\u0000\u0000\u0000-.\u0005"+
		"\r\u0000\u0000./\u0005\u0004\u0000\u0000/0\u0005\u000e\u0000\u000001\u0005"+
		"\u0010\u0000\u00001\u0007\u0001\u0000\u0000\u000023\u0005\r\u0000\u0000"+
		"34\u0005\u0005\u0000\u000045\u0005\u000e\u0000\u000056\u0005\u000f\u0000"+
		"\u00006\t\u0001\u0000\u0000\u000078\u0005\r\u0000\u000089\u0005\u0006"+
		"\u0000\u00009:\u0005\u000e\u0000\u0000:;\u0005\u0011\u0000\u0000;\u000b"+
		"\u0001\u0000\u0000\u0000<=\u0005\r\u0000\u0000=>\u0005\u0007\u0000\u0000"+
		">?\u0005\u000e\u0000\u0000?@\u0005\u0012\u0000\u0000@\r\u0001\u0000\u0000"+
		"\u0000AB\u0005\r\u0000\u0000BC\u0005\b\u0000\u0000CD\u0005\u000e\u0000"+
		"\u0000DE\u0005\u0013\u0000\u0000E\u000f\u0001\u0000\u0000\u0000FG\u0005"+
		"\r\u0000\u0000GH\u0005\t\u0000\u0000HI\u0005\u000e\u0000\u0000IJ\u0005"+
		"\u0014\u0000\u0000J\u0011\u0001\u0000\u0000\u0000KL\u0005\r\u0000\u0000"+
		"LM\u0005\n\u0000\u0000MN\u0005\u000e\u0000\u0000NO\u0005\u0016\u0000\u0000"+
		"O\u0013\u0001\u0000\u0000\u0000PQ\u0005\r\u0000\u0000QR\u0005\u000b\u0000"+
		"\u0000RS\u0005\u000e\u0000\u0000ST\u0005\u0015\u0000\u0000T\u0015\u0001"+
		"\u0000\u0000\u0000UV\u0005\r\u0000\u0000VW\u0005\f\u0000\u0000WX\u0005"+
		"\u000e\u0000\u0000XY\u0005\u0017\u0000\u0000Y\u0017\u0001\u0000\u0000"+
		"\u0000\u0002\u001c+";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}