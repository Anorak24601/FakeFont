package main;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class FromFile {

	String path;
	public FromFile() {
		JFrame window = new JFrame("Pick a .txt file");
		window.setSize(300,120);
		window.setLocationRelativeTo(null);
		window.setLayout(new FlowLayout());
		window.setBackground(Color.blue);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JTextArea pathLabel = new JTextArea(2,25);
		pathLabel.setText("Choose File (The background is black to represent your soon-to-be sins)");
		pathLabel.setEnabled(false);
		pathLabel.setLineWrap(true);
		pathLabel.setWrapStyleWord(true);
		pathLabel.setBackground(Color.black);
		JButton choose = new JButton("Choose");
		JButton convert = new JButton("Make Worse");
		convert.setEnabled(false);
		
		window.add(pathLabel);
		window.add(choose);
		window.add(convert);
		
		ActionListener act = e -> {
			if (e.getSource() == choose) {
				FileDialog fd = new FileDialog(window, "Choose", FileDialog.LOAD);
				fd.setVisible(true);
				
				if (fd.getFile() != null) {
					String fileName = fd.getFile();
					if (fileName.substring(fileName.length() - 4).equals(".txt")) {
						String filePath = fd.getDirectory();
						path = filePath + fileName;
						pathLabel.setText("Chosen File: " + fileName);
						convert.setEnabled(true);
					} else {
						pathLabel.setText("Invalid file type chosen");
						convert.setEnabled(false);
					}
				} else {
					pathLabel.setText("No File Chosen");
					convert.setEnabled(false);
				}
			}
			
			//reads file into string and then sends it to display
			if (e.getSource() == convert) {
				String content = "";
				try {
					FileReader fr = new FileReader(path);
					BufferedReader read = new BufferedReader(fr);
					
					String readln = read.readLine();
					while (readln != null) {
						content += readln;
						readln = read.readLine();
						if(readln != null) content += "\n";
					}
					read.close();
				} catch (Exception ex) {}
				
				new Display(content);
				window.dispose();
			}
		};
		
		choose.addActionListener(act);
		convert.addActionListener(act);
		
		window.setVisible(true);
	}
}