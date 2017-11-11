#include <stdlib.h>
#include <stdio.h>

import "i_os_api";


#define QUEUE_CAPACITY 10
#define QUEUE_ERROR -999
#define SCHEDULE_ERROR -999

interface i_queue{
	void init();
	int pop();
	int push(int e);
	int top();
};

channel os_queue implements i_queue{
	int* queue;
	int tail;
	int capacity;
	int curr;

	int pop(){
		int ret = QUEUE_ERROR;

		if(curr!=tail){
			ret = queue[curr];
			curr = (++curr) % capacity;
			//printf("Popped %d\n", ret);	
		}

		return ret;
	}

	int push(int e){
		if (curr!=((tail+1)%capacity)){
			queue[tail] = e;
			tail = (tail + 1)%capacity;
			//printf("Pushed %d\n", e);	
			return 0;
		}

		return QUEUE_ERROR;
	}
	
	int top()
	{
		if(curr!=tail) return queue[curr];
		else return -1;
	}

	void init(){
		capacity = QUEUE_CAPACITY;
		queue = (int*)malloc(QUEUE_CAPACITY*sizeof(int));
		curr = tail = 0;
	}

};

channel OS implements OSAPI{
	int current;	// current task
	os_queue rdyq;
	event e0,e1,e2,e3,e4,e5,e6,e7,e8,e9;
	int id_pool;

	void event_wait(int id)
	{
		switch((id%10))
		{
			case 0: wait(e0); break;
			case 1: wait(e1); break;
			case 2: wait(e2); break;
			case 3: wait(e3); break;
			case 4: wait(e4); break;
			case 5: wait(e5); break;
			case 6: wait(e6); break;
			case 7: wait(e7); break;
			case 8: wait(e8); break;
			case 9: wait(e9); break;
			default: break;
		}	
	}
	
	void event_notify(int id)
	{
		switch((id%10))
		{
			case 0: notify(e0); break;
			case 1: notify(e1); break;
			case 2: notify(e2); break;
			case 3: notify(e3); break;
			case 4: notify(e4); break;
			case 5: notify(e5); break;
			case 6: notify(e6); break;
			case 7: notify(e7); break;
			case 8: notify(e8); break;
			case 9: notify(e9); break;
			default: break;
		}	
	}


	int schedule(){
		int new_task;
		new_task  = rdyq.pop();
		if (new_task == QUEUE_ERROR)
			return -1;
		return new_task;
	}

	void dispatch() {
		current = schedule();
		event_notify(current);
	}
	
	Task task_create(char *name, int priority)
	{
		Task t;
		t.name  = name;
		t.priority = priority;
		id_pool++;
		t.id = id_pool;
		return t;
	}
	
	void task_start(Task t) {
		int error;
		error = rdyq.push(t.id); 

		while(error == QUEUE_ERROR){
			waitfor(1);
			rdyq.push(t.id);
		}

		if(current==-1)
		{
			if(rdyq.top()==t.id) dispatch();
		}
		event_wait(t.id);

	}

	void yield() {
		int task, error;
		task  = current;
		error = rdyq.push(task); 

		while(error == QUEUE_ERROR){
			waitfor(1);
			rdyq.push(task);
		}

		dispatch();
		event_wait(task);
	}

	void task_terminate() {
		current = -1;
		dispatch();
	}

	void time_wait(unsigned long long t) {
		waitfor(t);
		//yield();
	}

	int par_start()
	{
		int temp;
		temp  = current;
		current = -1;
		return temp;
	}

	void par_end(int t)
	{
		current = t;
	}

	int pre_wait() {
		int t;
		t = current;
		dispatch(); return t;
	}

	void post_wait(int t) {
		rdyq.push(t);
		if (current==-1) dispatch();
		event_wait(t);
	}

	void init(){
		rdyq.init();
		current = -1;
		id_pool = -1;
	}
};
