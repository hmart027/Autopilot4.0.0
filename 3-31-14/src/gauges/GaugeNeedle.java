package gauges;
import java.awt.Color;
import java.awt.Graphics;


public class GaugeNeedle{
	
	private Graphics g;
	private Color needleColor=Color.RED;
	private double initAngle=0;
	private double angle=0;
	private int radius=10;
	private int x=0;
	private int y=0;
	
	private int[][] points=new int[2][3];
	
	public GaugeNeedle(int x, int y, int initialRad){
		this.x=x;
		this.y=y;
		this.radius=initialRad;
	}
	
	public GaugeNeedle(int x, int y, int initialRad, double initialAngle, Color color){
		this.x=x;
		this.y=y;
		this.radius=initialRad;
		this.initAngle=initialAngle;
		this.needleColor=color;
	}
	
	public GaugeNeedle(int x, int y, int initialRad, Color color){
		this.x=x;
		this.y=y;
		this.radius=initialRad;
		this.needleColor=color;
	}
	
	public void setAngle(double angle,Graphics g){
		this.angle=angle;
		paint(g);
	}
	
	public void setRadius(int rad){
		this.radius=rad;
		paint(g);
	}
	
	public void paint(Graphics g){
		this.g=g;
		// Paint Arrow
		g.setColor(needleColor);
		findPoints();
		g.fillPolygon(points[0], points[1], 3);
		
		// Paint center
		g.setColor(Color.RED);
		g.fillOval(x-radius/6, y-radius/6, radius/3, radius/3);
	}
	
	private void findPoints(){
		points[0][0]=x+(int) (radius/10*Math.cos(angle+initAngle));
		points[0][1]=x-(int) (radius/10*Math.cos(angle+initAngle));
		points[0][2]=x+(int) (radius*Math.cos(angle-Math.PI/2+initAngle));
		
		points[1][0]=y+(int) (radius/10*Math.sin(angle+initAngle));
		points[1][1]=y-(int) (radius/10*Math.sin(angle+initAngle));
		points[1][2]=y+(int) (radius*Math.sin(angle-Math.PI/2+initAngle));
	}

}