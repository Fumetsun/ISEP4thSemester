#include "scomp_tests.h"

int main(void) {
	//printf("%scalled test main%s\n", CYAN, RESET);
	printf("%scalling clean_dir_test%s\n", CYAN, RESET);
	// printf("%sin main TEST_FLDR: %s\n", TEST_FLDR);
	clean_dir_tester(TEST_FLDR);
	
	printf("%scalling clean_files_tester%s\n", CYAN, RESET);
	clean_files_tester(TEST_FLDR);
	
	printf("%s\ncalling fldr_str_test%s\n", CYAN, RESET);
	fldr_str_tester();
	
	printf("%s\ncalling create_app_folder_tester%s\n", CYAN, RESET);
	create_app_folder_tester();
	
	printf("%s\ncalling create_out_tester%s\n", CYAN, RESET);
	create_out_tester();
	
	printf("%s\ncalling extract_app_code_test%s\n", CYAN, RESET);
	extract_app_code_test();
	
	printf("%s\ncalling extract_code_app_test%s\n", CYAN, RESET);
	extract_code_app_test();
	
	printf("%s\ncalling get_config_line_arg_test%s\n", CYAN, RESET);
	get_config_line_arg_test();
	
	printf("%s\ncalling get_job_ref_code_test%s\n", CYAN, RESET);
	get_job_ref_code_test();
	
	printf("%s\ncalling find_data_file_test%s\n", CYAN, RESET);
	find_data_file_test();
	
	printf("%s\ncalling get_application_name_test%s\n", CYAN, RESET);
	get_application_name_test();
	
	printf("%s\ncalling get_arg_info_test%s\n", CYAN, RESET);
	get_arg_info_test();
	
	printf("%s\ncalling valid_entry_args_test%s\n", CYAN, RESET);
	valid_entry_args_test();
	
	printf("%s\ncalling get_data_file_of_app_test%s\n", CYAN, RESET);
	get_data_file_of_app_test(OG_FILE_DIR);
	
	printf("%s\ncalling count_applications_test%s\n", CYAN, RESET);
	count_applications_test(OG_FILE_DIR);

	printf("%s\ncalling fill_codes_test%s\n", CYAN, RESET);
	fill_codes_test(OG_FILE_DIR);

	printf("%s\ncalling report_handler_test%s\n", CYAN, RESET);
	report_handler_test(OG_FILE_DIR, "outputDir", REPORT_EXAMPLE);

	return (0);
}
