#include "scomp_tests.h"

void get_arg_info_test(void)
{
	char *args[] = {"app", "config_test.txt"},
		 in_expected[] = "inputDir",
		 out_expected[] = "outputDir",
		 *in, *out;
	int n;
	printf("%sCalling get_arg_info\n%s", YELLOW, RESET);
	get_arg_info(2, args, &n, &out, &in);

	if (n == 2 && !strcmp(out, out_expected) && !strcmp(in, in_expected))
		printf("%sSUCCESS: get_arg_info passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: get_arg_info failed test%s\n", RED, RESET);
		
	free(out);
	free(in);
}