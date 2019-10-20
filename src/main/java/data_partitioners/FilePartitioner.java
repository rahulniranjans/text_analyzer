package data_partitioners;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FilePartitioner {
    public static int KILOBYTE = (int)Math.pow(2.0, 10.0);
    public static int MEGABYTE = (int)Math.pow(2.0, 20.0);
    public static long GIGABYTE = (long) Math.pow(2, 30);

    /*
    Reads the file from readFilePath and breaks it into output files with size of partitionSize(bytes).
    Each output file is written to writeFilePath with an index appended to the writeFilePath
    to indicate its order in all the output files.
    */
    public void partitionFile(String readFilepath, String writeFileDirectory, String writeFileName, String writeFileExtension, int partitionSize) {
        byte[] bytes = new byte[partitionSize];
        int outputFileCount = 0;
        try (FileInputStream readStream = new FileInputStream(readFilepath)) {
            do {
                readStream.read(bytes, 0, Math.min(partitionSize, readStream.available()));
                System.out.println("Read " + bytes.length + " bytes");
                String outputFilePath = writeFileDirectory + "/" + writeFileName + outputFileCount + writeFileExtension;
                try (FileOutputStream writeStream = new FileOutputStream(outputFilePath)) {
                    writeStream.write(bytes);
                    outputFileCount += 1;
                    System.out.println("Wrote output: " + outputFileCount);
                }
            } while (readStream.available() > 0);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
    }

    /*
    Appends the file located at readFilePath to the file located at writeFilePath appendTimes times.
    */
    public void appendTo(String readFilepath, String writeFilePath, int appendTimes) {
        int appendCount = 0;
        try 
        (
            FileInputStream readStream = new FileInputStream(readFilepath);
            BufferedInputStream bufferedReadStream = new BufferedInputStream(readStream);
            FileOutputStream writeStream = new FileOutputStream(writeFilePath, true);
        ) 
        {
            while (appendCount < appendTimes) {
                bufferedReadStream.mark(readStream.available() + 1);
                byte[] buffer = new byte[readStream.available()];
                bufferedReadStream.read(buffer, 0, buffer.length);
                appendCount += 1;
                System.out.println("Append " + appendCount);
                if (bufferedReadStream.markSupported()) {
                    System.out.println("Resetting stream.");
                    bufferedReadStream.reset();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
    }
}
