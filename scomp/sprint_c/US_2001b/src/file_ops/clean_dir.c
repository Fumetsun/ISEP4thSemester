#include "scomp.h"

/**
 * @brief uses execl to call rm -rf to clean
 * everything inside a directory
 * @param dir directory to clean
 */
void clean_dir(const char *dir)
{
	pid_t cleaner;
	int status;
	char cmd[PATH_MAX + 13];

	sprintf(cmd, "rm -rf %s/*", dir);
	cleaner = fork();
	if (!cleaner)
	{
		execl("/bin/sh", "sh", "-c", cmd, (char *)NULL);
	}
	wait(&status);
}

/**
 * @brief uses execl to remove all files starting with code from directory dir
 * @param dir directory to clean
 * @param code application code which files will start with
 */
void clean_files(const char *dir, int code)
{
	pid_t cleaner;
	int status;
	char cmd[PATH_MAX + 13];

	sprintf(cmd, "rm -rf %s/%d-*", dir, code);
	// printf("in clean_files %s\n", cmd);
	cleaner = fork();
	if (!cleaner)
	{
		execl("/bin/sh", "sh", "-c", cmd, (char *)NULL);
	}
	wait(&status);
}
