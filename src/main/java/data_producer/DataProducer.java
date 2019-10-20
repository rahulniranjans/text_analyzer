package data_producer;

import java.io.FileNotFoundException;

public interface DataProducer<T> {
	public boolean hasRemaining();
	public T get(int amount);
	public void initialize(String filePath) throws FileNotFoundException;
	public void close();
}
