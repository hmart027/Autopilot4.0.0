package autopilot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GoogleMaps extends JFrame {
	
	public GoogleMaps(){
		
		setBounds(0, 0, 900, 900);
		add(new ImagePanel());
		setVisible(true);
	}
	
	public class ImagePanel extends JPanel{

	    private Image map;

	    public ImagePanel() {
	    	URLConnection con;
			try {
				con = new URL("https://maps.googleapis.com/maps").openConnection();
				InputStream is = con.getInputStream();
				byte bytes[] = new byte[1000];
				is.read(bytes);
				System.out.println(bytes);
				is.close();
				Toolkit tk = getToolkit();
				map = tk.createImage(bytes);
				tk.prepareImage(map, -1, -1, null);
				
				setVisible(true);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(map, 0, 0, null); // see javadoc for more info on the parameters            
	    }

	}

}
