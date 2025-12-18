#include "scomp.h"

/**
 * @brief actual copying function
 * @param in file to copy
 * @param out new file
*/
static void file_cpy(const char *in, const char *out)
{
	FILE *src, *dest;
	char fl[PATH_MAX];
	size_t s;

	src = fopen(in, "rb");
	if (src == NULL)
	{
		perror("Worker child can't open in file");
		return;
	}

	dest = fopen(out, "wb");
	if (dest == NULL)
	{
		perror("Worker child can't open out file");
		fclose(src);
		return;
	}

	while ((s = fread(fl, 1, sizeof(fl), src)) > 0)
		fwrite(fl, 1, s, dest);

	fclose(src);
	fclose(dest);
}

/**
 * @brief copies files from in directory to out directory if 
 * the files match the application reference code
 * @param in input folder
 * @param out output folder
 * @param app_code application reference code
*/
static void do_work(const char *in, const char *out, char *app_code)
{
	DIR *dir;
	char path[PATH_MAX], in_path[PATH_MAX], out_path[PATH_MAX];
	struct stat st;
	struct dirent *s_dir;

	if (!(dir = opendir(in)))
	{
		perror("error opening input directory in worker child");
		exit(1);
	}

	while ((s_dir = readdir(dir)))
	{
		sprintf(path, "%s/%s", in, s_dir->d_name);
		// printf("dir_entry->d_name: %s, type: %d\n", s_dir->d_name, s_dir->d_type);
		if (s_dir->d_type != DT_REG && (stat(path, &st) == -1 || !S_ISREG(st.st_mode)))
			continue;
		if (strncmp(s_dir->d_name, app_code, strlen(app_code)))
			continue;
		sprintf(in_path, "%s/%s", in, s_dir->d_name);
		sprintf(out_path, "%s/%s", out, s_dir->d_name);
		file_cpy(in_path, out_path);
	}
	closedir(dir);
}

/**
 * @brief worker thread main function, waits for a read, creates
 * necessary folders and copies files from in folder to out folder
 * @param parent_pipe pipe where the parent will write
 * @param in input directory
 * @param out output directory
 * @param i worker number
 */
void worker(int *parent_pipe, char *in, char *out, int i)
{
	w_sig = 0;
	char file[PATH_MAX], *fldr, *last_fldr,
		*ref_code, *app_code;
	int app, n;

	(void)in;
	(void)out;
	close(parent_pipe[1]);
	while (!w_sig)
	{
		n = read(parent_pipe[0], file, PATH_MAX);
		file[n] = 0;
		if (n <= 0 || n >= PATH_MAX)
		{
			continue;
		}
		ref_code = get_job_ref_code(file);
		create_app_folder(out, ref_code);
		fldr = fldr_str(out, ref_code);
		app = extract_app_code(file);
		(void)app;
		app_code = extract_code_app(file);
		create_app_folder(fldr, app_code);
		last_fldr = fldr_str(fldr, app_code);
		do_work(in, last_fldr, app_code);
		kill(getppid(), SIGUSR2);
		free(ref_code);
		free(fldr);
		free(last_fldr);
		free(app_code);
	}
	close(parent_pipe[0]);
	printf("worker %d received signal %d, exiting\n", i, w_sig);
	exit(0);
}
