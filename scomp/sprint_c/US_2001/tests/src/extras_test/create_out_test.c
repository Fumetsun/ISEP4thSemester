#include "scomp_tests.h"

void fldr_str_tester(void)
{
	char test_dir[] = "test", test_subdir[] = "subdir",
		 dir_result[] = "test/subdir";

	char *test = fldr_str(test_dir, test_subdir);

	printf("called fldr_str and got: %s, expected: %s\n", test, dir_result);
	if (!strcmp(test, dir_result))
		printf("SUCCESS: fldr_str passed test\n");
	else
		printf("FAIL: fldr_str failed test\n");
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
	printf("calling create_app_folder function with (%s, %s)\n", TEST_FLDR, test);
	if (!create_app_folder(TEST_FLDR, test))
	{
		printf("function failed, returning\n");
		return;
	}

	if (fldr_checker_subdir(TEST_FLDR, test))
		printf("SUCCESS: create_app_folder passed test\n");
	else
	{
		printf("create_app_folder failed test, returning\n");
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
	printf("calling create_out function with (%s)\n", test);
	if (!create_out(test))
	{
		printf("function failed, returning\n");
		return;
	}

	if (fldr_checker_subdir(TEST_FLDR, "new_subdirectory"))
		printf("SUCCESS: create_out passed test\n");
	else
	{
		printf("FAIL: create_out failed test, returning\n");
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