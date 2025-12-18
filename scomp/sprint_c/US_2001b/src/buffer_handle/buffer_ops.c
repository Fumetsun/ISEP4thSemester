#include "scomp.h"

/**
 * @brief adds an application code to the circular buffer, 
 * using the semaphores for exclusivity and, if needed, 
 * to block adding new ones until some are freed
 * @param c struct that holds the information
 * @param app_code application code to add
 */
void buffer_add(t_circular *c, int app_code)
{
	sem_wait(c->open);
	sem_wait(c->exclude);

	c->bfr[c->write] = app_code;
	c->write = (c->write + 1 >= c->size) ? 0 : c->write + 1;
	// c->write = (c->write + 1) % c->size;
	// ex: size = 4, write = 3, (3 + 1) % 4 = 0; write = 2, (2 + 1) % 4 = 3; etc.

	sem_post(c->exclude);
	sem_post(c->closed);
}

/**
 * @brief "extracts" an application code from the circular buffer
 * @param c struct with the information
 */
int buffer_get(t_circular *c)
{
	int app_code;

	sem_wait(c->closed);
	sem_wait(c->exclude);

	app_code = c->bfr[c->read];
	c->read = (c->read + 1) % c->size;

	sem_post(c->exclude);
	sem_post(c->open);

	return app_code;
}

/**
 * @brief resets the counter and number of applications, 
 * used at start of every parent loop
 * @param c struct with the information
 * @param n_apps number of applications
 */
void buffer_set(t_circular *c, int n_apps)
{
	sem_wait(c->exclude);
	c->cnt = 0;
	c->apps = n_apps;
	sem_post(c->exclude);
}

/**
 * @brief increments the counter inside the struct
 * @param c struct with the information
 */
void buffer_inc(t_circular *c)
{
	sem_wait(c->exclude);
	c->cnt = c->cnt + 1;
	if (c->cnt == c->apps)
		sem_post(c->del);
	sem_post(c->exclude);
}

/**
 * @brief resets the counter and applications number inside the struct
 * @param c struct with the information
 * @warning unused in current implementation
 */
void buffer_reset(t_circular *c)
{
	sem_wait(c->del);
	sem_wait(c->exclude);

	c->cnt = 0;
	c->apps = 0;

	sem_post(c->exclude);
	sem_post(c->del);
}