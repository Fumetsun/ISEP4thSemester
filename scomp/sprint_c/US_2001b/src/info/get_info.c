#include "scomp.h"

/**
 * @brief obtains necessary information from configuration file, 
 * allocating memory for the char * components, 
 * so in and out must be freed later
 * @param av arguments from main
 * @param n reference to number of workers
 * @param out reference to output directory
 * @param in reference to input directory
 */
static void get_from_file(char **av, int *n, char **out, char **in)
{
	char *temp_n, line[CONFIG_MAX];
	FILE *fl;

	if (!(fl = fopen(av[1], "r")))
	{
		perror("Can't open configuration file");
		exit(0);
	}

	fgets(line, sizeof(line), fl);
	*in = get_config_line_arg(line);
	fgets(line, sizeof(line), fl);
	*out = get_config_line_arg(line);
	fgets(line, sizeof(line), fl);
	temp_n = get_config_line_arg(line);
	*n = atoi(temp_n);
	free(temp_n);
	fclose(fl);
}

/**
 * @brief extracts info from either file or input arguments
 * @param ac int, number of arguments
 * @param av arguments
 * @param n reference to number of workers
 * @param out reference to output directory
 * @param in reference to input directory
 */
void get_arg_info(int ac, char **av, int *n, char **out, char **in)
{
	if (ac == 4)
	{
		*n = atoi(av[3]);
		*out = av[2];
		*in = av[1];
	}
	else
		get_from_file(av, n, out, in);
}