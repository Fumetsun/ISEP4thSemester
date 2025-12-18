#include "scomp_tests.h"

int main(void) {
	//printf("called test main\n");
	printf("calling clean_dir_test\n");
	// printf("in main TEST_FLDR: %s\n", TEST_FLDR);
	clean_dir_tester(TEST_FLDR);
	printf("\ncalling fldr_str_test\n");
	fldr_str_tester();
	printf("\ncalling create_app_folder_tester\n");
	create_app_folder_tester();
	printf("\ncalling create_out_tester\n");
	create_out_tester();
	printf("\ncalling extract_app_code_test\n");
	extract_app_code_test();
	printf("\ncalling extract_code_app_test\n");
	extract_code_app_test();
	printf("\ncalling get_config_line_arg_test\n");
	get_config_line_arg_test();
	printf("\ncalling get_job_ref_code_test\n");
	get_job_ref_code_test();
	printf("\ncalling find_data_file_test\n");
	find_data_file_test();
	printf("\ncalling get_application_name_test\n");
	get_application_name_test();
	printf("\ncalling get_arg_info_test\n");
	get_arg_info_test();
	printf("\ncalling valid_entry_args_test\n");
	valid_entry_args_test();
	return (0);
}
