package mainApp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.*;

public class ChromosomeViewer {
	/**
	 * ensures: creates, initializes, and sets visible the Viewer's frame and
	 * component
	 */
	private String fileName = "Chromosome X";
	private String filePath = "";
	private File file;
	private JFrame frame;
	private ChromosomeComponent chComponent;

	// height of chComponent: this.chComponent.getHeight()
	// width of frame: frame.getWidth()

	public void driverMain() {
		final String frameTitle = "Chromosome Viewer";
		final int frameWidth = 310;
		final int frameHeight = 420;
		final int textFieldWidth = 3;

		this.frame = new JFrame();
		frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
		// frame.setResizable(false);

		frame.setVisible(true);

		// public int getFrameWidth() {return frame.getWidth()};

		// fileName - BorderLayout.NORTH

		JPanel titlePanel = new JPanel();

		JLabel fileNameLabel = new JLabel(fileName);
		titlePanel.add(fileNameLabel);

		JButton shareButton = new JButton("Share this chromosome");
		shareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShareChromosomeViewer(chComponent.handleGetChromosomeDataAsString()).driverMain();
			}			
		});
		titlePanel.add(shareButton);

		frame.add(titlePanel, BorderLayout.NORTH);

		// chromosome - BorderLayout.CENTER
		this.chComponent = new ChromosomeComponent();
		frame.add(chComponent, BorderLayout.CENTER);

		// buttons/fields - BorderLayout.SOUTH
		JLabel mRate = new JLabel("M Rate:");
		JTextField mRateField = new JTextField("1", textFieldWidth);
		JLabel mRateUnit = new JLabel("/N");

		/**
		 * Functional Mutate Button
		 * ensures: takes the int in mRateField and performs mutation on Chromosome
		 */
		JButton mutateButton = new JButton("Mutate");
		mutateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the mutation rate as a percentage from the text field
				try {
					double mutationRate = Double.parseDouble(mRateField.getText());

					if (mutationRate < 0 || mutationRate > chComponent.handleGetNumberOfGenesInChromosome()) {
						// Handle invalid input, show an error message, etc.
						JOptionPane.showMessageDialog(frame,
								"Invalid mutation rate. Please enter a number between 0 and "
										+ chComponent.handleGetNumberOfGenesInChromosome(),
								"Invalid Input", JOptionPane.ERROR_MESSAGE);
						return;
					}

					// Perform mutation for each gene based on the mutation rate
					chComponent.handleMutateGenesInChromosome(mutationRate);
					
					fileNameLabel.setText(fileName + " (mutated)");
					// Repaint the frame to reflect the changes
					frame.repaint();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame,
							"Invalid mutation rate. Please enter a number between 0 and "
									+ chComponent.handleGetNumberOfGenesInChromosome(),
							"Invalid Input", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});

		/**
		 * Function Load Button
		 * ensures: loads Chromosome with correct file type and correct standards
		 */
		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JFileChooser chooseFile = new JFileChooser();
		        int response = chooseFile.showOpenDialog(null);

		        if (response == JFileChooser.APPROVE_OPTION) {
		            file = new File(chooseFile.getSelectedFile().getAbsolutePath());
		            fileName = file.getName();
		            // filePath = file.getPath();
		            fileNameLabel.setText(fileName);

		            try {
		                List<String> lines = Files.readAllLines(file.toPath());
		                StringBuilder fileData = new StringBuilder();

		                for (String s : lines) {
		                    fileData.append(s);
		                }

		                // Check if the loaded file data is invalid in terms of length
		                int characterCount = fileData.length();
		                if (characterCount % 10 != 0) {
		                    // JOptionPane.showMessageDialog(null,
		                    //         "Invalid file data length. Expected a multiple of 10 characters, but loaded " + characterCount + " characters.",
		                    //         "Invalid Data Length",
		                    //         JOptionPane.ERROR_MESSAGE);

							// TODO: we can delete this if since chComponent.handleLoadDataFromFile throws the exception anyways
							throw new InvalidChromosomeFormatException(characterCount);
		                } else if (!checkChromosomeData(fileData.toString())){
							throw new InvalidChromosomeCharacterException();
						} else {
		                    // Proceed with loading and initializing the data

							chComponent.handleLoadDataFromFile(fileData.toString());  // TODO: change fileData type to String to avoid using toString everywhere 

		                    // chComponent.setChromosome(new Chromosome());
		                    // chComponent.handleStoreChromosomeData(fileData.toString());
		                    // chComponent.handleInitiateGeneWithFile();
		                    frame.repaint();
		                }
		            } catch (IOException | InvalidChromosomeFormatException | InvalidChromosomeCharacterException ex) {
						if (ex instanceof IOException) {
							JOptionPane.showMessageDialog(null,
		                        "Wrong file type or file does not exist.",
		                        "File Load Error",
		                        JOptionPane.ERROR_MESSAGE);
						}
		            }
		        }
		    }
		});


		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				// Deleted because not everybody has the directory
				// fileChooser.setCurrentDirectory(new File("C:\\Users\\%USERNAME%\\Documents\\GARP"));
				int response = fileChooser.showSaveDialog(null);

				if (response == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					if (!file.getName().toLowerCase().endsWith(".txt")) {
						file = new File(file.getAbsolutePath() + ".txt");
					}

					// Get the chromosome data in the required format (1 for black, 0 for green)
					String chromosomeData = chComponent.handleGetChromosomeDataAsString(); // .getChromosome().getChromosomeDataAsString();

					try (FileWriter writer = new FileWriter(file)) {
						writer.write(chromosomeData);  // TODO: do we ever close the writer?
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		JPanel buttonPanel = new JPanel();
		frame.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(mRate);
		buttonPanel.add(mRateField);
		buttonPanel.add(mRateUnit);
		buttonPanel.add(mutateButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(saveButton);

		frame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Gene gene = chComponent.containsGene(e.getX(), e.getY());
				if (gene != null) {
					gene.changeBit();
				}
				fileNameLabel.setText(fileName + " (new)"); // Appends the label text to include (new) if edited
				frame.repaint();
			}
		});

		frame.pack();
	} // driverMain

	public boolean checkChromosomeData(String fileData) {
		for (int i = 0; i < fileData.length(); i++) {
            if (!(fileData.charAt(i)=='0' || fileData.charAt(i)=='1')) {
                return false;
            }
        }
		return true;
	}

	public void handleDriverMain() {
		this.driverMain();
	}

	public static void main(String[] args) {
		ChromosomeViewer c = new ChromosomeViewer();
		c.handleDriverMain();
	} // main
}
