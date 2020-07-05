package gauges;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import drones.Drone;

@SuppressWarnings("serial")
public class Gauge extends JFrame {

	private Drone currentDrone;
	private AltitudeGauge altgauge;
	private AirspeedGauge airspeed;
	private VSIGauge vsi;

	public Gauge(Drone drone) {
		this.currentDrone = drone;
		setTitle("Gauges");
		setBounds(500, 300, 200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(new ContentPane());
		setVisible(true);
		pack();
		new Refresh().start();
	}

	private class ContentPane extends Container {
		public ContentPane() {
			setPreferredSize(new Dimension(400, 400));
			altgauge = new AltitudeGauge();
			airspeed = new AirspeedGauge();
			vsi = new VSIGauge();
			altgauge.showUI(false);
			airspeed.showUI(false);
			vsi.showUI(false);
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			add(altgauge);
			add(airspeed);
			add(vsi);
		}
	}

	public boolean changeDrone(Drone newDrone) {
		this.currentDrone = newDrone;
		if (currentDrone != null)
			return true;
		return false;
	}

	private class Refresh extends Thread {
		public void run() {
			while (true) {
				if (isVisible())
					if (currentDrone != null&&currentDrone.isConnected()) {
						altgauge.setAltitude(currentDrone.getAltitude());
						airspeed.setAirspeed(currentDrone.getAirspeed());
						vsi.setVSI(currentDrone.getClimbRate());
					}
				try {
					sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}