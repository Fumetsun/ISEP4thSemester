#ifndef SCOMP_H
#define SCOMP_H

#include <stdio.h>
#include <ctype.h>
#include <time.h>
#include <fcntl.h>
#include <errno.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <limits.h>
#include <dirent.h>
#include <stdbool.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <sys/wait.h>
#include <semaphore.h>
#include <sys/types.h>
#include <sys/inotify.h>
#include <linux/limits.h>

#define REF_CODE_MAX 15
#define APP_CODE_MAX 20
#define REF_CODE_MAX 15
#define APPLICANT_MAX 50
#define CONFIG_MAX 100
#define FILE_SIZE 27
#define TITLE1 "Application code:\n\t"
#define TITLE2 "Subdirectory:\n\t"
#define TITLE3 "Files:\n"
#define DATA_FILE "candidate-data.txt"
#define MMAP_NAME "file_bot_app_shm"
#define SEM_OPEN "file_bot_semaphore_open"
#define SEM_CLOSED "file_bot_semaphore_closed"
#define SEM_DEL "file_bot_semaphore_delete"
#define SEM_EXCL "file_bot_semaphore_exclusive"
#define SEM_MNTR "file_bot_semaphore_monitor"

typedef struct s_circular
{
	// dynamically sized to be an array with 
	// as many slots as there are workers
	int *bfr;
	// number of workers
	int size;
	// buffer position to read from
	int read;
	// buffer position to write to
	int write;
	// counts how many times a worker successfully copied the files
	int cnt;
	// saves how many applications are being processed
	// (how many applications are in the input directory
	// when the parent process leaves standby)
	int apps;
	// how many slots in the buffer are open (start: 0, max: size)
	sem_t *open;
	// how many slots in the buffer are closed (start: size, min: 0)
	sem_t *closed;
	// activates once all workers are done
	// basically tells the parent to clean the input directory
	sem_t *del;
	// exclusivity semaphore
	sem_t *exclude;
} t_circular;
// Additional note on sem_t *del
// this is used so that the parent can write the report
// without worry of the children having moved the files
// (instead of copying them), and still allow the input
// directory to be emptied in between uses

// saves what signal the processes received
extern int p_sig, w_sig, m_sig, w_ret;

// --- buffer_handle

// buffer_close.c
void buffer_close(t_circular *c, sem_t *mntr, int fd, bool og);

// buffer_init.c
t_circular *buffer_init(int size, int *fd);

// buffer_ops.c
void buffer_add(t_circular *c, int app_code);
int buffer_get(t_circular *c);
void buffer_set(t_circular *c, int n_apps);
void buffer_inc(t_circular *c);
void buffer_reset(t_circular *c);

// --- extras ---

// create_out.c
char *fldr_str(char *out, char *code);
bool create_app_folder(char *out, char *code);
bool create_out(char *out);

// extras.c
int extract_app_code(char *str);
char *extract_code_app(char *str);
char *get_file_name();
char *get_config_line_arg(char *ln);

// report_handler.c
void report_handler(FILE *fl, char *in, char *out, int app_code);

// --- file_ops

// clean_dir.c
void clean_dir(const char *dir);
void clean_files(const char *dir, int code);

// copy_files.c
void copy_files(char *in, char *out, char *app_code);

// move_files.c
void move_files(char *in, char *out, char *app_code);

// --- info ---

// get_app_info.c
char *get_job_ref_code(char *fl);
char *find_data_file(char *d, int n);
char *get_applicant_name(char *fl);
int get_app_code(char *in, int n);
char *get_data_file_of_app(char *d, int app_code);
int count_applications(char *in);
void fill_codes(char *in, int *arr, int sz);

// get_info.c
void get_arg_info(int ac, char **av, int *n, char **out, char **in);

// --- monitor ---

// monitor.c
void monitor_directory(const char *dirname, sem_t *mntr);

// --- signals ---

// signals_monitor.c
void set_sig_monitor(void);
void sig_monitor_handler(int sig, siginfo_t *info, void *ucontext);

// signals_parent.c
void set_sig_parent(void);
void sig_parent_handler(int sig, siginfo_t *info, void *ucontext);

// signals_worker.c
void set_sig_worker(void);
void sig_worker_handler(int sig, siginfo_t *info, void *ucontext);

// --- validate ---

// validate.c
bool valid_entry_args(int ac, char **av);

// --- worker ---

// worker.c
void worker(char *in, char *out, int i, t_circular *c);

#endif
