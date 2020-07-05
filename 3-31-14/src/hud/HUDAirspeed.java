package hud;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class HUDAirspeed extends Container {

	final int MINGAP = 5;
	final int MINWIDTH=90;
	final int MINHEIGHT=200;

	final double MININTERVAL = 1;
	final double MAXINTERVAL = 10;

	final int MININTERVALLENGHT = 15;
	final int MAXINTERVALLENGHT = 25;

	final double MINAIRSPEED = 0;
	final double MAXAIRSPEED = 2000;

	private Dimension contDim = new Dimension(MINWIDTH, MINHEIGHT);
	private Color color = Color.GREEN;
	private double airspeed = 0;

	private int indicatorPoints[][] = new int[2][5];

	public HUDAirspeed() {
		setPreferredSize(contDim);
		setPoints();
	}
	
	public HUDAirspeed(Color color) {
		this.color = color;
		setPreferredSize(contDim);
		setPoints();
	}
	
	public HUDAirspeed(Dimension dim) {
		if (dim.width>=MINWIDTH && dim.height>=MINHEIGHT)
			this.contDim = dim;
		setPreferredSize(contDim);
		setPoints();
	}

	public HUDAirspeed(Dimension dim, Color color) {
		if (dim.width>=MINWIDTH && dim.height>=MINHEIGHT)
			this.contDim = dim;
		this.color = color;
		setPreferredSize(contDim);
		setPoints();
	}
	
	public void setColor(Color color){
		this.color=color;
		repaint();
	}

	public void setAirspeed(double airspeed) {
		this.airspeed = airspeed;
		if(airspeed>=MAXAIRSPEED)this.airspeed=MAXAIRSPEED-1;
		if(airspeed<MINAIRSPEED)this.airspeed=MINAIRSPEED;
		repaint();
	}

	public double getAirspeed() {
		return airspeed;
	}

	private void setPoints() {
		indicatorPoints[0][0] = MINWIDTH - MAXINTERVALLENGHT*3-6;
		indicatorPoints[1][0] = contDim.height / 2 - 8;

		indicatorPoints[0][1] = MINWIDTH - MAXINTERVALLENGHT*3-6;
		indicatorPoints[1][1] = contDim.height / 2 + 8;

		indicatorPoints[0][2] = MINWIDTH - MAXINTERVALLENGHT*3/2-5;
		indicatorPoints[1][2] = contDim.height / 2 + 8;

		indicatorPoints[0][3] = MINWIDTH - MAXINTERVALLENGHT-5;
		indicatorPoints[1][3] = contDim.height / 2;

		indicatorPoints[0][4] = MINWIDTH - MAXINTERVALLENGHT*3/2-5;
		indicatorPoints[1][4] = contDim.height / 2 - 8;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.drawPolygon(indicatorPoints[0], indicatorPoints[1], 5);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		g.drawString(airString(airspeed),
				MINWIDTH - MAXINTERVALLENGHT*3-5, contDim.height / 2 + 5);
		scale(g);
	}

	private String airString(double airspeed) {
		String alt = "";
		double remainder = airspeed;
		if (airspeed / 1000 >= 1) {
			alt += Integer.toString((int) (airspeed / 1000)) + ",";
			remainder = remainder - ((int) (airspeed / 1000)) * 1000;
		}
		if (airspeed / 100 > 1) {
			alt += Integer.toString((int) (remainder / 100));
			remainder = remainder - ((int) remainder / 100) * 100;
		}
		alt += Integer.toString((int) (remainder / 10));
		remainder = remainder - ((int) remainder / 10) * 10;
		alt += Integer.toString((int) remainder);
		remainder = remainder - (int) remainder;
		if (airspeed / 1000 < 1)
			alt += "."+Integer.toString((int) (remainder*10));
		return alt;
	}

	private void scale(Graphics g) {
		double scaledAir = airspeed / MININTERVAL;
		if (((int) scaledAir != scaledAir)) {
			int indx = (int) (scaledAir);
			notInMiddle(indx + 1, indx, g);
			return;
		}
		inMiddle(g);
	}

	private void inMiddle(Graphics g) {
		int maxCount = contDim.height / MINGAP;
		int index = (int) (airspeed / MININTERVAL);
		for (int i = 0; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(MINWIDTH -2, contDim.height / 2 - i * MINGAP,
						MINWIDTH - MAXINTERVALLENGHT+2, contDim.height / 2 - i * MINGAP);
				if (i > 4)
					g.drawString(
							airString(index * MININTERVAL).substring(0, 4),
							MINWIDTH - 2*MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP);
			} else
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP,
						MINWIDTH - MININTERVALLENGHT, contDim.height / 2 - i * MINGAP);
			index++;
		}
		index = (int) (airspeed / MININTERVAL);
		for (int i = 0; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP,
						MINWIDTH - MAXINTERVALLENGHT+2, contDim.height / 2 - i * MINGAP);
				if (i < -4)
					g.drawString(
							airString(index * MININTERVAL).substring(0, 4),
							MINWIDTH - 2*MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP);
			} else
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP,
						MINWIDTH - MININTERVALLENGHT, contDim.height / 2 - i * MINGAP);
			index--;
		}

	}

	private void notInMiddle(int upper, int lower, Graphics g) {

		int maxCount = contDim.height / MINGAP;
		int index = upper;
		for (int i = 1; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP + MINGAP/2,
						MINWIDTH - MAXINTERVALLENGHT+2, contDim.height / 2 - i * MINGAP + MINGAP/2);
				if (i > 4)
					g.drawString(
							airString(index * MININTERVAL).substring(0, 4),
							MINWIDTH - 2*MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP + MINGAP/2);
			} else
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP + MINGAP/2,
						MINWIDTH - MININTERVALLENGHT, contDim.height / 2 - i * MINGAP + MINGAP/2);
			index++;
		}
		index = lower;
		for (int i = -1; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP - MINGAP/2,
						MINWIDTH - MAXINTERVALLENGHT+2, contDim.height / 2 - i * MINGAP - MINGAP/2);
				if (i < -4)
					g.drawString(
							airString(index * MININTERVAL).substring(0, 4),
							MINWIDTH - 2*MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP - MINGAP/2);
			} else
				g.drawLine(MINWIDTH - 2, contDim.height / 2 - i * MINGAP - MINGAP/2,
						MINWIDTH - MININTERVALLENGHT, contDim.height / 2 - i * MINGAP - MINGAP/2);
			index--;
		}
	}
}