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
NAME			= file_bot_tester
DEPENDENCIES	= ../include
CC				= gcc
SRC_DIR			= ../src/
TEST_DIR		= src/
OBJ_DIR			= ../obj/
INPUT_DIR		= ../inputDir
OUTPUT_DIR		= ../outputDir
CONFIG			= ../config.txt
OUT_FILE		= test_output.log
NUM_WORKERS		= 2
RM				= rm -rf
NODIRS			= --no-print-directory
CC_FLAGS		= -g -Wall -Werror -Wextra -I $(DEPENDENCIES)
VG				= valgrind -s --leak-check=full --show-leak-kinds=all --track-origins=yes --log-file=leaks.log

# DIRECTORIES
DIRS	= monitor signals worker validate extras info
PATHS	= $(addprefix $(SRC_DIR), $(DIRS))
TEST_DIRS = extras_test info_test validate_test helpers
TEST_PATHS	= $(addprefix $(TEST_DIR), $(TEST_DIRS))
vpath %.c $(SRC_DIR) $(PATHS) $(TEST_PATHS)

# FILES
SRCS	= monitor worker validate get_info create_out signals_monitor \
	signals_parent signals_worker get_app_info extras report_handler clean_dir
SRCS_TESTS = main clean_dir_test create_out_test folder_check extras_test \
	get_app_info_test get_info_test validate_test
OBJS	= $(addprefix $(OBJ_DIR), $(addsuffix .o, $(SRCS)))
TEST_OBJS	= $(addprefix $(OBJ_DIR), $(addsuffix .o, $(SRCS_TESTS)))
OBJS_FILES	= .tst


# COMPILATION
all : $(NAME)

$(NAME) : $(OBJS) $(TEST_OBJS)
	@$(CC) $(CFLAGS) $(OBJS) $(TEST_OBJS) -o $(NAME)
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

re : fclean all

run : all
	@./$(NAME) $(INPUT_DIR) $(OUTPUT_DIR) $(NUM_WORKERS)

.PHONY : all, clean, fclean, re, run
