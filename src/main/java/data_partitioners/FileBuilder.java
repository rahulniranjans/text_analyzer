package data_partitioners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileBuilder {
	public static void AppendFile(String inputFile, String outputFile, int appendTimes) throws IOException {
		int appendCount = 0;
		while (appendCount < appendTimes) {
			FileReader fReader = new FileReader(inputFile);
			BufferedReader reader = new BufferedReader(fReader);
			append(reader, outputFile);
			reader.close();
			appendCount++;
		}
	}
	
	private static void append(BufferedReader reader, String outputPath) throws IOException {
		FileWriter fWriter = new FileWriter(outputPath, true);
		BufferedWriter writer = new BufferedWriter(fWriter);
		reader.lines().forEach(line -> {
			try {
				writer.write(line);
				writer.append("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		writer.close();
	}
}
