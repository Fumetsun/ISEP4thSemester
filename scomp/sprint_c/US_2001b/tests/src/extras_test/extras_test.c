#include "scomp_tests.h"

void extract_app_code_test(void)
{
	char str[] = "email_bot_files/1-candidate-data.txt";
	printf("%scalling extract_app_code with (%s)%s\n", YELLOW, str, RESET);
	int n = extract_app_code(str);
	printf("%sgot %d, expected 1%s\n", MAGENTA, n, RESET);
	if (n == 1)
		printf("%sSUCCESS: extract_app_code passed test%s\n", GREEN, RESET);
	else
		printf("%sFAIL: extract_app_code failed test%s\n", RED, RESET);
}

void extract_code_app_test(void)
{
	char str[] = "email_bot_files/1-candidate-data.txt";
	printf("%scalling extract_code_app with (%s)%s\n", YELLOW, str, RESET);
	char *n = extract_code_app(str);
	printf("%sgot %s, expected 1%s\n", MAGENTA, n, RESET);
	if (!strcmp(n, "1"))
		printf("%sSUCCESS: extract_code_app passed test%s\n", GREEN, RESET);
	else
		printf("%sFAIL: extract_code_app failed test%s\n", RED, RESET);
	free(n);
}

void get_config_line_arg_test(void)
{
	char str[] = "Input directory: inputDir\n",
		 expected[] = "inputDir";
	printf("%scalling get_config_line_arg with %s%s\n", YELLOW, str, RESET);
	char *ret = get_config_line_arg(str);
	printf("%sgot %s, expected %s%s\n", MAGENTA, ret, expected, RESET);
	if (!strcmp(ret, expected))
		printf("%sSUCCESS: get_config_line_arg passed test%s\n", GREEN, RESET);
	else
		printf("%sFAIL: get_config_line_arg failed test%s\n", RED, RESET);
	free(ret);
}