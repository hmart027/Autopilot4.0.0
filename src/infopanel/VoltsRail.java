package infopanel;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class VoltsRail extends Container {

	// Objects in container
	private JLabel railName = new JLabel("Voltage Rails");;
	private JTextArea reading = new JTextArea();
	private Circle state;
	// Container's Dimensions
	private int width = 50;
	private int height = 50;
	// Container's Values
	private float voltage;
	private float vYellow = (float) 0.00;
	private float vRed = (float) 0.00;

	public VoltsRail(Dimension dim, String railName, float intReading) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.railName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		this.railName.setText(railName);
		this.railName.setHorizontalTextPosition(JTextField.RIGHT);
		this.width = dim.width;
		this.height = this.railName.getPreferredSize().height;
		this.railName.setPreferredSize(new Dimension(this.width / 3,
				this.height));
		this.reading
				.setPreferredSize(new Dimension(this.width / 3, this.height));
		this.reading.setText("    " + String.valueOf(intReading));
		this.reading.setEditable(false);
		setPreferredSize(new Dimension(dim.width, this.height));
		state = new Circle(height);
		state.setBackground(this.getBackground());
		add(state);
		add(this.railName);
		add(this.reading);
		setVoltage(intReading);
	}

	public float setVoltage(float voltage) {
		this.voltage = voltage;
		reading.setText(String.valueOf(voltage));
		voltageLvl();
		return this.voltage;
	}

	public void setThresholds(float yellowLvl, float redLvl) {
		this.vYellow = yellowLvl;
		this.vRed = redLvl;
	}

	private void voltageLvl() {
		if (this.voltage <= vRed) {
			state.setColor(Color.RED);
			return;
		}
		if (this.voltage <= vYellow) {
			state.setColor(Color.YELLOW);
			return;
		}
		state.setColor(Color.GREEN);
	}

	private class Circle extends JPanel {
		private Color color = Color.red;
		private float radius;

		public Circle(float diameter) {
			this.radius = diameter / 2;
		}

		public void setColor(Color color) {
			this.color = color;
			repaint();
		}

		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
			Graphics2D comp2D = (Graphics2D) comp;

			comp2D.setColor(color);
			Ellipse2D.Float sign1 = new Ellipse2D.Float(0, 0, this.radius,
					this.radius);
			comp2D.fill(sign1);
		}
	}

}