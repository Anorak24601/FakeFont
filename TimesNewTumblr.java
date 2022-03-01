package main;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TimesNewTumblr {

	public TimesNewTumblr() {
		//this gui is very basic, just a message and buttons that lead to the other classes
		JFrame win = new JFrame("Times Noo Asshat");
		win.setSize(200,120);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.setLayout(new FlowLayout());
		
		win.add(new JLabel("Welcome to the worst font style available"));
		
		JButton fromText = new JButton("Write Text");
		JButton fromFile = new JButton("Convert Text File");
		win.add(fromText);
		win.add(fromFile);
		
		//action listener for buttons
		ActionListener act = e -> {
			if(e.getSource() == fromText) new FromText();
			if(e.getSource() == fromFile) new FromFile();
		};
		fromText.addActionListener(act);
		fromFile.addActionListener(act);
		
		win.setVisible(true);
	}

	public static void main(String[] args) {
		new TimesNewTumblr();
	}
}