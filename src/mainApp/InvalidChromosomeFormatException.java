package mainApp;
import javax.swing.JOptionPane;

public class InvalidChromosomeFormatException extends Exception{
    public InvalidChromosomeFormatException(int characterCount) {
        JOptionPane.showMessageDialog(null,
		      "Invalid file data length. Expected a multiple of 10 characters, but loaded " + characterCount + " characters.",
		       "Invalid Data Length",
		        JOptionPane.ERROR_MESSAGE);
    }

}