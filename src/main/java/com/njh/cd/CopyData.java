package com.njh.cd;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

public class CopyData {
	private static String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop/";
	private static SimpleDateFormat SDF = new SimpleDateFormat("ddMMYYYY-HH mm");
	
	public static void main(String[] args) throws UnsupportedFlavorException, IOException {
		Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
		int initialCounter = 0;
		String initialValue = new String();
		DESKTOP_PATH = DESKTOP_PATH.replace("\\", "/");
		Path newFilePath = Paths.get(DESKTOP_PATH + "NJH" + SDF.format(new Date()) + ".csv");
		System.out.println("Program has started......");
		try {
			Files.createFile(newFilePath);
		}catch(IOException ex) {			
		}
		
		int fileCounter = 1;
		String finalRow = new String();
		StringJoiner sj = new StringJoiner(",");
		
		while(true) {
			Transferable transferable = clipBoard.getContents(null);
			
			if(initialCounter == 0) {
				initialCounter++;
				if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					initialValue = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				}
			}
			
			if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
				if(!initialValue.equals(data)) {
					if(fileCounter == 1) {
						sj = new StringJoiner(",");
						sj.add(data.replaceAll("\\n", ""));
						finalRow = data;
						fileCounter ++;
					}else if (fileCounter == 2) {
						sj.add(data.replaceAll("\\n", ""));
						finalRow = data;
						fileCounter ++;
					}else {
						sj.add(data.replaceAll("\\n", ""));
						finalRow = data;
						fileCounter = 1;
						String finalString = sj.toString().concat("\n");
						Files.write(newFilePath, finalString.getBytes(), StandardOpenOption.APPEND);
						System.out.println(sj.toString());
					}
					initialValue = data;
				}
			}
			
			try {
				Thread.sleep(1000);
			}catch(InterruptedException ie) {
				
			}
			
		}
		
	}

}
