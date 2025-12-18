#include "scomp.h"

int main(int argc, char **argv)
{
	int n_workers = 0, // number of workers
		fd;			   // saves shared memory file descriptor
	char *out = NULL,  // output directory
		*in = NULL,	   // input directory
		*report,	   // name of the report file (temporary)
		*report_fldr;  // relative path to report file
	sem_t *mntr;	   // semaphore used to communicate between monitor and parent
	t_circular *c;	   // struct that holds the circular buffer and semaphores used by parent and workers
	FILE *fl;		   // report file
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

	if ((mntr = sem_open(SEM_MNTR, O_CREAT | O_EXCL, 0644, 0)) == SEM_FAILED)
	{
		perror("sem_open for monitor semaphore");
		exit(1);
	}

	pid_t monitor_pid = fork();
	if (!monitor_pid)
	{
		set_sig_monitor();
		monitor_directory(in, mntr);
		printf("Monitor child exiting\n");
		// need to free these variables in case a configuration file was used
		if (argc == 2)
		{
			free(in);
			free(out);
		}
		exit(0);
	}

	c = buffer_init(n_workers, &fd);
	pid_t worker_pids[n_workers], wpid;
	int status;
	for (int i = 0; i < n_workers; i++)
	{
		worker_pids[i] = fork();
		if (!worker_pids[i])
		{
			set_sig_worker();
			worker(in, out, i, c);
			// need to free these variables in case a configuration file was used
			if (argc == 2)
			{
				free(in);
				free(out);
			}
			buffer_close(c, mntr, fd, false);
			printf("Worker %d exiting\n", i);
			exit(0);
		}
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

	int n,		// number of applications found at the start of the loop
		*codes; // int array of applications codes found
	// printf("report file: %s\n", report_fldr);
	while (p_sig != SIGINT)
	{
		sem_wait(mntr);
		if (p_sig == SIGINT)
			break;
		printf("Parent acting\n");
		p_sig = 0;

		n = count_applications(in);
		if (n <= 0)
		{
			printf("Parent didn't find anything in input directory\n");
			continue;
		}

		codes = (int *)calloc(n, sizeof(int));
		fill_codes(in, codes, n);

		printf("Parent found %d applications in input directory.\n", n);
		for (int i = 0; i < n; i++)
		{
			// printf("%d | ", codes[i]);
			if (codes[i] <= 0)
			{
				printf("\nError getting all the applications\n");
				n = 0;
				break;
			}
		}
		if (n <= 0)
			continue;
		printf("\n");

		buffer_set(c, n);

		for (int i = 0; p_sig != SIGINT && i < n; i++)
		{
			printf("sending application %d to workers\n", codes[i]);
			buffer_add(c, codes[i]);
		}

		sem_wait(c->del);

		printf("parent cleaning files.\n");
		// printf("c->cnt = %d, c->apps: %d\n", c->cnt, c->apps);
		for (int i = 0; i < n; i++)
		{
			// printf("cleaning %d\n", codes[i]);
			report_handler(fl, in, out, codes[i]);
			clean_files(in, codes[i]);
		}

		free(codes);
	}

	printf("Received signal to quit\n");
	printf("killing worker children\n");
	for (int i = 0; i < n_workers; i++)
	{
		// printf("closing child %d\n", i);
		kill(worker_pids[i], SIGUSR1);
	}

	fclose(fl);
	if (argc == 2)
	{
		free(in);
		free(out);
	}
	free(report_fldr);

	printf("killing monitor children\n");
	kill(monitor_pid, SIGUSR1);
	printf("parent waiting\n");
	while ((wpid = wait(&status)) > 0)
	{
		// printf("wpid: %d, status: %d\n", wpid, status);
	}

	printf("parent calling buffer cleaner\n");
	buffer_close(c, mntr, fd, true);

	printf("parent exiting\n");

	return (0);
}