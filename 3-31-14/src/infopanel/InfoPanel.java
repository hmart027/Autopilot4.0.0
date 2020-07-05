package infopanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import drones.Drone;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {

	private Drone currentDrone;
	private BatteryIndicator chargeLvl;
	private VoltsRail five;
	private VoltsRail twelve;
	private VoltsRail twenty;
	private LevelIndicatorPanel lvls;
	private Dimension dim;
	private Color color = Color.LIGHT_GRAY;
	
	private JLabel droneStat;												

	public InfoPanel(int width, int height, Drone drone) {
		this.currentDrone = drone;
		dim = new Dimension(width, height);
		droneStat=new JLabel("No Drone Connected");							
		droneStat.setFont(new Font("Times New Roman", Font.PLAIN, 14));		
		five = new VoltsRail(dim, " 5V:", (float) 0.00);
		twelve = new VoltsRail(dim, "12V:", (float) 0.00);
		twenty = new VoltsRail(dim, "20V:", (float) 0.00);
		chargeLvl=new BatteryIndicator(new Dimension(width-4, height/10),color);
		lvls=new LevelIndicatorPanel(dim);
		setPreferredSize(dim);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		Container empty = new Container();
		empty.setPreferredSize(new Dimension(dim.width, dim.height / 5));
		add(empty);
		five.setBackground(color);
		twelve.setBackground(color);
		twenty.setBackground(color);
		lvls.setBackground(color);
		lvls.setVisibleLvl(7, false);
		lvls.setVisibleLvl(6, false);
		add(droneStat);
		add(chargeLvl);
		add(five);
		add(twelve);
		add(twenty);
		add(lvls);
		new Refresh().start();
	}
	
	public boolean changeDrone(Drone newDrone) {
		this.currentDrone = newDrone;
		if (currentDrone != null)
			return true;
		return false;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void paintComponent(Graphics comp) {
		super.paintComponent(comp);
		Graphics2D comp2D = (Graphics2D) comp;

		comp2D.setColor(color);
		comp2D.fill3DRect(0, 0, dim.width, dim.height, true);
	}

	public VoltsRail getFive() {
		return five;
	}

	public VoltsRail getTwelve() {
		return twelve;
	}

	public VoltsRail getTwenty() {
		return twenty;
	}

	private class Refresh extends Thread{
		public void run(){
			while(true){
				if (currentDrone!=null){
					if(currentDrone.isConnected())
						droneStat.setText("Drone "+Integer.toString(currentDrone.getComponentID())+": Connected");
					else
						droneStat.setText("Drone "+Integer.toString(currentDrone.getComponentID())+": Lost Connection");
					five.setVoltage(currentDrone.getFiveLvl());
					twelve.setVoltage(currentDrone.getTwelveLvl());
					twenty.setVoltage(currentDrone.getBatteryLvl());
					chargeLvl.setChargeLvl(currentDrone.getChargeLvl());
					for(int i=0;i<7;i++){
						lvls.setState(currentDrone.isSystemON(i+1),i+1);
					}
				}
				try{
					sleep(10);
				}catch(InterruptedException e){}				
			}
		}
	}
}