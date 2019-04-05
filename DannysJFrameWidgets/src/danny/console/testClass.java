package danny.console;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class testClass {

	private static String stars(final int i) {
		String out = "";
		for(int a=0; a<i ;a++) {
			out+="*";
		}
		return out;
	}
	
	public static void main(String[] args) {
		consoleWindow c = new consoleWindow("Test Window",new Color(255,255,255)
				, new Color(0 ,0, 0), new Color(20,20,20), new Dimension(900,900)
				, true, JFrame.EXIT_ON_CLOSE, 18, "Courier");
		
		c.setInputText(">/");
		
		for(int i = 0;i<500; i++) {
			c.println("Test "+i);
		}

		c.readln();
		
		c.flush();
		for(int i = 0; i<100; i++){
			c.println(stars(i));	
		}	
		
	}

}
