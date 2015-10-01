package pucrio.inf1013.t1.trem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Queue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pucrio.inf1013.t1.trem.RailControlSystem;
import pucrio.inf1013.t1.trem.model.Train;
import pucrio.inf1013.t1.trem.model.Train.Direction;

@SuppressWarnings("serial")
public class RailControlSystemFrame extends JFrame implements MouseListener {

	private static final Point LEFT_EXIT_COORDINATES = new Point(0, 335);
	private static final Point LEFT_ENTRY_COORDINATES = new Point(0, 460);
	private static final Point RIGHT_EXIT_COORDINATES = new Point(1544, 339);
	private static final Point RIGHT_ENTRY_COORDINATES = new Point(1540, 460);
	private static final Point BRIDGE_LEFT_COORDINATES = new Point(471, 385);
	private static final Point BRIDGE_RIGHT_COORDINATES = new Point(1123, 385);
	private static final int TRAIN_CIRCLE_RADIUS = 16;
	
	public RailControlSystemFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Rail Control System - by Hugo Grochau");
		this.setResizable(false);
		Image backgroundImage = this.getImage("resources/Trem.jpg");
		Dimension frameDimension = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));	
	    this.setBounds(0,0,frameDimension.width, frameDimension.height);	
	    this.setContentPane(new ImagePanel(backgroundImage));
	    this.getContentPane().addMouseListener(this);
	    this.setVisible(true);
	}
	
	public void render(Graphics g) {
		RailControlSystem rcsi = RailControlSystem.getInstance();
		this.renderWaitingQueue(g, rcsi.getLeftToRightWaitingQueue(), Direction.LEFT_TO_RIGHT);
		this.renderWaitingQueue(g, rcsi.getRightToLeftWaitingQueue(), Direction.RIGHT_TO_LEFT);
	}
	
	private void renderWaitingQueue(Graphics g, Queue<Train> q, Direction d) {
		Point start = d.equals(Direction.LEFT_TO_RIGHT) ? (Point) this.LEFT_ENTRY_COORDINATES : (Point) this.RIGHT_ENTRY_COORDINATES;
		Point currentPoint = (Point) start.clone();
		Graphics2D g2 = (Graphics2D) g;
		for (Train t: q) {
			this.drawCircle(g2, currentPoint.x, currentPoint.y, this.TRAIN_CIRCLE_RADIUS, t.getColor());
			currentPoint.x += d.equals(Direction.LEFT_TO_RIGHT) ? TRAIN_CIRCLE_RADIUS*2 : TRAIN_CIRCLE_RADIUS*-2;
		}
	}
	
	private void drawCircle(Graphics2D g2, int x, int y, int radius, Color c) {
		g2.setColor(c);
		g2.fillOval(x, y, radius*2, radius*2);
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
	        render(g);
	    }
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.printf("%d, %d\n", me.getX(), me.getY());
		RailControlSystem.getInstance().addTrain(new Train(Direction.RIGHT_TO_LEFT));
		RailControlSystem.getInstance().tick();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
