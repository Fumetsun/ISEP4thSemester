#ifndef SCOMP_TESTS_H
#define SCOMP_TESTS_H

#include "scomp.h"

#define RESET "\033[0m"
#define RED "\033[0;31m"
#define GREEN "\033[0;32m"
#define YELLOW "\033[0;33m"
#define BLUE "\033[0;34m"
#define MAGENTA "\033[0;35m"
#define CYAN "\033[0;36m"
#define TEST_FLDR "test_files"
#define CP_FILE_REGULAR "cp ../email_bot_files/regular/* test_files"
#define CP_FILE_1 "cp ../email_bot_files/regular/1* test_files"
#define CP_FILE_2 "cp ../email_bot_files/regular/2* test_files"
#define OG_FILE_DIR "../email_bot_files/regular"
#define LS_FILE "ls test_files"
#define REPORT_EXAMPLE "report_output_example.txt"
#define REPORT_FILE "report_test_file.txt"

// --- extras_test ---


// create_out_test.c
void fldr_str_tester(void);
void create_app_folder_tester(void);
void create_out_tester(void);

// extras_test.c
void extract_app_code_test(void);
void extract_code_app_test(void);
void get_config_line_arg_test(void);

// report_handler_test.c
void report_handler_test(char *in, char *out, char *example);

// --- file_ops_test

// clean_dir_test.c
void clean_dir_tester(const char *dir);
void clean_files_tester(const char *dir);

// copy_files_test.c
void copy_files_test(char *in, char *out, char *app_code);

// --- info_test ---

// get_app_info_test.c
void get_job_ref_code_test(void);
void find_data_file_test(void);
void get_application_name_test(void);
void get_data_file_of_app_test(char *dir);
void count_applications_test(char *dir);
void fill_codes_test(char *dir);

// get_info_test.c
void get_arg_info_test(void);

// --- validate_test ---

// validate_test.c
void valid_entry_args_test(void);

// --- helpers ---

//  folder_check.c
bool fldr_checker_subdir(const char *d, const char *subdir);
bool check_fls(const char *d);
bool check_files_partial(const char *d, int code1, int code2);

#endif