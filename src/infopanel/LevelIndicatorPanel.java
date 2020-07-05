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
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LevelIndicatorPanel extends Container{

	// Container's Dimensions
	private int width;
	private int height;
	
	// Container elements
	private LevelIndicator[] levels=new LevelIndicator[7];

	public LevelIndicatorPanel(Dimension dim) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.width = dim.width;
		levels[0]=new LevelIndicator(dim.width,7);
		height=levels[0].getPreferredSize().height*7;
		setPreferredSize(new Dimension(this.width, this.height));
		Container headspace=new Container();
		headspace.setPreferredSize(new Dimension(this.width, this.height/7));
		add(headspace);
		add(levels[0]);
		for(int i=6;i>0;i--){
			levels[7-i]=new LevelIndicator(dim.width,i);
			add(levels[7-i]);
			setState(false,i);
		}
	}
	
	public void setVisibleLvl(int lvl, boolean state){
		if(lvl==0)return;
		levels[7-lvl].setVisible(state);
	}

	public boolean setState(boolean state,int lvl) {
		if(lvl==0)return false;
		return levels[7-lvl].setIState(state);
	}
	
	private class LevelIndicator extends Container{
		
		// Objects in container
		private JLabel railName = new JLabel();
		private Circle stateV;
		// Container's Dimensions
		private int width;
		private int height;
		// Container's Values
		private boolean stateL;

		public LevelIndicator(int width,int lvlNumber) {
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
			this.railName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			this.railName.setText(" Lvl "+Integer.toString(lvlNumber));
			this.railName.setHorizontalTextPosition(JTextField.RIGHT);
			this.width = width;
			this.height = this.railName.getPreferredSize().height;
			this.railName.setPreferredSize(new Dimension(this.width / 3,
					this.height));
			setPreferredSize(new Dimension(width, this.height));
			stateV = new Circle(height);
			stateV.setBackground(this.getBackground());
			add(stateV);
			add(this.railName);
			setIState(false);
		}

		public boolean setIState(boolean state) {
			this.stateL=state;
			toggleState();
			return this.stateL;
		}

		private void toggleState() {
			if (stateL) {
				stateV.setColor(Color.GREEN);
				return;
			}
			stateV.setColor(Color.RED);
		}
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