grammar ProgrammerInterview;

start: headliner NEWLINE*? interview;

interview: model
          | answers;


model:
                   single_choice_question NEWLINE*?
                   multiple_choice_question NEWLINE*?
                   integer_answer_question NEWLINE*?
                   decimal_answer_question NEWLINE*?
                   numerical_choice_question NEWLINE*?;



answers:
                   single_choice_question single_choice_answer NEWLINE*?
                   multiple_choice_question multiple_choice_answer NEWLINE*?
                   integer_answer_question integer_answer_answer NEWLINE*?
                   decimal_answer_question decimal_answer_answer NEWLINE*?
                   numerical_choice_question numerical_choice_answer NEWLINE*?;




single_choice_question: 'What is your level of education? (A - High School) (B - Bachelor) (C - Master) (D - Doctor) (Choose one)' NEWLINE*? 'R: ';
multiple_choice_question: 'Which of the following languages are you experienced in? (A - Java) (B - React) (C - Python) (D - Rust) (Choose however many)' NEWLINE*? 'R: ';
integer_answer_question: 'How many career years do you have under your belt?' NEWLINE*? 'R: ';
decimal_answer_question: 'What percentage of your time do you spend on coding compared to other tasks, such as debugging, code reviews, and meetings? (0-1, 76% = 0.76)' NEWLINE*? 'R: ';
numerical_choice_question: 'On a scale of 1 to 10, how comfortable overall are you with your proficiency in the languages you listed above, in a range of 3? (Example: 7-9)' NEWLINE*? 'R: ';


single_choice_answer: SINGLE_CHOICE_ANS;
multiple_choice_answer: MULT_CHOICE_ANS;
integer_answer_answer: INT_ANS;
decimal_answer_answer: DECI_ANS;
numerical_choice_answer: NUM_ANS;

MULT_CHOICE_ANS: (([A-Z]', '?)+'.'|',');
SINGLE_CHOICE_ANS: '(' [A-Z] ')';
INT_ANS : [0-9]+;
DECI_ANS: [0-9]+ '.' [0-9]+;
NUM_ANS: [0-9]+ '-' [0-9]+;
NEWLINE : '\r'? '\n';


headliner: '-Programmer Interview Model-';
