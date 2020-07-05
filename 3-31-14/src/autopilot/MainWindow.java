package autopilot;

// Frame for the Sketcher application
import gauges.Gauge;
import hud.HUD;
import infopanel.InfoPanel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mavlink.MAVLink;
import camara.CameraView2_0;
import drones.Drone;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JMenuBar menuBar = new JMenuBar(); // Window menu bar
	private Comm ports = new Comm();
	private StatusBar statusBar;
	private CommandWindow commandWindow;
	private LogWindow logWindow;
	private InfoPanel infoPanel;
	private CameraView2_0 cameraView;
	private HUD hud;
	private Gauge gauges;

	// Drones Interface
	private MAVLink link;
	private ArrayList<Drone> droneList;
	private Drone currentDrone;

	// Constructor
	public MainWindow(String title) {

		setTitle(title); // Set the window title
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(menuBar); // Add the menu bar to the window
		Dimension wndSize = (this.getToolkit()).getScreenSize(); // Get screen
																	// // size

		// Set the position to screen center & size to half screen size
		setBounds(wndSize.width / 6, wndSize.height / 6, // Position
				wndSize.width * 3 / 4, wndSize.height * 3 / 4); // Size
		addWindowListener(new WindowAction());
		setResizable(false);

		// Initializing Drone Interface
		link = new MAVLink();
		droneList = link.getDroneList();

		setContentPane(new ContentPane(this));
		menuBar.add(new PortMenu(ports.serialportList()));
		menuBar.add(new ToolsMenu());
		menuBar.add(cameraView.getCameraMenue());
		menuBar.add(new DroneListMenue());
		setVisible(true);
		new MAVLinkListener().start();

		// Adds KeyListener
		addKeyListener(new KeyAction());
		requestFocus();
	}

	private class MAVLinkListener extends Thread {
		public void run() {
			while (true) {
				InputStream input = null;
				while (ports.getState())
					if (input == null)
						input = ports.getInputStream();
					else
						try {
							while (input.available() > 0) {
								int number = input.read();
						//		System.out.println(Integer.toHexString(number));
								link.mavlink_parse_char((byte) number, null);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
			}
		}
	}

	private class ContentPane extends Container {

		public ContentPane(JFrame frame) {

			Dimension paneSize = new Dimension(frame.getWidth() - 10,
					frame.getHeight() - 50);
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			statusBar = new StatusBar(paneSize.width);

			Container top = new Container();
			top.setPreferredSize(new Dimension(paneSize.width, paneSize.height
					- statusBar.getPreferredSize().height));
			top.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			infoPanel = new InfoPanel(paneSize.width / 6,
					top.getPreferredSize().height, null);
			cameraView = new CameraView2_0(new Dimension(paneSize.width * 5 / 6,
					top.getPreferredSize().height));
			hud = new HUD(cameraView, null);
			top.add(hud);
			top.add(infoPanel);
			add(top);
			add(statusBar);
			infoPanel.getFive().setThresholds((float) 3.5, (float) 2.5);
		}
	}

	private class DroneListMenue extends JMenu {

		public DroneListMenue() {
			this.setText("Drones");
			for (int i = 0; i < droneList.size(); i++)
				add(new DroneItem(i));
			add(new RefreshDrones());
		}

		private class DroneItem extends JMenuItem {

			private int index;

			public DroneItem(int indx) {
				this.index = indx;
				setText("Drone "
						+ Integer
								.toString(droneList.get(indx).getComponentID()));
				addActionListener(new DroneAction());
			}

			private class DroneAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					currentDrone=droneList.get(index);
					byte[] dat = link.sendCommand(0, 66, new byte[] { 20, 6, 1 });
					ports.sendByteArray(dat);
					dat = link.sendCommand(0, 66, new byte[] { 20, 10, 1 });
					ports.sendByteArray(dat);
					dat = link.sendCommand(0, 66, new byte[] { 20, 11, 1 });
					ports.sendByteArray(dat);
					infoPanel.changeDrone(currentDrone);
					hud.changeDrone(currentDrone);
					if (gauges != null)
						gauges.changeDrone(currentDrone);
				}
			}
		}

		private void refresh() {
			removeAll();
			for (int i = 0; i < droneList.size(); i++)
				add(new DroneItem(i));
			add(new RefreshDrones());
		}

		private class RefreshDrones extends JMenuItem {

			private RefreshDrones() {
				setText("Refresh");
				addActionListener(new RefreshAction());
			}

			class RefreshAction implements ActionListener {

				public void actionPerformed(ActionEvent e) {
					refresh();
				}
			}
		}
	}

	private class ToolsMenu extends JMenu {

		public ToolsMenu() {
			this.setText("Tools");
			add(new GaugeItem());
			add(new CommandItem());
			add(new LogItem());
		}

		private class CommandItem extends JMenuItem {

			public CommandItem() {
				setText("Command");
				addActionListener(new CommandAction());
			}

			private class CommandAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					if (commandWindow == null) {
						commandWindow = new CommandWindow(ports);
					}
					commandWindow.setVisible(true);
				}
			}
		}

		private class LogItem extends JMenuItem {

			public LogItem() {
				setText("Log");
				addActionListener(new LogAction());
			}

			private class LogAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					if (logWindow == null) {
						logWindow = new LogWindow(ports);
					}
					logWindow.setVisible(true);
				}
			}
		}

		private class GaugeItem extends JMenuItem {

			public GaugeItem() {
				setText("Gauges");
				addActionListener(new GaugeAction());
			}

			private class GaugeAction implements ActionListener {
				public void actionPerformed(ActionEvent e) {
					if (gauges == null) {
						gauges = new Gauge(currentDrone);
					}
					gauges.setVisible(true);
				}
			}
		}
	}

	private class PortMenu extends JMenu {

		private ArrayList<String> portList;

		public PortMenu(ArrayList<String> portList) {
			this.portList = portList;
			setText("Ports");
			for (String port : portList)
				add(new Ports(port));
			add(new RefreshCom());
			add(new Ports("Disconect"));
		}

		private void refresh() {
			removeAll();
			for (String port : portList)
				add(new Ports(port));
			add(new RefreshCom());
			add(new Ports("Disconect"));
		}

		private class RefreshCom extends JMenuItem {

			private RefreshCom() {
				setText("Refresh");
				addActionListener(new RefreshAction());
			}

			class RefreshAction implements ActionListener {

				public void actionPerformed(ActionEvent e) {
					portList = ports.serialportList();
					refresh();
				}
			}
		}

		private class Ports extends JMenuItem {
			private String port = null;

			private Ports(String port) {
				this.port = port;
				setText(port);
				addActionListener(new PortAction());
			}

			class PortAction implements ActionListener {

				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("Disconect")) {
						ports.closeComm();
						statusBar.setDisconnected();
						return;
					}
					ports.getComm(port, 115200);
					statusBar.setConnected(port);
				}
			}
		}
	}

	private class KeyAction implements KeyListener {

		public void keyPressed(KeyEvent e) {
			char key = e.getKeyChar();
			System.out.println(key);

			if (key == 't') {
				ports.sendByteArray(link.sendCommand(0, 66, new byte[] {0}));
			}

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