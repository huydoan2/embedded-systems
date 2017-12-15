
import "c_packet_queue";
import "router";



Behavior PE(unsigned int id, i_packet_receiver router, i_packet_sender pe){
	
	void Main(void){
		packet p;
		if (id != 1){
			p.sender = id;
			p.time = 5;
			p.target = 1;
			pe.send(&p, 1);
		}
		else{
			router.receive(&p, 1);
			printf("sender %d\ntime%d\ntarget%d\n", p.sender, p.time, p.target);
		}
	}
}



Behavior Design(){

	// channels
	c_packet_queue r1_p1; c_packet_queue to_r1;
	c_packet_queue r2_p2; c_packet_queue to_r2;
	c_packet_queue r3_p3; c_packet_queue to_r3;
	c_packet_queue r4_p4; c_packet_queue to_r4;
	c_packet_queue r5_p5; c_packet_queue to_r5;
	c_packet_queue r6_p6; c_packet_queue to_r6;
	c_packet_queue r7_p7; c_packet_queue to_r7;
	c_packet_queue r8_p8; c_packet_queue to_r8;
	c_packet_queue r9_p9; c_packet_queue to_r9;
	c_packet_queue r10_p10; c_packet_queue to_r10;
	c_packet_queue r11_p11; c_packet_queue to_r11;
	c_packet_queue r12_p12; c_packet_queue to_r12;
	c_packet_queue r13_p13; c_packet_queue to_r13;
	c_packet_queue r14_p14; c_packet_queue to_r14;
	c_packet_queue r15_p15; c_packet_queue to_r15;
	c_packet_queue r16_p16; c_packet_queue to_r16;
	c_packet_queue r17_p17; c_packet_queue to_r17;
	c_packet_queue r18_p18; c_packet_queue to_r18;
	c_packet_queue r19_p19; c_packet_queue to_r19;
	c_packet_queue r20_p20; c_packet_queue to_r20;
	c_packet_queue r21_p21; c_packet_queue to_r21;
	c_packet_queue r22_p22; c_packet_queue to_r22;
	c_packet_queue r23_p23; c_packet_queue to_r23;
	c_packet_queue r24_p24; c_packet_queue to_r24;
	c_packet_queue r25_p25; c_packet_queue to_r25;
	c_packet_queue r26_p26; c_packet_queue to_r26;
	c_packet_queue r27_p27; c_packet_queue to_r27;
	c_packet_queue r28_p28; c_packet_queue to_r28;
	c_packet_queue r29_p29; c_packet_queue to_r29;
	c_packet_queue r30_p30; c_packet_queue to_r30;
	c_packet_queue r31_p31; c_packet_queue to_r31;
	c_packet_queue r32_p32; c_packet_queue to_r32;
	c_packet_queue r33_p33; c_packet_queue to_r33;
	c_packet_queue r34_p34; c_packet_queue to_r34;
	c_packet_queue r35_p35; c_packet_queue to_r35;
	c_packet_queue r36_p36; c_packet_queue to_r36;
	c_packet_queue r37_p37; c_packet_queue to_r37;


	// PEs
	PE pe0(0, r1_p1, to_r1);
	PE pe1(1, r2_p2, to_r2);
	PE pe2(2, r3_p3, to_r3);
	PE pe3(3, r4_p4, to_r4);
	PE pe4(4, r5_p5, to_r5);
	PE pe5(5, r6_p6, to_r6);
	PE pe6(6, r7_p7, to_r7);
	PE pe7(7, r8_p8, to_r8);
	PE pe8(8, r9_p9, to_r9);
	PE pe9(9, r10_p10, to_r10);
	PE pe10(10, r11_p11, to_r11);
	PE pe11(11, r12_p12, to_r12);
	PE pe12(12, r13_p13, to_r13);
	PE pe13(13, r14_p14, to_r14);
	PE pe14(14, r15_p15, to_r15);
	PE pe15(15, r16_p16, to_r16);
	PE pe16(16, r17_p17, to_r17);
	PE pe17(17, r18_p18, to_r18);
	PE pe18(18, r19_p19, to_r19);
	PE pe19(19, r20_p20, to_r20);
	PE pe20(20, r21_p21, to_r21);
	PE pe21(21, r22_p22, to_r22);
	PE pe22(22, r23_p23, to_r23);
	PE pe23(23, r24_p24, to_r24);
	PE pe24(24, r25_p25, to_r25);
	PE pe25(25, r26_p26, to_r26);
	PE pe26(26, r27_p27, to_r27);
	PE pe27(27, r28_p28, to_r28);
	PE pe28(28, r29_p29, to_r29);
	PE pe29(29, r30_p30, to_r30);
	PE pe30(30, r31_p31, to_r31);
	PE pe31(31, r32_p32, to_r32);
	PE pe32(32, r33_p33, to_r33);
	PE pe33(33, r34_p34, to_r34);
	PE pe34(34, r35_p35, to_r35);
	PE pe35(35, r36_p36, to_r36);
	PE pe36(36, r37_p37, to_r37);

	// Routers
	Router r1(-3, 3, to_r1, r1_p1, to_r2, to_r34, to_r22, to_r28, to_r5, to_r6);
	Router r2(-2, 3, to_r2, r2_p2, to_r3, to_r35, to_r34, to_r1, to_r6, to_r7);
	Router r3(-1, 3, to_r3, r3_p3, to_r4, to_r36, to_r35, to_r2, to_r7, to_r8);
	Router r4(0, 3, to_r4, r4_p4, to_r16, to_r37, to_r36, to_r3, to_r8, to_r9);
	Router r5(-3, 2, to_r5, r5_p5, to_r6, to_r1, to_r28, to_r33, to_r10, to_r11);
	Router r6(-2, 2, to_r6, r6_p6, to_r7, to_r2, to_r1, to_r5, to_r11, to_r12);
	Router r7(-1, 2, to_r7, r7_p7, to_r8, to_r3, to_r2, to_r6, to_r12, to_r13);
	Router r8(0, 2, to_r8, r8_p8, to_r9, to_r4, to_r3, to_r7, to_r13, to_r14);
	Router r9(1, 2, to_r9, r9_p9, to_r23, to_r16, to_r4, to_r8, to_r14, to_r15);
	Router r10(-3, 1, to_r10, r10_p10, to_r11, to_r5, to_r33, to_r37, to_r16, to_r17);
	Router r11(-2, 1, to_r11, r11_p11, to_r12, to_r6, to_r5, to_r10, to_r17, to_r18);
	Router r12(-1, 1, to_r12, r12_p12, to_r13, to_r7, to_r6, to_r11, to_r18, to_r19);
	Router r13(0, 1, to_r13, r13_p13, to_r14, to_r8, to_r7, to_r12, to_r19, to_r20);
	Router r14(1, 1, to_r14, r14_p14, to_r15, to_r9, to_r8, to_r13, to_r20, to_r21);
	Router r15(2, 1, to_r15, r15_p15, to_r29, to_r23, to_r9, to_r14, to_r21, to_r22);
	Router r16(-3, 0, to_r16, r16_p16, to_r17, to_r10, to_r37, to_r4, to_r9, to_r23);
	Router r17(-2, 0, to_r17, r17_p17, to_r18, to_r11, to_r10, to_r16, to_r23, to_r24);
	Router r18(-1, 0, to_r18, r18_p18, to_r19, to_r12, to_r11, to_r17, to_r24, to_r25);
	Router r19(0, 0, to_r19, r19_p19, to_r20, to_r13, to_r12, to_r18, to_r25, to_r26);
	Router r20(1, 0, to_r20, r20_p20, to_r21, to_r14, to_r13, to_r19, to_r26, to_r27);
	Router r21(2, 0, to_r21, r21_p21, to_r22, to_r15, to_r14, to_r20, to_r27, to_r28);
	Router r22(3, 0, to_r22, r22_p22, to_r34, to_r29, to_r15, to_r21, to_r28, to_r1);
	Router r23(-2, -1, to_r23, r23_p23, to_r24, to_r17, to_r16, to_r9, to_r15, to_r29);
	Router r24(-1, -1, to_r24, r24_p24, to_r25, to_r18, to_r17, to_r23, to_r29, to_r30);
	Router r25(0, -1, to_r25, r25_p25, to_r26, to_r19, to_r18, to_r24, to_r30, to_r31);
	Router r26(1, -1, to_r26, r26_p26, to_r27, to_r20, to_r19, to_r25, to_r31, to_r32);
	Router r27(2, -1, to_r27, r27_p27, to_r28, to_r21, to_r20, to_r26, to_r32, to_r33);
	Router r28(3, -1, to_r28, r28_p28, to_r1, to_r22, to_r21, to_r27, to_r33, to_r5);
	Router r29(-1, -2, to_r29, r29_p29, to_r30, to_r24, to_r23, to_r15, to_r22, to_r34);
	Router r30(0, -2, to_r30, r30_p30, to_r31, to_r25, to_r24, to_r29, to_r34, to_r35);
	Router r31(1, -2, to_r31, r31_p31, to_r32, to_r26, to_r25, to_r30, to_r35, to_r36);
	Router r32(2, -2, to_r32, r32_p32, to_r33, to_r27, to_r26, to_r31, to_r36, to_r37);
	Router r33(3, -2, to_r33, r33_p33, to_r5, to_r28, to_r27, to_r32, to_r37, to_r10);
	Router r34(0, -3, to_r34, r34_p34, to_r35, to_r30, to_r29, to_r22, to_r1, to_r2);
	Router r35(1, -3, to_r35, r35_p35, to_r36, to_r31, to_r30, to_r34, to_r2, to_r3);
	Router r36(2, -3, to_r36, r36_p36, to_r37, to_r32, to_r31, to_r35, to_r3, to_r4);
	Router r37(3, -3, to_r37, r37_p37, to_r10, to_r33, to_r32, to_r36, to_r4, to_r16);


	void Main(void){
		par{
			 pe0.main(); pe1.main(); pe2.main(); pe3.main(); pe4.main(); pe5.main(); pe6.main();
			 pe7.main(); pe8.main(); pe9.main(); pe10.main(); pe11.main(); pe12.main(); pe13.main();
			 pe14.main(); pe15.main(); pe16.main(); pe17.main(); pe18.main(); pe19.main(); pe20.main();
			 pe21.main(); pe22.main(); pe23.main(); pe24.main(); pe25.main(); pe26.main(); pe27.main();
			 pe28.main(); pe29.main(); pe30.main(); pe31.main(); pe32.main(); pe33.main(); pe34.main();
			 pe35.main(); pe36.main();

			 r1_p1.main(); r2_p2.main(); r3_p3.main(); r4_p4.main(); r5_p5.main(); r6_p6.main(); r7_p7.main();
			 r8_p8.main(); r9_p9.main(); r10_p10.main(); r11_p11.main(); r12_p12.main(); r13_p13.main(); r14_p14.main();
			 r15_p15.main(); r16_p16.main(); r17_p17.main(); r18_p18.main(); r19_p19.main(); r20_p20.main(); r21_p21.main();
			 r22_p22.main(); r23_p23.main(); r24_p24.main(); r25_p25.main(); r26_p26.main(); r27_p27.main(); r28_p28.main();
			 r29_p29.main(); r30_p30.main(); r31_p31.main(); r32_p32.main(); r33_p33.main(); r34_p34.main(); r35_p35.main();
			 r36_p36.main(); r37_p37.main();


		}
	}
}

Behavior Main(){
	Design d;

	int Main(void){
		d.main();
	}
}