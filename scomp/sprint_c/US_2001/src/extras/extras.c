#include "scomp.h"

/**
 * @brief extract the application code from a file
 * @param str string from which to get code
 * @return application reference code
 */
int extract_app_code(char *str)
{
	char *strt = strrchr(str, '/');
	strt++;
	return (atoi(strt));
}

/**
 * @brief extract the application code from a file
 * @param str string from which to get code
 * @return application reference code
*/
char *extract_code_app(char *str)
{
	//printf("in extract code app, received: %s\n", str);
	if (!str)
		return (NULL);
	char *strt = strrchr(str, '/'), *ret;
	strt++;
	int i = -1;
	while (strt[++i] && isdigit(strt[i]))
		;
	ret = (char *)malloc((i + 1) * sizeof(char));
	strncpy(ret, strt, i);
	ret[i] = 0;
	return (ret);
}

static void fill_file(char *file, int *i, int helper)
{
	int n = *i;
	file[n++] = helper / 10 + 48;
	file[n++] = helper - (helper / 10 * 10) + 48;
	*i = n;
}

/**
 * @brief creates a file name based on date
 * @return file name
*/
char *get_file_name()
{
	char *file;
	int helper, i = 8;
	time_t t = time(NULL);
	struct tm datetime = *localtime(&t);

	file = (char *)malloc(sizeof(char) * (FILE_SIZE + 1));
	if (file == NULL)
	{
		exit(EXIT_FAILURE);
	}
	file[FILE_SIZE] = 0;
	strcpy(file, "reports_");
	//strcpy(file, "sensors_");
	helper = datetime.tm_year + 1900;
	for (int j = 1000; i < 12; i++, j /= 10)
		file[i] = helper / j - (helper / (j * 10) * 10) + 48;
	helper = datetime.tm_mon + 1;
	fill_file(file, &i, helper);
	helper = datetime.tm_mday;
	fill_file(file, &i, helper);
	helper = datetime.tm_hour;
	fill_file(file, &i, helper);
	helper = datetime.tm_min;
	fill_file(file, &i, helper);
	helper = datetime.tm_sec;
	fill_file(file, &i, helper);
	// printf("i: %d\n", i);
	strcpy(file + i, ".txt");
	return (file);
}

/**
 * @brief extracts info from a line, assumes 
 * it's from the config file
 * @param ln line to extract from
 * @return information extracted
*/
char *get_config_line_arg(char *ln)
{
	char *temp, *ret;
	size_t s;

	temp = strrchr(ln, ':');
	temp += 2;
	s = strlen(temp);
	ret = (char *) malloc(s * sizeof(char));
	strncpy(ret, temp, s - 1);
	ret[s - 1] = 0;
	return (ret);
}