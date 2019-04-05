package danny.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class consoleWindow {
	
	//vars
	private JFrame jframe;		//master
	private JTextArea log;
	private JScrollPane scrollPane;
	private JTextArea input;
	
	public boolean readyToReadFlag;
	public String name;
	
	final private KeyListener er = new EnterRemover();
		
	//constants	
	static final private BorderLayout layout = new BorderLayout (0, 0);
	static final private int max = 100000;
	
	//some defaults for lazy people i.e: me
	static final public String defaultFont = "Courier";
	static final public int defaultFontSize = 18;
	static final public Color defaultTextColor = new Color(255,255,255);
	static final public Color defaultForegroundColor = new Color(20,20,20);
	static final public Color defaultBackgroundColor = new Color(0 ,0, 0);
	
	public void setBackgroundColour(Color bgColour) {
		this.jframe.setBackground(bgColour);
		this.input.setBackground(bgColour);
		this.log.setBackground(bgColour);
	}
	
	public void setForegroundColour(Color fgColour) {
		this.jframe.setBackground(fgColour);
		this.input.setBackground(fgColour);
		this.log.setBackground(fgColour);
	}
	
	public void setJFrameVisibility(boolean visibility) {
		this.jframe.setVisible(visibility);
	}
	
	public JPanel getJPanelFromConsole() {
		JPanel out= new JPanel(layout);
		out.add(this.log);
		out.add(this.input);
		return out;
	}
	
	//methods
	public String getInputText() {
		return this.input.getText();
	}
	
	public void setInputText(String text) {
		this.input.setText(text);
	}
	
	public void clearInputField() {
		this.setInputText("");
	}
	
	private String wrappedTextFrom(String txt, int charsPerRow) {
		String out = "";
		
		if(charsPerRow==0) {
			System.out.println("Line 79 error "+charsPerRow);
			return txt;
		}
		
		for(String line: txt.split("\n")) {
			int i = 1;
			for(char c: line.toCharArray()) {
				
				if(i%charsPerRow == 0) {
					out+="\n";
				}
				
				out+=c;
				
				i++;
			}
		}
		
		return out;
	}
	
	public void println(String text) {
		text = wrappedTextFrom(text, this.log.getRows());	//resizable windows have row length varying
		
		String txt = this.log.getText();
		this.log.setText(txt+text+"\n");
		
		txt+=text+"\n";
		
		int Counter = text.split("\n").length;
		
		this.scrollPane.getVerticalScrollBar().setValue(
				this.scrollPane.getVerticalScrollBar().getValue() + Counter + 1 );
	}
		
	public void flush() {
		this.log.setText("");
		this.scrollPane.getVerticalScrollBar().setValue(0);
	}
	
	public void setName(String name) {
		this.name = name;
		this.jframe.setTitle(name);
	}
	
	public String readln() {
		String out = "";		
		this.input.removeKeyListener(this.er);		
		KeyListener keyL = new EnterListener(this);
		this.input.addKeyListener(keyL);
		
		while (!this.readyToReadFlag) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		out = this.getInputText().replaceAll("\n", "");
		
		this.setInputText("");
		this.input.removeKeyListener(keyL);		
		this.input.addKeyListener(this.er);
				
		return out;		
	}
	
	public consoleWindow(String name, Color TextColour,	Color BackGroundColour, 
			Color BackGroundColourTextArea, Dimension size, boolean Resizeable,
			int closeOperation, int fontSize, String fontName) {
		
		this.name = name;
		
		//instaniate
		
		this.jframe = new JFrame(name);
		this.jframe.setBackground(BackGroundColour);
		this.jframe.setLayout(layout);		
		
		this.log = new JTextArea();	
		
		DefaultCaret caret = (DefaultCaret)log.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);	

		this.scrollPane = new JScrollPane(this.log);

		this.input = new JTextArea();
		
		//add objects		
		this.jframe.add(this.log, BorderLayout.NORTH);
		this.jframe.add(this.input, BorderLayout.SOUTH);
		this.jframe.getContentPane().add(this.scrollPane);	
		
		//set properties		
		this.jframe.setSize(size);
		this.jframe.setDefaultCloseOperation(closeOperation);
		this.jframe.setResizable(Resizeable);
		this.jframe.setBackground(BackGroundColour);
		this.jframe.setForeground(TextColour);
		this.jframe.setFont(new Font(fontName, Font.BOLD,fontSize));
				
		this.log.setBackground(BackGroundColour);
		this.log.setForeground(TextColour);
		this.log.setSize(size.width, size.height-75);
		this.log.setFont(new Font(fontName, Font.BOLD,fontSize));
		this.log.setText("Console widget loaded.\n(c) 2019 github.com/djpiper28 \n");
		this.log.setCaretColor(TextColour);
		this.log.setEditable(false);
		this.log.setWrapStyleWord(true);
		this.log.setAutoscrolls(true);
		this.log.setMaximumSize(new Dimension(size.width, size.height-fontSize*3));
		this.log.setPreferredSize(new Dimension(size.width, size.height-fontSize*3));
		this.log.setLineWrap(true);
		this.log.setRows(max);

		this.scrollPane.setSize(new Dimension(30,size.height));
		this.scrollPane.setBackground(new Color(200,50,200));
		this.scrollPane.setForeground(new Color(255,255,255));
		this.scrollPane.setViewportView(this.log);
		this.scrollPane.setAutoscrolls(true);
		this.scrollPane.getVerticalScrollBar().setAutoscrolls(true);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.input.setLineWrap(true);
		this.input.setBackground(BackGroundColourTextArea);
		this.input.setEditable(true);
		this.input.setForeground(TextColour);
		this.input.setSize(size.width, 10);
		this.input.setFont(new Font("Courier", Font.BOLD,fontSize));
		this.input.setText("");
		this.input.setCaretColor(TextColour);
		this.input.setPreferredSize(new Dimension(size.width, fontSize*3));
		this.input.setMaximumSize(new Dimension(size.width, fontSize*3));
		this.input.setWrapStyleWord(true);
		this.input.setAutoscrolls(true);
		this.input.addKeyListener(this.er);				
		
		for(Component c:this.jframe.getComponents()) {
			c.setVisible(true);
		}
		this.jframe.setVisible(true);

	}
	
}
class EnterListener implements KeyListener {

	final private consoleWindow frame;
	
	public EnterListener(consoleWindow frame) {
		this.frame = frame;
		this.frame.readyToReadFlag = false;;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyChar()=='\n') {
			this.frame.readyToReadFlag = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
class EnterRemover implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyChar()=='\n') {
			e.consume();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}

