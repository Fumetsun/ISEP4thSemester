grammar JobRequirement_LifeGuard;

start:
    headliner NEWLINE*? job_requirements;

job_requirements:
    model
    | candidate_answers;

model:
    decimal_answer_question NEWLINE*?
    date_question NEWLINE*?
    time_question NEWLINE*?
    numeric_scale_question NEWLINE*?;

candidate_answers:
    decimal_answer_question decimal_answer_answer NEWLINE*?
    date_question date_answer NEWLINE*?
    time_question time_answer NEWLINE*?
    numeric_scale_question numeric_scale_answer NEWLINE*?;

headliner: '- Pool LifeGuard Job Requirements -';


// Questions:
decimal_answer_question : '# Enter your height in meters. (example: 1,30)' NEWLINE*?
        'Height:';
date_question : '# Enter your date of birth. (dd/MM/yyyy)' NEWLINE*?
        'Date of Birth:';
time_question : '# Enter the hour you are applying to work on. (HH:mm)' NEWLINE*?
        'Hour:';
numeric_scale_question : '# From 1-10, enter how would you qualify yourself in ability to swim. (integer)' NEWLINE*?
        'Answer:';

// Answers:
decimal_answer_answer: (SPACE)* (DECIMAL_ANSWER);
date_answer: (SPACE)* (DATE_ANSWER);
time_answer: (SPACE)* (TIME_ANSWER);
numeric_scale_answer: (SPACE)* (NUMERIC_SCALE_ANSWER);


NEWLINE : [\r\n]+;
SPACE: ' ';
DECIMAL_ANSWER: [0-2] ',' [0-9][0-9];
DATE_ANSWER: DAY  MONTH  YEAR;
DAY: ('1'[0-9] | '2'[0-9] | '3'[0-1] | INTEGER) '/';
MONTH: (INTEGER | '1'[0-2]) '/';
YEAR: '19'[3-9][0-9] | '20'[0-1][0-9];
TIME_ANSWER: HOURS  MINUTES;
HOURS: (INTEGER | '1'[0-9] | '2'[0-4]) ':';
MINUTES: '0'[0-9] | [1-6][0-9];
NUMERIC_SCALE_ANSWER: INTEGER | '10';
INTEGER:('0')? [1-9];