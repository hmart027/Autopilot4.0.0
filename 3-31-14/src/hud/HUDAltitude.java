package hud;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class HUDAltitude extends Container {

	final int MINGAP = 5;
	final int MINWIDTH=90;
	final int MINHEIGHT=200;

	final double MININTERVAL = 10;
	final double MAXINTERVAL = 100;

	final int MININTERVALLENGHT = 15;
	final int MAXINTERVALLENGHT = 25;

	final double MINALTITUDE = 0;
	final double MAXALTITUDE = 100000;

	private Dimension contDim = new Dimension(MINWIDTH, MINHEIGHT);
	private Color color = Color.GREEN;
	private double altitude = 0;

	private int indicatorPoints[][] = new int[2][5];

	public HUDAltitude() {
		setPreferredSize(contDim);
		setPoints();
	}
	
	public HUDAltitude(Color color) {
		this.color = color;
		setPreferredSize(contDim);
		setPoints();
	}
	
	public HUDAltitude(Dimension dim) {
		if (dim.width>=MINWIDTH && dim.height>=MINHEIGHT)
			this.contDim = dim;
		setPreferredSize(contDim);
		setPoints();
	}

	public HUDAltitude(Dimension dim, Color color) {
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

	public void setAltitude(double altitude) {
		this.altitude =(int) altitude;
		if(altitude>=MAXALTITUDE)this.altitude=MAXALTITUDE-1;
		if(altitude<MINALTITUDE)this.altitude=MINALTITUDE;
		repaint();
	}

	public double getAltitude() {
		return altitude;
	}

	private void setPoints() {
		indicatorPoints[0][0] = MAXINTERVALLENGHT*3+6;
		indicatorPoints[1][0] = contDim.height / 2 - 8;

		indicatorPoints[0][1] = MAXINTERVALLENGHT*3+6;
		indicatorPoints[1][1] = contDim.height / 2 + 8;

		indicatorPoints[0][2] = MAXINTERVALLENGHT*3/2+5;
		indicatorPoints[1][2] = contDim.height / 2 + 8;

		indicatorPoints[0][3] = MAXINTERVALLENGHT+5;
		indicatorPoints[1][3] = contDim.height / 2;

		indicatorPoints[0][4] = MAXINTERVALLENGHT*3/2+5;
		indicatorPoints[1][4] = contDim.height / 2 - 8;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		g.drawPolygon(indicatorPoints[0], indicatorPoints[1], 5);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		g.drawString(altString(altitude),
				MAXINTERVALLENGHT*3/2+5, contDim.height / 2 + 5);
		scale(g);
	}

	private String altString(double altitude) {
		String alt = "";
		double remainder = altitude;
		if (altitude / 1000 >= 1) {
			alt += Integer.toString((int) (altitude / 1000)) + ",";
			remainder = remainder - ((int) (altitude / 1000)) * 1000;
		}
		alt += Integer.toString((int) (remainder / 100));
		remainder = remainder - ((int) remainder / 100) * 100;
		alt += Integer.toString((int) (remainder / 10));
		remainder = remainder - ((int) remainder / 10) * 10;
		if (altitude / 1000 >= 1)
			alt += Integer.toString((int) remainder);
		else
			alt += Double.toString(remainder);
		return alt;
	}

	private void scale(Graphics g) {
		double scaledAlt = altitude / MININTERVAL;
		if (((int) scaledAlt != scaledAlt)) {
			int indx = (int) (scaledAlt);
			notInMiddle(indx + 1, indx, g);
			return;
		}
		inMiddle(g);
	}

	private void inMiddle(Graphics g) {
		int maxCount = contDim.height / MINGAP;
		int index = (int) (altitude / MININTERVAL);
		for (int i = 0; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(2, contDim.height / 2 - i * MINGAP,
						MAXINTERVALLENGHT-2, contDim.height / 2 - i * MINGAP);
				if (i > 4)
					g.drawString(
							altString(index * MININTERVAL).substring(0, 4),
							MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP);
			} else
				g.drawLine(2, contDim.height / 2 - i * MINGAP,
						MININTERVALLENGHT, contDim.height / 2 - i * MINGAP);
			index++;
		}
		index = (int) (altitude / MININTERVAL);
		for (int i = 0; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(2, contDim.height / 2 - i * MINGAP,
						MAXINTERVALLENGHT-2, contDim.height / 2 - i * MINGAP);
				if (i < -4)
					g.drawString(
							altString(index * MININTERVAL).substring(0, 4),
							MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP);
			} else
				g.drawLine(2, contDim.height / 2 - i * MINGAP,
						MININTERVALLENGHT, contDim.height / 2 - i * MINGAP);
			index--;
		}

	}

	private void notInMiddle(int upper, int lower, Graphics g) {

		int maxCount = contDim.height / MINGAP;
		int index = upper;
		for (int i = 1; i < maxCount / 2; i++) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(2, contDim.height / 2 - i * MINGAP + MINGAP/2,
						MAXINTERVALLENGHT-2, contDim.height / 2 - i * MINGAP + MINGAP/2);
				if (i > 4)
					g.drawString(
							altString(index * MININTERVAL).substring(0, 4),
							MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP + MINGAP/2);
			} else
				g.drawLine(2, contDim.height / 2 - i * MINGAP + MINGAP/2,
						MININTERVALLENGHT, contDim.height / 2 - i * MINGAP + MINGAP/2);
			index++;
		}
		index = lower;
		for (int i = -1; i > -maxCount / 2; i--) {
			if (index - index / 10 * 10 == 0) {
				g.drawLine(2, contDim.height / 2 - i * MINGAP - MINGAP/2,
						MAXINTERVALLENGHT-2, contDim.height / 2 - i * MINGAP - MINGAP/2);
				if (i < -4)
					g.drawString(
							altString(index * MININTERVAL).substring(0, 4),
							MAXINTERVALLENGHT, contDim.height / 2 - (i - 1)
									* MINGAP - MINGAP/2);
			} else
				g.drawLine(2, contDim.height / 2 - i * MINGAP - MINGAP/2,
						MININTERVALLENGHT, contDim.height / 2 - i * MINGAP - MINGAP/2);
			index--;
		}
	}
}