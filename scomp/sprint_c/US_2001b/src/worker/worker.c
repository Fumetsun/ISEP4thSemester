#include "scomp.h"

/**
 * @brief worker thread main function, waits for a read, creates
 * necessary folders and copies files from in folder to out folder
 * @param parent_pipe pipe where the parent will write
 * @param in input directory
 * @param out output directory
 * @param i worker number
 */
void worker(char *in, char *out, int i, t_circular *c)
{
	w_sig = 0;
	char *file, *fldr, *last_fldr, *ref_code, *app_code;
	int app;

	while (!w_sig)
	{
		app = buffer_get(c);
		if (w_sig)
			break;
		printf("worker %d got application %d\n", i, app);

		file = get_data_file_of_app(in, app);
		ref_code = get_job_ref_code(file);
		create_app_folder(out, ref_code);
		fldr = fldr_str(out, ref_code);
		app_code = extract_code_app(file);
		create_app_folder(fldr, app_code);
		last_fldr = fldr_str(fldr, app_code);

		/* printf("worker %d\napp: %d | file: %s | ref_code: %s | fldr: %s | last_fldr: %s | app_code: %s\n", \
			i, app, file, ref_code, fldr, last_fldr, app_code); */

		copy_files(in, last_fldr, app_code);
		// move_files(in, last_fldr, app_code);

		//printf("worker %d calling buffer inc\n", i);
		buffer_inc(c);

		free(ref_code);
		free(fldr);
		free(last_fldr);
		free(app_code);
		free(file);

		usleep(500);
		// ^ just here to see if in practice 
		// more than one worker is seen acting
	}

	printf("worker %d received signal %d\n", i, w_sig);
}
