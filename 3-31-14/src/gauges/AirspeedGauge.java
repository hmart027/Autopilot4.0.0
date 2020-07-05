package gauges;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.OverlayLayout;


@SuppressWarnings("serial")
public class AirspeedGauge extends Container{
	
	final double MAXAIRSPEED=600.00;
	final double MINAIRSPEED=0.00;
	
	private Image img;
	private double airspeed=0;
	private JButton plus;
	private JButton minus;
	private GaugeNeedle hundrethNeedle;
	private Buttons ui;
	
	public AirspeedGauge(){
		try {
		    File url = new File("airspeed gauge.jpg");
		    img = ImageIO.read(url);
		} catch (IOException e) {}
		hundrethNeedle=new GaugeNeedle(100, 100, 70, -3*Math.PI/4, Color.RED);
		ui=new Buttons();
		setPreferredSize(new Dimension(200,200));		
		LayoutManager overlay = new OverlayLayout(this);
		setLayout(overlay);
		add(new Needles());
		add(ui);
		add(new Background());
	}
	
	public double getAirspeed(){
		return airspeed;
	}
	
	public void setAirspeed(double airspeed){
		if(airspeed<MINAIRSPEED)airspeed=MINAIRSPEED;
		if(airspeed>MAXAIRSPEED)airspeed=MAXAIRSPEED;
		this.airspeed=airspeed;
		hundrethNeedle.setAngle((airspeed*360/800)*Math.PI/180,this.getGraphics());
		repaint();
	}
	
	public void showUI(boolean show){
		ui.setVisible(show);
	}
	
	private class Needles extends Container{
		public Needles(){
			setPreferredSize(new Dimension(200,200));
		}
		public void paint(Graphics g) {
			hundrethNeedle.paint(g);
		}
	}
	
	private class Buttons extends Container{
		public Buttons(){
			setPreferredSize(new Dimension(200,200));
			setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
			plus=new IncreaseButton();
			minus=new DecreaseButton();
			Container cont1=new Container();
			int x =200-plus.getPreferredSize().width-minus.getPreferredSize().width;
			cont1.setPreferredSize(new Dimension(x,20));
			Container cont2=new Container();
			int y =200-plus.getPreferredSize().height;
			cont2.setPreferredSize(new Dimension(200,y));
			add(cont2);
			add(plus);
			add(cont1);
			add(minus);
		}
	}
	
	private class Background extends Container{
		public Background(){
			setPreferredSize(new Dimension(200,200));
		}
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0,200,200, null);
		}
	}
						
	private class IncreaseButton extends JButton{
		
		public IncreaseButton(){
			setText("+");
			addActionListener(new IncreaseAction());
			setFocusable(false);
		}
		
		private class IncreaseAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				airspeed+=10;
				setAirspeed(airspeed);
			}
		}
	}
	
	private class DecreaseButton extends JButton{
		
		public DecreaseButton(){
			setText("-");
			addActionListener(new DecreaseAction());
			setFocusable(false);
		}
		
		private class DecreaseAction implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				airspeed-=10;
				setAirspeed(airspeed);
			}
		}
	}

}