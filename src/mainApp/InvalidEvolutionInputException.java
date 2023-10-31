package mainApp;

import javax.swing.JOptionPane;

public class InvalidEvolutionInputException extends Exception{
    public InvalidEvolutionInputException(int minValue, int maxValue, String messageTitle) {
        JOptionPane.showMessageDialog(null,
		      "Invalid input. Expected a value between " + minValue + " and " + maxValue + ".",
		       messageTitle,
		        JOptionPane.ERROR_MESSAGE);
    }
}