import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Main {
	
	public final static String PATH_TO_IMAGE = "MemeTemplate.png";
	public final static int LINE_LENGTH = 18;
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the top line of the meme:");
		String inTop = sc.nextLine();
		System.out.println("Enter the bottom line of the meme:");
		String inBottom = sc.nextLine();
		drawText(wrapText(inTop, LINE_LENGTH), wrapText(inBottom, LINE_LENGTH));
	}
	
	static String wrapText (String text, int len)
	{
	  // return empty array for null text
	  if (text == null)
		  return "";

	  // return text if len is zero or less
	  if (len <= 0)
		  return text;

	  // return text if less than length
	  if (text.length() <= len)
		  return text;

	  char [] chars = text.toCharArray();
	  Vector lines = new Vector();
	  StringBuffer line = new StringBuffer();
	  StringBuffer word = new StringBuffer();

	  for (int i = 0; i < chars.length; i++) {
	    word.append(chars[i]);

	    if (chars[i] == ' ') {
	      if ((line.length() + word.length()) > len) {
	        lines.add(line.toString());
	        line.delete(0, line.length());
	      }

	      line.append(word);
	      word.delete(0, word.length());
	    }
	  }

	  // handle any extra chars in current word
	  if (word.length() > 0) {
	    if ((line.length() + word.length()) > len) {
	      lines.add(line.toString());
	      line.delete(0, line.length());
	    }
	    line.append(word);
	  }

	  // handle extra line
	  if (line.length() > 0) {
	    lines.add(line.toString());
	  }

	  String [] ret = new String[lines.size()];
	  int c = 0; // counter
	  for (Enumeration e = lines.elements(); e.hasMoreElements(); c++) {
	    ret[c] = (String) e.nextElement();
	  }

	  String toReturn = "";
	  for(String s : ret) {
		  toReturn += s + "\n";
	  }
	  return toReturn;
	}
	
	public static void drawText(String top, String bottom) throws IOException {
		BufferedImage template = ImageIO.read(new File(PATH_TO_IMAGE));
		int topStartX = 15;
		int topStartY = 50;
		int botStartX = 15;
		int botStartY = 580;
		
		Graphics g = template.getGraphics();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Consolas", Font.BOLD, 36));
		for(String line : top.split("\n"))
			g.drawString(line, topStartX, topStartY += g.getFontMetrics().getHeight());
		for(String line : bottom.split("\n"))
			g.drawString(line, botStartX, botStartY += g.getFontMetrics().getHeight());
		String outputName = "output" + System.currentTimeMillis() + ".jpg";
		ImageIO.write(template,  "jpg", new File(outputName));
		System.out.println("Saved as \"output" + System.currentTimeMillis() + ".jpg\" in the location of the main file.");
	}
	
}
