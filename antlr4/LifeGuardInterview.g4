grammar LifeGuardInterview;

start: headliner NEWLINE*? interview;

interview: model
          | answers;

model:
                   true_false_question NEWLINE*?
                   date_question NEWLINE*?
                   time_question NEWLINE*?
                   short_text_question NEWLINE*?;


answers:
                   true_false_question true_false_answer NEWLINE*?
                   date_question date_answer NEWLINE*?
                   time_question time_answer NEWLINE*?
                   short_text_question short_text_answer NEWLINE*?;


headliner: '-Programmer Interview Model-';

true_false_question: 'In your experience you would say that a drowning person is not dangerous to the lifeguard. (T/F)' NEWLINE*? 'R: ';
date_question: 'What is your date of birth? (dd/MM/yyyy)' NEWLINE*? 'R: ';
time_question: 'What time can you come in during the civil work week, Monday through Friday? (HH:mm)' NEWLINE*? 'R: ';
short_text_question: 'Can you describe yourself in 5 words or less? (Text Answer -> Format: Word, Word, Word.)' NEWLINE*? 'R: ';


true_false_answer: TRUE_FALSE_ANS;
date_answer: DATE_DAY;
time_answer: TIME;
short_text_answer: TEXT_ANS;


TRUE_FALSE_ANS : 'True' | 'False';
TIME: DIGIT DIGIT ':' DIGIT DIGIT;
DATE_DAY: DIGIT DIGIT '/' DIGIT DIGIT '/' DIGIT DIGIT DIGIT DIGIT;
TEXT_ANS : (([a-zA-Z]+', '?)+'.'|',');
NEWLINE : '\r'? '\n';


fragment DIGIT: [0-9];

