#include "scomp_tests.h"

void extract_app_code_test(void)
{
	char str[] = "email_bot_files/1-candidate-data.txt";
	printf("calling extract_app_code with (%s)\n", str);
	int n = extract_app_code(str);
	printf("got %d, expected 1\n", n);
	if (n == 1)
		printf("SUCCESS: extract_app_code passed test\n");
	else
		printf("FAIL: extract_app_code failed test\n");
}

void extract_code_app_test(void)
{
	char str[] = "email_bot_files/1-candidate-data.txt";
	printf("calling extract_code_app with (%s)\n", str);
	char *n = extract_code_app(str);
	printf("got %s, expected 1\n", n);
	if (!strcmp(n, "1"))
		printf("SUCCESS: extract_code_app passed test\n");
	else
		printf("FAIL: extract_code_app failed test\n");
	free(n);
}

void get_config_line_arg_test(void)
{
	char str[] = "Input directory: inputDir\n",
		 expected[] = "inputDir";
	printf("calling get_config_line_arg with %s", str);
	char *ret = get_config_line_arg(str);
	printf("got %s, expected %s\n", ret, expected);
	if (!strcmp(ret, expected))
		printf("SUCCESS: get_config_line_arg passed test\n");
	else
		printf("FAIL: get_config_line_arg failed test\n");
	free(ret);
}