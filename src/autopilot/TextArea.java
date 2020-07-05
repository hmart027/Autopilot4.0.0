package autopilot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class TextArea extends JScrollPane {

	private JTextArea textArea 	 = new JTextArea();
	private JTextArea log		 = new JTextArea();
	private JScrollBar vertical  = this.getVerticalScrollBar();

	public TextArea() {
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.red));
		setAutoscrolls(true);
		setPreferredSize(new Dimension(550, 300));
		setViewportView(textArea);
	}
	
	public TextArea(int width, int height) {
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				Color.red));
		setAutoscrolls(true);
		setPreferredSize(new Dimension(width, height));
		setViewportView(textArea);
	}
	
	public boolean addKeyAction(KeyListener listener){
		textArea.addKeyListener(listener);
		return true;
	}
	
	public String clear(){
		String txt=textArea.getText();
		textArea.setText("");
		log.append(txt);
		return txt;
	}
	
	public String getText(){
		return textArea.getText();
	}

	public void setEditable(boolean state){
		textArea.setEditable(state);
	}
	
	public boolean saveLog(String path, String fileName){
		try {
			FileWriter destination=new FileWriter(path+fileName);
			BufferedWriter out=new BufferedWriter(destination);
			out.write(log.getText());
			out.close();
			return true;
		} catch (IOException e) {e.printStackTrace();}
		return false;
	}
	
	public boolean addText(String txt) {

		this.textArea.append(txt);
		this.log.append(txt);
		int maxpos = vertical.getMaximum() - vertical.getVisibleAmount();
		int currentpos = vertical.getValue() + vertical.getVisibleAmount();
		if (currentpos >= maxpos) {
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		return true;
	}
}