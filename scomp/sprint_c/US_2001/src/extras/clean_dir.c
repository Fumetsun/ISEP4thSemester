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
	if (!cleaner) {
		execl("/bin/sh", "sh", "-c", cmd, (char *) NULL);
	}
	wait(&status);
}
