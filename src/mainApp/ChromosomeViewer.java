package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public class ChromosomeViewer {
	/**
	 * ensures: creates, initializes, and sets visible the Viewer's frame and component
	 */

	public Chromosome chromosome = new Chromosome();
	
	public void driverMain() {
		final String frameTitle = "Chromosome Viewer";
		final int frameWidth = 600;
		final int frameHeight = 400;
		final int textFieldWidth = 10; // needs to be changed to be in relation with frame width
		

		JFrame frame = new JFrame();
		frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight));

		frame.setVisible(true);
		
		//JPanel panel = new JPanel();
		//frame.add(panel, BorderLayout.SOUTH);			
		
		// chromosome - BorderLayout.CENTER
		ChromosomeComponent chComponent = new ChromosomeComponent();	
		frame.add(chComponent);
		
		// buttons/fields - BorderLayout.SOUTH
		JButton mutateButton = new JButton("Mutate");
		
		JLabel mRate = new JLabel("M Rate:_/N");
		JTextField mRateField = new JTextField("1", textFieldWidth);
		
		// Load button functionality
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("C:\\Users\\%USERNAME%\\Documents\\GARP"));
				int response = fileChooser.showSaveDialog(null);
				
				if (response==JFileChooser.APPROVE_OPTION) {
					File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
					System.out.println(file);
					try {
						List<String> lines = Files.readAllLines(file.toPath());
						for (String s : lines) {
							chromosome.storeChromosomeData(s);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		JButton saveButton = new JButton("Save");
		
		
		/**panel.add(mutateButton, BorderLayout.EAST);
		panel.add(mRate, BorderLayout.EAST);
		panel.add(mRateField, BorderLayout.CENTER);
		panel.add(loadButton, BorderLayout.CENTER);
		panel.add(saveButton, BorderLayout.CENTER);*/
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.add(panel, BorderLayout.SOUTH);
		c.fill=GridBagConstraints.HORIZONTAL;
		c.ipady=GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(mutateButton, c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(mRate, c);
		c.gridx= 2;
		c.gridy = 0; 
		panel.add(mRateField);
		c.gridx=0;
		c.gridy=1;
		panel.add(loadButton, c);
		c.gridx=1;
		c.gridy=1;
		panel.add(saveButton, c);
		
		frame.pack();
	} // driverMain
	
	public void handleDriverMain() {
		this.driverMain();
	}
	
	public Chromosome getChromosome() {
		return this.chromosome;
	}
	
	public void setChromosome(Chromosome c) {
		this.chromosome = c;
	}
	
	public static void main(String[] args) {
		ChromosomeViewer c = new ChromosomeViewer();
		c.handleDriverMain();
		int bit = 1;
		char cded = (char)(bit + '0');
		System.out.println(cded);
	} // main
}
