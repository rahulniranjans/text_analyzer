package bigData;

import java.io.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JLabel;
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
	public static void main(String[] args) throws IOException{
		final JFrame frame;
		final JLabel a=new javax.swing.JLabel();
		final JLabel b=new javax.swing.JLabel();
		final JLabel c=new javax.swing.JLabel();
		final JLabel e=new javax.swing.JLabel();
		final JLabel f=new javax.swing.JLabel();
		final JLabel g=new javax.swing.JLabel();
		a.setText("Enter Input for Word Count\n");
		b.setText("Enter first set(Comma Separated)\n");
		c.setText("Enter second set(Comma Separated)\n");
		e.setText("----------------------------------------------\n");
		f.setText("WORD COUNT\n");
		g.setText("SET COUNT\n");
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
   				
    		}
        	outputFile.setDialogTitle("Select the Output File");
    		returnValue2 = outputFile.showOpenDialog(null);
    		if (returnValue2 == JFileChooser.APPROVE_OPTION) {
   				File selectedFile2 = outputFile.getSelectedFile();
   				outputPath=selectedFile2.getAbsolutePath();
   				
    		}
    		final String ip=inputPath, op=outputPath;
    		frame = new JFrame("Selection");
    		JTextField text=new JTextField("",10);
    		JTextField text1=new JTextField("",10);
    		JTextField text2=new JTextField("",10);
    		
    	
    	    JButton showDialogButton1 = new JButton("Word Count");
    	    JButton showDialogButton2 = new JButton("Set Count");
    	    showDialogButton1.addActionListener(new ActionListener()
    	    {
    	      public void actionPerformed(ActionEvent e)
    	      {
    	    	
    	    	  String inputString=text.getText();
    	        runWordCount(ip,op,inputString);
    	    	frame.dispose();
    	      }
    	    });
    	    showDialogButton2.addActionListener(new ActionListener()
    	    {
    	      public void actionPerformed(ActionEvent e)
    	      {
    	    	 String input1=text1.getText();
    	    	 String input2=text2.getText();
    	    	 String[] a;
    	    	 String[] b;
    	    	 
    	    	 a = input1.split(",");
    	    	 b = input2.split(",");
    	    	 
    	        runSetCount(ip,op,a,b);
    	        frame.dispose();
    	      }
    	    });
    	    
    	    
    	    frame.getContentPane().setLayout(new FlowLayout());
    	    frame.add(e);
    	    frame.add(f);
    	    frame.add(a);
    	    frame.add(text);
    	    frame.add(showDialogButton1);
    	    frame.add(e);
    	    frame.add(g);
    	    frame.add(b);
    	    frame.add(text1);
    	    frame.add(c);
    	    frame.add(text2);
    	    frame.add(showDialogButton2);
    	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	    frame.setPreferredSize(new Dimension(232, 315));
    	    frame.pack();
    	    frame.setLocationRelativeTo(null);
    	    frame.setVisible(true);		
		}catch(Exception e1) { e1.printStackTrace(); }
	}
	
	private static void runWordCount(String inputPath, String outputPath, String regEx){
		DataProducer<String> producer = new LineProducer(1024 * 100000);
		WordCount wc = new WordCount(producer, 20, regEx, inputPath, outputPath);
		wc.execute();
	}
	
	private static void runSetCount(String inputPath, String outputPath, String[] words, String[] badWords) {
		DataProducer<String> producer = new LineProducer(1024 * 100000);
		List<Set<String>> sets = new ArrayList<>();
		Set<String> good = new HashSet<String>();
		good.addAll(Arrays.asList(words));
		sets.add(good);
		Set<String> bad = new HashSet<String>();
		bad.addAll(Arrays.asList(badWords));
		sets.add(bad);
		SetCount setCount = new SetCount(producer, sets, inputPath, outputPath);
		setCount.execute();
	}
}
