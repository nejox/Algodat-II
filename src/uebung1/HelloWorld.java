package uebung1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HelloWorld {
	public static void main(String[] args) {

		String output = "";

		long start = System.nanoTime();
		// 10x
		for (int i = 0; i < 10; i++) {
			System.out.println("Hello World!");
		}
		long end = System.nanoTime();

		output += "10x: " + (end-start) + "\n";

		start = System.nanoTime();
		// 100x
		for (int i = 0; i < 100; i++) {
			System.out.println("Hello World!");
		}
		end = System.nanoTime();

		output += "100x: " + (end-start) + "\n";

		start = System.nanoTime();
		// 1000x
		for (int i = 0; i < 1000; i++) {
			System.out.println("Hello World!");
		}
		end = System.nanoTime();

		output += "1.000x: " + (end-start) + "\n";

		start = System.nanoTime();
		// 10.000x
		for (int i = 0; i < 10000; i++) {
			System.out.println("Hello World!");
		}
		end = System.nanoTime();

		output += "10.000x: " + (end-start) + "\n";
		System.out.println(output);


	}
}
