package pucrio.inf1013.t1.trem.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import pucrio.inf1013.t1.trem.RailControlSystem;
import pucrio.inf1013.t1.trem.Train;
import pucrio.inf1013.t1.trem.Train.Direction;

@SuppressWarnings("serial")
public class SpawnTrainFrame extends JFrame implements ActionListener {
	JSpinner speedSpinner;
	JSpinner distanceSpinner;
	JComboBox<String> directionComboBox;

	public SpawnTrainFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Add new train");
		this.setResizable(true);
	    this.setBounds(1570, 0, 200, 200);
	    
	    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	    
	    this.add(new JLabel("Speed:"));
	    SpinnerModel speedModel = new SpinnerNumberModel(50, 0, 150, 5);
	    this.speedSpinner = new JSpinner(speedModel);
	    this.add(speedSpinner);
	    
	    this.add(new JLabel("Minimal distance:"));
	    SpinnerModel distanceModel = new SpinnerNumberModel(50, 0, 150, 5);
	    this.distanceSpinner = new JSpinner(distanceModel);
	    this.add(distanceSpinner);
	    
	    this.add(new JLabel("Direction"));
	    String[] options = {"Left to right", "Right to left"};
	    this.directionComboBox = new JComboBox<String>(options);
	    this.add(directionComboBox);
	    
	    JButton addButton = new JButton("Add");
	    addButton.addActionListener(this);
	    this.add(addButton);
	    
	    this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = (int) this.speedSpinner.getValue();
		int minimalDistance = (int) this.distanceSpinner.getValue();
		Direction d = this.directionComboBox.getSelectedIndex() == 0 ? Direction.LEFT_TO_RIGHT : Direction.RIGHT_TO_LEFT;
		
		Train t = new Train(d, speed, minimalDistance);
		RailControlSystem.getInstance().addTrain(t);
	}
	
}
