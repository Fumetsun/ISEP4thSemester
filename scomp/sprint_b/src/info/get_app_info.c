#include "scomp.h"

/**
 * @brief given a file, it extracts the first line
 * (assumes the file is -candidate-data.txt),
 * which is the job reference code
 * @param fl file from which to extract job reference code
 * @return if successful returns the job reference code
 */
char *get_job_ref_code(char *fl)
{
	FILE *file;
	char line[REF_CODE_MAX + 1];

	if (!fl)
		return (NULL);
	// printf("in get_job_ref_code, fl: %s\n", fl);
	file = fopen(fl, "r");
	if (!file)
	{
		perror("Error opening file.");
		return (NULL);
	}

	fgets(line, sizeof(line), file);
	fclose(file);

	// printf("job ref code: %s\n", line);
	char *ln;
	size_t s = strlen(line);
	ln = (char *) malloc(s * sizeof(char));
	int i = -1, j = -1;
	while (line[++i] && line[i] != '\n')
		ln[++j] = line[i];
	ln[++j] = 0;
	// printf("fl in get_job_ref_code: %s\n", fl);
	return (ln);
}

/**
 * @brief searches a folder for a data file that follows 
 * a specific naming format, can extract more than one, until there 
 * are no more data files in directory
 * @param d directory
 * @param n number of data files already found
 * @return memory allocated string of file name
*/
char *find_data_file(char *d, int n)
{
	int i, count = 0;
	char *fl, path[PATH_MAX];
	size_t s;
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(d);

	if (!dir)
	{
		perror("Error opening directory.");
		return (NULL);
	}
	// printf("hello from find_data_file\n");

	while ((dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", d, dir_entry->d_name);
		// printf("dir_entry->d_name: %s, type: %d\n", dir_entry->d_name, dir_entry->d_type);
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode))) {
			//printf("skipping entry\n");
			continue;
		}
		i = -1;
		while (dir_entry->d_name[++i])
			;
		if (i < 20)
			continue;
		i -= 18;
		// printf("file after formatting: %s\n", &fl[i]);
		if (!strcmp(&dir_entry->d_name[i], DATA_FILE))
		{
			if (count++ < n)
				continue;
			s = strlen(dir_entry->d_name) + strlen(d) + 2;
			fl = (char *) malloc(s * sizeof(char));
			strcpy(fl, d);
			fl[strlen(d)] = '/';
			strcpy(fl + strlen(d) + 1, dir_entry->d_name);
			fl[s - 1] = 0;
			closedir(dir);
			// printf("file returned: %s\n", fl);
			return (fl);
		}
	}
	// printf("leaving find_data_file without returning.\n");
	closedir(dir);
	return (NULL);
}

/**
 * @brief extracts applicant name from file
 * @param fl file from which to extract
 * @return memory allocated string containing name
*/
char *get_applicant_name(char *fl)
{
	FILE *file;
	char line[APPLICANT_MAX + 1];

	if (!fl)
		return (NULL);
	file = fopen(fl, "r");
	if (!file)
	{
		perror("Error opening file.");
		return (NULL);
	}

	for (int i = 0; i < 3; i++) {
		fgets(line, sizeof(line), file);
		// printf("line: %s\n", line);
	}
	fclose(file);

	char *ln;
	size_t s = strlen(line);
	ln = (char *)malloc(s * sizeof(char));
	int i = -1, j = -1;
	while (line[++i] && line[i] != '\n')
		ln[++j] = line[i];
	ln[++j] = 0;
	// printf("in get applicant name, returning: %s\n", ln);
	return (ln);
}