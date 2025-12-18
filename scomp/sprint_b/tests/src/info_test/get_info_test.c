#include "scomp_tests.h"

void get_arg_info_test(void)
{
	char *args[] = {"app", "config_test.txt"},
		 in_expected[] = "inputDir",
		 out_expected[] = "outputDir",
		 *in, *out;
	int n;
	printf("Calling get_arg_info\n");
	get_arg_info(2, args, &n, &out, &in);

	if (n == 2 && !strcmp(out, out_expected) && !strcmp(in, in_expected))
		printf("SUCCESS: get_arg_info passed test\n");
	else
		printf("FAILED: get_arg_info failed test\n");
	free(out);
	free(in);
}