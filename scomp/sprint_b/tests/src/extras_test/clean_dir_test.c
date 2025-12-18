#include "scomp_tests.h"

void clean_dir_tester(const char *dir)
{
	pid_t pid;
	int status;

	printf("running command: %s\n", CP_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", CP_FILE, (char *)NULL);
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
		printf("SUCCESS: files were successfully deleted\n");
}
