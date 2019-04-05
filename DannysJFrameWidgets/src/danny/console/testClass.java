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
		consoleWindow c = new consoleWindow("Test Window",new Color(0,255,0)
				, new Color(20 ,20, 0), new Color(255,20,20), new Dimension(900,900)
				, true, JFrame.EXIT_ON_CLOSE, 48, "Comic Sans MS");
		
		consoleWindow b = new consoleWindow();
		
		b.println("TEST of two consoles and the default console method");
		
		c.setInputText(">/");
		
		for(int i = 0;i<500; i++) {
			c.println("Test "+i);
		}

		c.println("Hit enter (readln test)");
		
		c.readln();
		
		c.println("Go to the other window.");
		b.println("Hit enter (readln test)");

		b.readln();
		
		c.flush();
		for(int i = 0; i<100; i++){
			c.println(stars(i));	
		}	
		
	}

}
