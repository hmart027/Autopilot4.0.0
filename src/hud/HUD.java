package hud;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.OverlayLayout;

import drones.Drone;

@SuppressWarnings("serial")
public class HUD extends Container {

	final int MINWIDTH = 600;
	final int MINHEIGHT = 400;

	private Dimension contDim = new Dimension(MINWIDTH, MINHEIGHT);
	private Color color= Color.RED;
	private Container overlay;
	
	private Drone currentDrone;
	private HUDCompass comp;
	private HUDAirspeed air;
	private HUDAltitude alt;
	private HUDAttitude att;

	
	public HUD(Drone drone) {
		this.currentDrone=drone;
		new Frame();
		new Refresh().start();
	}
	
	public HUD(Dimension dim, Drone drone) {
		this.currentDrone=drone;
		if (dim.width >= MINWIDTH
				&& dim.height >= MINHEIGHT) {
			this.contDim = dim;
			setPreferredSize(contDim);
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			HUDContainer();
			add(overlay);
		} else
			new Frame();
		new Refresh().start();
	}

	public HUD(Container container, Drone drone) {
		this.currentDrone=drone;
		if (container.getPreferredSize().width >= MINWIDTH
				&& container.getPreferredSize().height >= MINHEIGHT) {
			this.contDim = container.getPreferredSize();
			setPreferredSize(contDim);
			setLayout(new OverlayLayout(this));
			HUDContainer();
			add(overlay);
			add(container);
		} else
			new Frame();
		new Refresh().start();
	}
	
	public HUD(Container container, Color color, Drone drone) {
		this.currentDrone=drone;
		if (container.getPreferredSize().width >= MINWIDTH
				&& container.getPreferredSize().height >= MINHEIGHT) {
			this.contDim = container.getPreferredSize();
			this.color=color;
			setPreferredSize(contDim);
			setLayout(new OverlayLayout(this));
			HUDContainer();
			add(overlay);
			add(container);
		} else
			new Frame();
		new Refresh().start();
	}
	
	public Drone getDrone(){
		return currentDrone;
	}
	
	public boolean changeDrone(Drone newDrone) {
		this.currentDrone = newDrone;
		if (currentDrone != null)
			return true;
		return false;
	}

	public void setColor(Color color){
		this.color=color;
		air.setColor(color);
		comp.setColor(color);
		alt.setColor(color);
		att.setColor(color);
	}
	
	public double getPitch(){
		return att.getPitch();
	}
	
	public double getRoll(){
		return att.getRoll();
	}
	
	public double getHeading(){
		return comp.getHeading();
	}

	public double getAirspeed(){
		return air.getAirspeed();
	}
	
	public double getAltitude(){
		return alt.getAltitude();
	}

	private void HUDContainer() {
		overlay=new Container();
		overlay.setPreferredSize(contDim);
		overlay.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		comp = new HUDCompass(new Dimension(contDim.width-400, 70), color);
		air = new HUDAirspeed(new Dimension(100, contDim.height-100), color);
		alt = new HUDAltitude(new Dimension(100, contDim.height-100), color);
		att = new HUDAttitude(new Dimension(contDim.width-200, contDim.height-100), color);
		Container cont0 = new Container();
		cont0.setPreferredSize(new Dimension(contDim.width, 20));
		Container cont1 = new Container();
		cont1.setPreferredSize(new Dimension(200, 60));
		overlay.add(cont0);
		overlay.add(air);
		overlay.add(att);
		overlay.add(alt);
		overlay.add(cont1);
		overlay.add(comp);
	}

	private class Frame extends JFrame {
		public Frame() {
			setTitle("HUD");
			setBounds(500, 300, 600, 400);
			setResizable(false);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			minimalContainer();
			setContentPane(overlay);
			setVisible(true);
			pack();
		}

		private void minimalContainer() {
			overlay = new Container();
			overlay.setPreferredSize(new Dimension(600, 400));
			overlay.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			comp = new HUDCompass(new Dimension(200, 70), color);
			att = new HUDAttitude(new Dimension(400, 300), color);
			air = new HUDAirspeed(new Dimension(100, 300), color);
			alt = new HUDAltitude(new Dimension(100, 300), color);
			Container cont0 = new Container();
			cont0.setPreferredSize(new Dimension(600, 20));
			Container cont1 = new Container();
			cont1.setPreferredSize(new Dimension(200, 60));
			overlay.add(cont0);
			overlay.add(air);
			overlay.add(att);
			overlay.add(alt);
			overlay.add(cont1);
			overlay.add(comp);
		}
	}
	
	private class Refresh extends Thread{
		public void run(){
			while(true){
				if (currentDrone!=null && currentDrone.isConnected()){
					comp.setHeading(currentDrone.getHeading());
					alt.setAltitude(currentDrone.getAltitude());
					air.setAirspeed(currentDrone.getAirspeed());
					att.setPitch(Math.toDegrees(currentDrone.getPitch()));
					att.setRoll(Math.toDegrees(currentDrone.getRoll()));
				}
				try{
					sleep(100);
				}catch(InterruptedException e){}				
			}
		}
	}

}