import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ButtonWithImages {
	private static HashMap<String, Integer> pokemonHP = new HashMap<>();
	private static HashMap<String, Integer> pokemonDEF = new HashMap<>();
	private static HashMap<String, Integer> pokemonATK = new HashMap<>();
    private static ArrayList<String> selectedButtons = new ArrayList<>();
    private static JTextArea textArea;

    public static void main(String[] args) {
    	
    	
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png","C:/Users/andre/Desktop/Pokemon/venusaur-f.png","C:/Users/andre/Desktop/Pokemon/blastoise.png","C:/Users/andre/Desktop/Pokemon/pikachu-f.png","C:/Users/andre/Desktop/Pokemon/mewtwo.png","C:/Users/andre/Desktop/Pokemon/eevee.png"};

        // Create JFrame
        JFrame frame = new JFrame("Pokemon Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel to hold buttons
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 0 rows, 3 columns

        //label for the instruction
        JLabel instructionLabel = new JLabel("Select 3 Pokémon", SwingConstants.CENTER);
        mainPanel.add(instructionLabel, BorderLayout.NORTH);


        // Create and add buttons with their respective names
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i], new ImageIcon(imagePaths[i]));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        // Add buttonPanel to panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        //jtext area add
        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setColumns(25); // Set JTextArea to be 2  by 1
        mainPanel.add(new JScrollPane(textArea), BorderLayout.EAST);

        // Add panel to frame
        frame.getContentPane().add(mainPanel);

        // Set frame size, make it visible
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

    static class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (selectedButtons.contains(buttonText)) {
                if (selectedButtons.size() == 2) {
                    // If this is the second selection, just return without showing an error
                    selectedButtons.add(buttonText);
                } else {
                    // Display error message only if it's not the second selection
                    textArea.append("You've already selected " + buttonText + ".\n");
                }
                return;
            }
            if (selectedButtons.size() < 3) {
                selectedButtons.add(buttonText);
            }

            if (selectedButtons.size() == 3) {
                // Close the current window
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
                frame.dispose();
                startPokemonFirstPick();
            }
        }
    }

    private static void startPokemonFirstPick() {
        JFrame firstpickframe = new JFrame("First Pokemon Menu");
        firstpickframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel to hold buttons
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 2 rows, 3 columns

        JLabel instructionLabel = new JLabel("Select your first pokemon to send out", SwingConstants.CENTER);
        firstpickframe.add(instructionLabel, BorderLayout.NORTH);

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

        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setColumns(25); // Set JTextArea to be 2  by 1
        firstpickframe.add(new JScrollPane(textArea), BorderLayout.EAST);

        // Add panel to frame
        firstpickframe.getContentPane().add(panel);

        // Set frame size, make it visible
        firstpickframe.setSize(900, 600);
        firstpickframe.setVisible(true);
    }

    private static String getImagePath(String pokemonName) {
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png","C:/Users/andre/Desktop/Pokemon/venusaur-f.png","C:/Users/andre/Desktop/Pokemon/blastoise.png","C:/Users/andre/Desktop/Pokemon/pikachu-f.png","C:/Users/andre/Desktop/Pokemon/mewtwo.png","C:/Users/andre/Desktop/Pokemon/eevee.png"};

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
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (selectedButtons.contains(buttonText)) {
                // Display error message
            	JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
                frame.dispose();
                startPokemonBattleframe();
            }
        }
    
    private static void startPokemonBattleframe() {
        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Size and visbility
        battleFrame.setSize(900,600);
        battleFrame.setVisible(true);
        
      //Add hp values
    	pokemonHP.put("Charizard_hash", 78);
    	pokemonHP.put("Venusaur_hash", 80);
    	pokemonHP.put("Blastoise_hash", 79);
    	pokemonHP.put("Pikachu_hash", 35);
    	pokemonHP.put("Mewtwo_hash",106 );
    	pokemonHP.put("Eevee", 55);
    	
    	pokemonDEF.put("Charizard_hash",94);
    	pokemonDEF.put("Venusaur_hash", 92);
    	pokemonDEF.put("Blastoise_hash",103);
    	pokemonDEF.put("Pikachu_hash",55);
    	pokemonDEF.put("Mewtwo_hash",90);
    	pokemonDEF.put("Eevee",78);
    	
    	pokemonATK.put("Charizard_hash",97);
    	pokemonATK.put("Venusaur_hash",91);
    	pokemonATK.put("Blastoise_hash",84);
    	pokemonATK.put("Pikachu_hash",78);
    	pokemonATK.put("Mewtwo_hash",132);
    	pokemonATK.put("Eevee",70);
     }
    }
   }