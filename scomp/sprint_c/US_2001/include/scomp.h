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
#include <sys/wait.h>
#include <sys/types.h>
#include <sys/inotify.h>

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

// supposedly save what signal the processes received
// not actually used for much
extern int p_sig, w_sig, m_sig, w_ret;


// --- extras ---

// clean_dir.c
void clean_dir(const char *dir);

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
void report_handler(FILE *fl, char *in, char *out, char *info_file);


// --- info ---

// get_app_info.c
char *get_job_ref_code(char *fl);
char *find_data_file(char *d, int n);
char *get_applicant_name(char *fl);

// get_info.c
void get_arg_info(int ac, char **av, int *n, char **out, char **in);


// --- monitor ---

// monitor.c
void monitor_directory(const char *dirname, const pid_t pid);


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
void worker(int *parent_pipe, char *in, char *out, int i);

#endif
