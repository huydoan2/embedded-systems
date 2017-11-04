
#include <sim.sh>


interface OSAPI{
	
	void init();
	void start(int sched_alg);
	void interrupt_return();
	Task task_create(char *name, int type,
	sim_time period);
	void task_terminate();
	void task_sleep();
	void task_activate(Task t);
	void task_endcycle();
	void task_kill(Task t);
	Task par_start();
	void par_end(Task t);
	Task pre_wait();
	void post_wait(Task t);
	void time_wait(sim_time nsec);
};
