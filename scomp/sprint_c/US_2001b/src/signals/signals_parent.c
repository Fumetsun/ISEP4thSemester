#include "scomp.h"

int p_sig;
int w_ret;

void sig_parent_handler(int sig, siginfo_t *info, void *ucontext)
{
	(void)ucontext;
	(void)info;
	/* if (sig == SIGUSR1)
	{
		printf("Parent received SIGUSR1 signal\n");
		p_sig = sig;
	}
	else if (sig == SIGUSR2)
	{
		printf("Parent received SIGUSR2 signal\n");
		p_sig = sig;
		w_ret++;
	} */
	if (sig == SIGINT)
	{
		printf("Parent received SIGINT signal\n");
		p_sig = sig;
	}
}

/**
 * @brief sets the signals for the parent process, 
 * SIGUSR1 is used by the monitor child to inform of new files, 
 * SIGUSR2 is used by the worker child to inform that it has finished copying files
 */
void set_sig_parent(void)
{
	struct sigaction sig;

	// this method doesn't require memory allocation
	// thus removing the risk of leaks
	sig = (struct sigaction){.sa_flags = SA_SIGINFO,
		.sa_sigaction = sig_parent_handler};
	/* sig.sa_handler = &sig_parent_handler;
	sig.sa_flags = SA_SIGINFO; */
	// sigaction(SIGUSR1, &sig, NULL);
	// sigaction(SIGUSR2, &sig, NULL);
	sigaction(SIGINT, &sig, NULL);
}
