#include "scomp.h"

int w_sig;

void sig_worker_handler(int sig, siginfo_t *info, void *ucontext)
{
	(void)ucontext;
	(void)info;
	if (sig == SIGUSR1)
	{
		printf("Worker received sigusr1 signal\n");
		w_sig = sig;
	}
}

/**
 * @brief sets the signals for the monitor child,
 * SIGUSR1 is used by the parent to stop the process
 */
void set_sig_worker(void)
{
	struct sigaction sig;

	// this method doesn't require memory allocation
	// thus removing the risk of leaks
	sig = (struct sigaction){.sa_flags = SA_SIGINFO,
		.sa_sigaction = sig_worker_handler};
	/* sig.sa_handler = &sig_worker_handler;
	sig.sa_flags = SA_SIGINFO; */
	sigaction(SIGUSR1, &sig, NULL);
	signal(SIGQUIT, SIG_IGN);
	signal(SIGINT, SIG_IGN);
}
