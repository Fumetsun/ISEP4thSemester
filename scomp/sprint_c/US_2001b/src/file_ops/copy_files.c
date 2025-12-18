#include "scomp.h"

/**
 * @brief uses execl to call sh and use cp to copy 
 * applications from an input directory to an output directory
 * @param in directory to copy from
 * @param out directory to copy to
 * @param app_code application code of the files to copy
 */
void copy_files(char *in, char *out, char *app_code)
{
	char cmd[PATH_MAX];
	pid_t pid;
	int status;

	sprintf(cmd, "cp %s/%s-* %s", in, app_code, out);
	// printf("in copy_files, %s\n", cmd);
	pid = fork();
	if (!pid)
	{
		execl("/bin/sh", "sh", "-c", cmd, (char *)NULL);
		exit(1);
	}
	wait(&status);
}