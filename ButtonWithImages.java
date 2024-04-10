import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ButtonWithImages {

    private static ArrayList<String> selectedButtons = new ArrayList<>();

    public static void main(String[] args) {
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"src/Pokemon/charizard.png","src/Pokemon/venusaur-f.png","src/Pokemon/blastoise.png","src/Pokemon/pikachu-f.png","src/Pokemon/mewtwo.png","src/Pokemon/eevee.png" };

        // Create JFrame
        JFrame frame = new JFrame("Pokemon Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel to hold buttons
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3)); // 2 rows, 3 columns

        //label for the instruction
        JLabel instructionLabel = new JLabel("Select 3 Pokémon", SwingConstants.CENTER);
        panel.add(instructionLabel, BorderLayout.NORTH);

     // Create and add buttons with their respective names
        for (int i = 0; i < buttonNames.length; i++) {
            ImageIcon icon = new ImageIcon(imagePaths[i]);
            JButton button = new JButton(buttonNames[i], icon);
            button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the icon
            button.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }


        // Add panel to frame
        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.getContentPane().add(panel);

        // Set frame size, make it visible
        frame.setSize(500, 600);
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
	                startPokemonBattleMode();
	        }
	    }}
    private static void startPokemonBattleMode() {
        JFrame battleFrame = new JFrame("Select First Pokemon");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel to hold buttons
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 2 rows, 3 columns

     // Create and add buttons with their respective names and images
        for (String pokemon : selectedButtons) {
            ImageIcon icon = new ImageIcon(getImagePath(pokemon));
            JButton button = new JButton(pokemon, icon);
            button.setVerticalTextPosition(SwingConstants.BOTTOM); // Text below the icon
            button.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
            button.addActionListener(new BattleButtonClickListener());
            buttonPanel.add(button);
        }

        // Add buttonPanel to panel
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Add panel to frame
        battleFrame.getContentPane().add(panel);

        // Set frame size, make it visible
        battleFrame.setSize(500, 300);
        battleFrame.setVisible(true);
    }
		private static String getImagePath(String pokemonName) {
		    String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
		    String[] imagePaths = {"src/Pokemon/charizard.png","src/Pokemon/venusaur-f.png","src/Pokemon/blastoise.png","src/Pokemon/pikachu-f.png","src/Pokemon/mewtwo.png","src/Pokemon/eevee.png" };

		    for (int i = 0; i < buttonNames.length; i++) {
		        if (buttonNames[i].equals(pokemonName)) {
		            return imagePaths[i];
		        }
		    }
		    return null; // Handle if pokemonName is not found
		}
		
		static class BattleButtonClickListener implements ActionListener {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Handle button clicks during battle mode if needed
		    }
		}
	}
