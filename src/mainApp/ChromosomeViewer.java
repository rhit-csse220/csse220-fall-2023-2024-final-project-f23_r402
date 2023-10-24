package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public class ChromosomeViewer {
	/**
	 * ensures: creates, initializes, and sets visible the Viewer's frame and
	 * component
	 */

	public String fileName = "Chromosome X";
	public String filePath = "";
	public File file;
	public JFrame frame;
	public ChromosomeComponent chComponent;

	public void driverMain() {
		final String frameTitle = "Chromosome Viewer";
		final int frameWidth = 310;
		final int frameHeight = 420;
		final int textFieldWidth = 10; // needs to be changed to be in relation with frame width

		this.frame = new JFrame();
		frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight));

		frame.setVisible(true);

		JLabel fileNameLabel = new JLabel(fileName);
		frame.add(fileNameLabel, BorderLayout.NORTH);

		// JPanel panel = new JPanel();
		// frame.add(panel, BorderLayout.SOUTH);

		// chromosome - BorderLayout.CENTER
		this.chComponent = new ChromosomeComponent();
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
				JFileChooser chooseFile = new JFileChooser();
				chooseFile.setCurrentDirectory(new File("C:\\Users\\%USERNAME%\\Documents\\GARP\\"));
				int response = chooseFile.showOpenDialog(null);

				if (response == JFileChooser.APPROVE_OPTION) {
					file = new File(chooseFile.getSelectedFile().getAbsolutePath());
					// Storing file name
					fileName = file.getName();
					filePath = file.getPath();
					fileNameLabel.setText(fileName);
					try {
						List<String> lines = Files.readAllLines(file.toPath());
						for (String s : lines) {
							chComponent.chromosome = new Chromosome();
							chComponent.handleStoreChromosomeData(s);
							chComponent.handleLoadGene();
							frame.repaint();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter writer = new PrintWriter(filePath);
					BufferedWriter bWriter = new BufferedWriter(writer);
					for (Gene gene : chComponent.chromosome.genes) {
						bWriter.write(gene.getBit());
					}
					bWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/**
		 * panel.add(mutateButton, BorderLayout.EAST); panel.add(mRate,
		 * BorderLayout.EAST); panel.add(mRateField, BorderLayout.CENTER);
		 * panel.add(loadButton, BorderLayout.CENTER); panel.add(saveButton,
		 * BorderLayout.CENTER);
		 */

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.add(panel, BorderLayout.SOUTH);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(mutateButton, c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(mRate, c);
		c.gridx = 2;
		c.gridy = 0;
		panel.add(mRateField);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(loadButton, c);
		c.gridx = 1;
		c.gridy = 1;
		panel.add(saveButton, c);

		frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO figure out how this will change it on click
				Gene gene = chComponent.containsGene(e.getX(), e.getY());
				if (gene != null) {
					gene.changeBit();
				}
				fileNameLabel.setText(fileName+" (new)"); // Appends the label text to include (new) if edited
				frame.repaint();
			}
		});

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
