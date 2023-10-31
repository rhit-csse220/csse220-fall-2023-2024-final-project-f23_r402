package mainApp;

import javax.swing.JOptionPane;

public class InvalidEvolutionIntegerException extends Exception{
    public InvalidEvolutionIntegerException(String messageTitle) {
        JOptionPane.showMessageDialog(null,
		      "Invalid input. Expected an integer bigger than 0.",
		       messageTitle,
		        JOptionPane.ERROR_MESSAGE);
    }
}
