#include "scomp.h"

/**
 * @brief checks the 3 required field for any unacceptable parameters
 * @param n number of workers
 * @param in input directory
 * @param out output directory
 * @return true if valid, false otherwise.
 * prints error message with additional information
 */
static bool check_args(char *n, char *in, char *out)
{
	int test = 0, i = -1;
	struct stat st;

	while (n[++i])
	{
		if (!isdigit(n[i]))
		{
			printf("Number of workers not a digit.\n");
			return false;
		}
	}
	if (!(test = atoi(n)))
	{
		printf("Error parsing number of workers.\n");
		return false;
	}
	else if (test < 1)
	{
		printf("Invalid number of workers (%d).\n", test);
		return false;
	}
	if (stat(in, &st) == -1 || !S_ISDIR(st.st_mode))
	{
		printf("Invalid input directory.\n");
		return false;
	}
	size_t s = strlen(in);
	if (in[s - 1] == '/' || in[s - 1] == '\\')
	{
		printf("Invalid input directory, can't end in \\ or /.\n");
		return false;
	}
	if (stat(out, &st) == 0 && !S_ISDIR(st.st_mode))
	{
		printf("Invalid output directory.\n");
		return false;
	}
	s = strlen(out);
	if (out[s - 1] == '/' || out[s - 1] == '\\')
	{
		printf("Invalid output directory, can't end in \\ or /.\n");
		return false;
	}
	return true;
}

/**
 * @brief checks if file config is valid
 * @param ac number of arguments
 * @param av arguments
 * @return true if valid, false otherwise
 */
static bool check_file(int ac, char **av)
{
	char *in, *out, *n, line[CONFIG_MAX];
	FILE *fl;
	(void)ac;

	if (!(fl = fopen(av[1], "r")))
	{
		perror("Can't open configuration file");
		return false;
	}

	fgets(line, sizeof(line), fl);
	in = get_config_line_arg(line);
	fgets(line, sizeof(line), fl);
	out = get_config_line_arg(line);
	fgets(line, sizeof(line), fl);
	n = get_config_line_arg(line);

	// printf("in check_file, in: %s, out: %s, n: %s\n", in, out, n);
	bool ret = check_args(n, in, out);
	fclose(fl);
	return (free(in), free(out), free(n), ret);
}

/***
 * @brief checks if input arguments are valid
 * @param ac number of arguments
 * @param av char** arguments
 * @return true if valid, false otherwise
 */
bool valid_entry_args(int ac, char **av)
{
	if (ac < 2)
		return false;
	else if (ac == 2)
		return check_file(ac, av);
	else if (ac != 4)
		return false;
	return check_args(av[3], av[1], av[2]);
}
