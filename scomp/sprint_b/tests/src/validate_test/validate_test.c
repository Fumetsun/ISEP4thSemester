#include "scomp_tests.h"

void valid_entry_args_test(void)
{
	char *valid_av[] = {"app", "../inputDir", "../outputDir", "4"},
		 *invalid_av1[] = {"app", "inputDir", "../outputDir", "4"},
		 *invalid_av2[] = {"app", "../inputDir", "outputDir/", "4"},
		 *invalid_av3[] = {"app", "../inputDir", "../outputDir", "-4"};
	
	printf("testing valid_entry_args\n");

	if (valid_entry_args(4, valid_av))
		printf("SUCCESS: valid_entry_args passed test 1\n");
	else
		printf("FAILED: valid_entry_args failed test 1\n");
	if (!valid_entry_args(4, invalid_av1))
		printf("SUCCESS: valid_entry_args passed test 2\n");
	else
		printf("FAILED: valid_entry_args failed test 2\n");
	if (!valid_entry_args(4, invalid_av2))
		printf("SUCCESS: valid_entry_args passed test 3\n");
	else
		printf("FAILED: valid_entry_args failed test 3\n");
	if (!valid_entry_args(4, invalid_av3))
		printf("SUCCESS: valid_entry_args passed test 4\n");
	else
		printf("FAILED: valid_entry_args failed test 4\n");
}