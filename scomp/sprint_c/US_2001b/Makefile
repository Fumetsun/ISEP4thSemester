# COLOURS
DEF_COLOR = \033[0;39m
GRAY = \033[0;90m
RED = \033[0;91m
GREEN = \033[0;92m
YELLOW = \033[0;93m
BLUE = \033[0;94m
MAGENTA = \033[0;95m
CYAN = \033[0;96m
WHITE = \033[0;97m

# DEFINITIONS
NAME			= app_file_bot
DEPENDENCIES	= include
CC				= gcc
SRC_DIR			= src/
OBJ_DIR			= obj/
INPUT_DIR		= inputDir
OUTPUT_DIR		= outputDir
CONFIG			= config.txt
NUM_WORKERS		= 5
RM				= rm -rf
NODIRS			= --no-print-directory
CC_FLAGS		= -g -Wall -Werror -Wextra -lrt -lpthread -I $(DEPENDENCIES)
VG				= valgrind -s --leak-check=full --show-leak-kinds=all --track-origins=yes --log-file=leaks.log

# DIRECTORIES
DIRS	= buffer_handle extras file_ops info monitor signals validate worker
PATHS	= $(addprefix $(SRC_DIR), $(DIRS))
vpath %.c $(SRC_DIR) $(PATHS)

# FILES
SRCS	= main buffer_close buffer_init buffer_ops create_out \
		extras report_handler clean_dir copy_files move_files get_app_info \
		get_info monitor signals_monitor signals_parent signals_worker validate worker
OBJS	= $(addprefix $(OBJ_DIR), $(addsuffix .o, $(SRCS)))
OBJS_FILES	= .tst


# COMPILATION
all : $(NAME)

$(NAME) : $(OBJS)
	@$(CC) $(CFLAGS) $(OBJS) -o $(NAME)
	@echo "$(GREEN)Finished compilation.$(DEF_COLOR)"

$(OBJ_DIR)%.o : %.c | $(OBJS_FILES)
	@echo "$(YELLOW)Compiling: $<$(DEF_COLOR)"
	@$(CC) $(CC_FLAGS) -c $< -o $@

$(OBJS_FILES) :
	@mkdir -p $(OBJ_DIR)

clean :
	@$(RM) $(OBJ_DIR)
	@echo "$(BLUE)Cleaned files.$(DEF_COLOR)"

fclean : clean
	@$(RM) $(OBJS_FILES)
	@$(RM) $(OBJ_DIR)
	@$(RM) $(NAME)

extras :
	@rm /dev/shm/*
	@rm $(INPUT_DIR)/*

re : fclean all

run : all
	@./$(NAME) $(INPUT_DIR) $(OUTPUT_DIR) $(NUM_WORKERS)

leaks : all
	@$(VG) ./$(NAME) $(INPUT_DIR) $(OUTPUT_DIR) $(NUM_WORKERS)

config : all
	@$(VG) ./$(NAME) $(CONFIG)

debug : all
	@gdb -tui --args $(NAME)

.PHONY : all, clean, fclean, re, run, leaks, debug, extras
