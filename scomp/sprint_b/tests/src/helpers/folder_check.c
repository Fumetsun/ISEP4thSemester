#include "scomp_tests.h"

bool fldr_checker_subdir(const char *d, const char *subdir)
{
	char path[PATH_MAX];
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(d);

	if (!dir)
	{
		perror("fldr_checker_subdir can't open directory");
		return (NULL);
	}

	while ((dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", d, dir_entry->d_name);
		if (stat(path, &st) == 0 && S_ISDIR(st.st_mode) && !strcmp(dir_entry->d_name, subdir))
		{
			closedir(dir);
			return (true);
		}
	}
	closedir(dir);
	return (false);
}

bool check_fls(const char *d)
{
	int count = 0;
	char path[PATH_MAX];
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(d);

	// printf("d: %s\n", d);
	if (!dir)
	{
		perror("Clean_dir_tester can't open directory");
		return (NULL);
	}

	while ((dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", d, dir_entry->d_name);
		if (dir_entry->d_type == DT_REG || (stat(path, &st) == 0 && S_ISREG(st.st_mode)))
			count++;
	}
	closedir(dir);
	return (count > 0);
}