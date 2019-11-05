package bigData;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data_producer.DataProducer;
import data_producer.LineProducer;
import jobs.SetCount;
import jobs.WordCount;

public class Driver {
	public static void main(String[] args) {
		long start = Instant.now().getEpochSecond();
		runSetCount();
		System.out.println("Runtime: " + (Instant.now().getEpochSecond() - start));
	}
	
	private static void runWordCount() {
		DataProducer<String> producer = new LineProducer(1024 * 100000);
		WordCount wc = new WordCount(producer, 20, "and|of|then|how|on|to",
				"/home/levi/Downloads/4GB.txt", 
				"/home/levi/Documents/wordcount/4GB.txt");
		wc.execute();
	}
	
	private static void runSetCount() {
		DataProducer<String> producer = new LineProducer(1024 * 100000);
		List<Set<String>> sets = new ArrayList<>();
		Set<String> good = new HashSet<String>();
		String[] words = { "happy", "glad", "yes", "excited", "kind" };
		good.addAll(Arrays.asList(words));
		sets.add(good);
		Set<String> bad = new HashSet<String>();
		String[] badWords = { "sad", "bad", "mean", "no", "but", "however" };
		bad.addAll(Arrays.asList(badWords));
		sets.add(bad);
		SetCount setCount = new SetCount(producer, sets, 
				"/home/levi/Downloads/4GB.txt", 
				"/home/levi/Documents/setcount/4GB.txt");
		setCount.execute();
	}
}