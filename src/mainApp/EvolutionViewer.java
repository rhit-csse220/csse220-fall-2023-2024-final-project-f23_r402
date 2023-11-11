package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
* The EvolutionViewer class is responsible for creating a graphical user interface
* for viewing the evolution of populations. It allows users to configure various
* parameters for the evolution process and visualize the results.
*/
public class EvolutionViewer {
    // constants
    public static final int TIMER_DELAY = 1500;
    public static final int SUBMIT_FORM_KEY = KeyEvent.VK_ENTER;
    
    // fields
    private EvolutionComponent evComponent;
    private IndividualViewer indViewer;
    private PopulationViewer popViewer;
    private HistogramViewer histViewer;
    
    /**
    * The driverMain method initializes and sets up the Evolution Viewer application.
    * It creates a graphical user interface, sets up user input fields, and handles
    * the evolution process.
    */
    public void driverMain(){
        final String frameTitle = "Evolution Viewer";
        final int frameWidth = 800;
        final int frameHeight = 400;
        final int textFieldWidth = 3;
        
        JFrame frame = new JFrame();
        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        this.evComponent = new EvolutionComponent();
        frame.add(this.evComponent, BorderLayout.CENTER);
                
        //Text fields array
        JTextField[] textFields = new JTextField[5];
        
        // create a panel for buttons
        JPanel buttonPanel = new JPanel();
        // Set up the panel to use a horizontal layout and give it a background color
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        // Mutation rate
        JLabel mRate = new JLabel("Mutation %: ");
        JTextField mRateField = new JTextField("0", textFieldWidth);
        textFields[0] = mRateField;

        buttonPanel.add(mRate);
        buttonPanel.add(mRateField);
        
        // Dropdown panel for choosing a selection method
        JPanel dropdownPanel  = new JPanel();
        JLabel dropdownLabel = new JLabel("Selection:");
        dropdownPanel.add(dropdownLabel);
        
        // Modify this if you wish to add different numbers of things into the simulation
        String[] selectionMethods = {"Truncation", "Roulette", "Rank", "Research"};
        // create a combo box with the fixed array so you can pick how many things to add
        JComboBox<String> addSelectionChooser = new JComboBox<String>(selectionMethods);
        //set its maximum size to be its preferred size so it doesn't get too big
        addSelectionChooser.setMaximumSize( addSelectionChooser.getPreferredSize() );
        
        dropdownPanel.add(addSelectionChooser);
        dropdownPanel.setMaximumSize( dropdownPanel.getPreferredSize() );
        buttonPanel.add(dropdownPanel);
        
        // Crossover
        JLabel crossover = new JLabel("Crossover?");
        JCheckBox checkCrossover = new JCheckBox();

        buttonPanel.add(crossover);
        buttonPanel.add(checkCrossover);
        
        // Population
        JLabel population = new JLabel("Population: ");
        JTextField populationField = new JTextField("100", textFieldWidth);
        textFields[1] = populationField;

        buttonPanel.add(population);
        buttonPanel.add(populationField);
        
        // Generations
        JLabel generations = new JLabel("Generation: ");
        JTextField generationsField = new JTextField("100", textFieldWidth);
        textFields[2] = generationsField;
        
        buttonPanel.add(generations);
        buttonPanel.add(generationsField);
        
        // Genome length
        JLabel genomeLength = new JLabel("Genome: ");
        JTextField genomeLengthField = new JTextField("20", textFieldWidth);
        textFields[3] = genomeLengthField;
        
        buttonPanel.add(genomeLength);
        buttonPanel.add(genomeLengthField);
        
        // Elitism
        JLabel elitism = new JLabel("Elitism %: ");
        JTextField elitismField = new JTextField("0", textFieldWidth);
        textFields[4] = elitismField;
        
        buttonPanel.add(elitism);
        buttonPanel.add(elitismField);
      
        // Fitness function
        JLabel fitnessFunctionLabel = new JLabel("Fitness Func:");
        JPanel fitnessFunctionDropdownPanel  = new JPanel();
        fitnessFunctionDropdownPanel.add(fitnessFunctionLabel);
        
        String[] fitnessFunctionOptions = {"Default", "Smiley", "Sus"};
        JComboBox<String> fitnessFunctionChooser = new JComboBox<String>(fitnessFunctionOptions);
        fitnessFunctionChooser.setMaximumSize( fitnessFunctionChooser.getPreferredSize() );
        
        fitnessFunctionDropdownPanel.add(fitnessFunctionChooser);
        fitnessFunctionDropdownPanel.setMaximumSize( fitnessFunctionDropdownPanel.getPreferredSize() );
        
        buttonPanel.add(fitnessFunctionDropdownPanel);

        // Fast Evolution
        JLabel fastEvolutionLabel = new JLabel("Fast?");
        JCheckBox fastEvolutionCheckBox = new JCheckBox();
        
        buttonPanel.add(fastEvolutionLabel);
        buttonPanel.add(fastEvolutionCheckBox);

        // Stop at terminating condition
        JLabel autoStopLabel = new JLabel("Auto Stop?");
        JCheckBox autoStopCheckBox = new JCheckBox();
        buttonPanel.add(autoStopLabel);
        buttonPanel.add(autoStopCheckBox);
        
        // Start Evolution
        JButton startEvolutionButton = new JButton("Start Evolution");
        
        startEvolutionButton.addActionListener(new ActionListener() {
            
            private boolean passedErrorCheck = true;
            Timer timer = new Timer(TIMER_DELAY/Integer.parseInt(generationsField.getText()), new ActionListener() {
                int generationCount = -1;

                private void resetEvolution() {
                    startEvolutionButton.setText("Start Evolution");
                    timer.restart();
                    makeAllFieldsEditable(textFields, addSelectionChooser, checkCrossover, fastEvolutionCheckBox, autoStopCheckBox);
                    try {
                        evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText(), fitnessFunctionChooser.getSelectedItem().toString());
                    } catch (InvalidGenomeLengthException e) { }

                    generationCount = -1;
                    timer.stop();
                    indViewer.stopTimer();
                    popViewer.stopTimer();
                    histViewer.stopTimer();
                }

                private void initializeWindows(){
                    indViewer = new IndividualViewer();
                    indViewer.getIndComponent().setPopulation(evComponent.handleGetPopulation());
                    indViewer.setTimerDelay(timer.getDelay());
                    indViewer.driverMain();

                    popViewer = new PopulationViewer();
                    popViewer.handleSetPopulation(evComponent.handleGetPopulation());
                    popViewer.setTimerDelay(timer.getDelay());
                    popViewer.driverMain();
                    
                    histViewer = new HistogramViewer();
                    histViewer.handleSetPopulation(evComponent.handleGetPopulation());
                    histViewer.setTimerDelay(timer.getDelay());
                    histViewer.driverMain();
                }
                
                int count = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!fastEvolutionCheckBox.isSelected()){
                        if (passedErrorCheck){
                            if (autoStopCheckBox.isSelected() && evComponent.checkForFitness100()) {
                                count++;
                                if (count == 5) {
                                    JOptionPane.showMessageDialog(null, "The first generation with perfect genes is " + (generationCount-5), "Perfect Genes Found", JOptionPane.INFORMATION_MESSAGE);
                                    resetEvolution();
                                    timer.stop();
                                    count = 0;
                                    return;
                                }
                            }
                            
                            if (generationCount == -1){
                                try {
                                    evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText(), fitnessFunctionChooser.getSelectedItem().toString());
                                } catch (InvalidGenomeLengthException e1) { }

                                if (indViewer!=null){
                                    indViewer.shutDownFrame();
                                    popViewer.shutDownFrame();
                                    histViewer.shutDownFrame();
                                }
                                
                                initializeWindows();

                                generationCount++;
                                frame.repaint();

                            } else if (generationCount <= Integer.parseInt(generationsField.getText())){
                                evComponent.handleSelection();
                                generationCount++;
                                frame.repaint();
                            } else {
                              resetEvolution();
                            }
                        } else{
                            timer.stop();
                        }
                    } else {
                        timer.stop();
                    }
                    frame.requestFocus();
                }
            });
            
            @Override
            public void actionPerformed(ActionEvent e){
                if (!fastEvolutionCheckBox.isSelected()){
                    try{
                        boolean[] checkForError = new boolean[1];
                        checkFields(textFields, fitnessFunctionChooser.getSelectedItem().toString(), checkForError);
                        if (checkForError[0]){
                            this.passedErrorCheck = true;
                            makeAllFieldsUneditable(textFields, addSelectionChooser, checkCrossover, fastEvolutionCheckBox, autoStopCheckBox);
                            if (startEvolutionButton.getText().equals("Start Evolution")){
                                evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText(), fitnessFunctionChooser.getSelectedItem().toString());
                                startEvolutionButton.setText("Pause");
                                timer.start();
                            } else if (startEvolutionButton.getText().equals("Pause")){
                                startEvolutionButton.setText("Continue");
                                timer.stop();
                            } else if (startEvolutionButton.getText().equals("Continue")){
                                startEvolutionButton.setText("Pause");
                                timer.start();
                            }
                        } else{
                            this.passedErrorCheck = false;
                        }
                    } catch (Exception ex){}
                }
            }
        });
        
        buttonPanel.add(startEvolutionButton);

        // Adding an Enter-button shortcut for "submitting the form" and starting the evolution and a Space-Bar shortcut
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == SUBMIT_FORM_KEY) {
                    startEvolutionButton.doClick();
                }
            }
            
        });

        for (JTextField textField : textFields) {
            textField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) { }

                @Override
                public void keyPressed(KeyEvent e) { }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == SUBMIT_FORM_KEY) {
                        startEvolutionButton.doClick();
                    }
                }
                
            });
        }

        /**
        * This block of code defines an array of `EvolutionWorker` objects, a button for starting Fast Evolution,
        * and an action listener to handle the button's behavior.
        */
        final EvolutionWorker[] evolutionWorker = {null}; // Declare as an array to make it effectively final
        
        /**
        * An ActionListener implementation to control the behavior of Fast Evolution when the fastEvolutionCheckBox is selected.
        */
        class EvolutionActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (fastEvolutionCheckBox.isSelected()) {
                    try{
                        boolean[] checkForError = new boolean[1];
                        checkFields(textFields, fitnessFunctionChooser.getSelectedItem().toString(), checkForError);

                        if (checkForError[0]){
                            if (startEvolutionButton.getText().equals("Start Evolution")) {
                                if (evolutionWorker[0] != null) {
                                    if (evolutionWorker[0].isShutAllFrames()) {
                                        indViewer.shutDownFrame();
                                        popViewer.shutDownFrame();
                                        histViewer.shutDownFrame();
                                    }
                                } else if (indViewer!=null){
                                    indViewer.shutDownFrame();
                                    popViewer.shutDownFrame();
                                    histViewer.shutDownFrame();
                                }
                            
                                try {
                                    evComponent.setAll(populationField.getText(), addSelectionChooser.getSelectedItem().toString(), mRateField.getText(), checkCrossover.isBorderPaintedFlat(), generationsField.getText(), genomeLengthField.getText(), elitismField.getText(), fitnessFunctionChooser.getSelectedItem().toString());
                                } catch (InvalidGenomeLengthException e1) {}

                                indViewer = new IndividualViewer();
                                indViewer.getIndComponent().setPopulation(evComponent.handleGetPopulation());
                                indViewer.driverMain();
                                popViewer = new PopulationViewer();
                                popViewer.handleSetPopulation(evComponent.handleGetPopulation());
                                popViewer.driverMain();
                                histViewer = new HistogramViewer();
                                histViewer.handleSetPopulation(evComponent.handleGetPopulation());
                                histViewer.driverMain();
                
                                startEvolutionButton.setText("Pause");
                
                                // Create and execute an EvolutionWorker to run the evolution in the background
                                evolutionWorker[0] = new EvolutionWorker(evComponent, Integer.parseInt(generationsField.getText()), startEvolutionButton);
                                evolutionWorker[0].setPaused(false);
                                evolutionWorker[0].setAutoStopEnabled(autoStopCheckBox.isSelected());
                                evolutionWorker[0].execute();
                            } else if (startEvolutionButton.getText().equals("Pause")) {
                                // Pause the FAST Evolution process
                                startEvolutionButton.setText("Resume");
                                evolutionWorker[0].setPaused(true);
                
                                // Cancel the running EvolutionWorker if it exists and is not yet done
                                if (evolutionWorker[0] != null && !evolutionWorker[0].isDone()) {
                                    evolutionWorker[0].cancel(true);
                                }
                            } else if (startEvolutionButton.getText().equals("Resume")) {
                                // Resume the FAST Evolution process
                                startEvolutionButton.setText("Pause");
                                evolutionWorker[0].setPaused(false);
                
                                // Create a new EvolutionWorker to continue the evolution
                                evolutionWorker[0] = new EvolutionWorker(evComponent, Integer.parseInt(generationsField.getText()), startEvolutionButton);
                                evolutionWorker[0].setAutoStopEnabled(autoStopCheckBox.isSelected());
                                evolutionWorker[0].execute();
                            }
                        }
                    } catch (Exception ex){ }
                    
                }
            }
        }
        
        startEvolutionButton.addActionListener(new EvolutionActionListener());

        buttonPanel.add(startEvolutionButton);

        // Title on left corner
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(new JLabel("Population"));

        // Help Button
        JButton helpButton = new JButton("Help center");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpScreenViewer().handleDriverMain();
            }
            
        });
        titlePanel.add(helpButton);
        
        //Line plot chart
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.pack();
    }
    
    /**
     * ensures: makes all fields uneditable when evolution is running/not ended
     * @param textFields
     * @param addSelectionChooser
     * @param checkCrossover
     * @param fastEvolutionCheckbox
     * @param autoStopCheckBox
     */
    public void makeAllFieldsUneditable(JTextField[] textFields, JComboBox<String> addSelectionChooser, JCheckBox checkCrossover, JCheckBox fastEvolutionCheckbox, JCheckBox autoStopCheckBox){
        for (int i = 0; i < 5; i++){
            textFields[i].setEditable(false);
        }
        addSelectionChooser.setEnabled(false);
        checkCrossover.setEnabled(false);
        fastEvolutionCheckbox.setEnabled(false);
        autoStopCheckBox.setEnabled(false);
    }
    
    /**
     * ensures: makes all fields editable when evolution ends
     * @param textFields
     * @param addSelectionChooser
     * @param checkCrossover
     * @param fastEvolutionCheckbox
     * @param autoStopCheckBox
     * @param isResearchCheckBox
    */
    public void makeAllFieldsEditable(JTextField[] textFields, JComboBox<String> addSelectionChooser, JCheckBox checkCrossover, JCheckBox fastEvolutionCheckbox, JCheckBox autoStopCheckBox){
        for (int i = 0; i < 5; i++){
            textFields[i].setEditable(true);
        }
        addSelectionChooser.setEnabled(true);
        checkCrossover.setEnabled(true);
        fastEvolutionCheckbox.setEnabled(true);
        autoStopCheckBox.setEnabled(true);
    }
    
    /**
     * ensures: checks whether the data in the text fields is valid
     * @param textFields
     * @param fitnessFunction
     * @param hasError
     * @throws Exception
     */
    public void checkFields(JTextField[] textFields, String fitnessFunction, boolean[] hasError) throws Exception{
        // textFields[0] - mRateField
        try{
            if (Double.parseDouble(textFields[0].getText()) < 0 || Double.parseDouble(textFields[0].getText()) > 100){
                throw new InvalidEvolutionInputException(0, 100, "Invalid Mutation Rate");
            }
        } catch (Exception e){
            hasError[0] = false;
            if (!(e instanceof InvalidEvolutionInputException)){
                throw new InvalidEvolutionInputException(0, 100, "Invalid Mutation Rate");
            }
            return;
        }
        
        // textFields[1] - populationField
        try{
            if (Integer.parseInt(textFields[1].getText()) <= 0){
                throw new InvalidEvolutionIntegerException("Invalid Population");
            }
        } catch (Exception e){
            hasError[0] = false;
            if (!(e instanceof InvalidEvolutionIntegerException)){
                throw new InvalidEvolutionIntegerException("Invalid Population");
            }
            return;
        }
        
        // textFields[2] - generationsField
        try{
            if (Integer.parseInt(textFields[2].getText()) <= 0){
                throw new InvalidEvolutionIntegerException("Invalid Number of Generations");
            }
        } catch (Exception e){
            hasError[0] = false;
            if (!(e instanceof InvalidEvolutionIntegerException)){
                throw new InvalidEvolutionIntegerException("Invalid Number of Generations");
            }
            return;
        }
        
        // textFields[3] - genomeLength
        try{
            if (fitnessFunction.equals("Default")){
                if (Integer.parseInt(textFields[3].getText()) <= 0 || Integer.parseInt(textFields[3].getText()) % 10 != 0){
                    throw new InvalidEvolutionMultipleException("Invalid Genome Length");
                }
            } else{
                if (Integer.parseInt(textFields[3].getText()) != 100){
                    throw new InvalidGenomeLengthException(100);
                }
            }
        } catch (Exception e){
            hasError[0] = false;
            if (!(e instanceof InvalidEvolutionMultipleException || e instanceof InvalidGenomeLengthException)){
                throw new InvalidEvolutionMultipleException("Invalid Genome Length");
            }
            return;
        }
        
        // textFields[4] - Elitism
        try{
            if (Double.parseDouble(textFields[4].getText()) < 0 || Double.parseDouble(textFields[4].getText()) > 100){
                throw new InvalidEvolutionInputException(0, 100, "Invalid Elitism");
            }
        } catch (Exception e){
            hasError[0] = false;
            if (!(e instanceof InvalidEvolutionInputException)){
                throw new InvalidEvolutionInputException(0, 100, "Invalid Elitism");
            }
            return;
        }
        
        // no error
        hasError[0] = true;
    }
    
    
    /**
    * The handleDriverMain method is responsible for executing the Evolution Viewer.
    * It calls the driverMain method to set up the application and start the evolution process.
    */
    public void handleDriverMain(){
        this.driverMain();
    }
    
    /**
    * The main method is the entry point of the Evolution Viewer application.
    * It creates an instance of EvolutionViewer and initiates the application.
    * @param args The command-line arguments (not used in this application).
    */
    public static void main(String[] args) {
        EvolutionViewer evViewer = new EvolutionViewer();
        evViewer.handleDriverMain();
    }
}