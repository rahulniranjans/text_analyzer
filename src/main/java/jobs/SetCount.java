package jobs;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.Set;

import data_producer.DataProducer;

public class SetCount implements Job<Object> {
	private DataProducer<String> producer;
	List<Set<String>> sets;
	private String inputPath;
	private String outputPath;

	public SetCount(DataProducer<String> producer, List<Set<String>> sets, String inputPath, String outputPath) {
		this.producer = producer;
		this.sets = sets;
		this.inputPath = inputPath;
		this.outputPath = outputPath;
	}

	public Object execute() {
		try {
			producer.initialize(this.inputPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HashMap<String, Long> map = setKeysToMapKeys();
		while (producer.hasRemaining()) {
			String text = producer.get(1);
			text = text.replaceAll("\\p{P}", " ");
			StringTokenizer tokenizer = new StringTokenizer(text);
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken();
				map.compute(token, (word, count) -> count == null ? null : count + 1l);
			}
		}
		producer.close();
		Map<Set<String>, Long> setCounts = computeSetCounts(map);
		writeResults(outputPath, setCounts);
		return null;
	}

	private void writeResults(String path, Map<Set<String>, Long> results) {
		try (FileWriter writer = new FileWriter(path)) {
			writer.write("set, count\n");
			for (Entry<Set<String>, Long> entry : results.entrySet()) {
				StringBuilder builder = new StringBuilder();
				entry.getKey().forEach(key-> builder.append(key + " "));
				builder.append("," + entry.getValue() + "\n");
				writer.write(builder.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private HashMap<String, Long> setKeysToMapKeys() {
		HashMap<String, Long> map = new HashMap<String, Long>();
		for (Set<String> set : sets) {
			set.forEach(word -> map.put(word, 0l));
		}
		return map;
	}
	
	private HashMap<Set<String>, Long> computeSetCounts(Map<String, Long> words) {
		HashMap<Set<String>, Long> setCounts = new HashMap<>();
		sets.forEach(set -> setCounts.put(set, 0l));
		sets.forEach(set -> {
			long total = 0l;
			for (String word : set) {
				total += words.get(word);
			}
			setCounts.put(set, total);
		});
		return setCounts;
	}
}


































