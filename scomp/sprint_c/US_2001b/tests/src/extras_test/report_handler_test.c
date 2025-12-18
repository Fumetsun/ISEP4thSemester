#include "scomp_tests.h"

void report_handler_test(char *in, char *out, char *example)
{
	FILE *fl = fopen(REPORT_FILE, "w");

	printf("%scalling report_handler(-, %s, %s, 1)%s\n",
		   YELLOW, in, out, RESET);

	report_handler(fl, in, out, 1);

	fclose(fl);

	FILE *fl_example = fopen(example, "r"),
		 *fl_output = fopen(REPORT_FILE, "r");
	char line_example[1000], line_output[1000], *ret_example, *ret_output;

	if (fl_example == NULL || fl_output == NULL)
	{
		if (fl_example != NULL)
			fclose(fl_example);
		if (fl_output != NULL)
			fclose(fl_output);
		return;
	}

	bool same = true;

	while (same)
	{
		ret_example = fgets(line_example, 1000, fl_example);
		ret_output = fgets(line_output, 1000, fl_output);

		if (ret_example == NULL || ret_output == NULL)
		{
			if (!(ret_example == NULL && ret_output == NULL))
				same = false;
			break;
		}

		if (strcmp(line_example, line_output))
			same = false;
	}

	fclose(fl_example);
	fclose(fl_output);

	if (same)
		printf("%sSUCCESS: report_handler passed test%s\n", GREEN, RESET);
	else
		printf("%sFAILED: report_handler_test failed test%s\n", RED, RESET);
}