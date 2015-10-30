package pucrio.inf1013.t1.trem.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pucrio.inf1013.t1.trem.RailControlSystem;
import pucrio.inf1013.t1.trem.Train;
import pucrio.inf1013.t1.trem.Train.Direction;

@SuppressWarnings("serial")
public class RailControlSystemFrame extends JFrame {

	public static final Point STOPLIGHT_LEFT_COORDINATES = new Point(200, 200);
	public static final Point STOPLIGHT_RIGHT_COORDINATES = new Point(1300, 200);
	private StopLight leftStopLight;
	private StopLight rightStopLight;

	public RailControlSystemFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Rail Control System - by Hugo Grochau");
		this.setResizable(false);
		Image backgroundImage = this.getImage("resources/Trem.jpg");
		Dimension frameDimension = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		this.setBounds(0, 0, frameDimension.width, frameDimension.height);
		this.setContentPane(new ImagePanel(backgroundImage));

		this.leftStopLight = new StopLight(RailControlSystemFrame.STOPLIGHT_LEFT_COORDINATES);
		this.rightStopLight = new StopLight(RailControlSystemFrame.STOPLIGHT_RIGHT_COORDINATES);

		this.setVisible(true);
	}

	public void render(Graphics g) {
		RailControlSystem rcsi = RailControlSystem.getInstance();
		this.leftStopLight.render((Graphics2D) g, rcsi.getLeftStopLightState());
		this.rightStopLight.render((Graphics2D) g, rcsi.getRightStopLightState());
		this.renderWaitingQueue(g, rcsi.getLeftToRightTrains(), Direction.LEFT_TO_RIGHT);
		this.renderWaitingQueue(g, rcsi.getRightToLeftTrains(), Direction.RIGHT_TO_LEFT);
	}

	private void renderWaitingQueue(Graphics g, List<Train> q, Direction d) {
		Graphics2D g2 = (Graphics2D) g;
		for (Train t : q) {
			RailControlSystemFrame.drawCircle(g2, t.getPosition(), t.calculateY(),
					RailControlSystem.TRAIN_CIRCLE_RADIUS, t.getColor());
		}
	}

	public static void drawCircle(Graphics2D g2, int x, int y, int radius, Color c) {
		g2.setColor(c);
		g2.fillOval(x, y, radius * 2, radius * 2);
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

}
