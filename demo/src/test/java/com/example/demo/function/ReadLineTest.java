package com.example.demo.function;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ReadLineTest {

	@Test
	void test() {
		String fileAddress = "src/main/resources/26000.txt";
		long start = System.currentTimeMillis();
		ReadLine rl = new ReadLine(fileAddress,10000);
		System.out.println(System.currentTimeMillis()-start);
		String linetest = "CHAPTER XIV";
		System.out.println(linetest);
		String res = rl.read(52879).getContent();
		System.out.println(res);
		System.out.println(System.currentTimeMillis()-start);
		assertTrue(linetest.equals(res));
	}

}
