grammar JobRequirementSpecification_Programmer;

start:
    headliner NEWLINE*? job_requirements;

job_requirements:
    model
    | candidate_answers;

model:
    true_false_question NEWLINE*?
    short_answer_question NEWLINE*?
    single_choice_question NEWLINE*?
    multiple_choice_question NEWLINE*?
    integer_answer_question;

candidate_answers:
    true_false_question true_false_answer NEWLINE*?
    short_answer_question short_answer_answer NEWLINE*?
    single_choice_question single_choice_answer NEWLINE*?
    multiple_choice_question multiple_choice_answer NEWLINE*?
    integer_answer_question integer_answer_answer;

headliner: '- Lead Programmer Job Requirements -';


// Questions:
true_false_question : '# Answer if the following phrase is true or false. (True | False)' NEWLINE*?
        'You have already worked in this position.' NEWLINE*?
        'Answer:';
short_answer_question : '# Enter your type of academic-degree. (None | Bachelors | Masters | PhD)' NEWLINE*?
        'Academic-Degree:';
single_choice_question : '# Select the option that answers the following question.' NEWLINE*?
        'How many team projects and teams have you lead?' NEWLINE*?
        'A - Never lead any project/team.' NEWLINE*? 'B - 1 to 3.' NEWLINE*? 'C - 4 to 5.' NEWLINE*? 'D - 5+.' NEWLINE*?
        'Answer:';
multiple_choice_question : '# Select one or more  programming languages you are proficient in (example: A, B).' NEWLINE*?
        'A - Java' NEWLINE*? 'B - Javascript' NEWLINE*? 'C - Python' NEWLINE*? 'D - C#' NEWLINE*?
        'Answer:';
integer_answer_question : '# Enter your number of years of experience. (integer)' NEWLINE*?
        'Answer:';

// Answers:
true_false_answer: (SPACE)* (TRUE_FALSE_ANSWER);
short_answer_answer: (SPACE)* (SHORT_ANSWER);
single_choice_answer: (SPACE)* (SINGLE_OPTION);
multiple_choice_answer: (SPACE)* (SINGLE_OPTION ( ',' (SPACE)* SINGLE_OPTION)*);
integer_answer_answer: (SPACE)* (INTEGER);


NEWLINE : [\r\n]+;
SPACE: ' ';
TRUE_FALSE_ANSWER: 'True' | 'T' | 'true' | 'TRUE'
                | 'False' | 'F' | 'false' | 'FALSE';
SHORT_ANSWER: 'None' | 'none' | 'NONE'
            | 'Bachelors' | 'BACHELORS' | 'bachelors'
            | 'Masters' | 'MASTERS' | 'masters'
            | 'PhD' | 'phd' | 'PHD' | 'Phd';
SINGLE_OPTION: 'A' | 'a'
            | 'B' | 'b'
            | 'C' | 'c'
            | 'D' | 'd';
INTEGER: [0-9]+;
