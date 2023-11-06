package mainApp;

import javax.swing.JOptionPane;

public class InvalidGenomeLengthException extends Exception {

    public InvalidGenomeLengthException(int requiredLength) {
        JOptionPane.showMessageDialog(null,
		      "The genome should be of length " + requiredLength,
                "Invalid genome length",
		        JOptionPane.ERROR_MESSAGE);
    }
    
}
