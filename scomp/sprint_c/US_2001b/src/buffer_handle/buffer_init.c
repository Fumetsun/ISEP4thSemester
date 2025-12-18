#include "scomp.h"

/**
 * @brief initializes all the values inside the circular buffer
 * @param size number of workers
 * @param fd reference to a variable in main, 
 * so the shared memory file descriptor can be saved
 * @return t_circular struct initialized in shared memory
 */
t_circular *buffer_init(int size, int *fd)
{
	size_t s = sizeof(t_circular) + size * sizeof(int);

	*fd = shm_open(MMAP_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IRUSR);
	if (*fd == -1)
	{
		perror("shm_open fd in buffer_init");
		exit(1);
	}

	if (ftruncate(*fd, s) == -1) {
		perror("ftruncate in buffer_init");
		exit(1);
	}

	t_circular *c = mmap(NULL, s, PROT_READ | PROT_WRITE, MAP_SHARED, *fd, 0);
	if (c == NULL || c == MAP_FAILED) {
		perror("mmap error in buffer_init");
		exit(1);
	}

	if ((c->open = sem_open(SEM_OPEN, O_CREAT | O_EXCL, 0644, size)) == SEM_FAILED) {
		perror("sem_open for c->open in buffer init");
		exit(1);
	}

	if ((c->closed = sem_open(SEM_CLOSED, O_CREAT | O_EXCL, 0644, 0)) == SEM_FAILED) {
		perror("sem_open for c->closed in buffer init");
		exit(1);
	}

	if ((c->exclude = sem_open(SEM_EXCL, O_CREAT | O_EXCL, 0644, 1)) == SEM_FAILED) {
		perror("sem_open for c->exclude in buffer init");
		exit(1);
	}

	if ((c->del = sem_open(SEM_DEL, O_CREAT | O_EXCL, 0644, 0)) == SEM_FAILED) {
		perror("sem_open for c->del in buffer init");
		exit(1);
	}

	c->size = size;
	c->read = 0;
	c->write = 0;
	c->bfr = (int *) (c + 1);

	return (c);
}