package bigData;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;

import data_producer.DataProducer;
import data_producer.LineProducer;
import jobs.SetCount;
import jobs.WordCount;

public class Driver {
	public static void main(String[] args) {
		final JFrame frame;
		String inputPath="", outputPath="";
		int returnValue1,returnValue2;
		JFileChooser inputFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		JFileChooser outputFile = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		try {
        	inputFile.setDialogTitle("Select the Input File");
    		returnValue1 = inputFile.showOpenDialog(null);
    		if (returnValue1 == JFileChooser.APPROVE_OPTION) {
   				File selectedFile1 = inputFile.getSelectedFile();
   				inputPath=selectedFile1.getAbsolutePath();
   				System.out.println(inputPath);
    		}
        	outputFile.setDialogTitle("Select the Output File");
    		returnValue2 = outputFile.showOpenDialog(null);
    		if (returnValue2 == JFileChooser.APPROVE_OPTION) {
   				File selectedFile2 = outputFile.getSelectedFile();
   				outputPath=selectedFile2.getAbsolutePath();
   				System.out.println(outputPath);
    		}
    		final String ip=inputPath, op=outputPath;
    		frame = new JFrame("Selection");
    	    JButton showDialogButton1 = new JButton("Word Count");
    	    JButton showDialogButton2 = new JButton("Set Count");
    	    showDialogButton1.addActionListener(new ActionListener()
    	    {
    	      public void actionPerformed(ActionEvent e)
    	      {
    	        runWordCount(ip,op);
    	    	frame.dispose();
    	      }
    	    });
    	    showDialogButton2.addActionListener(new ActionListener()
    	    {
    	      public void actionPerformed(ActionEvent e)
    	      {
    	        runSetCount(ip,op);
    	        frame.dispose();
    	      }
    	    });
    	    frame.getContentPane().setLayout(new FlowLayout());
    	    frame.add(showDialogButton1);
    	    frame.add(showDialogButton2);
    	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	    frame.setPreferredSize(new Dimension(200, 100));
    	    frame.pack();
    	    frame.setLocationRelativeTo(null);
    	    frame.setVisible(true);		
		}catch(Exception e) { e.printStackTrace(); }
	}
	
	private static void runWordCount(String inputPath, String outputPath) {
		DataProducer<String> producer = new LineProducer(1024 * 100000);
		WordCount wc = new WordCount(producer, 20, "and|of|then|how|on|to", inputPath, outputPath);
		wc.execute();
	}
	
	private static void runSetCount(String inputPath, String outputPath) {
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
		SetCount setCount = new SetCount(producer, sets, inputPath, outputPath);
		setCount.execute();
	}
}