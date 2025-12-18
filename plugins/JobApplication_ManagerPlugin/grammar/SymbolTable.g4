grammar SymbolTable;

start: headliner rules;

headliner: TEXT;

rules: question+;

question: QUEST_TYPE_SYMBOL '|' TEXT (answer)+;

answer: '|' (TEXT|NUMBER) '_' NUMBER;

// QUESTION IDENTIFIERS
QUEST_TYPE_SYMBOL: '<SCANS>'
                  | '<MCANS>'
                  | '<INTANS>'
                  | '<DECANS>'
                  | '<NUMANS>'
                  | '<TFANS>'
                  | '<DATEANS>'
                  | '<TIMEANS>'
                  | '<TEXTANS>';



// NUMERIC VALUES
NUMBER: [0-9]+;

TEXT: ~[<>|\r\n_]+;

// LETTER FOR CHOICE QUESTIONS
LETTER: [a-zA-Z];


// SKIP WHITESPACES AND NEWLINES FOR FLEXIBLE FORMATTING
WS: [ \t]+ -> skip;
NL: [\r\n]+ -> skip;
