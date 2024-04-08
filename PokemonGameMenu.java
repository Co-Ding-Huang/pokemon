import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PokemonGameMenu {

    private static ArrayList<String> selectedButtons = new ArrayList<>();

    public static void main(String[] args) {
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};

        // Create JFrame
        JFrame frame = new JFrame("Pokemon Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel to hold buttons
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 0 rows, 3 columns
        
        //label for the instruction
        JLabel instructionLabel = new JLabel("Select 3 Pokémon", SwingConstants.CENTER);
        panel.add(instructionLabel, BorderLayout.NORTH);

        // Create and add buttons with their respective names
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        // Add panel to frame
        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        // Set frame size, make it visible
        frame.setSize(500, 300);
        frame.setVisible(true);
    }

    static class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (selectedButtons.contains(buttonText)) {
                // Display error message
                JFrame errorFrame = new JFrame("Pokemon Error");
                errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel errorPanel = new JPanel();
                JLabel errorMessage = new JLabel("You've already selected " + buttonText);
                errorPanel.add(errorMessage);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        errorFrame.dispose();
                    }
                });
                errorPanel.add(closeButton);

                errorFrame.getContentPane().add(errorPanel);
	                errorFrame.pack();
	                errorFrame.setVisible	(true);
	                
	                return;
	            }
	
	            if (selectedButtons.size() < 3) {
	                selectedButtons.add(buttonText);
	            }
	
	            if (selectedButtons.size() == 3) {
	                // Close the current window
	                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
	                frame.dispose();
	                // Open a new window for battle screen
	                JFrame battleFrame = new JFrame("Battle Screen");
	                battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	                // Create main panel
	                JPanel mainPanel = new JPanel(new BorderLayout());

	                // Create panel for battle screen
	                JPanel battlePanel = new JPanel();
	                battlePanel.setBackground(Color.WHITE);
	                mainPanel.add(battlePanel, BorderLayout.CENTER);
	                
	                // Create panel for displaying selected Pokémon
	                JPanel pokemonPanel = new JPanel(new GridLayout(0, 3));
	                for (String pokemon : selectedButtons) {
	                    JLabel pokemonLabel = new JLabel(pokemon);
	                    pokemonPanel.add(pokemonLabel);
	                    
	                }
	                mainPanel.add(pokemonPanel, BorderLayout.SOUTH);

	                // Add main panel to battle frame
	                battleFrame.getContentPane().add(mainPanel);
	                battleFrame.setSize(400, 300); // Set size of battle frame
	                battleFrame.setVisible(true);
	            }
	        }
	    }
}