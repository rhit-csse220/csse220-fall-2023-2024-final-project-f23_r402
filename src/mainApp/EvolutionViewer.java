package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * The EvolutionViewer class is responsible for creating a graphical user interface
 * for viewing the evolution of populations. It allows users to configure various
 * parameters for the evolution process and visualize the results.
 */

public class EvolutionViewer {
    public static final int TIMER_DELAY = 15;

    
    public JFrame frame;
    public EvolutionComponent evComponent;
    public int generations;

       /**
     * The driverMain method initializes and sets up the Evolution Viewer application.
     * It creates a graphical user interface, sets up user input fields, and handles
     * the evolution process.
     */

    public void driverMain(){
        final String frameTitle = "Evolution Viewer";
		final int frameWidth = 1000;
		final int frameHeight = 400;
        final int textFieldWidth = 3;
        
        this.frame = new JFrame();
        frame.setTitle(frameTitle);
		frame.setSize(frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        evComponent = new EvolutionComponent();
        frame.add(evComponent, BorderLayout.CENTER);

        //Text fields array
        JTextField[] textFields = new JTextField[5];

        //create a panel for buttons
		JPanel buttonPanel = new JPanel();
		//Set up the panel to use a horizontal layout and give it a background color
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        //Mutation rate
        JLabel mRate = new JLabel("Mutation Rate (N/pop): ");
		JTextField mRateField = new JTextField("1", textFieldWidth);
        textFields[0]=mRateField;

        buttonPanel.add(mRate);
        buttonPanel.add(mRateField);

        //Dropdown panel for choosing a selection method
        JPanel dropdownPanel  = new JPanel();
		JLabel dropdownLabel = new JLabel("Selection: ");
		dropdownPanel.add(dropdownLabel);

        //Modify this if you wish to add different numbers of things into the simulation
		String[] selectionMethods = {"Truncation", "Roulette", "Rank"};
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
        JLabel population = new JLabel("Population: ");
        JTextField populationField = new JTextField("100", textFieldWidth);
        textFields[1] = populationField;

        buttonPanel.add(population);
        buttonPanel.add(populationField);

        //Generations
        JLabel generations = new JLabel("Generations: ");
        JTextField generationsField = new JTextField("100", textFieldWidth);
        textFields[2] = generationsField;

        buttonPanel.add(generations);
        buttonPanel.add(generationsField);

        //Genome length
        JLabel genomeLength = new JLabel("Genome Length: ");
        JTextField genomeLengthField = new JTextField("100", textFieldWidth);
        textFields[3] = genomeLengthField;

        buttonPanel.add(genomeLength);
        buttonPanel.add(genomeLengthField);

        //Elitism
        JLabel elitism = new JLabel("Elitism %: ");
        JTextField elitismField = new JTextField("1", textFieldWidth);
        textFields[4] = elitismField;

        buttonPanel.add(elitism);
        buttonPanel.add(elitismField);

        //Start Evolution
        JButton startEvolutionButton = new JButton("Start Evolution");
        startEvolutionButton.addActionListener(new ActionListener() {
            Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
                int generationCount = -1;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (generationCount == -1){
                        evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText());
                        generationCount++;
                        frame.repaint();
                    }
                    if (generationCount <= Integer.parseInt(generationsField.getText())){
                        evComponent.handleSelection();
                        generationCount++;
                        evComponent.generationCount = generationCount;
                        frame.repaint();
                    }
                    else {
                        startEvolutionButton.setText("Start Evolution");
                        // System.out.println(evComponent.population.chromosomes.get(0).getChromosomeDataAsString());
                        timer.restart();

                        //TODO populationField might not be needed to be initialized here i think lawl
                        evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText());
                        generationCount = -1;
                        timer.stop();
                    }
                }
            });

            @Override
            public void actionPerformed(ActionEvent e) {
                if (startEvolutionButton.getText().equals("Start Evolution")){
                    evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText());
                    startEvolutionButton.setText("Pause");
                    timer.start();
                } else if (startEvolutionButton.getText().equals("Pause")){
                    startEvolutionButton.setText("Continue");
                    timer.stop();
                } else if (startEvolutionButton.getText().equals("Continue")){
                    startEvolutionButton.setText("Pause");
                    timer.start();
                }
            }
        });
        
        buttonPanel.add(startEvolutionButton);

        //Line plot chart
        frame.add(new JLabel("Population"), BorderLayout.NORTH);
        
        frame.pack();

        // Modify the dimensions of the given text fields (Has to be after frame.pack(), i can't explain why)
        for (JTextField textField : textFields) {
            textField.setBounds(textField.getX(), textField.getY()+8, textField.getWidth(), 20); // TODO Substitute Magic Numbers Here!
        }
    }


    /**
     * The handleDriverMain method is responsible for executing the Evolution Viewer.
     * It calls the driverMain method to set up the application and start the evolution process.
     */

    public void handleDriverMain(){
        this.driverMain();
        //  this.evComponent.population.giveFitness(); //To check if the chromosomes were sorted according to fitness
        // this.evComponent.population = new Population(200, 150); 
        // for (int i = 0; i < 200; i++){
        //     this.evComponent.population.truncationSelection(1);
        //     this.evComponent.population.giveFitness();
        // }
        // System.out.println(this.evComponent.population.chromosomes.get(0).getChromosomeDataAsString());
        //  this.evComponent.population.giveFitness(); //To check if the chromosomes were sorted according to fitness
    }   


      /**
     * The main method is the entry point of the Evolution Viewer application.
     * It creates an instance of EvolutionViewer and initiates the application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    
    public static void main(String[] args) {
        EvolutionViewer evViewer = new EvolutionViewer();
        evViewer.handleDriverMain();
    }
}
