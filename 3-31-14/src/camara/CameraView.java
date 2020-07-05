package camara;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CameraView extends JPanel {

	private Dimension dim;
	private ArrayList<CaptureDeviceInfo> cameras;
	private Player player;
	private boolean showing=false;
	@SuppressWarnings("unused")
	private Format[] formats;

	public CameraView(Dimension dim) {
		this.dim = dim;
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setPreferredSize(this.dim);
		setBackground(Color.black);
		refreshCaptureDevices();
	}
	
	public boolean isShowing(){
		return showing;
	}

	public boolean initCamera(CaptureDeviceInfo cam) {
		if(player!=null){
			player.close();
			player=null;
			removeAll();
		}
		try {
			MediaLocator locator=cam.getLocator();
			formats=cam.getFormats();
				
			if (locator != null)
				player = Manager.createRealizedPlayer(locator);
			if (player != null) {
				Component visual=player.getVisualComponent();
				add(visual);
				player.start();
				revalidate();
				showing=true;
				return true;
			}
		} catch (Exception e) {
			if(e.getMessage().contains("Could not connect"))
				System.out.println("Could Not Connect to Camera");
		}
		showing=false;
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
			private int cameraNumber=0;

			public CameraItem(CaptureDeviceInfo cam) {
				this.camera = cam;
				this.cameraNumber++;
				setText("Camera "+this.cameraNumber);
				addActionListener(new CameraAction());
			}

			private class CameraAction implements ActionListener {

				public void actionPerformed(ActionEvent e) {
					if(initCamera(camera))
						setEnabled(false);
				}
			}
		}
	}
}
