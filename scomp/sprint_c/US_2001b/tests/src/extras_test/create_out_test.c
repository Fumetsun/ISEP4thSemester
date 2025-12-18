#include "scomp_tests.h"

void fldr_str_tester(void)
{
	char test_dir[] = "test", test_subdir[] = "subdir",
		 dir_result[] = "test/subdir";

	char *test = fldr_str(test_dir, test_subdir);

	printf("%scalled fldr_str and got: %s, expected: %s%s\n", MAGENTA, test, dir_result, RESET);

	if (!strcmp(test, dir_result))
		printf("%sSUCCESS: fldr_str passed test%s\n", GREEN, RESET);
	else
		printf("%sFAIL: fldr_str failed test%s\n", RED, RESET);
	free(test);
}

void create_app_folder_tester(void)
{
	pid_t pid;
	int status;
	char test[] = "new_subdirectory";

	printf("running command: %s, verify that directory is empty\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("%scalling create_app_folder function with (%s, %s)%s\n", YELLOW, TEST_FLDR, test, RESET);
	if (!create_app_folder(TEST_FLDR, test))
	{
		printf("function failed, returning\n");
		return;
	}

	if (fldr_checker_subdir(TEST_FLDR, test))
		printf("%sSUCCESS: create_app_folder passed test%s\n", GREEN, RESET);
	else
	{
		printf("%sFAIL: create_app_folder failed test, returning%s\n", RED, RESET);
		return;
	}

	printf("running command: %s, verify new subdir created\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("calling clean_dir function\n");
	clean_dir(TEST_FLDR);
}

void create_out_tester(void)
{
	pid_t pid;
	int status;
	char test[] = "test_files/new_subdirectory";

	printf("running command: %s, verify that directory is empty\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("%scalling create_out function with (%s)%s\n", YELLOW, test, RESET);
	if (!create_out(test))
	{
		printf("function failed, returning\n");
		return;
	}

	if (fldr_checker_subdir(TEST_FLDR, "new_subdirectory"))
		printf("%sSUCCESS: create_out passed test%s\n", GREEN, RESET);
	else
	{
		printf("%sFAIL: create_out failed test, returning%s\n", RED, RESET);
		return;
	}

	printf("running command: %s, verify new subdir created\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("calling clean_dir function\n");
	clean_dir(TEST_FLDR);
}