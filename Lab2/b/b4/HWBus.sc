//////////////////////////////////////////////////////////////////////
// File:   	HWBus.sc
//////////////////////////////////////////////////////////////////////
#include <string.h>
#include "susan.sh"

import "i_send";
import "i_receive";
import "i_os_api";

// Simple hardware bus

#define ADDR_WIDTH	16u
#define DATA_WIDTH	7220u

#define IN_ADDR 0
#define OUT_ADDR 1

/* -----  Bus instantiation example ----- */

// Bus protocol interfaces
interface IMasterHardwareBus
{
  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
};
  
interface ISlaveHardwareBus
{
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
};


// Bus protocol channel
channel HardwareBus()
  implements IMasterHardwareBus, ISlaveHardwareBus
{
  // wires
  int A;
  uchar D[IMAGE_SIZE];
  event ack, ready;

  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {    
	A = addr;
  	notify ready;
  	wait ack;
  	waitfor(1000);
  	memcpy(data, D, sizeof(unsigned char)*len);
  }
  
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    A = addr;
    memcpy(D, data, sizeof(unsigned char)*len);
    waitfor(1000);
    notify ready;
    wait ack;
  }
  
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {
    do {
  		wait ready;
  	} while(addr != A);
  	waitfor(2000);
  	memcpy(data, D, sizeof(unsigned char)*len);
  	notify ack;
  }
  
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    do {
  		wait ready;
  	} while(addr != A);
  	waitfor(2000);
  	memcpy(D, data, sizeof(unsigned char)*len);
  	notify ack;
  }
};

interface IMasterDriver
{
  void send(const void* data, unsigned long len);
  void receive(void* data, unsigned long len);
};

interface ISlaveDriver
{
  void send(const void* data, unsigned long len);
  void receive(void* data, unsigned long len);
};

channel MasterDriver(IMasterHardwareBus Bus, i_receive read_intr, i_receive send_intr, OSAPI OS_i)
  implements IMasterDriver{
      void send(const void* data, unsigned long len){
  	int t;
    t = OS_i.pre_wait();
    send_intr.receive();
    OS_i.post_wait(t);
	  Bus.MasterWrite(OUT_ADDR, data, len);
    }

    void receive(void* data, unsigned long len){
      int t;
      t = OS_i.pre_wait();
      read_intr.receive();
      OS_i.post_wait(t);
      Bus.MasterRead(IN_ADDR, data, len);
    }
};

channel SlaveDriver(ISlaveHardwareBus Bus, i_send send_intr, i_send read_intr)
  implements ISlaveDriver{
    void send(const void* data, unsigned long len){
     	 
       send_intr.send();
       Bus.SlaveWrite(IN_ADDR, data, len);
    }

    void receive(void* data, unsigned long len){

       read_intr.send();
       Bus.SlaveRead(OUT_ADDR, data, len);
    }
};
