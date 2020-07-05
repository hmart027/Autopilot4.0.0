package hud;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class HUDCompass extends Container {

	final int MINGAP = 5;
	final int MINWIDTH = 100;
	final int MINHEIGHT = 60;

	final double MININTERVAL = 1;
	final double MAXINTERVAL = 10;

	final int MININTERVALLENGHT = 15;
	final int MAXINTERVALLENGHT = 25;

	final double MINHEADING = 0;
	final double MAXHEADING = 360;

	private Dimension contDim = new Dimension(MINWIDTH, MINHEIGHT);
	private Color color = Color.GREEN;
	private double heading = 0;
	
	private int indicatorPoints[][] = new int[2][3];

	public HUDCompass() {
		setPreferredSize(contDim);
	}

	public HUDCompass(Color color) {
		this.color = color;
		setPreferredSize(contDim);
	}

	public HUDCompass(Dimension dim) {
		if (dim.width >= MINWIDTH && dim.height >= MINHEIGHT)
			this.contDim = dim;
		setPreferredSize(contDim);
	}

	public HUDCompass(Dimension dim, Color color) {
		if (dim.width >= MINWIDTH && dim.height >= MINHEIGHT)
			this.contDim = dim;
		this.color = color;
		setPreferredSize(contDim);
	}
	
	public void setColor(Color color){
		this.color=color;
		repaint();
	}

	public void setHeading(double heading) {
		this.heading = heading;
		if (heading >= MAXHEADING)
			this.heading = MINHEADING;
		if (heading < MINHEADING)
			this.heading = MINHEADING;
		repaint();
	}

	public double getHeading() {
		return heading;
	}

	private void setPoints() {
		indicatorPoints[0][0] = contDim.width/2;
		indicatorPoints[1][0] = contDim.height - MAXINTERVALLENGHT*5/2 + 6;

		indicatorPoints[0][1] = contDim.width/2 - 8;
		indicatorPoints[1][1] = contDim.height - MAXINTERVALLENGHT*5/2 - 6;

		indicatorPoints[0][2] = contDim.width/2 + 8;
		indicatorPoints[1][2] = contDim.height - MAXINTERVALLENGHT*5/2 - 6;
	}
	
	public void paint(Graphics g) {
		setPoints();
		g.setColor(color);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		g.drawPolygon(indicatorPoints[0], indicatorPoints[1], 3);
		g.drawRect(contDim.width/2-MAXINTERVALLENGHT*3/4, contDim.height - MAXINTERVALLENGHT + 2, MAXINTERVALLENGHT*3/2, 16);
		String out=hdgString(heading/MININTERVAL);
		if(out.length()==1)
			g.drawString(out, contDim.width / 2 - MAXINTERVALLENGHT/2 + 8, contDim.height - MAXINTERVALLENGHT + 16);
		else g.drawString(out.substring(0, 3), contDim.width / 2 - MAXINTERVALLENGHT/2 + 4, contDim.height - MAXINTERVALLENGHT + 16);
		scale(g);
	}

	private String hdgString(double heading) {
		if (heading < MINHEADING)
			heading = MAXHEADING + heading;
		if (heading >= MAXHEADING)
			heading = heading - MAXHEADING;
		if (heading==0) return  "N";
		if (heading==90)return  "E";
		if (heading==180)return "S";
		if (heading==270)return "W";
		String alt = "";
		double remainder = heading;
		alt += Integer.toString((int) (remainder / 100));
		remainder = remainder - ((int) remainder / 100) * 100;
		alt += Integer.toString((int) (remainder / 10));
		remainder = remainder - ((int) remainder / 10) * 10;
		alt += Integer.toString((int) remainder);
		remainder = remainder - (int) remainder;
		alt += "." + Integer.toString((int) (remainder * 10));
		return alt;
	}

	private void scale(Graphics g) {
		double scaledAir = heading / MININTERVAL;
		if (((int) scaledAir != scaledAir)) {
			int indx = (int) (scaledAir);
			notInMiddle(indx + 1, indx, g);
			return;
		}
		inMiddle(g);
	}

	private void inMiddle(Graphics g) {
		int maxCount = contDim.width / MINGAP;
		int index = (int) (heading / MININTERVAL);
		for (int i = 0; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(contDim.width / 2 + i * MINGAP, contDim.height
						- MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP, contDim.height - 2
								* MAXINTERVALLENGHT - 2);
				if (i>6){
					String out=hdgString(index * MININTERVAL);
					if(out.length()==1)
						g.drawString(out, contDim.width / 2 + (i-1) * MINGAP, contDim.height - MAXINTERVALLENGHT + 16);
					else g.drawString(out.substring(0, 3), contDim.width / 2 + (i - 2) * MINGAP, contDim.height - MAXINTERVALLENGHT + 16);
				}
			} else
				g.drawLine(contDim.width / 2 + i * MINGAP, contDim.height
						- 2*MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP, contDim.height
								+ MININTERVALLENGHT - 2*MAXINTERVALLENGHT - 2);
			index++;
		}
		index = (int) (heading / MININTERVAL);
		for (int i = 0; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(contDim.width / 2 + i * MINGAP, contDim.height
						- MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP, contDim.height - 2
								* MAXINTERVALLENGHT - 2);
				if (i<-6){
					String out=hdgString(index * MININTERVAL);
					if(out.length()==1)
						g.drawString(out, contDim.width / 2 + (i-1)*MINGAP, contDim.height - MAXINTERVALLENGHT + 16);
					else g.drawString(out.substring(0, 3), contDim.width / 2 + (i - 2) * MINGAP, contDim.height - MAXINTERVALLENGHT + 16);
				}
			} else
				g.drawLine( contDim.width / 2 + i * MINGAP, contDim.height
						- 2*MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP, contDim.height
						+ MININTERVALLENGHT - 2*MAXINTERVALLENGHT - 2);
			index--;
		}

	}

	private void notInMiddle(int upper, int lower, Graphics g) {

		int maxCount = contDim.width / MINGAP;
		int index = upper;
		for (int i = 1; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(contDim.width / 2 + i * MINGAP - MINGAP/2 , contDim.height
						- MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP - MINGAP/2, contDim.height - 2
								* MAXINTERVALLENGHT - 2);
				if(i>6){
					String out=hdgString(index * MININTERVAL);
					if(out.length()==1)
						g.drawString(out, contDim.width / 2 + (i-1) * MINGAP - MINGAP/2, contDim.height - MAXINTERVALLENGHT + 16);
					else g.drawString(out.substring(0, 3), contDim.width / 2 + (i - 2) * MINGAP - MINGAP/2, contDim.height - MAXINTERVALLENGHT + 16);
				}	
			} else
				g.drawLine(contDim.width / 2 + i * MINGAP - MINGAP/2, contDim.height
						- 2*MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP - MINGAP/2, contDim.height
								+ MININTERVALLENGHT - 2*MAXINTERVALLENGHT - 2);
			index++;
		}
		index = lower;
		for (int i = -1; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(contDim.width / 2 + i * MINGAP + MINGAP/2, contDim.height
						- MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP + MINGAP/2, contDim.height - 2
								* MAXINTERVALLENGHT - 2);
				if(i<-6){
					String out=hdgString(index * MININTERVAL);
					if(out.length()==1)
						g.drawString(out, contDim.width / 2 + (i-1) * MINGAP + MINGAP/2, contDim.height - MAXINTERVALLENGHT + 16);
					else g.drawString(out.substring(0, 3), contDim.width / 2 + (i - 2) * MINGAP + MINGAP/2, contDim.height - MAXINTERVALLENGHT + 16);
				}
			} else
				g.drawLine(contDim.width / 2 + i * MINGAP + MINGAP/2, contDim.height
						- 2*MAXINTERVALLENGHT - 2,
						contDim.width / 2 + i * MINGAP + MINGAP/2, contDim.height
								+ MININTERVALLENGHT - 2*MAXINTERVALLENGHT - 2);
			index--;
		}
	}
}