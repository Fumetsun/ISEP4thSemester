// Generated from C:/Users/alfre/Desktop/Plugins/InterviewModel/grammar/SymbolTable.g4 by ANTLR 4.13.1
package org.antlr4Generated.symbolTable;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SymbolTableLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, QUEST_TYPE_SYMBOL=3, NUMBER=4, TEXT=5, LETTER=6, WS=7, 
		NL=8;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "QUEST_TYPE_SYMBOL", "NUMBER", "TEXT", "LETTER", "WS", 
			"NL"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'|'", "'_'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "QUEST_TYPE_SYMBOL", "NUMBER", "TEXT", "LETTER", "WS", 
			"NL"
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


	public SymbolTableLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SymbolTable.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\by\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"^\b\u0002\u0001\u0003\u0004\u0003a\b\u0003\u000b\u0003\f\u0003b\u0001"+
		"\u0004\u0004\u0004f\b\u0004\u000b\u0004\f\u0004g\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0004\u0006m\b\u0006\u000b\u0006\f\u0006n\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0004\u0007t\b\u0007\u000b\u0007\f\u0007u\u0001\u0007"+
		"\u0001\u0007\u0000\u0000\b\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0001\u0000\u0005\u0001\u000009\u0006"+
		"\u0000\n\n\r\r<<>>__||\u0002\u0000AZaz\u0002\u0000\t\t  \u0002\u0000\n"+
		"\n\r\r\u0084\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000"+
		"\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000"+
		"\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000"+
		"\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000"+
		"\u0001\u0011\u0001\u0000\u0000\u0000\u0003\u0013\u0001\u0000\u0000\u0000"+
		"\u0005]\u0001\u0000\u0000\u0000\u0007`\u0001\u0000\u0000\u0000\te\u0001"+
		"\u0000\u0000\u0000\u000bi\u0001\u0000\u0000\u0000\rl\u0001\u0000\u0000"+
		"\u0000\u000fs\u0001\u0000\u0000\u0000\u0011\u0012\u0005|\u0000\u0000\u0012"+
		"\u0002\u0001\u0000\u0000\u0000\u0013\u0014\u0005_\u0000\u0000\u0014\u0004"+
		"\u0001\u0000\u0000\u0000\u0015\u0016\u0005<\u0000\u0000\u0016\u0017\u0005"+
		"S\u0000\u0000\u0017\u0018\u0005C\u0000\u0000\u0018\u0019\u0005A\u0000"+
		"\u0000\u0019\u001a\u0005N\u0000\u0000\u001a\u001b\u0005S\u0000\u0000\u001b"+
		"^\u0005>\u0000\u0000\u001c\u001d\u0005<\u0000\u0000\u001d\u001e\u0005"+
		"M\u0000\u0000\u001e\u001f\u0005C\u0000\u0000\u001f \u0005A\u0000\u0000"+
		" !\u0005N\u0000\u0000!\"\u0005S\u0000\u0000\"^\u0005>\u0000\u0000#$\u0005"+
		"<\u0000\u0000$%\u0005I\u0000\u0000%&\u0005N\u0000\u0000&\'\u0005T\u0000"+
		"\u0000\'(\u0005A\u0000\u0000()\u0005N\u0000\u0000)*\u0005S\u0000\u0000"+
		"*^\u0005>\u0000\u0000+,\u0005<\u0000\u0000,-\u0005D\u0000\u0000-.\u0005"+
		"E\u0000\u0000./\u0005C\u0000\u0000/0\u0005A\u0000\u000001\u0005N\u0000"+
		"\u000012\u0005S\u0000\u00002^\u0005>\u0000\u000034\u0005<\u0000\u0000"+
		"45\u0005N\u0000\u000056\u0005U\u0000\u000067\u0005M\u0000\u000078\u0005"+
		"A\u0000\u000089\u0005N\u0000\u00009:\u0005S\u0000\u0000:^\u0005>\u0000"+
		"\u0000;<\u0005<\u0000\u0000<=\u0005T\u0000\u0000=>\u0005F\u0000\u0000"+
		">?\u0005A\u0000\u0000?@\u0005N\u0000\u0000@A\u0005S\u0000\u0000A^\u0005"+
		">\u0000\u0000BC\u0005<\u0000\u0000CD\u0005D\u0000\u0000DE\u0005A\u0000"+
		"\u0000EF\u0005T\u0000\u0000FG\u0005E\u0000\u0000GH\u0005A\u0000\u0000"+
		"HI\u0005N\u0000\u0000IJ\u0005S\u0000\u0000J^\u0005>\u0000\u0000KL\u0005"+
		"<\u0000\u0000LM\u0005T\u0000\u0000MN\u0005I\u0000\u0000NO\u0005M\u0000"+
		"\u0000OP\u0005E\u0000\u0000PQ\u0005A\u0000\u0000QR\u0005N\u0000\u0000"+
		"RS\u0005S\u0000\u0000S^\u0005>\u0000\u0000TU\u0005<\u0000\u0000UV\u0005"+
		"T\u0000\u0000VW\u0005E\u0000\u0000WX\u0005X\u0000\u0000XY\u0005T\u0000"+
		"\u0000YZ\u0005A\u0000\u0000Z[\u0005N\u0000\u0000[\\\u0005S\u0000\u0000"+
		"\\^\u0005>\u0000\u0000]\u0015\u0001\u0000\u0000\u0000]\u001c\u0001\u0000"+
		"\u0000\u0000]#\u0001\u0000\u0000\u0000]+\u0001\u0000\u0000\u0000]3\u0001"+
		"\u0000\u0000\u0000];\u0001\u0000\u0000\u0000]B\u0001\u0000\u0000\u0000"+
		"]K\u0001\u0000\u0000\u0000]T\u0001\u0000\u0000\u0000^\u0006\u0001\u0000"+
		"\u0000\u0000_a\u0007\u0000\u0000\u0000`_\u0001\u0000\u0000\u0000ab\u0001"+
		"\u0000\u0000\u0000b`\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000"+
		"c\b\u0001\u0000\u0000\u0000df\b\u0001\u0000\u0000ed\u0001\u0000\u0000"+
		"\u0000fg\u0001\u0000\u0000\u0000ge\u0001\u0000\u0000\u0000gh\u0001\u0000"+
		"\u0000\u0000h\n\u0001\u0000\u0000\u0000ij\u0007\u0002\u0000\u0000j\f\u0001"+
		"\u0000\u0000\u0000km\u0007\u0003\u0000\u0000lk\u0001\u0000\u0000\u0000"+
		"mn\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000no\u0001\u0000\u0000"+
		"\u0000op\u0001\u0000\u0000\u0000pq\u0006\u0006\u0000\u0000q\u000e\u0001"+
		"\u0000\u0000\u0000rt\u0007\u0004\u0000\u0000sr\u0001\u0000\u0000\u0000"+
		"tu\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000uv\u0001\u0000\u0000"+
		"\u0000vw\u0001\u0000\u0000\u0000wx\u0006\u0007\u0000\u0000x\u0010\u0001"+
		"\u0000\u0000\u0000\u0006\u0000]bgnu\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}