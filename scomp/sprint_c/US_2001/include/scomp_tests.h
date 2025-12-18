#ifndef SCOMP_TESTS_H
#define SCOMP_TESTS_H

#include "scomp.h"

#define TEST_FLDR "test_files"
#define CP_FILE "cp ../email_bot_files/* test_files"
#define LS_FILE "ls test_files"

// --- extras_test ---

// clean_dir_test.c
void clean_dir_tester(const char *dir);

// create_out_test.c
void fldr_str_tester(void);
void create_app_folder_tester(void);
void create_out_tester(void);

// extras_test.c
void extract_app_code_test(void);
void extract_code_app_test(void);
void get_config_line_arg_test(void);

// --- info_test ---

// get_app_info_test.c
void get_job_ref_code_test(void);
void find_data_file_test(void);
void get_application_name_test(void);

// get_info_test.c
void get_arg_info_test(void);

// --- validate_test ---

// validate_test.c
void valid_entry_args_test(void);

// --- helpers ---

//  folder_check.c
bool fldr_checker_subdir(const char *d, const char *subdir);
bool check_fls(const char *d);

#endif