package researchApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mainApp.ChromosomeComponent;
import mainApp.ChromosomeViewer;
import mainApp.Gene;
import mainApp.InvalidChromosomeCharacterException;
import mainApp.InvalidChromosomeFormatException;

public class ResearchChromosomeViewer extends ChromosomeViewer {

	protected ResearchChromosomeComponent chComponent;

    public ResearchChromosomeViewer(){
    }

    @Override
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
		JLabel fileNameLabel = new JLabel(fileName);
		frame.add(fileNameLabel, BorderLayout.NORTH);

		// chromosome - BorderLayout.CENTER
		this.chComponent = new ResearchChromosomeComponent();
		frame.add(chComponent, BorderLayout.CENTER);

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
				ResearchGene gene = chComponent.containsGene(e.getX(), e.getY());
				if (gene != null) {
					gene.changeBit();
				}
				fileNameLabel.setText(fileName + " (new)"); // Appends the label text to include (new) if edited
				frame.repaint();
			}
		});

		frame.pack();
    }

	

    public static void main(String[] args) {
		ResearchChromosomeViewer rC = new ResearchChromosomeViewer();
		rC.handleDriverMain();
	} // main
}
