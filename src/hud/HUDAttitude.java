package hud;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

@SuppressWarnings("serial")
public class HUDAttitude extends Container {

	final int MINWIDTH = 200;
	final int MINHEIGHT = 200;

	final double MINPITCHINTERVAL = 1;
	final double MAXPITCHINTERVAL = 5;
	final double ROLLINTERVAL = 5;

	final int MINGAP = 10;
	final int DASHLENGHT = 5;

	final double MINPITCH = -90;
	final double MAXPITCH = 90;
	final double MINROLL = -180;
	final double MAXROLL = 180;

	private Dimension contDim = new Dimension(MINWIDTH, MINHEIGHT);
	private Color color = Color.GREEN;
	private double pitch = 0;
	private double roll = 0;

	private int indicatorPoints[][] = new int[2][7];
	private Point center = new Point();

	public HUDAttitude() {
		setPreferredSize(contDim);
		center.x = contDim.width / 2;
		center.y = contDim.height / 2;
	}

	public HUDAttitude(Color color) {
		this.color = color;
		setPreferredSize(contDim);
		center.x = contDim.width / 2;
		center.y = contDim.height / 2;
	}

	public HUDAttitude(Dimension dim) {
		if (dim.width >= MINWIDTH && dim.height >= MINHEIGHT)
			this.contDim = dim;
		setPreferredSize(contDim);
		center.x = contDim.width / 2;
		center.y = contDim.height / 2;
	}

	public HUDAttitude(Dimension dim, Color color) {
		if (dim.width >= MINWIDTH && dim.height >= MINHEIGHT)
			this.contDim = dim;
		this.color = color;
		setPreferredSize(contDim);
		center.x = contDim.width / 2;
		center.y = contDim.height / 2;
	}

	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	public void setPitch(double pitch) {
		this.pitch = (int)(Math.asin(Math.sin(pitch*Math.PI/180))*180/Math.PI);
		repaint();
	}

	public double getPitch() {
		return pitch;
	}

	public void setRoll(double roll) {
		this.roll = (int) roll;
		if (roll > MAXROLL)
			this.roll = MAXROLL;
		if (roll < MINROLL)
			this.roll = MINROLL;
		repaint();
	}

	public double getRoll() {
		return roll;
	}

	private void setPoints() {

		indicatorPoints[0][0] = contDim.width / 2
				+ (int) (40 * Math.cos(Math.PI));
		indicatorPoints[1][0] = contDim.height / 2
				+ (int) (40 * Math.sin(Math.PI));

		indicatorPoints[0][1] = contDim.width / 2
				+ (int) (15 * Math.cos(Math.PI));
		indicatorPoints[1][1] = contDim.height / 2
				+ (int) (15 * Math.sin(Math.PI));

		indicatorPoints[0][2] = contDim.width / 2
				+ (int) (15 * Math.cos(Math.PI - Math.PI * 2 / 6));
		indicatorPoints[1][2] = contDim.height / 2
				+ (int) (15 * Math.sin(Math.PI - Math.PI * 2 / 6));

		indicatorPoints[0][3] = contDim.width / 2
				+ (int) (5 * Math.cos(Math.PI / 2));
		indicatorPoints[1][3] = contDim.height / 2
				+ (int) (5 * Math.sin(Math.PI / 2));

		indicatorPoints[0][4] = contDim.width / 2
				+ (int) (15 * Math.cos(Math.PI * 2 / 6));
		indicatorPoints[1][4] = contDim.height / 2
				+ (int) (15 * Math.sin(Math.PI * 2 / 6));

		indicatorPoints[0][5] = contDim.width / 2 + 15;
		indicatorPoints[1][5] = contDim.height / 2;

		indicatorPoints[0][6] = contDim.width / 2 + 40;
		indicatorPoints[1][6] = contDim.height / 2;
	}

	public void paint(Graphics g) {
		setPoints();
		g.setColor(color);
		for (int i = 0; i < indicatorPoints[0].length - 1; i++) {
			g.drawLine(indicatorPoints[0][i], indicatorPoints[1][i],
					indicatorPoints[0][i + 1], indicatorPoints[1][i + 1]);
		}
		g.translate(center.x, center.y);
		pitchScale(g);
	}

	private String pitchString(double pitch) {
		String alt = "";
		if (pitch < 0) {
			pitch = Math.abs(pitch);
			alt += "-";
		}
		double remainder = pitch;
		remainder = remainder - ((int) remainder / 100) * 100;
		alt += Integer.toString((int) (remainder / 10));
		remainder = remainder - ((int) remainder / 10) * 10;
		alt += Integer.toString((int) remainder);
		return alt;
	}

