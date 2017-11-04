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
};

channel os_queue implements i_queue{
	int* queue;
	int size;
	int capacity;
	int curr;

	int pop(){
		int ret = QUEUE_ERROR;

		if(size != 0){
			ret = queue[curr];
			curr = (++curr) % capacity;
			--size;			
		}

		return ret;
	}

	int push(int e){
		if (size < capacity){
			queue[curr+size] = e;
			++size;
			return 0;
		}

		return -1;
	}

	void init(){
		capacity = QUEUE_CAPACITY;
		queue = (int*)malloc(QUEUE_CAPACITY*sizeof(int));
		curr = size = 0;
	}

};

channel OS implements OSAPI{
	int current;	// current task
	os_queue rdyq;
	event e1, e2;

	int schedule(){
		int new_task;
		new_task  = rdyq.pop();
		if (new_task == QUEUE_ERROR)
			return 0;
		return new_task;
	}

	void dispatch() {
		current = schedule();
		switch(current){
			case 1:
				notify e1;
				break;
			case 2:
				notify e2;
				break;
			default:
				printf("No ready task to run\n");				
				break;
		}		
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
		switch(task){
			case 1:
				wait e1;
				break;
			case 2:
				wait e2;
				break;
			default:
				printf("No task to yield to\n");
				break;
		}
	}

	void time_wait(unsigned long long t) {
		waitfor(t);
		yield();
	}

	int pre_wait() {
		int t;
		t = current;
		dispatch(); return t;
	}

	void post_wait(int t) {
		rdyq.push(t);
		if (!current) dispatch();
		switch(t){
			case 1:
				wait e1;
				break;
			case 2:
				wait e2;
				break;
			default:
				printf("No task to yield to\n");
				break;
		}
	}

	void init(){
		rdyq.init();
		rdyq.push(1);
		dispatch();
	}
};
