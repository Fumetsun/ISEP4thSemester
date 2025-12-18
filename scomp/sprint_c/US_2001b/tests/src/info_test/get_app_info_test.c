#include "scomp_tests.h"

void get_job_ref_code_test(void)
{
	char ref_code[] = "IBM-000123",
		 fl[] = "../email_bot_files/regular/1-candidate-data.txt";
	printf("%scalling get_job_ref_code with (%s)%s\n", YELLOW, fl, RESET);
	char *ret = get_job_ref_code(fl);
	printf("%sgot: %s, expected: %s%s\n", MAGENTA, ret, ref_code, RESET);
	if (!strcmp(ret, ref_code))
		printf("%sSUCCESS: get_job_ref_code passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: get_job_ref_code failed test%s\n", RED, RESET);
	free(ret);
}

void find_data_file_test(void)
{
	char data_file1[] = "../email_bot_files/regular/1-candidate-data.txt",
		 data_file2[] = "../email_bot_files/regular/2-candidate-data.txt",
		 fl[] = "../email_bot_files/regular";

	printf("%scalling find_data_file with (%s, 0)%s\n", YELLOW, fl, RESET);
	char *ret = find_data_file(fl, 0);
	printf("%sgot: %s, expected: %s%s\n", MAGENTA, ret, data_file1, RESET);
	if (!strcmp(ret, data_file1))
		printf("%sSUCCESS: find_data_file passed test 1%s\n", GREEN, RESET);
	else
		printf("%sFAILED: find_data_file failed test 1%s\n", RED, RESET);
	free(ret);

	printf("%scalling find_data_file with (%s, 1)%s\n", YELLOW, fl, RESET);
	ret = find_data_file(fl, 1);
	printf("%sgot: %s, expected: %s%s\n", MAGENTA, ret, data_file2, RESET);
	if (!strcmp(ret, data_file2))
		printf("%sSUCCESS: find_data_file passed test 2%s\n", GREEN, RESET);
	else
		printf("%sFAILED: find_data_file failed test 2%s\n", RED, RESET);
	free(ret);

	printf("%scalling find_data_file with (%s, 2)%s\n", YELLOW, fl, RESET);
	ret = find_data_file(fl, 2);
	printf("%sgot: %s, expected: null%s\n", MAGENTA, ret, RESET);
	if (!ret)
		printf("%sSUCCESS: find_data_file passed test 3%s\n", GREEN, RESET);
	else
		printf("%sFAILED: find_data_file failed test 3%s\n", RED, RESET);
}

void get_application_name_test(void)
{
	char applicant_name[] = "John Doe",
		 fl[] = "../email_bot_files/regular/1-candidate-data.txt";
	printf("%scalling get_job_applicant_name with (%s)%s\n", YELLOW, fl, RESET);
	char *ret = get_applicant_name(fl);
	printf("%sgot: %s, expected: %s%s\n", MAGENTA, ret, applicant_name, RESET);
	if (!strcmp(ret, applicant_name))
		printf("%sSUCCESS: get_applicant_name passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: get_applicant_name failed test%s\n", RED, RESET);
	free(ret);
}

void get_data_file_of_app_test(char *dir)
{
	char to_find[] = "../email_bot_files/regular/2-candidate-data.txt";
	int code = 2;

	printf("%scalling get_data_file_of_app(%s, %d)%s\n",
		   YELLOW, dir, code, RESET);

	char *ret = get_data_file_of_app(dir, code);

	printf("%sgot: %s, expected: %s%s\n", MAGENTA, ret, to_find, RESET);

	if (!strcmp(ret, to_find))
		printf("%sSUCCESS: get_data_file_of_app passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: get_data_file_of_app failed test%s\n", RED, RESET);

	free(ret);
}

void count_applications_test(char *dir)
{
	int expected = 2;

	printf("%scalling count_applications(%s)%s\n", YELLOW, dir, RESET);

	int get = count_applications(dir);

	printf("%sgot: %d, expected: %d%s\n", MAGENTA, get, expected, RESET);

	if (get == expected)
		printf("%sSUCCESS: count_applications passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: count_applications failed test%s\n", RED, RESET);
}

void fill_codes_test(char *dir)
{
	bool code1 = false, code2 = false;
	int arr[2];

	printf("%scalling fill_codes%s\n", YELLOW, RESET);

	fill_codes(dir, arr, 2);

	for (int i = 0; i < 2; i++) {
		if (!code1 && arr[i] == 1)
			code1 = true;
		else if (!code2 && arr[i] == 2)
			code2 = true;
	}

	if (code1 && code2)
		printf("%sSUCCESS: fill_codes passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: fill_codes failed test%s\n", RED, RESET);
}