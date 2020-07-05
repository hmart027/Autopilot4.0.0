package camara;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import javax.media.Buffer;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;

@SuppressWarnings("serial")
public class CameraView2_0 extends Container {

	private Dimension dim;
	private ArrayList<CaptureDeviceInfo> cameras;
	private Player player;
	private FrameGrabbingControl fgc = null;

	public CameraView2_0(Dimension dim) {
		this.dim = dim;
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setPreferredSize(this.dim);
		setBackground(Color.black);
		refreshCaptureDevices();
		new Change().start();
	}

	public void paint(Graphics g) {
		Image img=frameGrabber();
		if(img==null);
			g.fillRect(0, 0, dim.width, dim.height);
		g.drawImage(img, 0, 0, dim.width, dim.height, null);
	}

	private Image frameGrabber() {
		if (player == null)
			return null;
		// Grab a frame
		Buffer buf = fgc.grabFrame();
		// Convert it to an image
		BufferToImage btoi = new BufferToImage((VideoFormat) buf.getFormat());
		return btoi.createImage(buf);
	}

	public boolean initCamera(CaptureDeviceInfo cam) {
		if (player != null) {
			player.close();
			player = null;
			removeAll();
		}
		try {
			MediaLocator locator = cam.getLocator();

			if (locator != null)
				player = Manager.createRealizedPlayer(locator);
			if (player != null) {
				fgc = (FrameGrabbingControl) player.getControl(""
						+ "javax.media.control.FrameGrabbingControl");
				player.start();
				return true;
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Could not connect"))
				System.out.println("Could Not Connect to Camera");
		}
		;
		return false;
	}

	private void refreshCaptureDevices() {

		cameras = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Vector<CaptureDeviceInfo> list = CaptureDeviceManager
				.getDeviceList(null);
		for (CaptureDeviceInfo temp : list) {
			if (temp.getName().startsWith("vfw:")) {
				cameras.add(temp);
			}
		}
	}

	public JMenu getCameraMenue() {
		return new CameraMenue();
	}

	private class CameraMenue extends JMenu {

		public CameraMenue() {
			setText("Cameras");
			refreshCaptureDevices();
			if (cameras.size() == 0) {
				add(new JMenuItem("No cameras"));
				return;
			}
			for (CaptureDeviceInfo cam : cameras) {
				add(new CameraItem(cam));
			}
		}

		private class CameraItem extends JMenuItem {

			private CaptureDeviceInfo camera;
			private int cameraNumber = 0;

			public CameraItem(CaptureDeviceInfo cam) {
				this.camera = cam;
				this.cameraNumber++;
				setText("Camera " + this.cameraNumber);
				addActionListener(new CameraAction());
			}

			private class CameraAction implements ActionListener {

				public void actionPerformed(ActionEvent e) {
					if (initCamera(camera))
						setEnabled(false);
				}
			}
		}
	}

	private class Change extends Thread {
		public void run() {
			while (true) {
				try {
					sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
			}
		}
	}
}