package main;

import javax.swing.*;
import java.awt.*;

public class FromText {

	public FromText() {
		//gui setup is similar to logs
		JFrame win = new JFrame("Convert from text");
		win.setSize(100,280);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setLayout(new FlowLayout());
		
		//text area with scroll bar
		JTextArea text = new JTextArea(12,28);
		text.setBackground(Color.black);
		text.setForeground(new Color(122,138,153));
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setText("Write your text here (The background is black to represent your soon-to-be sins)");
		JScrollPane scroll = new JScrollPane(text);
		win.add(scroll);
		
		//button and button action
		JButton convert = new JButton("Make Worse");
		convert.addActionListener(e -> {
			new Display(text.getText());
			win.dispose();
		});
		convert.setBorder(BorderFactory.createLineBorder(new Color(122,138,153),1));
		win.add(convert);
		
		//make it real
		win.setVisible(true);
	}

}