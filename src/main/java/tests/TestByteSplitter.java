package tests;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestByteSplitter {

	@Test
	public void test() {
		byte space = 32;
		byte newline = 10;
		String words = "hi bye taco";
		byte[] bytes = words.getBytes();
		List<byte[]> wordBytes = new ArrayList<>();
		int last = -1;
		byte[] search = "taco".getBytes();
		int i = 0;
		for (; i < bytes.length; i++) {
			if (bytes[i] == space || bytes[i] == newline) {
				byte[] word = Arrays.copyOfRange(bytes, last + 1, i);
				if (Arrays.equals(word, search)) {
					System.out.println("Match Found");
				}
				if (i != bytes.length - 1) {
					last = i;
				}
			}
		}
		byte[] word;
		i -= 1;
		if (bytes[i] == space || bytes[i] == newline) {
			word = Arrays.copyOfRange(bytes, last + 1, i);
		}
		else {
			word = Arrays.copyOfRange(bytes, last + 1, i + 1);
		}
		if (Arrays.equals(word, search)) {
			System.out.println("Match Found");
		}
	}

}
