import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class PokemonAttacks {
    static Font customFont = loadFont("src/Pokemon/font.ttf");
    private static HashMap<String, Integer> pokemonHP = new HashMap<>();
    private static HashMap<String, Integer> pokemonDEF = new HashMap<>();
    private static HashMap<String, Integer> pokemonATK = new HashMap<>();
    static ArrayList<String> selectedButtons = new ArrayList<>();
    static ArrayList<String> remainingPokemons = new ArrayList<>();
    private static JTextArea textArea;
    private static String opponentPokemon;

    public static void main(String[] args) {
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"src/Pokemon/charizard.png", "src/Pokemon/venusaur-f.png", "src/Pokemon/blastoise.png", "src/Pokemon/pikachu-f.png", "src/Pokemon/mewtwo.png", "src/Pokemon/eevee.png"};

        // Add all Pokémon names to the remainingPokemons list
        remainingPokemons.addAll(Arrays.asList(buttonNames));

        pokemonHP.put("Charizard_hash", 78);
        pokemonHP.put("Venusaur_hash", 80);
        pokemonHP.put("Blastoise_hash", 79);
        pokemonHP.put("Pikachu_hash", 35);
        pokemonHP.put("Mewtwo_hash", 106);
        pokemonHP.put("Eevee_hash", 55);

        pokemonDEF.put("Charizard_hash", 94);
        pokemonDEF.put("Venusaur_hash", 92);
        pokemonDEF.put("Blastoise_hash", 103);
        pokemonDEF.put("Pikachu_hash", 55);
        pokemonDEF.put("Mewtwo_hash", 90);
        pokemonDEF.put("Eevee_hash", 78);

        pokemonATK.put("Charizard_hash", 97);
        pokemonATK.put("Venusaur_hash", 91);
        pokemonATK.put("Blastoise_hash", 84);
        pokemonATK.put("Pikachu_hash", 78);
        pokemonATK.put("Mewtwo_hash", 132);
        pokemonATK.put("Eevee_hash", 70);

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
        textArea.setFont(customFont);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.EAST);

        // Add panel to frame
        frame.getContentPane().add(mainPanel);

        // Set frame size, make it visible
        frame.setSize(900, 600);
        frame.setVisible(true);
    }
    private static Font loadFont(String fontFilePath) {
        try {
            // Load font from file
            return Font.createFont(Font.TRUETYPE_FONT, new java.io.File(fontFilePath)).deriveFont(Font.PLAIN, 14);
        } catch (Exception e) {
            e.printStackTrace();
            // If font loading fails, return default font
            return new Font("Arial", Font.PLAIN, 14);
        }
    }

    static class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();
            source.setBackground(Color.GRAY);//Set color to gray when clicked
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
                remainingPokemons.remove(buttonText); // Remove the selected Pokémon from remainingPokemons
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
        String[] imagePaths = {"src/Pokemon/charizard.png", "src/Pokemon/venusaur-f.png", "src/Pokemon/blastoise.png", "src/Pokemon/pikachu-f.png", "src/Pokemon/mewtwo.png", "src/Pokemon/eevee.png"};
        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equals(pokemonName)) {
                return imagePaths[i];
            }
        }
        return null; // Handle if pokemonName is not found
    }

    static void startPokemonBattleframe(String selectedPokemon) {
    	Font customFont = loadFont("src/Pokemon/font.ttf");
        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a JPanel to hold the Pokémon image and empty space
        JPanel mainPanel = new JPanel() {//DK what this serial thing is but it fixed the code
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundbattle = new ImageIcon("src/Pokemon/Background1.png");
                Image background = backgroundbattle.getImage();
                g.drawImage(background, 0, 0, 600, 500, this);
            }
        };
        mainPanel.setLayout(null);
        
        // Create a panel to hold the player's Pokémon
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout()); // Border layout
        playerPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH); // Adjust the space as needed
        

        ImageIcon selectedPokemonBackIcon = new ImageIcon(getBackImagePath(selectedPokemon));
        JLabel selectedPokemonBackLabel = new JLabel(selectedPokemonBackIcon);
        selectedPokemonBackLabel.setBounds(100, 400, selectedPokemonBackIcon.getIconWidth(), selectedPokemonBackIcon.getIconHeight());
        playerPanel.add(selectedPokemonBackLabel, BorderLayout.CENTER);

        // Create a panel to hold the opponent's Pokémon
        JPanel opponentPanel = new JPanel();
        opponentPanel.setLayout(new BorderLayout()); // Border layout
        opponentPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH); // Adjust the space as needed

        if (!remainingPokemons.isEmpty()) {
            Random rand = new Random();
            opponentPokemon = remainingPokemons.get(rand.nextInt(remainingPokemons.size()));
            remainingPokemons.remove(opponentPokemon);
            ImageIcon opponentIcon = new ImageIcon(getImagePath(opponentPokemon));
            JLabel opponentPokemonLabel = new JLabel(opponentIcon);
            opponentPokemonLabel.setBounds(400, 250, opponentIcon.getIconWidth(), opponentIcon.getIconHeight());
            mainPanel.add(opponentPokemonLabel);
            textArea.append("\nOpponent selected: " + opponentPokemon);
        }

        mainPanel.add(opponentPanel, BorderLayout.EAST);
        // Create JPanel to hold buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2)); // 2 rows, 2 columns

        // Create and add buttons
        String[] attackButtons = {"Tackle", "Protect", "Growl", "Bulk Up"};
        for (String attack : attackButtons) {
            JButton moveButton = new JButton(attack);
            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get opponent's defense and selected Pokemon's attack
                    int opponentDefense = pokemonDEF.get(opponentPokemon + "_hash");
                    int selectedPokemonAttack = pokemonATK.get(selectedPokemon + "_hash");

                    // Calculate damage as double
                    double damageDouble = ((double) opponentDefense / selectedPokemonAttack) * 10;

                    // Convert double damage to int
                    int damage = (int) Math.round(damageDouble);

                    // Subtract damage from opponent's HP
                    int currentHP = pokemonHP.get(opponentPokemon + "_hash");
                    int newHP = currentHP - damage;
                    pokemonHP.put(opponentPokemon + "_hash", newHP);

                    // Update textArea
                    textArea.append(selectedPokemon + " used " + attack + ". " + opponentPokemon + " took " + damage + " damage.\n");
                    textArea.append(opponentPokemon + "'s HP: " + newHP + "\n");
                    
                    // Check if opponent has fainted
                    if (newHP <= 0) {
                        textArea.append(opponentPokemon + " has fainted.\n");
                        // Send another Pokémon if there are remaining
                        if (!remainingPokemons.isEmpty()) {
                            opponentPanel.removeAll();
                            Random rand = new Random();
                            opponentPokemon = remainingPokemons.get(rand.nextInt(remainingPokemons.size())); // Assign value to opponentPokemon
                            remainingPokemons.remove(opponentPokemon); // Remove the selected Pokémon from remainingPokemons
                        } else {
                            // If no more Pokémon, display a message
                            JLabel noPokemonLabel = new JLabel("No more Pokémon left for opponent.", JLabel.CENTER);
                            opponentPanel.add(noPokemonLabel, BorderLayout.CENTER);
                        }
                    }
                }
            });
            buttonPanel.add(moveButton);
        }
        
        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setFont(customFont);
        textArea.setColumns(50); // Set JTextArea to be 2 by 1
        battleFrame.add(new JScrollPane(textArea), BorderLayout.EAST);
        buttonPanel.setBounds(0, 470, 600, 100);
        mainPanel.add(buttonPanel);
        mainPanel.add(selectedPokemonBackLabel);//Added it here so the textbox above it
        // Add mainPanel to the frame
        battleFrame.add(mainPanel);

        // Size and visibility
        battleFrame.setSize(900, 600);
        battleFrame.setVisible(true);
    }
    
    public static String getBackImagePath(String pokemon) {
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] backImagePaths = {"src/Pokemon/CharizardBack.png", "src/Pokemon/VenusaurBack.png", "src/Pokemon/BlastoiseBack.png", "src/Pokemon/PikachuBack.png", "src/Pokemon/MewtwoBack.png", "src/Pokemon/EeveeBack.png"};
        
        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equals(pokemon)) {
                return backImagePaths[i];
            }
        }
        return null;
    }

    static class AttackButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String moveName = source.getText();

            // Implement your attack logic here
            textArea.append("\nYou used " + moveName + "!");
        }
    }
}

// Moved outside of the PokemonHealthBar class
class BattleButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (PokemonAttacks.selectedButtons.contains(buttonText)) {
            // Display error message
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
            frame.dispose();
            PokemonAttacks.startPokemonBattleframe(buttonText);
        }
    }
} 