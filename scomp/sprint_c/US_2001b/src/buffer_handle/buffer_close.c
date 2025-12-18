#include "scomp.h"

/**
 * @brief cleans up worker and parent processes
 * @param c struct that holds almost all the info
 * @param mntr monitor semaphore
 * @param fd shared memory file descriptor
 * @param og used to distinguish between being called by parent 
 * or worker, parent unlinks semaphores and shared memory
 */
void buffer_close(t_circular *c, sem_t *mntr, int fd, bool og)
{
	if (og)
	{
		sem_unlink(SEM_OPEN);
		sem_unlink(SEM_CLOSED);
		sem_unlink(SEM_DEL);
		sem_unlink(SEM_EXCL);
		sem_unlink(SEM_MNTR);
	}

	sem_close(c->open);
	sem_close(c->closed);
	sem_close(c->del);
	sem_close(c->exclude);
	sem_close(mntr);

	if ((munmap(c, sizeof(t_circular) + c->size * sizeof(int))) < 0)
	{
		perror("Munmap in buffer_close");
		exit(1);
	}

	if (og && (shm_unlink(MMAP_NAME)) < 0)
	{
		perror("Shm_unlink in buffer_close");
		exit(1);
	}
	close(fd);
}