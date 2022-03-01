package main;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class Display {

	public Display(String input) {
		//time to strip down a string into something to process
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < input.length(); i++) {
			sb.append(input.charAt(i));
			
			//removes additional spaces
			if(input.charAt(i) == ' ') {
				int extraSpaces = 0;
				while(input.charAt(i+extraSpaces) == ' ')extraSpaces++;
				i += extraSpaces-1;
			}
		}
		
		//make an array of the words, along with a boolean array to keep track of fonts per word
		String strippedInput = sb.toString();
		String[] inputWords = strippedInput.split(" ");
		boolean[] wordInTNR = new boolean[inputWords.length]; //true means word should be in TNR at base
		for(int i=0, j=0; i<wordInTNR.length; i++,j++) {
			wordInTNR[i] = true;
			if(inputWords[i].contains("\n")) {
				j--;
			}
			
			if((j+1)%7 == 0) wordInTNR[i] = false; //every seventh word is in arial
			if((j+1)%3 == 0) { //every 3rd word is in owo speech
				StringBuffer owo = new StringBuffer();
				char[] wordChars = inputWords[i].toCharArray();
				for(int k=0;k<wordChars.length;k++) {
					if(wordChars[k] == 'l' || wordChars[k] == 'r') owo.append('w');
					else owo.append(wordChars[k]);
				}
				inputWords[i] = owo.toString(); //switch actual word with owo version
			}
			
			//random 5 percent chance that a word is in quotes, only if computer rolls a nat 1
			int roll20 = (int)(Math.random()*20)+1;
			if(roll20==1) inputWords[i] = "\"" + inputWords[i] + "\"";
		}
		
		//rebuild the string so the owo speech stays
		String finalInput = "";
		for(String i : inputWords) finalInput += i + " ";
		
		//now go by characters, same kind of thing as before
		char[] inputChars = finalInput.toCharArray();
		boolean[] charInTNR = new boolean[inputChars.length];
		for(int i=0,wordIndex=0,j=1; i<charInTNR.length; i++,j++) {
			if(inputChars[i]==' ') { //spaces don't do anything
				wordIndex++;
				j--;
			}
			else if(inputChars[i]=='\n') j--;
			else {//actually sets a font properly
				if(i>=charInTNR.length) break;
				charInTNR[i] = wordInTNR[wordIndex];
				if(j%7==0) charInTNR[i] = !wordInTNR[wordIndex]; //every 7th letter is in the opposite font of its word
			}
		}
		
		//now that fontsetting is done, let's make a GUI for it
		JFrame win = new JFrame("Times Noo Asshat");
		win.setSize(100,305);
		win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		win.setLocationRelativeTo(null);
		Container cp = win.getContentPane();
		JTextPane display = new JTextPane();
		SimpleAttributeSet saSet = new SimpleAttributeSet();
		Font tnr = new Font("Times New Roman", Font.PLAIN, 12);
		Font arial = new Font("Arial", Font.PLAIN, 12);
		Font sans = new Font("Comic Sans MS", Font.PLAIN, 12);
		StyleConstants.setFontFamily(saSet, tnr.getFamily()); //default to TNR at beginning
		StyleConstants.setFontSize(saSet, tnr.getSize());
		
		//Start editing displayed text
		display.setCharacterAttributes(saSet, true);
		display.setText(Character.toString(inputChars[0]));
		Document doc = display.getStyledDocument();
		
		int coloredIndex = (int)(Math.random()*20)+8;
		
		//go thru each character
		for(int i=1;i<inputChars.length;i++) {
			if(charInTNR[i] && inputChars[i] != 'w') {//character is in TNR
				saSet = new SimpleAttributeSet();
				StyleConstants.setFontFamily(saSet, tnr.getFamily());
				StyleConstants.setFontSize(saSet, tnr.getSize());
				if(!charInTNR[i-1] && inputChars[i-1] != ' ') StyleConstants.setItalic(saSet, true);
			}
			else if(inputChars[i] == 'w') { //w's are in comic sans
				saSet = new SimpleAttributeSet();
				StyleConstants.setFontFamily(saSet, sans.getFamily());
				StyleConstants.setFontSize(saSet, sans.getSize());
			}
			else {//character is in Arial
				saSet = new SimpleAttributeSet();
				StyleConstants.setFontFamily(saSet, arial.getFamily());
				StyleConstants.setFontSize(saSet, arial.getSize());
			}
			
			//every 7-27th character, including spaces, is a random color. Very random.
			if(i==coloredIndex) {
				int red = (int)(Math.random()*205);
				int green = (int)(Math.random()*205);
				int blue = (int)(Math.random()*205);
				
				//a space means the highlight is that color, otherwise it's the letter's color. may change later
				if(inputChars[i] == ' ') StyleConstants.setBackground(saSet, new Color(red,green,blue));
				else StyleConstants.setForeground(saSet, new Color(red,green,blue));
				
				coloredIndex += (int)(Math.random()*20)+8; //reset random chance
			}
			
			//10 percent chance a letter is in bold
			int roll10 = (int)(Math.random()*10)+1;
			if(roll10 == 1) StyleConstants.setBold(saSet, true);
			
			try { //insert character with font correction
				doc.insertString(doc.getLength(), Character.toString(inputChars[i]), saSet);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
		//finish it out bb
		JScrollPane scroll = new JScrollPane(display);
		cp.add(scroll, BorderLayout.CENTER);
		display.setCaretPosition(0);
		display.setEditable(false);
		win.setVisible(true);
	}
}