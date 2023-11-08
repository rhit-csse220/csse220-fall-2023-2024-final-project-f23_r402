package mainApp;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.channels.ReadPendingException;
import java.nio.charset.StandardCharsets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ShareChromosomeViewer extends Viewer {

    private static final int SCREEN_PADDING = 15;
    private static final String MESSAGE_BODY = "This chromosome was generated with the ++GARP++ app: ";
    
    private String message;
    private String whatsappURL;
    private String redditURL;
    private String twitterURL;

    public ShareChromosomeViewer(String chromosomeData) {
        this.message = MESSAGE_BODY + chromosomeData;
        this.message = URLEncoder.encode(this.message, StandardCharsets.UTF_8);
        this.whatsappURL = "https://wa.me/?text=" + this.message;
        this.redditURL = "https://reddit.com/submit?title=" + this.message;
        this.twitterURL = "https://twitter.com/intent/tweet?text=" + this.message;
    }

    @Override
    public void driverMain() {
        JFrame frame = new JFrame("Share Chromosome");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(new EmptyBorder(SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING, SCREEN_PADDING));

        JButton emailButton = new JButton("Email");
        emailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI mailto = new URI("mailto:?subject=Chromosome%20Data&body=" + message.replace("+", "%20"));
                    desktop.mail(mailto);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                                                    "This service is not supported on your device",
                                                    "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                }   
            }
            
        });
        mainPanel.add(emailButton);
        
        JButton whatsappButton = new JButton("WhatsApp");
        whatsappButton.addActionListener(new OpenWebsiteButtonListener(this.whatsappURL));
        mainPanel.add(whatsappButton);
        
        JButton redditButton = new JButton("Reddit");
        redditButton.addActionListener(new OpenWebsiteButtonListener(this.redditURL));
        mainPanel.add(redditButton);

        JButton twitterButton = new JButton("Twitter / X");
        twitterButton.addActionListener(new OpenWebsiteButtonListener(this.twitterURL));
        mainPanel.add(twitterButton);

        frame.add(mainPanel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setVisible(true);
    }

}
