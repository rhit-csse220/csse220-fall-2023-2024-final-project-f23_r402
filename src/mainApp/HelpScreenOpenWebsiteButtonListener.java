package mainApp;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

public class HelpScreenOpenWebsiteButtonListener implements ActionListener {

    private URI uri;

    public HelpScreenOpenWebsiteButtonListener(String url) {
        try {
            this.uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * ensures: opens a webpage in the browser
     * @param uri URI of the page necessary
     * @return whether the process was successful
     */
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openWebpage(this.uri);
    }
    
}
