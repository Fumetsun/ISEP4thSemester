#include "scomp.h"

int main(int argc, char **argv)
{
	int n_workers = 0;
	char *out = NULL, *in = NULL, *report, *report_fldr;
	FILE *fl;
	// new_files = 0, stop_program = 0;
	p_sig = 0;

	// printf("%d\n%s\n%s\n%s\n", argc, argv[1], argv[2], argv[3]);
	if (!valid_entry_args(argc, argv))
	{
		printf("Invalid arguments\n");
		printf("Expected input:\n");
		printf("./program (input directory) (output directory) (number of workers)\n");
		printf("./program (config file)\n");
		printf("Example config file format: \n");
		printf("Input directory: (input directory)\n");
		printf("Output directory: (output directory)\n");
		printf("Number of worker: (number of worker)\n");
		return (0);
	}
	get_arg_info(argc, argv, &n_workers, &out, &in);
	create_out(out);
	// printf("in: %s\nout: %s\nn: %d\n", in, out, n_workers);

	pid_t monitor_pid = fork();
	if (!monitor_pid)
	{
		set_sig_monitor();
		monitor_directory(in, getppid());
		exit(1);
	}

	pid_t worker_pids[n_workers], wpid;
	int parent_pipe[2], status;
	if (pipe(parent_pipe) < 0)
	{
		perror("pipe error.");
		exit(1);
	}
	for (int i = 0; i < n_workers; i++)
	{
		worker_pids[i] = fork();
		if (!worker_pids[i])
		{
			set_sig_worker();
			worker(parent_pipe, in, out, i);
			exit(1);
		}
		close(parent_pipe[0]);
	}

	set_sig_parent();

	report = get_file_name();
	report_fldr = fldr_str(out, report);
	free(report);
	if (!(fl = fopen(report_fldr, "w")))
	{
		perror("error opening report file.");
		exit(1);
	}

	// info file contains all the necessary info
	// for whatever operations are required
	// ex.: 1-candidate-data.txt
	char *info_file;
	int apps;
	// printf("report file: %s\n", report_fldr);
	while (1)
	{
		pause();
		if (p_sig == SIGINT)
			break;
		p_sig = 0;
		printf("Parent acting\n");
		apps = 0;
		while (p_sig != SIGINT && (info_file = find_data_file(in, apps)))
		{
			printf("sending to worker\n");
			write(parent_pipe[1], info_file, strlen(info_file));
			report_handler(fl, in, out, info_file);
			free(info_file);
			if (p_sig != SIGINT)
				pause();
			apps++;
		}
		clean_dir(in);
		kill(monitor_pid, SIGUSR2);
	}

	printf("Received signal to quit\n");
	printf("killing worker children\n");
	for (int i = 0; i < n_workers; i++)
	{
		// printf("closing child %d\n", i);
		kill(worker_pids[i], SIGUSR1);
	}

	close(parent_pipe[1]);
	fclose(fl);
	if (argc == 2)
	{
		free(in);
		free(out);
	}
	free(report_fldr);
	
	printf("killing monitor child\n");
	kill(monitor_pid, SIGUSR1);
	printf("parent waiting\n");
	while ((wpid = wait(&status)) > 0)
	{
		// printf("wpid: %d, status: %d\n", wpid, status);
	}

	printf("parent exiting\n");

	return (0);
}