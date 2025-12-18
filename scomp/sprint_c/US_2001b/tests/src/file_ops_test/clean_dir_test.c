#include "scomp_tests.h"

void clean_dir_tester(const char *dir)
{
	pid_t pid;
	int status;

	printf("running command: %s\n", CP_FILE_REGULAR);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", CP_FILE_REGULAR, (char *)NULL);
	wait(&status);
	if (!check_fls(dir))
	{
		printf("no files were created, exiting\n");
		return;
	}
	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("calling clean_dir function\n");
	clean_dir(dir);
	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	if (!check_fls(dir))
		printf("%sSUCCESS: files were successfully deleted%s\n", GREEN, RESET);
	else
		printf("%sERROR: files were not deleted%s\n", RED, RESET);
}

void clean_files_tester(const char *dir)
{
	pid_t pid;
	int status;

	printf("running command: %s\n", CP_FILE_REGULAR);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", CP_FILE_REGULAR, (char *)NULL);
	wait(&status);
	if (!check_fls(dir))
	{
		printf("no files were created, exiting\n");
		return;
	}
	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	printf("%scalling clean_files function to clean code 1%s\n", YELLOW, RESET);
	clean_files(dir, 1);
	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);
	if (check_files_partial(dir, 1, 2))
		printf("%sSUCCESS: files with application code 1 were successfully deleted%s\n", GREEN, RESET);
	else
		printf("%sERROR: files were not deleted%s\n", RED, RESET);
	
	clean_dir(dir);
}
