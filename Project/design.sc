#include <stdio.h>

import "c_packet_queue";
import "router";
import "PE_replay.sc";
import "monitor.sc";

behavior Design(){
	

	// channels
	c_packet_queue r1_p1(QUEUE_SIZE); c_packet_queue to_r1(QUEUE_SIZE);
	c_packet_queue r2_p2(QUEUE_SIZE); c_packet_queue to_r2(QUEUE_SIZE);
	c_packet_queue r3_p3(QUEUE_SIZE); c_packet_queue to_r3(QUEUE_SIZE);
	c_packet_queue r4_p4(QUEUE_SIZE); c_packet_queue to_r4(QUEUE_SIZE);
	c_packet_queue r5_p5(QUEUE_SIZE); c_packet_queue to_r5(QUEUE_SIZE);
	c_packet_queue r6_p6(QUEUE_SIZE); c_packet_queue to_r6(QUEUE_SIZE);
	c_packet_queue r7_p7(QUEUE_SIZE); c_packet_queue to_r7(QUEUE_SIZE);
	c_packet_queue r8_p8(QUEUE_SIZE); c_packet_queue to_r8(QUEUE_SIZE);
	c_packet_queue r9_p9(QUEUE_SIZE); c_packet_queue to_r9(QUEUE_SIZE);
	c_packet_queue r10_p10(QUEUE_SIZE); c_packet_queue to_r10(QUEUE_SIZE);
	c_packet_queue r11_p11(QUEUE_SIZE); c_packet_queue to_r11(QUEUE_SIZE);
	c_packet_queue r12_p12(QUEUE_SIZE); c_packet_queue to_r12(QUEUE_SIZE);
	c_packet_queue r13_p13(QUEUE_SIZE); c_packet_queue to_r13(QUEUE_SIZE);
	c_packet_queue r14_p14(QUEUE_SIZE); c_packet_queue to_r14(QUEUE_SIZE);
	c_packet_queue r15_p15(QUEUE_SIZE); c_packet_queue to_r15(QUEUE_SIZE);
	c_packet_queue r16_p16(QUEUE_SIZE); c_packet_queue to_r16(QUEUE_SIZE);
	c_packet_queue r17_p17(QUEUE_SIZE); c_packet_queue to_r17(QUEUE_SIZE);
	c_packet_queue r18_p18(QUEUE_SIZE); c_packet_queue to_r18(QUEUE_SIZE);
	c_packet_queue r19_p19(QUEUE_SIZE); c_packet_queue to_r19(QUEUE_SIZE);
	c_packet_queue r20_p20(QUEUE_SIZE); c_packet_queue to_r20(QUEUE_SIZE);
	c_packet_queue r21_p21(QUEUE_SIZE); c_packet_queue to_r21(QUEUE_SIZE);
	c_packet_queue r22_p22(QUEUE_SIZE); c_packet_queue to_r22(QUEUE_SIZE);
	c_packet_queue r23_p23(QUEUE_SIZE); c_packet_queue to_r23(QUEUE_SIZE);
	c_packet_queue r24_p24(QUEUE_SIZE); c_packet_queue to_r24(QUEUE_SIZE);
	c_packet_queue r25_p25(QUEUE_SIZE); c_packet_queue to_r25(QUEUE_SIZE);
	c_packet_queue r26_p26(QUEUE_SIZE); c_packet_queue to_r26(QUEUE_SIZE);
	c_packet_queue r27_p27(QUEUE_SIZE); c_packet_queue to_r27(QUEUE_SIZE);
	c_packet_queue r28_p28(QUEUE_SIZE); c_packet_queue to_r28(QUEUE_SIZE);
	c_packet_queue r29_p29(QUEUE_SIZE); c_packet_queue to_r29(QUEUE_SIZE);
	c_packet_queue r30_p30(QUEUE_SIZE); c_packet_queue to_r30(QUEUE_SIZE);
	c_packet_queue r31_p31(QUEUE_SIZE); c_packet_queue to_r31(QUEUE_SIZE);
	c_packet_queue r32_p32(QUEUE_SIZE); c_packet_queue to_r32(QUEUE_SIZE);
	c_packet_queue r33_p33(QUEUE_SIZE); c_packet_queue to_r33(QUEUE_SIZE);
	c_packet_queue r34_p34(QUEUE_SIZE); c_packet_queue to_r34(QUEUE_SIZE);
	c_packet_queue r35_p35(QUEUE_SIZE); c_packet_queue to_r35(QUEUE_SIZE);
	c_packet_queue r36_p36(QUEUE_SIZE); c_packet_queue to_r36(QUEUE_SIZE);
	c_packet_queue r37_p37(QUEUE_SIZE); c_packet_queue to_r37(QUEUE_SIZE);

	int start1 = 0, end1 = 0;
	int start2 = 0, end2 = 0;
	int start3 = 0, end3 = 0;
	int start4 = 0, end4 = 0;
	int start5 = 0, end5 = 0;
	int start6 = 0, end6 = 0;
	int start7 = 0, end7 = 0;
	int start8 = 0, end8 = 0;
	int start9 = 0, end9 = 0;
	int start10 = 0, end10 = 0;
	int start11 = 0, end11 = 0;
	int start12 = 0, end12 = 0;
	int start13 = 0, end13 = 0;
	int start14 = 0, end14 = 0;
	int start15 = 0, end15 = 0;
	int start16 = 0, end16 = 0;
	int start17 = 0, end17 = 0;
	int start18 = 0, end18 = 0;
	int start19 = 0, end19 = 0;
	int start20 = 0, end20 = 0;
	int start21 = 0, end21 = 0;
	int start22 = 0, end22 = 0;
	int start23 = 0, end23 = 0;
	int start24 = 0, end24 = 0;
	int start25 = 0, end25 = 0;
	int start26 = 0, end26 = 0;
	int start27 = 0, end27 = 0;
	int start28 = 0, end28 = 0;
	int start29 = 0, end29 = 0;
	int start30 = 0, end30 = 0;
	int start31 = 0, end31 = 0;
	int start32 = 0, end32 = 0;
	int start33 = 0, end33 = 0;
	int start34 = 0, end34 = 0;
	int start35 = 0, end35 = 0;
	int start36 = 0, end36 = 0;
	int start37 = 0, end37 = 0;


	int tsync1 = 0;
	int tsync2 = 0;
	int tsync3 = 0;
	int tsync4 = 0;
	int tsync5 = 0;
	int tsync6 = 0;
	int tsync7 = 0;
	int tsync8 = 0;
	int tsync9 = 0;
	int tsync10 = 0;
	int tsync11 = 0;
	int tsync12 = 0;
	int tsync13 = 0;
	int tsync14 = 0;
	int tsync15 = 0;
	int tsync16 = 0;
	int tsync17 = 0;
	int tsync18 = 0;
	int tsync19 = 0;
	int tsync20 = 0;
	int tsync21 = 0;
	int tsync22 = 0;
	int tsync23 = 0;
	int tsync24 = 0;
	int tsync25 = 0;
	int tsync26 = 0;
	int tsync27 = 0;
	int tsync28 = 0;
	int tsync29 = 0;
	int tsync30 = 0;
	int tsync31 = 0;
	int tsync32 = 0;
	int tsync33 = 0;
	int tsync34 = 0;
	int tsync35 = 0;
	int tsync36 = 0;
	int tsync37 = 0;

	// PEs
	PE pe0(0, r1_p1, to_r1, start1, end1, tsync1);
	PE pe1(1, r2_p2, to_r2, start2, end2, tsync2);
	PE pe2(2, r3_p3, to_r3, start3, end3, tsync3);
	PE pe3(3, r4_p4, to_r4, start4, end4, tsync4);
	PE pe4(4, r5_p5, to_r5, start5, end5, tsync5);
	PE pe5(5, r6_p6, to_r6, start6, end6, tsync6);
	PE pe6(6, r7_p7, to_r7, start7, end7, tsync7);
	PE pe7(7, r8_p8, to_r8, start8, end8, tsync8);
	PE pe8(8, r9_p9, to_r9, start9, end9, tsync9);
	PE pe9(9, r10_p10, to_r10, start10, end10, tsync10);
	PE pe10(10, r11_p11, to_r11, start11, end11, tsync11);
	PE pe11(11, r12_p12, to_r12, start12, end12, tsync12);
	PE pe12(12, r13_p13, to_r13, start13, end13, tsync13);
	PE pe13(13, r14_p14, to_r14, start14, end14, tsync14);
	PE pe14(14, r15_p15, to_r15, start15, end15, tsync15);
	PE pe15(15, r16_p16, to_r16, start16, end16, tsync16);
	PE pe16(16, r17_p17, to_r17, start17, end17, tsync17);
	PE pe17(17, r18_p18, to_r18, start18, end18, tsync18);
	PE pe18(18, r19_p19, to_r19, start19, end19, tsync19);
	PE pe19(19, r20_p20, to_r20, start20, end20, tsync20);
	PE pe20(20, r21_p21, to_r21, start21, end21, tsync21);
	PE pe21(21, r22_p22, to_r22, start22, end22, tsync22);
	PE pe22(22, r23_p23, to_r23, start23, end23, tsync23);
	PE pe23(23, r24_p24, to_r24, start24, end24, tsync24);
	PE pe24(24, r25_p25, to_r25, start25, end25, tsync25);
	PE pe25(25, r26_p26, to_r26, start26, end26, tsync26);
	PE pe26(26, r27_p27, to_r27, start27, end27, tsync27);
	PE pe27(27, r28_p28, to_r28, start28, end28, tsync28);
	PE pe28(28, r29_p29, to_r29, start29, end29, tsync29);
	PE pe29(29, r30_p30, to_r30, start30, end30, tsync30);
	PE pe30(30, r31_p31, to_r31, start31, end31, tsync31);
	PE pe31(31, r32_p32, to_r32, start32, end32, tsync32);
	PE pe32(32, r33_p33, to_r33, start33, end33, tsync33);
	PE pe33(33, r34_p34, to_r34, start34, end34, tsync34);
	PE pe34(34, r35_p35, to_r35, start35, end35, tsync35);
	PE pe35(35, r36_p36, to_r36, start36, end36, tsync36);
	PE pe36(36, r37_p37, to_r37, start37, end37, tsync37);

	// Routers
	int neg1 = -1;
	int neg2 = -2;
	int neg3 = -3;

	Router r1(neg3, 3, to_r1, r1_p1, to_r2, to_r34, to_r22, to_r28, to_r5, to_r6, start1, end1);
	Router r2(neg2, 3, to_r2, r2_p2, to_r3, to_r35, to_r34, to_r1, to_r6, to_r7, start2, end2);
	Router r3(neg1, 3, to_r3, r3_p3, to_r4, to_r36, to_r35, to_r2, to_r7, to_r8, start3, end3);
	Router r4(0, 3, to_r4, r4_p4, to_r16, to_r37, to_r36, to_r3, to_r8, to_r9, start4, end4);
	Router r5(neg3, 2, to_r5, r5_p5, to_r6, to_r1, to_r28, to_r33, to_r10, to_r11, start5, end5);
	Router r6(neg2, 2, to_r6, r6_p6, to_r7, to_r2, to_r1, to_r5, to_r11, to_r12, start6, end6);
	Router r7(neg1, 2, to_r7, r7_p7, to_r8, to_r3, to_r2, to_r6, to_r12, to_r13, start7, end7);
	Router r8(0, 2, to_r8, r8_p8, to_r9, to_r4, to_r3, to_r7, to_r13, to_r14, start8, end8);
	Router r9(1, 2, to_r9, r9_p9, to_r23, to_r16, to_r4, to_r8, to_r14, to_r15, start9, end9);
	Router r10(neg3, 1, to_r10, r10_p10, to_r11, to_r5, to_r33, to_r37, to_r16, to_r17, start10, end10);
	Router r11(neg2, 1, to_r11, r11_p11, to_r12, to_r6, to_r5, to_r10, to_r17, to_r18, start11, end11);
	Router r12(neg1, 1, to_r12, r12_p12, to_r13, to_r7, to_r6, to_r11, to_r18, to_r19, start12, end12);
	Router r13(0, 1, to_r13, r13_p13, to_r14, to_r8, to_r7, to_r12, to_r19, to_r20, start13, end13);
	Router r14(1, 1, to_r14, r14_p14, to_r15, to_r9, to_r8, to_r13, to_r20, to_r21, start14, end14);
	Router r15(2, 1, to_r15, r15_p15, to_r29, to_r23, to_r9, to_r14, to_r21, to_r22, start15, end15);
	Router r16(neg3, 0, to_r16, r16_p16, to_r17, to_r10, to_r37, to_r4, to_r9, to_r23, start16, end16);
	Router r17(neg2, 0, to_r17, r17_p17, to_r18, to_r11, to_r10, to_r16, to_r23, to_r24, start17, end17);
	Router r18(neg1, 0, to_r18, r18_p18, to_r19, to_r12, to_r11, to_r17, to_r24, to_r25, start18, end18);
	Router r19(0, 0, to_r19, r19_p19, to_r20, to_r13, to_r12, to_r18, to_r25, to_r26, start19, end19);
	Router r20(1, 0, to_r20, r20_p20, to_r21, to_r14, to_r13, to_r19, to_r26, to_r27, start20, end20);
	Router r21(2, 0, to_r21, r21_p21, to_r22, to_r15, to_r14, to_r20, to_r27, to_r28, start21, end21);
	Router r22(3, 0, to_r22, r22_p22, to_r34, to_r29, to_r15, to_r21, to_r28, to_r1, start22, end22);
	Router r23(neg2, neg1, to_r23, r23_p23, to_r24, to_r17, to_r16, to_r9, to_r15, to_r29, start23, end23);
	Router r24(neg1, neg1, to_r24, r24_p24, to_r25, to_r18, to_r17, to_r23, to_r29, to_r30, start24, end24);
	Router r25(0, neg1, to_r25, r25_p25, to_r26, to_r19, to_r18, to_r24, to_r30, to_r31, start25, end25);
	Router r26(1, neg1, to_r26, r26_p26, to_r27, to_r20, to_r19, to_r25, to_r31, to_r32, start26, end26);
	Router r27(2, neg1, to_r27, r27_p27, to_r28, to_r21, to_r20, to_r26, to_r32, to_r33, start27, end27);
	Router r28(3, neg1, to_r28, r28_p28, to_r1, to_r22, to_r21, to_r27, to_r33, to_r5, start28, end28);
	Router r29(neg1, neg2, to_r29, r29_p29, to_r30, to_r24, to_r23, to_r15, to_r22, to_r34, start29, end29);
	Router r30(0, neg2, to_r30, r30_p30, to_r31, to_r25, to_r24, to_r29, to_r34, to_r35, start30, end30);
	Router r31(1, neg2, to_r31, r31_p31, to_r32, to_r26, to_r25, to_r30, to_r35, to_r36, start31, end31);
	Router r32(2, neg2, to_r32, r32_p32, to_r33, to_r27, to_r26, to_r31, to_r36, to_r37, start32, end32);
	Router r33(3, neg2, to_r33, r33_p33, to_r5, to_r28, to_r27, to_r32, to_r37, to_r10, start33, end33);
	Router r34(0, neg3, to_r34, r34_p34, to_r35, to_r30, to_r29, to_r22, to_r1, to_r2, start34, end34);
	Router r35(1, neg3, to_r35, r35_p35, to_r36, to_r31, to_r30, to_r34, to_r2, to_r3, start35, end35);
	Router r36(2, neg3, to_r36, r36_p36, to_r37, to_r32, to_r31, to_r35, to_r3, to_r4, start36, end36);
	Router r37(3, neg3, to_r37, r37_p37, to_r10, to_r33, to_r32, to_r36, to_r4, to_r16, start37, end37);

	monitor m
		(
	  tsync1,
	  tsync2,
	  tsync3,
	  tsync4,
	  tsync5,
	  tsync6,
	  tsync7,
	  tsync8,
	  tsync9,
	  tsync10,
	  tsync11,
	  tsync12,
	  tsync13,
	  tsync14,
	  tsync15,
	  tsync16,
	  tsync17,
	  tsync18,
	  tsync19,
	  tsync20,
	  tsync21,
	  tsync22,
	  tsync23,
	  tsync24,
	  tsync25,
	  tsync26,
	  tsync27,
	  tsync28,
	  tsync29,
	  tsync30,
	  tsync31,
	  tsync32,
	  tsync33,
	  tsync34,
	  tsync35,
	  tsync36,
	  tsync37

		);

	void main(void){
		par{
			 m.main();
			 pe0.main(); pe1.main(); pe2.main(); pe3.main(); pe4.main(); pe5.main(); pe6.main();
			 pe7.main(); pe8.main(); pe9.main(); pe10.main(); pe11.main(); pe12.main(); pe13.main();
			 pe14.main(); pe15.main(); pe16.main(); pe17.main(); pe18.main(); pe19.main(); pe20.main();
			 pe21.main(); pe22.main(); pe23.main(); pe24.main(); pe25.main(); pe26.main(); pe27.main();
			 pe28.main(); pe29.main(); pe30.main(); pe31.main(); pe32.main(); pe33.main(); pe34.main();
			 pe35.main(); pe36.main();

			 r1.main(); r2.main(); r3.main(); r4.main(); r5.main(); r6.main(); r7.main();
			 r8.main(); r9.main(); r10.main(); r11.main(); r12.main(); r13.main(); r14.main();
			 r15.main(); r16.main(); r17.main(); r18.main(); r19.main(); r20.main(); r21.main();
			 r22.main(); r23.main(); r24.main(); r25.main(); r26.main(); r27.main(); r28.main();
			 r29.main(); r30.main(); r31.main(); r32.main(); r33.main(); r34.main(); r35.main();
			 r36.main(); r37.main();


		}
	}
};

