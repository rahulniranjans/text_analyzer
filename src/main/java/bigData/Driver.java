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

public class Driver {
	public static void main(String[] args) {
		long start = Instant.now().getEpochSecond();
		runSetCount();
		System.out.println("Runtime: " + (Instant.now().getEpochSecond() - start));
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
				"C:\\Users\\signo\\OneDrive-Purdue University Fort Wayne\\Classes\\CS590_Big_Data\\Term_Project\\data\\8GB.txt", 
				"C:\\Users\\signo\\OneDrive-Purdue University Fort Wayne\\Classes\\CS590_Big_Data\\Term_Project\\data\\setcount\\8GBOutput.txt");
		setCount.execute();
	}
}