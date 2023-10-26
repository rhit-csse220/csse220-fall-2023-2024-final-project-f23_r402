package mainApp;
import javax.swing.JOptionPane;

public class InvalidChromosomeCharacterException extends Exception{
    public InvalidChromosomeCharacterException() {
        JOptionPane.showMessageDialog(null,
		      "Invalid characters in file.",
		       "Invalid Data Length",
		        JOptionPane.ERROR_MESSAGE);
    }

}
