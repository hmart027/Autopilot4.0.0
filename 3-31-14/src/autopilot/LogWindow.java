package autopilot;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class LogWindow extends JFrame {
	
	private Comm ports;
	private StatusBar statusBar;
	private TextArea logText;

	public LogWindow(Comm port) {
		
		this.ports = port;
		logText=port.getTextArea();
		statusBar = new StatusBar(logText.getPreferredSize().width);
		setTitle("Log");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension wndSize = (this.getToolkit()).getScreenSize();
		Dimension frameSize=new Dimension(logText.getPreferredSize().width+10,
				logText.getPreferredSize().height+statusBar.getPreferredSize().height+30);
		// Set the position to screen center & size to half screen size
		setBounds(wndSize.width / 4, wndSize.height / 4, // Position
				frameSize.width, frameSize.height); // Size
		addWindowListener(new WindowAction());
		setResizable(false);

		setContentPane(new ContentPane());
		setVisible(true);

		new ConstantRefresh().start();
		
	}
		
	private class ContentPane extends Container {

		public ContentPane() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			logText.setEditable(false);
			add(logText);
			add(statusBar);
		}
	}
		
	public void refresh() {
		if (ports.getState())
			statusBar.setConnected(ports.getPort());
		else
			statusBar.setDisconnected();
	}

	private class ConstantRefresh extends Thread {
		public void run() {
			while (true) {
				refresh();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class WindowAction implements WindowListener {

		public void windowActivated(WindowEvent arg0) {

		}

		public void windowClosed(WindowEvent arg0) {

		}

		public void windowClosing(WindowEvent arg0) {

		}

		public void windowDeactivated(WindowEvent arg0) {

		}

		public void windowDeiconified(WindowEvent arg0) {

		}

		public void windowIconified(WindowEvent arg0) {

		}

		public void windowOpened(WindowEvent arg0) {

		}
	}

}
