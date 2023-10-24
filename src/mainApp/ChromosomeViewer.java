package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.*;

public class ChromosomeViewer {
	/**

	 * ensures: creates, initializes, and sets visible the Viewer's frame and component

	 */

	public void driverMain() {
		final String frameTitle = "Chromosome Viewer";
		final int frameWidth = 600;
		final int frameHeight = 400;
//		final int frameXLoc = 1000;
//		final int frameYLoc = 1000;
		final int textFieldWidth = 10; // needs to be changed to be in relation with frame width

		JFrame frame = new JFrame();
		frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
//		frame.setLocation(frameXLoc, frameYLoc);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		frame.add(panel);
		
		//panel.setLayout(null);
		
		// text file name - BorderLayout.NORTH
		
		// chromosome - BorderLayout.CENTER
		
		// buttons/fields - BorderLayout.SOUTH
		JButton mutateButton = new JButton("Mutate");
		JLabel mRate = new JLabel("M Rate:_/N");
		JTextField mRateField = new JTextField("1", textFieldWidth);
		
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("C:\\Users\\"));
				int response = fileChooser.showSaveDialog(null);
				
				if (response==JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println(file);
					try {
						List<String> lines = Files.readAllLines(file.toPath());
						for (String s : lines) {
							System.out.println(s);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton saveButton = new JButton("Save");
		
		panel.add(mutateButton, BorderLayout.CENTER);
		panel.add(mRate, BorderLayout.CENTER);
		panel.add(mRateField, BorderLayout.CENTER);
		panel.add(loadButton, BorderLayout.CENTER);
		panel.add(saveButton, BorderLayout.CENTER);
		
		frame.pack();
	} // driverMain
	
	public void handleDriverMain() {
		this.driverMain();
	}
	
	public static void main(String[] args) {
		ChromosomeViewer c = new ChromosomeViewer();
		c.handleDriverMain();	
	} // main
}
