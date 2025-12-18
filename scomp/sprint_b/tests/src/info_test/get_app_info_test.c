#include "scomp_tests.h"

void get_job_ref_code_test(void)
{
	char ref_code[] = "IBM-000123",
		fl[] = "../email_bot_files/1-candidate-data.txt";
	printf("calling get_job_ref_code with (%s)\n", fl);
	char *ret = get_job_ref_code(fl);
	printf("got: %s, expected: %s\n", ret, ref_code);
	if (!strcmp(ret, ref_code))
		printf("SUCCESS: get_job_ref_code passed test\n");
	else
		printf("FAILED: get_job_ref_code failed test\n");
	free(ret);
}

void find_data_file_test(void)
{
	char data_file1[] = "../email_bot_files/1-candidate-data.txt",
		 data_file2[] = "../email_bot_files/2-candidate-data.txt",
		 fl[] = "../email_bot_files";

	printf("calling find_data_file with (%s, 0)\n", fl);
	char *ret = find_data_file(fl, 0);
	printf("got: %s, expected: %s\n", ret, data_file1);
	if (!strcmp(ret, data_file1))
		printf("SUCCESS: find_data_file passed test 1\n");
	else
		printf("FAILED: find_data_file failed test 1\n");
	free(ret);

	printf("calling find_data_file with (%s, 1)\n", fl);
	ret = find_data_file(fl, 1);
	printf("got: %s, expected: %s\n", ret, data_file2);
	if (!strcmp(ret, data_file2))
		printf("SUCCESS: find_data_file passed test 2\n");
	else
		printf("FAILED: find_data_file failed test 2\n");
	free(ret);

	printf("calling find_data_file with (%s, 2)\n", fl);
	ret = find_data_file(fl, 2);
	printf("got: %s, expected: null\n", ret);
	if (!ret)
		printf("SUCCESS: find_data_file passed test 3\n");
	else
		printf("FAILED: find_data_file failed test 3\n");
}

void get_application_name_test(void)
{
	char applicant_name[] = "John Doe",
		 fl[] = "../email_bot_files/1-candidate-data.txt";
	printf("calling get_job_applicant_name with (%s)\n", fl);
	char *ret = get_applicant_name(fl);
	printf("got: %s, expected: %s\n", ret, applicant_name);
	if (!strcmp(ret, applicant_name))
		printf("SUCCESS: get_applicant_name passed test\n");
	else
		printf("FAILED: get_applicant_name failed test\n");
	free(ret);
}