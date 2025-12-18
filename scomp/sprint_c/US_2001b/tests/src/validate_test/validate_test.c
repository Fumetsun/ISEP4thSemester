#include "scomp_tests.h"

void valid_entry_args_test(void)
{
	char *valid_av[] = {"app", "../inputDir", "../outputDir", "4"},
		 *invalid_av1[] = {"app", "inputDir", "../outputDir", "4"},
		 *invalid_av2[] = {"app", "../inputDir", "outputDir/", "4"},
		 *invalid_av3[] = {"app", "../inputDir", "../outputDir", "-4"};
	
	printf("testing valid_entry_args\n");

	if (valid_entry_args(4, valid_av))
		printf("%sSUCCESS: valid_entry_args passed test 1%s\n", GREEN, RESET);
	else
		printf("%sFAILED: valid_entry_args failed test 1%s\n", RED, RESET);
	if (!valid_entry_args(4, invalid_av1))
		printf("%sSUCCESS: valid_entry_args passed test 2%s\n", GREEN, RESET);
	else
		printf("%sFAILED: valid_entry_args failed test 2%s\n", RED, RESET);
	if (!valid_entry_args(4, invalid_av2))
		printf("%sSUCCESS: valid_entry_args passed test 3%s\n", GREEN, RESET);
	else
		printf("%sFAILED: valid_entry_args failed test 3%s\n", RED, RESET);
	if (!valid_entry_args(4, invalid_av3))
		printf("%sSUCCESS: valid_entry_args passed test 4%s\n", GREEN, RESET);
	else
		printf("%sFAILED: valid_entry_args failed test 4%s\n", RED, RESET);
}