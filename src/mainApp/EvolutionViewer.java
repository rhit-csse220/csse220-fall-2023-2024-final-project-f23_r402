package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class EvolutionViewer {
    public JFrame frame;
    public EvolutionComponent evComponent;
    public void driverMain(){
        final String frameTitle = "Evolution Viewer";
		final int frameWidth = 1000;
		final int frameHeight = 400;
        final int textFieldWidth = 3;
        
        this.frame=new JFrame();
        frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        this.evComponent=new EvolutionComponent();
        frame.add(evComponent, BorderLayout.CENTER);
        
        //Text fields array
        JTextField[] textFields = new JTextField[5];

        //create a panel for buttons
		JPanel buttonPanel = new JPanel();
		//Set up the panel to use a horizontal layout and give it a background color
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //Mutation rate
        JLabel mRate = new JLabel("Mutation Rate (N/pop) ");
		JTextField mRateField = new JTextField("1", 1);
        textFields[0]=mRateField;

        buttonPanel.add(mRate);
        buttonPanel.add(mRateField);

        //Dropdown panel for choosing a selection method
        JPanel dropdownPanel  = new JPanel();
		JLabel dropdownLabel = new JLabel("Selection ");
		dropdownPanel.add(dropdownLabel);

        //Modify this if you wish to add different numbers of things into the simulation
		String[] selectionMethods = {"Truncation", "Parent"};
		// create a combo box with the fixed array so you can pick how many things to add
		JComboBox<String> addSelectionChooser = new JComboBox<String>(selectionMethods);
		//set its maximum size to be its preferred size so it doesn't get too big
		addSelectionChooser.setMaximumSize( addSelectionChooser.getPreferredSize() );

		dropdownPanel.add(addSelectionChooser);
		dropdownPanel.setMaximumSize( dropdownPanel.getPreferredSize() );

        buttonPanel.add(dropdownPanel);

        //Crossover
        JLabel crossover = new JLabel("Crossover? ");
        JCheckBox checkCrossover = new JCheckBox();

        buttonPanel.add(crossover);
        buttonPanel.add(checkCrossover);

        //Population
        JLabel population = new JLabel("Population ");
        JTextField populationField = new JTextField();
        textFields[1] = populationField;

        buttonPanel.add(population);
        buttonPanel.add(populationField);

        //Generations
        JLabel generations = new JLabel("Generations ");
        JTextField generationsField = new JTextField();
        textFields[2] = generationsField;

        buttonPanel.add(generations);
        buttonPanel.add(generationsField);

        //Genome length
        JLabel genomeLength = new JLabel("Genome Length ");
        JTextField genomeLengthField = new JTextField();
        textFields[3] = genomeLengthField;

        buttonPanel.add(genomeLength);
        buttonPanel.add(genomeLengthField);

        //Elitism
        JLabel elitism = new JLabel("Elitism % ");
        JTextField elitismField = new JTextField();
        textFields[4] = elitismField;

        buttonPanel.add(elitism);
        buttonPanel.add(elitismField);

        //Start Evolution
        JButton startEvolutionButton = new JButton("Start Evolution");
        
        buttonPanel.add(startEvolutionButton);

        frame.pack();
    }

    public void handleDriverMain(){
        this.driverMain();
        //this.evComponent.population.giveFitness(); To check if the chromosomes were sorted according to fitness
    }

    public static void main(String[] args) {
        EvolutionViewer evViewer = new EvolutionViewer();
        evViewer.handleDriverMain();
    }
}
