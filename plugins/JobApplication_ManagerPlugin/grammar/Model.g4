grammar Model;

start: headliner question+;

// Generic Headliner Recognizer "-<Profession Name> Interview Model-" Ex.: -Lawyer Interview Model-
headliner: '-' WORD (' Interview Model-' | ' Requirements Model-');


// Matches Questions of Requested type by PM/Client
question: single_choice_question
        | multiple_choice_question
        | integer_question
        | decimal_question
        | numerical_choice_question
        | true_false_question
        | date_question
        | time_question
        | text_question;


// Question Patterns
single_choice_question: PROMPT SQUES ANS_PROMPT SINGLE_CHOICE_ANS;
multiple_choice_question: PROMPT MQUES ANS_PROMPT MULT_CHOICE_ANS;
integer_question: PROMPT IQUES ANS_PROMPT INT_ANS;
decimal_question: PROMPT DQUES ANS_PROMPT DECI_ANS;
numerical_choice_question: PROMPT NQUES ANS_PROMPT NUM_ANS;
true_false_question: PROMPT TFQUES ANS_PROMPT TRUE_FALSE_ANS;
date_question: PROMPT DATEQUES ANS_PROMPT DATE_DAY;
time_question: PROMPT TIMEQUES ANS_PROMPT TIME;
text_question: PROMPT TEXTQUES ANS_PROMPT TEXT_ANS;


// QUESTION IDENTIFIERS
SQUES : '<SCANS>';
MQUES : '<MCANS>';
IQUES : '<INTANS>';
DQUES  : '<DECANS>';
NQUES  : '<NUMANS>';
TFQUES : '<TFANS>';
DATEQUES : '<DATEANS>';
TIMEQUES : '<TIMEANS>';
TEXTQUES : '<TEXTANS>';

// TEXT PROMPTS
PROMPT : [0-9]+ '- ' ~[<>]+;
ANS_PROMPT : 'R: ';


// ANSWER FORMATS
MULT_CHOICE_ANS: (([A-Z]', '?)+'.'|',');
SINGLE_CHOICE_ANS: '(' [A-Z] ')';
INT_ANS : [0-9]+;
DECI_ANS: [0-9]+ '.' [0-9]+;
NUM_ANS: [0-9]+ '-' [0-9]+;
TRUE_FALSE_ANS : 'True' | 'False';
TIME: DIGIT DIGIT ':' DIGIT DIGIT;
DATE_DAY: DIGIT DIGIT '/' DIGIT DIGIT '/' DIGIT DIGIT DIGIT DIGIT;
TEXT_ANS : (([a-zA-Z]+', '?)+'.'|','){0,255};

// LETTER FOR CHOICE QUESTIONS
LETTER : [a-zA-Z];


// SKIP WHITESPACES AND NEWLINES FOR FLEXIBLE FORMATTING
WS : [ \t\r\n]+ -> skip; // skip white space
NEWLINE : [\r\n]+ -> skip; // skip newlines



// SINGLE WORD FOR JOB NAME IN HEADLINER
WORD : [a-zA-Z]+;


// SINGLE DIGIT FOR PURPOSE OF DECIMAL NUMBER
fragment DIGIT: [0-9];