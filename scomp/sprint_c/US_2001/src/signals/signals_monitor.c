#include "scomp.h"

int m_sig;

void sig_monitor_handler(int sig, siginfo_t *info, void *ucontext)
{
	(void)ucontext;
	(void)info;
	if (sig == SIGUSR1)
	{
		printf("Monitor received SIGUSR1 signal\n");
		m_sig = sig;
	}
	else if (sig == SIGUSR2)
	{
		printf("Monitor received SIGUSR2 signal\n");
		m_sig = sig;
	}
}

/**
 * @brief sets the signals for the monitor child,
 * SIGUSR2 is used by the parent process to let the child
 * resume work, and SIGUSR1 to stop it
 */
void set_sig_monitor(void)
{
	struct sigaction sig;

	// this method doesn't require memory allocation
	// thus removing the risk of leaks
	sig = (struct sigaction){.sa_flags = SA_SIGINFO,
							 .sa_sigaction = sig_monitor_handler};
	/* sig.sa_handler = &sig_monitor_handler;
	sig.sa_flags = SA_SIGINFO; */
	sigaction(SIGUSR1, &sig, NULL);
	sigaction(SIGUSR2, &sig, NULL);
	signal(SIGQUIT, SIG_IGN);
	signal(SIGINT, SIG_IGN);
	// signal(SIGUSR2, SIG_IGN);
}
