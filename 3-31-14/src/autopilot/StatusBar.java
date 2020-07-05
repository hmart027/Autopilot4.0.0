package autopilot;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatusBar extends Container {
	
	private JLabel status=new JLabel("Not Connected");
	private int diameter = status.getPreferredSize().height;
	private Circle circle;

	public StatusBar(int width) {
		status.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		setPreferredSize(new Dimension(width,
				status.getPreferredSize().height));
		setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 0));
		this.circle=new Circle();
		add(circle);
		add(status);
	}
	
	public boolean setConnected(String connectedTo){
		status.setText("Connected to: "+connectedTo);
		circle.setColor(Color.GREEN);
		return true;
	}
	
	public boolean setDisconnected(){
		status.setText("Not Connected");
		circle.setColor(Color.RED);
		return true;
	}

	private class Circle extends JPanel {
		private Color color=Color.red;
		
		public void setColor(Color color){
			this.color=color;
			repaint();
		}
		
		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
			Graphics2D comp2D = (Graphics2D) comp;

			comp2D.setColor(color);
			Ellipse2D.Float sign1 = new Ellipse2D.Float(0, 0, diameter / 2,
					diameter / 2);
			comp2D.fill(sign1);
		}
	}
}