#include "scomp_tests.h"

void copy_files_test(char *in, char *out, char *app_code)
{
	pid_t pid;
	int status;


	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);

	printf("%scalling copy_files function (%s, %s, %s)%s\n", 
	YELLOW, in, out, app_code, RESET);
 
	printf("running command: %s\n", LS_FILE);
	pid = fork();
	if (!pid)
		execl("/bin/sh", "sh", "-c", LS_FILE, (char *)NULL);
	wait(&status);

	if (check_files_partial(out, -1, atoi(app_code)))
		printf("%sSUCCESS: files with application code 1 were successfully deleted%s\n", GREEN, RESET);
	else
		printf("%sERROR: files were not deleted%s\n", RED, RESET);

	clean_dir(out);
}