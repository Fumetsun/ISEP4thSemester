#include "scomp.h"

/**
 * @brief creates and returns a new path composed of
 * a directory (out) and its subdirectory (code)
 * @param directory in which to create new folder
 * @param code new folder to create
 * @return new path
 */
char *fldr_str(char *out, char *code)
{
	char *fldr, path[PATH_MAX];
	size_t s;

	sprintf(path, "%s/%s", out, code);
	int i = -1;
	while (path[++i] && path[i] != '\n')
		;
	if (path[i] == '\n')
		path[i] = 0;

	s = strlen(path) + 1;
	fldr = (char *) malloc(s * sizeof(char));
	strcpy(fldr, path);
	fldr[s - 1] = 0;
	return (fldr);
}

/**
 * @brief creates a subdirectory (code) withing a specified directory (out)
 * @param directory in which to create new folder
 * @param code new folder to create
 * @return true if creation successful, false otherwise
*/
bool create_app_folder(char *out, char *code)
{
	char path[PATH_MAX];

	sprintf(path, "%s/%s", out, code);
	// printf("in create_app_folder, sending %s to create_out\n", path);
	int i = -1;
	while (path[++i] && path[i] != '\n')
		;
	if (path[i] == '\n')
		path[i] = 0;
	return create_out(path);
}

/**
 * @brief checks if out param exists, if not creates it as a directory
 * @param out char* directory
 */
bool create_out(char *out)
{
	struct stat st;

	if (stat(out, &st) == 0)
		return false ;
	if (mkdir(out, 0777))
	{
		perror("error creating folder");
		printf("couldn't create: %s\n", out);
		return false;
	}
	return true;
}