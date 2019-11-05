package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import data_partitioners.FileBuilder;

public class BuildFile {

	@Test
	public void BuildFileTest() {
		String inputFilePath = "C:\\Users\\signo\\Documents\\data\\4GB.txt";
		try {
			FileBuilder.AppendFile(inputFilePath, "C:\\Users\\signo\\Documents\\data\\8GB.txt", 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
