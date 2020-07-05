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
public class VSIGauge extends Container{
	
	final double MAXVSI=100.00;
	final double MINVSI=-100.00;
	
	private Image img;
	private double vsi=0;
	private JButton plus;
	private JButton minus;
	private GaugeNeedle hundrethNeedle;
	private Buttons ui;
	
	public VSIGauge(){
		try {
		    File url = new File("VSI gauge.jpg");
		    img = ImageIO.read(url);
		} catch (IOException e) {}
		hundrethNeedle=new GaugeNeedle(100, 100, 70, -Math.PI/2, Color.RED);
		ui=new Buttons();
		setPreferredSize(new Dimension(200,200));		
		LayoutManager overlay = new OverlayLayout(this);
		setLayout(overlay);
		add(new Needles());
		add(ui);
		add(new Background());
	}
	
	public double getVSI(){
		return vsi;
	}
	
	public void setVSI(double vsi){
		if(vsi<MINVSI)vsi=MINVSI;
		if(vsi>MAXVSI)vsi=MAXVSI;
		this.vsi=vsi;
		hundrethNeedle.setAngle((vsi*180/114)*Math.PI/180,this.getGraphics());
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
				vsi+=10;
				setVSI(vsi);
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
				vsi-=10;
				setVSI(vsi);
			}
		}
	}

}