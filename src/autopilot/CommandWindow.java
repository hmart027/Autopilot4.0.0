package autopilot;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class CommandWindow extends JFrame {

	private Comm ports;
	private StatusBar statusBar;
	private TextArea sendText;

	// Constructor
	public CommandWindow(Comm port) {
		
		this.ports = port;
		setTitle("Command"); // Set the window title
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension wndSize = (this.getToolkit()).getScreenSize(); 

		// Set the position to screen center & size to half screen size
		setBounds(wndSize.width / 4, wndSize.height / 4, // Position
				wndSize.width / 2, 98); // Size
		addWindowListener(new WindowAction());
		setResizable(false);

		setContentPane(new ContentPane(this));
		setVisible(true);

		new ConstantRefresh().start();

	}

	private class ContentPane extends Container {

		public ContentPane(JFrame frame) {

			Dimension paneSize = new Dimension(frame.getWidth() - 10,
					frame.getHeight() - 50);
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			sendText = new TextArea();
			sendText.setEditable(true);
			sendText.setPreferredSize(new Dimension(paneSize.width - 100, 50));
			Container cont1 = new Container();
			cont1.setPreferredSize(new Dimension(paneSize.width, 50));
			cont1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			cont1.add(sendText);
			cont1.add(new SendButton(sendText));

			statusBar = new StatusBar(paneSize.width);

			add(cont1);
			add(statusBar);
			addKeyListener(new KeyAction());
		}
	}

	private class SendButton extends JButton {

		private TextArea text;

		public SendButton(TextArea text) {
			this.text = text;
			setText("Send");
			setPreferredSize(new Dimension(100, 50));
			addActionListener(new SendAction());
			setEnabled(true);
			addKeyListener(new KeyAction());
		}

		private class SendAction implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				String command = text.clear();
				ports.sendString(command);
				sendText.transferFocus();
			}
		}
	}

	private class ConstantRefresh extends Thread {
		public void run() {
			while (true) {
				if (ports.getState())
					statusBar.setConnected(ports.getPort());
				else
					statusBar.setDisconnected();
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyAction implements KeyListener{

		public void keyPressed(KeyEvent e) {
			sendText.addText(e.getKeyChar()+"");
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {
			
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