	private void pitchScale(Graphics g) {
		int maxCount = contDim.height / MINGAP;
		int index = (int) (pitch / MINPITCHINTERVAL);

		for (int i = 0; i < maxCount / 2; i++) {
			if (index < 90)
				if (index - (int) (index / MAXPITCHINTERVAL) * MAXPITCHINTERVAL == 0) {
					if (index < 0)
						negative(i, index, g);
					if (index > 0)
						positive(i, index, g);
					if (index == 0) {
						Point p1 = new Point(2 * DASHLENGHT - center.x, -i
								* MINGAP);
						Point p2 = new Point(-50, -i * MINGAP);
						p1 = rotate(p1, roll);
						p2 = rotate(p2, roll);
						g.drawLine(p1.x, p1.y, p2.x, p2.y);
						p1 = new Point(50, -i * MINGAP);
						p2 = new Point(center.x - 2 * DASHLENGHT, -i * MINGAP);
						p1 = rotate(p1, roll);
						p2 = rotate(p2, roll);
						g.drawLine(p1.x, p1.y, p2.x, p2.y);
					}
				}
			index++;
		}
		index = (int) (pitch / MINPITCHINTERVAL);
		if (index > -90)
			for (int i = 0; i > -maxCount / 2; i--) {
				if (index - (int) (index / MAXPITCHINTERVAL) * MAXPITCHINTERVAL == 0) {
					if (index < 0)
						negative(i, index, g);
					if (index > 0)
						positive(i, index, g);
					if (index == 0) {
						Point p1 = new Point(2 * DASHLENGHT - center.x, -i
								* MINGAP);
						Point p2 = new Point(-50, -i * MINGAP);
						p1 = rotate(p1, roll);
						p2 = rotate(p2, roll);
						g.drawLine(p1.x, p1.y, p2.x, p2.y);
						p1 = new Point(50, -i * MINGAP);
						p2 = new Point(center.x - 2 * DASHLENGHT, -i * MINGAP);
						p1 = rotate(p1, roll);
						p2 = rotate(p2, roll);
						g.drawLine(p1.x, p1.y, p2.x, p2.y);
					}
				}
				index--;
			}
	}

	private void positive(int i, int index, Graphics g) {
		// Left side
		Point p1 = new Point(5 * DASHLENGHT - center.x*3/4, -i * MINGAP);
		Point p2 = new Point(-50, -i * MINGAP);
		p1 = rotate(p1, roll);
		p2 = rotate(p2, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p2 = new Point(5 * DASHLENGHT - center.x*3/4, -i * MINGAP - 2 * DASHLENGHT);
		p2 = rotate(p2, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p1 = new Point(4 * DASHLENGHT - center.x*3/4, - i* MINGAP - 3 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawString(pitchString(index), p1.x, p1.y);
		// Right side
		p1 = new Point(50, -i * MINGAP);
		p2 = new Point(center.x*3/4 - 5 * DASHLENGHT, -i * MINGAP);
		p1 = rotate(p1, roll);
		p2 = rotate(p2, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p1 = new Point(center.x*3/4 - 5 * DASHLENGHT, -i * MINGAP- 2 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p1 = new Point(center.x*3/4 - 6 * DASHLENGHT, - i* MINGAP - 3 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawString(pitchString(index), p1.x, p1.y);
	}

	private void negative(int i, int index, Graphics g) {
		int maxLines = (contDim.width*3/4 - 100) / DASHLENGHT - 10;
		for (int x = 0; x < maxLines / 2; x += 2) {
			// Left side
			Point p1 = new Point(- x * DASHLENGHT - 50 , -i * MINGAP);
			Point p2 = new Point(- (x+1) * DASHLENGHT - 50, -i * MINGAP);
			p1 = rotate(p1, roll);
			p2 = rotate(p2, roll);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			// Right side
			p1 = new Point(50 + x * DASHLENGHT, -i * MINGAP);
			p2 = new Point(50 + (x+1) * DASHLENGHT, -i * MINGAP);
			p1 = rotate(p1, roll);
			p2 = rotate(p2, roll);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
		
		// Left side
		Point p1 = new Point(5 * DASHLENGHT - center.x*3/4, -i * MINGAP);
		p1 = rotate(p1, roll);
		Point p2 = new Point(5 * DASHLENGHT - center.x*3/4, -i * MINGAP - 2 * DASHLENGHT);
		p2 = rotate(p2, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p1 = new Point(4 * DASHLENGHT - center.x*3/4, - i* MINGAP - 3 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawString(pitchString(index), p1.x, p1.y);
		// Right side
		p2 = new Point(center.x*3/4 - 5 * DASHLENGHT, -i * MINGAP);
		p2 = rotate(p2, roll);
		p1 = new Point(center.x*3/4 - 5 * DASHLENGHT, -i * MINGAP- 2 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		p1 = new Point(center.x*3/4 - 6 * DASHLENGHT, - i* MINGAP - 3 * DASHLENGHT);
		p1 = rotate(p1, roll);
		g.drawString(pitchString(index), p1.x, p1.y);
	}

	private Point rotate(Point p, double degAngle) {
		Point out = new Point();
		degAngle = degAngle * Math.PI / 180;
		out.x = (int) (p.x * Math.cos(degAngle) - p.y * Math.sin(degAngle));
		out.y = (int) (p.x * Math.sin(degAngle) + p.y * Math.cos(degAngle));
		return out;
	}
}