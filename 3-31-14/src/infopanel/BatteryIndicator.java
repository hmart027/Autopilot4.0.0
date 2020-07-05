package infopanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BatteryIndicator extends JPanel {

	private Dimension dimension;
	private BatterySymbol battery;
	private BatteryLabel label;

	public BatteryIndicator(Dimension dim,Color color) {
		this.dimension = dim;
		setPreferredSize(dimension);
		setBackground(color);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		battery=new BatterySymbol(dim.height);
		battery.setBackground(this.getBackground());
		label=new BatteryLabel(dim.height);
		label.setBackground(color);
		add(label);
		add(battery);
	}

	public void setChargeLvl(int chargeLvl) {
		battery.setChargeLvl(chargeLvl);
		label.setChargeLvl(chargeLvl);
		repaint();
	}
	
	private class BatteryLabel extends JPanel{
		
		private JLabel chargeLabel;
		private int topPanel=0;
		
		public BatteryLabel(int height){
			Dimension dim=new Dimension(height,height);
			setPreferredSize(dim);
			chargeLabel=new JLabel("0 %");
			topPanel=(height-chargeLabel.getPreferredSize().height)/4;
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, topPanel));
			add(chargeLabel);
		}
		
		public void setChargeLvl(int chargeLvl){
			chargeLabel.setText(Integer.toString(chargeLvl)+" %");
		}
	}
	
	private class BatterySymbol extends JPanel{
		
		private Dimension dimension;
		private int chargeLvl;
		private Point origin;
		private int height;
		private int batHeight;
		private int batWidht;
		private int fullWidht;
		
		public BatterySymbol(int height) {
			this.dimension = new Dimension(height*3/2,height);
			setPreferredSize(dimension);
		}
		
		public void setChargeLvl(int chargeLvl) {
			this.chargeLvl = chargeLvl;
			repaint();
		}
		
		private void drawCharge(Graphics g) {
			if(chargeLvl>100)chargeLvl=100;
			g.setColor(Color.green);
			if (chargeLvl < 80) {
				g.setColor(Color.YELLOW);
			}
			if (chargeLvl < 40) {
				g.setColor(Color.RED);
			}
			if (chargeLvl <= 90) {
				g.fillRect(origin.x + 1, origin.y + 1, batWidht * (chargeLvl) / 90
						- 1, batHeight - 1);
			}
			if (chargeLvl > 90) {
				g.fillRect(origin.x + 1, origin.y + 1, (batWidht) - 1,
						batHeight - 1);
				g.fillRect(origin.x + batWidht, origin.y + 1
						+ (int) (batHeight * 1.5 / 8), (fullWidht - batWidht)
						* (chargeLvl - 90) / 10, batHeight * 5 / 8 - 1);
			}
		}

		private void drawOutline(Graphics g) {
			height = dimension.height;
			batHeight = height * 3 / 6;
			batWidht = batHeight * 2;
			fullWidht = batHeight * 9 / 4;
			Point point1 = new Point(height / 8, height / 8);
			origin = point1;
			Point point2 = new Point(height / 8 + batWidht, height / 8);
			Point point3 = new Point(height / 8 + batWidht,
					(int) (height / 8 + batHeight * 1.5 / 8));
			Point point4 = new Point(height / 8 + batHeight * 9 / 4,
					(int) (height / 8 + batHeight * 1.5 / 8));
			Point point5 = new Point(height / 8 + batHeight * 9 / 4, (int) (height
					/ 8 + batHeight - batHeight * 1.5 / 8));
			Point point6 = new Point(height / 8 + batWidht, (int) (height / 8
					+ batHeight - batHeight * 1.5 / 8));
			Point point7 = new Point(height / 8 + batWidht, height / 8 + batHeight);
			Point point8 = new Point(height / 8, height / 8 + batHeight);

			g.setColor(Color.BLACK);
			g.drawLine(point1.x, point1.y, point2.x, point2.y);
			g.drawLine(point2.x, point2.y, point3.x, point3.y);
			g.drawLine(point3.x, point3.y, point4.x, point4.y);
			g.drawLine(point4.x, point4.y, point5.x, point5.y);
			g.drawLine(point5.x, point5.y, point6.x, point6.y);
			g.drawLine(point6.x, point6.y, point7.x, point7.y);
			g.drawLine(point7.x, point7.y, point8.x, point8.y);
			g.drawLine(point8.x, point8.y, point1.x, point1.y);
		}

		private void background (Graphics g){			
		    g.setColor(this.getBackground());
		    g.fillRect(0, 0,dimension.width,dimension.height);
		}
		
		public void paint(Graphics g) {
			background(g);
			drawOutline(g);
			drawCharge(g);
		}
	}
}