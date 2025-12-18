#include "scomp.h"

/**
 * @brief utilizes inotify to watch over input directory, send a signal
 * when a new file is created or moved into the directory
 * @param dirname directory over which function will watch
 * @param pid parent process id to send signals to
 */
void monitor_directory(const char *dirname, sem_t *mntr)
{
	int fd, wd;

	m_sig = 0;
	fd = inotify_init();
	if (fd == -1)
	{
		perror("inotify_init");
		exit(EXIT_FAILURE);
	}
	// creates an inotify event that will reacted whenever
	// a new file is created or moved into the directory
	wd = inotify_add_watch(fd, dirname, IN_MOVED_TO | IN_CREATE);
	if (wd == -1)
	{
		perror("inotify_add_watch");
		exit(EXIT_FAILURE);
	}

	char buf[sizeof(struct inotify_event) + NAME_MAX + 1];
	struct inotify_event *event = (struct inotify_event *)buf;
	ssize_t len;

	while (1 && m_sig != SIGUSR1)
	{
		// sem_wait(mntr);
		printf("Monitor watching\n");
		len = read(fd, buf, sizeof(buf));
		if (len == -1)
		{
			if (m_sig != SIGUSR1)
			{
				printf("Error in monitor read\n");
				perror("inotify read");
				exit(EXIT_FAILURE);
			}
			else
				break;
		}

		// printf("Monitor read %s, len %ld\n", event->name, len);
		// printf("Event mask: %x\n", event->mask);
		if (event->mask & IN_CREATE || event->mask & IN_MOVED_TO)
		{
			sleep(1);
			// used purely to allow all the files to be copied
			// before informing the parent process
			sem_post(mntr);
			printf("sem_post in monitor\n");
			// usleep(500);
		}
	}

	if (inotify_rm_watch(fd, wd) == -1)
		perror("inotify_rm_watch");
	
	sem_close(mntr);
	close(fd);
}