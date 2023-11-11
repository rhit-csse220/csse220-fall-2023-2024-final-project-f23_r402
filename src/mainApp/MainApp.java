package mainApp;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Class: MainApp
 * @author F23_R402
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	// constants
	static final int LOGO_SIZE = 175;
	static final int SCREEN_PADDING = 15;

	// fields
	EvolutionViewer evViewer = new EvolutionViewer();
	IndividualViewer indViewer = new IndividualViewer();

	/**
	 * ensures: the app is run
	 */
	public void runApp() {
		evViewer.handleDriverMain();
		evViewer.handleDriverMain();
	} // runApp

	/**
	 * The entry point for the application
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Genetic Algorithm Program");

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING));

		JPanel logoPanel = new JPanel();
		ImageIcon logoIcon = null;
		try {
			logoIcon = new ImageIcon(ImageIO.read(new File("logo.png")).getScaledInstance(LOGO_SIZE, LOGO_SIZE, 0));
		} catch (IOException e) {
			System.out.println("No logo file");
		}

		JLabel logoLabel = new JLabel(logoIcon);
		logoLabel.setHorizontalAlignment(JLabel.CENTER);

		logoPanel.add(logoLabel);

		JPanel titlePanel = new JPanel();
		JLabel titleLabel = new JLabel("Genetic Algorithm Program");
		titleLabel.setFont(new Font(null, Font.PLAIN, 25));
		titlePanel.add(titleLabel);

		JPanel buttonsPanel = new JPanel();
		JButton chromosomeButton = new JButton("Chromosome Screen");
		chromosomeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ChromosomeViewer().handleDriverMain();
			}
		});
		buttonsPanel.add(chromosomeButton);

		JButton evolutionButton = new JButton("Evolution Screen");
		evolutionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EvolutionViewer().handleDriverMain();
			}
			
		});
		buttonsPanel.add(evolutionButton);

		JButton helpButton = new JButton("Help Screen");
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new HelpScreenViewer().handleDriverMain();
			}
			
		});
		buttonsPanel.add(helpButton);

		JPanel poweredByPanel = new JPanel();
		poweredByPanel.setBorder(new EmptyBorder(SCREEN_PADDING, 0, 0, 0));
		JLabel poweredByLabel = new JLabel("Powered by Mark, PJ, Jason, and Alex");
		poweredByLabel.setFont(new Font(null, Font.ITALIC, 14));
		poweredByPanel.add(poweredByLabel);

		panel.add(logoPanel);
		panel.add(titlePanel);
		panel.add(buttonsPanel);
		panel.add(poweredByPanel);

		frame.add(panel);
		frame.pack();

		frame.setIconImage(logoIcon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}