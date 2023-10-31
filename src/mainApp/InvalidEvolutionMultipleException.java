package mainApp;

import javax.swing.JOptionPane;

public class InvalidEvolutionMultipleException extends Exception{
    public InvalidEvolutionMultipleException(String messageTitle) {
        JOptionPane.showMessageDialog(null,
		      "Invalid input. Expected an integer bigger than 0 and a multiple of 10.",
		       messageTitle,
		        JOptionPane.ERROR_MESSAGE);
    }
}
