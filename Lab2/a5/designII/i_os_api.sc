
#include <sim.sh>

typedef struct Task
{
	char * name;
	int priority;
	int id;
} Task;


interface OSAPI{
	
	void init();
	Task task_create(char *name, int priority, int id);
	void task_start(Task t);
	void task_terminate();
	//void task_sleep();
	int par_start();
	void par_end(int);
	int pre_wait();
	void post_wait(int t);
	void time_wait(sim_time nsec);
};
