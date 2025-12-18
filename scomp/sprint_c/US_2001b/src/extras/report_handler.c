#include "scomp.h"

/**
 * @brief utilizing an already opened file descriptor in generates
 * a report of what files were copied for what applicant
 * @param fl open file descriptor
 * @param in input directory
 * @param out outpur directory
 * @param info_file file from which to extract necessary info
 */
void report_handler(FILE *fl, char *in, char *out, int app_code) // char *info_file)
{
	char *info_file = get_data_file_of_app(in, app_code),
		 *ref_code = get_job_ref_code(info_file),
		 *applicant_name = get_applicant_name(info_file),
		 *app = extract_code_app(info_file),
		 *fldr = fldr_str(out, ref_code),
		 *final_fldr = fldr_str(fldr, app),
		 path[PATH_MAX];
	DIR *dir;
	struct stat st;
	struct dirent *s_dir;

	if (!(dir = opendir(in)))
	{
		perror("error opening input directory in report handler");
		exit(1);
	}
	fprintf(fl, "%s\nApplication reference code: %s\nSubdirectory: %s\n",
			applicant_name, ref_code, final_fldr);

	while ((s_dir = readdir(dir)))
	{
		sprintf(path, "%s/%s", in, s_dir->d_name);
		// printf("dir_entry->d_name: %s, type: %d\n", s_dir->d_name, s_dir->d_type);
		if (s_dir->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
			continue;
		if (app_code != atoi(s_dir->d_name))
			continue;
		/* if (strncmp(s_dir->d_name, app, strlen(app)))
			continue; */
		fprintf(fl, "%s\n", s_dir->d_name);
	}

	fprintf(fl, "\n");
	closedir(dir);
	free(info_file);
	free(ref_code);
	free(applicant_name);
	free(app);
	free(fldr);
	free(final_fldr);
}
