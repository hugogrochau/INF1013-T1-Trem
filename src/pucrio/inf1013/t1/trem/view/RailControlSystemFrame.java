package pucrio.inf1013.t1.trem.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RailControlSystemFrame extends JFrame {

	
	public RailControlSystemFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Rail Control System - by Hugo Grochau");
		this.setResizable(false);
		Image backgroundImage = this.getImage("resources/Trem.jpg");
		Dimension frameDimension = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));	
	    this.setBounds(0,0,frameDimension.width, frameDimension.height);	
	    this.setContentPane(new ImagePanel(backgroundImage));
	    this.setVisible(true);

	}
	
	public Image getImage(String path) {
		Image img = null;
		try {
			img = new ImageIcon(path).getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return img;
	}
	
	class ImagePanel extends JPanel {
	    private Image image;
	    public ImagePanel(Image image) {
	        this.image = image;
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	    }
	}
	
}
