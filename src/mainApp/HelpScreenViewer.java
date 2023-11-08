package mainApp;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HelpScreenViewer extends Viewer {

    static final int SCREEN_PADDING = 15;
    static final String descriptionText = "<html>"
                + "This program deals with Genetic Algorithms and has two main features.<br>"
                + "<ul><li>First, the program allows users to create, save to a file, and load from a file<br>"
                + "a single chromosome. A black square stands for 1 in genome, a green one for 0.</li>"
                + "<li>Overall, genomes of different lengths are supported, but they should be divisible<br>"
                + "by 10. Each gene in chromosome is toggleable, meaning its value can be changed from<br>"
                + "0 to 1 and vice versa</li><br><br>"
                + "<li>The other feature is simulating the entire evolution. Our tool provides users with<br>"
                + "nice plots of the fittest, average, and the worst populations and the hamming distance.</li>"
                + "<li>It also provides visualisations for the whole population, the best chromosome, and the<br>"
                + "histogram</li>."
                + "<li>Users have a variety tools to customize their starting population and the evolution process<br>"
                + "such as being able to set the mutation rate, selection algorithm, population size, number of<br>"
                + "generations, and multiple other parameters.</li>"
                + "<li>The Evolution Viewer also provides users with<br>"
                + "the ability to use shortcuts, such as pressing Enter to start and pause the evolution,<br>"
                + "to improve the user experience.</li></ul>"
                + "</html>";

    /**
     * ensures: opens the help screen
     */
    @Override
    public void driverMain() {
        
        JFrame frame = new JFrame("Help");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Help");
        titleLabel.setFont(new Font(null, Font.BOLD, 25));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel);

        JPanel descriptionPanel = new JPanel();
        JLabel descriptionLabel = new JLabel(descriptionText);
        descriptionLabel.setFont(new Font(null, Font.PLAIN, 13));
        descriptionPanel.add(descriptionLabel);
        mainPanel.add(descriptionPanel);

        JPanel usefulLinksTextPanel = new JPanel();
        JLabel usefulLinksLabel = new JLabel("Useful links");
        usefulLinksLabel.setFont(new Font(null, Font.BOLD, 14));
        usefulLinksTextPanel.add(usefulLinksLabel);
        mainPanel.add(usefulLinksTextPanel);

        JPanel linksPanel = new JPanel();
        JButton geneticAlgorithmsButton = new JButton("Wikipedia: Genetic Algorithms");
        geneticAlgorithmsButton.addActionListener(new OpenWebsiteButtonListener("https://en.wikipedia.org/wiki/Genetic_algorithm"));
        linksPanel.add(geneticAlgorithmsButton);
        JButton originalResearchButton = new JButton("Original Research Paper");
        originalResearchButton.addActionListener(new OpenWebsiteButtonListener("https://citeseerx.ist.psu.edu/document?repid=rep1&type=pdf&doi=f9197ff9fdabd2b78bfe0602365011c6699b0d66"));
        linksPanel.add(originalResearchButton);
        JButton shareProgramButton = new JButton("Share This Program!");
        shareProgramButton.addActionListener(new OpenWebsiteButtonListener("https://www.youtube.com/shorts/SXHMnicI6Pg"));
        linksPanel.add(shareProgramButton);

        mainPanel.add(linksPanel);

        frame.add(mainPanel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * This is a wrapper method for this.driverMain()
     */
    public void handleDriverMain() {
        this.driverMain();
    }
    
}
