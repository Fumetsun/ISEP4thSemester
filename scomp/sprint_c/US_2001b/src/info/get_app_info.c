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
	ln = (char *)malloc(s * sizeof(char));
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
 * @warning unused in current implementation
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
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
		{
			// printf("skipping entry\n");
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
			fl = (char *)malloc(s * sizeof(char));
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

	for (int i = 0; i < 3; i++)
	{
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

/**
 * @brief searches a folder for a data file that follows
 * a specific naming format, can extract more than one code, 
 * until there are no more data files in directory
 * @param d directory
 * @param n number of data files already found
 * @return int application code
 * @warning unused in current implementation
 */
int get_app_code(char *in, int n)
{
	int i, code = -1, count = 0, current, prev = -1;
	char path[PATH_MAX];
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(in);

	if (!dir)
	{
		perror("Error opening directory.");
		return (-1);
	}

	while ((dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", in, dir_entry->d_name);
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
		{
			continue;
		}
	
		i = -1;
		while (dir_entry->d_name[++i])
			;
		if (i < 20)
			continue;
		i -= 18;
		if (strcmp(&dir_entry->d_name[i], DATA_FILE))
			continue;

		current = atoi(dir_entry->d_name);
		if (current == prev)
			continue;

		if (++count < n)
			continue;
		code = atoi(dir_entry->d_name);
		break;
	}

	closedir(dir);
	return (code);
}

/**
 * @brief using a directory and an application code 
 * finds and returns that application's information file
 * @param d directory to search
 * @param app_code application number to search for
 * @return memory allocated string containing info file name
 */
char *get_data_file_of_app(char *d, int app_code)
{
	int i;
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
		// printf("dir_entry: %s\n", dir_entry->d_name);
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
		{
			continue;
		}

		if (atoi(dir_entry->d_name) != app_code)
			continue;

		i = -1;
		while (dir_entry->d_name[++i])
			;
		if (i < 20)
			continue;
		i -= 18;
		// printf("file after formatting: %s\n", &fl[i]);
		if (!strcmp(&dir_entry->d_name[i], DATA_FILE))
		{
			s = strlen(dir_entry->d_name) + strlen(d) + 2;
			fl = (char *)malloc(s * sizeof(char));
			strcpy(fl, d);
			fl[strlen(d)] = '/';
			strcpy(fl + strlen(d) + 1, dir_entry->d_name);
			fl[s - 1] = 0;
			closedir(dir);
			return (fl);
		}
	}

	closedir(dir);
	return (NULL);
}

/**
 * @brief counts number of distinct applications 
 * found in directory in (uses the application code at 
 * the start to distinguish)
 * @param in directory to search
 */
int count_applications(char *in)
{
	int i, count = 0, prev = -1, current;
	char path[PATH_MAX];
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(in);

	if (!dir)
	{
		perror("Error opening directory.");
		return (-1);
	}
	// printf("hello from find_data_file\n");

	while ((dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", in, dir_entry->d_name);
		// printf("dir_entry->d_name: %s, type: %d\n", dir_entry->d_name, dir_entry->d_type);
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
			continue;
	
		i = -1;
		while (dir_entry->d_name[++i])
			;
		if (i < 20)
			continue;
		i -= 18;
		if (strcmp(&dir_entry->d_name[i], DATA_FILE))
			continue;

		current = atoi(dir_entry->d_name);

		if (current == prev)
			continue;
		count++;
		prev = current;
	}

	closedir(dir);
	return (count);
}

/**
 * @brief fills an array of ints with the application codes 
 * found in a directory
 * @param in directory to search
 * @param arr array to fill
 * @param sz size of array
 */
void fill_codes(char *in, int *arr, int sz)
{
	int i, count = 0, prev = -1, current;
	char path[PATH_MAX];
	struct stat st;
	struct dirent *dir_entry;
	DIR *dir = opendir(in);

	if (!dir)
	{
		perror("Error opening directory.");
		return ;
	}

	while (count < sz && (dir_entry = readdir(dir)))
	{
		sprintf(path, "%s/%s", in, dir_entry->d_name);
		if (dir_entry->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
			continue;
	
		i = -1;
		while (dir_entry->d_name[++i])
			;
		if (i < 20)
			continue;
		i -= 18;
		if (strcmp(&dir_entry->d_name[i], DATA_FILE))
			continue;

		current = atoi(dir_entry->d_name);

		if (current == prev)
			continue;
		
		arr[count++] = current;
		prev = current;
	}

	closedir(dir);
}