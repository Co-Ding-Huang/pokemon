import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class PokemonWithMoves {
    private static HashMap<String, Integer> pokemonHP = new HashMap<>();
    private static HashMap<String, Integer> pokemonDEF = new HashMap<>();
    private static HashMap<String, Integer> pokemonATK = new HashMap<>();
    static ArrayList<String> selectedButtons = new ArrayList<>();
    static ArrayList<String> remainingPokemons = new ArrayList<>();
    private static JTextArea textArea;
    private static String opponentPokemon;
    private static JProgressBar opponentHealthBar;
    private static JProgressBar playerHealthBar;
    
   

    public static void main(String[] args) {
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png","C:/Users/andre/Desktop/Pokemon/venusaur-f.png","C:/Users/andre/Desktop/Pokemon/blastoise.png","C:/Users/andre/Desktop/Pokemon/pikachu-f.png","C:/Users/andre/Desktop/Pokemon/mewtwo.png","C:/Users/andre/Desktop/Pokemon/eevee.png"};

        // Add all Pokémon names to the remainingPokemons list
        remainingPokemons.addAll(Arrays.asList(buttonNames));

        pokemonHP.put("Charizard_hash", 78);
        pokemonHP.put("Venusaur_hash", 80);
        pokemonHP.put("Blastoise_hash", 79);
        pokemonHP.put("Pikachu_hash", 35);
        pokemonHP.put("Mewtwo_hash",106 );
        pokemonHP.put("Eevee_hash", 55);

        pokemonDEF.put("Charizard_hash",94);
        pokemonDEF.put("Venusaur_hash", 92);
        pokemonDEF.put("Blastoise_hash",103);
        pokemonDEF.put("Pikachu_hash",55);
        pokemonDEF.put("Mewtwo_hash",90);
        pokemonDEF.put("Eevee_hash",78);

        pokemonATK.put("Charizard_hash",97);
        pokemonATK.put("Venusaur_hash",91);
        pokemonATK.put("Blastoise_hash",84);
        pokemonATK.put("Pikachu_hash",78);
        pokemonATK.put("Mewtwo_hash",132);
        pokemonATK.put("Eevee_hash",70);

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
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png","C:/Users/andre/Desktop/Pokemon/venusaur-f.png","C:/Users/andre/Desktop/Pokemon/blastoise.png","C:/Users/andre/Desktop/Pokemon/pikachu-f.png","C:/Users/andre/Desktop/Pokemon/mewtwo.png","C:/Users/andre/Desktop/Pokemon/eevee.png"};
        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equals(pokemonName)) {
                return imagePaths[i];
            }
        }
        return null; // Handle if pokemonName is not found
    }

    static void startPokemonBattleframe(String selectedPokemon) {
        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold the Pokémon image and empty space
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a panel to hold the player's Pokémon
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout()); // Border layout
        playerPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH); // Adjust the space as needed

        // Create health bar for player
        playerHealthBar = new JProgressBar(0, pokemonHP.get(selectedPokemon + "_hash"));
        playerHealthBar.setValue(pokemonHP.get(selectedPokemon + "_hash"));
        playerHealthBar.setStringPainted(true);
        // Set the color of the player's health bar based on its value
        playerHealthBar.setForeground(getHealthBarColor(playerHealthBar.getValue(), playerHealthBar.getMaximum()));
        playerPanel.add(playerHealthBar, BorderLayout.NORTH);

        ImageIcon selectedPokemonIcon = new ImageIcon(getImagePath(selectedPokemon));
        Image playerPokemonImage = selectedPokemonIcon.getImage();
        Image resizedPlayerPokemonImage = playerPokemonImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Adjust the size as needed
        ImageIcon resizedPlayerIcon = new ImageIcon(resizedPlayerPokemonImage);
        JLabel selectedPokemonLabel = new JLabel(resizedPlayerIcon, JLabel.CENTER);
        playerPanel.add(selectedPokemonLabel, BorderLayout.CENTER);

        // Create a panel to hold the opponent's Pokémon
        JPanel opponentPanel = new JPanel();
        opponentPanel.setLayout(new BorderLayout()); // Border layout
        opponentPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH); // Adjust the space as needed

        // Randomly select a Pokémon from remainingPokemons
        if (!remainingPokemons.isEmpty()) {
            Random rand = new Random();
            opponentPokemon = remainingPokemons.get(rand.nextInt(remainingPokemons.size())); // Declare opponentPokemon as static
            ImageIcon opponentIcon = new ImageIcon(getImagePath(opponentPokemon));
            Image opponentPokemonImage = opponentIcon.getImage();
            Image resizedOpponentPokemonImage = opponentPokemonImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Adjust the size as needed
            ImageIcon resizedOpponentIcon = new ImageIcon(resizedOpponentPokemonImage);
            JLabel opponentPokemonLabel = new JLabel(resizedOpponentIcon, JLabel.CENTER);
            opponentPanel.add(opponentPokemonLabel, BorderLayout.CENTER);

            // Create health bar for opponent
            opponentHealthBar = new JProgressBar(0, pokemonHP.get(opponentPokemon + "_hash"));
            opponentHealthBar.setValue(pokemonHP.get(opponentPokemon + "_hash"));
            opponentHealthBar.setStringPainted(true);
            // Set the color of the opponent's health bar based on its value
            opponentHealthBar.setForeground(getHealthBarColor(opponentHealthBar.getValue(), opponentHealthBar.getMaximum()));
            opponentPanel.add(opponentHealthBar, BorderLayout.NORTH);
        } else {
            JLabel noPokemonLabel = new JLabel("No more Pokémon left for opponent.", JLabel.CENTER);
            opponentPanel.add(noPokemonLabel);
        }

        // Add the player and opponent panels to the main panel
        mainPanel.add(playerPanel, BorderLayout.WEST);
        mainPanel.add(opponentPanel, BorderLayout.EAST);

        // Add the main panel to the frame
        battleFrame.getContentPane().add(mainPanel);

        // Create JPanel to hold buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2)); // 2 rows, 2 columns

     // Create and add buttons
        String[] attackButtons = {"Tackle", "Protect", "Growl", "Bulk Up"};
        for (String attack : attackButtons) {
            JButton moveButton = new JButton(attack);
            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (attack.equals("Tackle")) {
                        // Get selected Pokemon's attack and opponent's defense
                        int selectedPokemonAttack = pokemonATK.get(selectedPokemon + "_hash");
                        int opponentDefense = pokemonDEF.get(opponentPokemon + "_hash");

                        // Calculate damage
                        double damageDouble = ((double) selectedPokemonAttack / opponentDefense) * 10;

                        // Apply a modifier for better balancing
                        damageDouble *= 1.5; // Example: Increase damage by 50%

                        // Convert double damage to int
                        int damage = (int) Math.round(damageDouble);

                        // Subtract damage from opponent's HP
                        int currentHP = pokemonHP.get(opponentPokemon + "_hash");
                        int newHP = Math.max(0, currentHP - damage); // Ensure HP doesn't go below 0
                        pokemonHP.put(opponentPokemon + "_hash", newHP);
                        
                        opponentHealthBar.setValue(newHP);
                        // Set the color of the opponent's health bar based on its value
                        opponentHealthBar.setForeground(getHealthBarColor(newHP, opponentHealthBar.getMaximum()));

                        // Update textArea
                        textArea.append("\n" + selectedPokemon + " used " + attack + ". " + opponentPokemon + " took " + damage + " damage.\n");
                        textArea.append(opponentPokemon + "'s HP: " + newHP + "\n");

                        // Close frame on opponent faint
                        if (newHP <= 0) {
                        	textArea.append("Battle over!!!");
                            return;
                        }
                    } 
                    else if (attack.equals("Growl")) {
                        textArea.append(  "\n" + selectedPokemon + " used Growl. " + opponentPokemon + "'s attack fell. " + "\n");
                    }
                    else if (attack.equals("Protect")) {
                    	textArea.append("\n" + selectedPokemon + " used Protect. " + opponentPokemon + "'s move has no effect. " + "\n");
                    }
                    else if (attack.equals("Bulk Up")) {
                    	textArea.append("\n" + selectedPokemon + " used Bulk Up. " + selectedPokemon + "'s attack increased. " + "\n");
                    }
                    
                    
                    // After your attack, generate a random attack for the enemy Pokémon
                    String[] enemyAttacks = {"Tackle", "Growl", "Protect", "Bulk Up"};
                    Random rand = new Random();
                    String enemyAttack = enemyAttacks[rand.nextInt(enemyAttacks.length)];
                    
                    // Perform the enemy's attack
                    // You can implement the logic for each enemy attack similarly to your own attacks
                    // For demonstration purposes, let's assume enemy always uses Tackle
                    int opponentAttackPower = pokemonATK.get(opponentPokemon + "_hash");
                    int playerDefense = pokemonDEF.get(selectedPokemon + "_hash");
                    double enemyDamageDouble = ((double) opponentAttackPower / playerDefense) * 10;
                    // Apply a modifier for better balancing if needed
                    enemyDamageDouble *= 1.5; // Example: Increase damage by 50%
                    int enemyDamage = (int) Math.round(enemyDamageDouble);
                    int playerCurrentHP = pokemonHP.get(selectedPokemon + "_hash");
                    int playerNewHP = Math.max(0, playerCurrentHP - enemyDamage); // Ensure HP doesn't go below 0
                    pokemonHP.put(selectedPokemon + "_hash", playerNewHP);
                    playerHealthBar.setValue(playerNewHP);
                    playerHealthBar.setForeground(getHealthBarColor(playerNewHP, playerHealthBar.getMaximum()));
                    textArea.append(opponentPokemon + " used " + enemyAttack + ". " + selectedPokemon + " took " + enemyDamage + " damage.\n");
                    textArea.append(selectedPokemon + "'s HP: " + playerNewHP + "\n");

                    
                    
                    // Check if player's Pokémon faints
                    if (playerNewHP <= 0) {
                    	battleFrame.dispose();
                        return;
                    }
                }
            });
            buttonPanel.add(moveButton);
        }
        // Add buttonPanel to frame
        battleFrame.add(buttonPanel, BorderLayout.SOUTH);

        // Textbox for console
        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setColumns(25); // Set JTextArea to be 2 by 1
        battleFrame.add(new JScrollPane(textArea), BorderLayout.EAST);

        // Size and visibility
        battleFrame.setSize(900, 600);
        battleFrame.setVisible(true);
    }

 // Method to get the color of the health bar based on its value
    private static Color getHealthBarColor(int currentValue, int maxValue) {
        float hue = (float) currentValue / maxValue * 0.4f; // Hue ranges from 0.0 to 0.4 (green to red)
        return Color.getHSBColor(hue, 1.0f, 1.0f);
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

// Moved outside of the Pokemonopens3rdwindow class
class BattleButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (PokemonWithMoves.selectedButtons.contains(buttonText)) {
            // Display error message
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
            frame.dispose();
            PokemonWithMoves.startPokemonBattleframe(buttonText);
        }
    }
}