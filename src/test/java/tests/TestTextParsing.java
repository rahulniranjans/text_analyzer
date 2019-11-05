package tests;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import data_partitioners.FilePartitioner;
import data_producer.MappedFileProducer;

public class TestTextParsing {
	private static MappedFileProducer producer;
	private byte[] bytes;
	
	@BeforeClass
	public static void initializeMappedFile() {
		producer = new MappedFileProducer(FilePartitioner.MEGABYTE * 500);
		producer.initialize("data\\itcont.txt");
	}
	
	@Before
	public void initializeBytes() {
		this.bytes = producer.get(FilePartitioner.MEGABYTE * 250);
	}

	@Test
	public void testParsingStringsWithTokenizer() {
		String text = new String(this.bytes);
		StringTokenizer tokenizer = new StringTokenizer(text, "|");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
		}
	}
	
	@Test
	public void testParsingStringsWithSplit() {
		String text = new String(this.bytes);
		String[] tokens = text.split("|");
		for (String token : tokens) {
			String t = token;
		}
	}
	
	@Test
	public void testParsingWithRegex() {
		String text = new String(this.bytes);
		Pattern pattern = Pattern.compile("\\W+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			String match = matcher.group();
		}
	}
}
