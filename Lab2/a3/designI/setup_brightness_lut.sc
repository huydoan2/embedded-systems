#include "susan.sh"
import "i_os_api";

behavior SetupBrightnessLutThread(uchar bp[516], in int thID, OSAPI OS_i)
{
       
    void main(void) {
        int   k;
        float temp;
        int thresh, form;
	
	//-------OS---------
	Task sb;
	unsigned long long time;
	sb = OS_i.task_create("sb", 1);
	OS_i.task_start(sb);
	time = 0;
	//-----------------      
  
        thresh = BT;
        form = 6;

        //for(k=-256;k<257;k++)
       for(k=(-256)+512/PROCESSORS*thID; k<(-256)+512/PROCESSORS*thID+512/PROCESSORS+1; k++){
            temp=((float)k)/((float)thresh);
            temp=temp*temp;
            if (form==6)
                temp=temp*temp*temp;
            temp=100.0*exp(-temp);
            bp[(k+258)] = (uchar) temp;
        
	    //-----Delay annotation-----
	    OS_i.time_wait(2700);
	    time = time + 2700;
	    if(time>SCH_SLICE)
	    {
	    	OS_i.yield();
		time = 0;
	    }
	    //--------------------------
        }

	OS_i.task_terminate();

    }

};
 
behavior SetupBrightnessLut(uchar bp[516], OSAPI OS_i)
{
       
    SetupBrightnessLutThread setup_brightness_thread_0(bp, 0, OS_i);
    SetupBrightnessLutThread setup_brightness_thread_1(bp, 1, OS_i);
       
    void main(void) {
        setup_brightness_thread_0;
        setup_brightness_thread_1;
    }

};

