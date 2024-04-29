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
    private static boolean protectActive = false;

    public static void main(String[] args) {
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png", "C:/Users/andre/Desktop/Pokemon/venusaur-f.png", "C:/Users/andre/Desktop/Pokemon/blastoise.png", "C:/Users/andre/Desktop/Pokemon/pikachu-f.png", "C:/Users/andre/Desktop/Pokemon/mewtwo.png", "C:/Users/andre/Desktop/Pokemon/eevee.png"};

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

        JFrame frame = new JFrame("Pokemon Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3));

        JLabel instructionLabel = new JLabel("Select 3 Pokémon", SwingConstants.CENTER);
        mainPanel.add(instructionLabel, BorderLayout.NORTH);

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i], new ImageIcon(imagePaths[i]));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setRows(2);
        textArea.setColumns(25);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.EAST);

        frame.getContentPane().add(mainPanel);

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
                    selectedButtons.add(buttonText);
                } else {
                    textArea.append("You've already selected " + buttonText + ".\n");
                }
                return;
            }
            if (selectedButtons.size() < 3) {
                selectedButtons.add(buttonText);
                remainingPokemons.remove(buttonText);
            }

            if (selectedButtons.size() == 3) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
                frame.dispose();
                startPokemonFirstPick();
            }
        }
    }

    private static void startPokemonFirstPick() {
        JFrame firstpickframe = new JFrame("First Pokemon Menu");
        firstpickframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3));

        JLabel instructionLabel = new JLabel("Select your first pokemon to send out", SwingConstants.CENTER);
        firstpickframe.add(instructionLabel, BorderLayout.NORTH);

        for (String pokemon : selectedButtons) {
            ImageIcon icon = new ImageIcon(getImagePath(pokemon));
            JButton button = new JButton(pokemon, icon);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.addActionListener(new BattleButtonClickListener(pokemon)); // Use BattleButtonClickListener here
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setRows(2);
        textArea.setColumns(25);
        firstpickframe.add(new JScrollPane(textArea), BorderLayout.EAST);

        firstpickframe.getContentPane().add(panel);

        firstpickframe.setSize(900, 600);
        firstpickframe.setVisible(true);
    }

    private static String getImagePath(String pokemonName) {
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"C:/Users/andre/Desktop/Pokemon/charizard.png", "C:/Users/andre/Desktop/Pokemon/venusaur-f.png", "C:/Users/andre/Desktop/Pokemon/blastoise.png", "C:/Users/andre/Desktop/Pokemon/pikachu-f.png", "C:/Users/andre/Desktop/Pokemon/mewtwo.png", "C:/Users/andre/Desktop/Pokemon/eevee.png"};
        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equals(pokemonName)) {
                return imagePaths[i];
            }
        }
        return null;
    }

    static void startPokemonBattleframe(String selectedPokemon) {
        // Generate the opponent's Pokemon here
        opponentPokemon = remainingPokemons.get(new Random().nextInt(remainingPokemons.size()));

        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerHealthBar = new JProgressBar(0, pokemonHP.get(selectedPokemon + "_hash"));
        playerHealthBar.setValue(pokemonHP.get(selectedPokemon + "_hash"));
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(getHealthBarColor(playerHealthBar.getValue(), playerHealthBar.getMaximum()));
        playerPanel.add(playerHealthBar, BorderLayout.NORTH);

        ImageIcon selectedPokemonIcon = new ImageIcon(getImagePath(selectedPokemon));
        JLabel playerPokemonLabel = new JLabel(selectedPokemon, selectedPokemonIcon, SwingConstants.CENTER);
        playerPokemonLabel.setVerticalAlignment(SwingConstants.BOTTOM); // Align the selected Pokemon to the bottom
        playerPanel.add(playerPokemonLabel, BorderLayout.CENTER);

        mainPanel.add(playerPanel, BorderLayout.WEST);

        JPanel opponentPanel = new JPanel(new BorderLayout());
        opponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        opponentHealthBar = new JProgressBar(0, pokemonHP.get(opponentPokemon + "_hash"));
        opponentHealthBar.setValue(pokemonHP.get(opponentPokemon + "_hash"));
        opponentHealthBar.setStringPainted(true);
        opponentHealthBar.setForeground(getHealthBarColor(opponentHealthBar.getValue(), opponentHealthBar.getMaximum()));
        opponentPanel.add(opponentHealthBar, BorderLayout.NORTH);

        ImageIcon opponentPokemonIcon = new ImageIcon(getImagePath(opponentPokemon));
        JLabel opponentPokemonLabel = new JLabel(opponentPokemon, opponentPokemonIcon, SwingConstants.CENTER);
        opponentPokemonLabel.setVerticalAlignment(SwingConstants.TOP); // Align the opponent Pokemon to the top
        opponentPanel.add(opponentPokemonLabel, BorderLayout.CENTER);

        mainPanel.add(opponentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

        JButton tackleButton = new JButton("Tackle");
        tackleButton.addActionListener(new AttackButtonClickListener(selectedPokemon, opponentPokemon));
        buttonPanel.add(tackleButton);

        JButton growlButton = new JButton("Growl");
        growlButton.addActionListener(new AttackButtonClickListener(selectedPokemon, opponentPokemon));
        buttonPanel.add(growlButton);

        JButton protectButton = new JButton("Protect");
        protectButton.addActionListener(new AttackButtonClickListener(selectedPokemon, opponentPokemon));
        buttonPanel.add(protectButton);

        JButton bulkUpButton = new JButton("Bulk Up");
        bulkUpButton.addActionListener(new AttackButtonClickListener(selectedPokemon, opponentPokemon));
        buttonPanel.add(bulkUpButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 400)); // Set preferred size for the text area
        mainPanel.add(scrollPane, BorderLayout.EAST); // Place the scroll pane containing text area on the right

        battleFrame.getContentPane().add(mainPanel);
        battleFrame.setSize(900, 600);
        battleFrame.setVisible(true);
    }

    private static void tackleMove(String opponentPokemon, String selectedPokemon) {
        // Get selectedPokemon's attack and opponentPokemon's defense
        int selectedAttack = pokemonATK.get(selectedPokemon + "_hash");
        int opponentDefense = pokemonDEF.get(opponentPokemon + "_hash");

        // Calculate damage
        double damageDouble = ((double) selectedAttack / opponentDefense) * 10;

        // Apply a modifier for better balancing
        damageDouble *= 1.5; // Example: Increase damage by 50%

        // Convert double damage to int
        int damage = (int) Math.round(damageDouble);

        // Subtract damage from opponentPokemon's HP
        int currentHP = pokemonHP.get(opponentPokemon + "_hash");
        int newHP = Math.max(0, currentHP - damage); // Ensure HP doesn't go below 0
        pokemonHP.put(opponentPokemon + "_hash", newHP);

        opponentHealthBar.setValue(newHP);
        // Set the color of the opponentPokemon's health bar based on its value
        opponentHealthBar.setForeground(getHealthBarColor(newHP, opponentHealthBar.getMaximum()));

        // Update textArea
        textArea.append("\n" + selectedPokemon + " used Tackle. " + opponentPokemon + " took " + damage + " damage.\n");
        textArea.append(opponentPokemon + "'s HP: " + newHP + "\n");

        // Close frame on opponentPokemon faint
        if (newHP <= 0) {
            textArea.append(opponentPokemon + " has fainted! You win!");
        }
    }

    private static void tackleMoveOpponent(String selectedPokemon, String opponentPokemon ) {
        // Get selectedPokemon's attack and opponentPokemon's defense
        int opponentAttack = pokemonATK.get(opponentPokemon + "_hash");
        int selectedDefense = pokemonDEF.get(selectedPokemon + "_hash");

        // Calculate damage
        double damageDoubleOP = ((double) opponentAttack / selectedDefense) * 10;

        // Apply a modifier for better balancing
        damageDoubleOP *= 1.5; // Example: Increase damage by 50%

        // Convert double damage to int
        int damage = (int) Math.round(damageDoubleOP);

        // Subtract damage from opponentPokemon's HP
        int currentHPOP = pokemonHP.get(selectedPokemon + "_hash");
        int newHPOP = Math.max(0, currentHPOP - damage); // Ensure HP doesn't go below 0
        pokemonHP.put(selectedPokemon + "_hash", newHPOP);

        playerHealthBar.setValue(newHPOP);
        // Set the color of the opponentPokemon's health bar based on its value
        playerHealthBar.setForeground(getHealthBarColor(newHPOP, playerHealthBar.getMaximum()));

        // Update textArea
        textArea.append("\n" + opponentPokemon + " used Tackle. " + selectedPokemon + " took " + damage + " damage.\n");
        textArea.append(selectedPokemon + "'s HP: " + newHPOP + "\n");

        // Close frame on opponentPokemon faint
        if (newHPOP <= 0) {
            textArea.append(selectedPokemon + " has fainted! You lose!");
        }
    }
    private static void growlMove(String opponentPokemon, String selectedPokemon) {
        
        // Assuming Growl reduces the selectedPokemon's attack by 20%
        int selectedAttack = pokemonATK.get(opponentPokemon + "_hash");
        int newAttack = (int) (selectedAttack * 0.8); // Reduce attack by 20%
        pokemonATK.put(opponentPokemon + "_hash", newAttack);

        textArea.append("\n" + selectedPokemon + " used Growl. " + opponentPokemon + "'s attack fell.\n");
    }
    private static void growlMoveOpponent(String selectedPokemon, String opponentPokemon ) {
        // Reduce the attack of the selectedPokemon
        // Assuming Growl reduces the selectedPokemon's attack by 20%
        int selectedAttack = pokemonATK.get(selectedPokemon + "_hash");
        int newAttack = (int) (selectedAttack * 0.8); // Reduce attack by 20%
        pokemonATK.put(selectedPokemon + "_hash", newAttack);

        textArea.append("\n" + opponentPokemon + " used Growl. " + selectedPokemon + "'s attack fell.\n");
    }

    private static void protectMove(String selectedPokemon) {
        protectActive = true;
        textArea.append("\n" + selectedPokemon + " used Protect. It took no damage.\n");
    }

    private static void bulkUpMove(String selectedPokemon) {
      
        // Assuming Bulk Up increases the selectedPokemon's defense by 20%
        int selectedDefense = pokemonDEF.get(selectedPokemon + "_hash");
        int newDefense = (int) (selectedDefense * 1.2); // Increase defense by 20%
        pokemonDEF.put(selectedPokemon + "_hash", newDefense);

        textArea.append("\n" + selectedPokemon + " used Bulk Up. " + selectedPokemon + "'s defense increased.\n");
    }
    
    private static void bulkUpMoveOpponent(String opponentPokemon) {
        // Increase the defense of the opponentPokemon
        // Assuming Bulk Up increases the opponentPokemon's defense by 20%
        int opponentDefense = pokemonDEF.get(opponentPokemon + "_hash");
        int newDefense = (int) (opponentDefense * 1.2); // Increase defense by 20%
        pokemonDEF.put(opponentPokemon + "_hash", newDefense);

        textArea.append("\n" + opponentPokemon + " used Bulk Up. " + opponentPokemon + "'s defense increased.\n");
    }
    private static void opponentTurn(String selectedPokemon) {
        String[] enemyAttacks = {"Tackle", "Growl", "Bulk Up"};
        Random rand = new Random();
        String enemyAttack = enemyAttacks[rand.nextInt(enemyAttacks.length)];

        // Check if Protect is active and if the opponent's move is Tackle
        if (protectActive && enemyAttack.equals("Tackle")) {
            textArea.append("\n" + opponentPokemon + "'s Tackle move was blocked by Protect!\n");
            protectActive = false; // Reset Protect status
            return; // Skip opponent's turn
        }

        // Perform the enemy's attack
        if (enemyAttack.equals("Tackle")) {
            tackleMoveOpponent(selectedPokemon, opponentPokemon);
        } else if (enemyAttack.equals("Growl")) {
            growlMoveOpponent(selectedPokemon, opponentPokemon);
        } else if (enemyAttack.equals("Bulk Up")) {
            bulkUpMoveOpponent(opponentPokemon);
        }
    }

    private static Color getHealthBarColor(int currentValue, int maxValue) {
        float hue = (float) currentValue / maxValue * 0.4f; // Hue ranges from 0.0 to 0.4 (green to red)
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    static class AttackButtonClickListener implements ActionListener {
        private String selectedPokemon;
        private String opponentPokemon;

        public AttackButtonClickListener(String selectedPokemon, String opponentPokemon) {
            this.selectedPokemon = selectedPokemon;
            this.opponentPokemon = opponentPokemon;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String attack = source.getText();

            if (attack.equals("Tackle")) {
                tackleMove(opponentPokemon, selectedPokemon);
            } else if (attack.equals("Growl")) {
                growlMove(opponentPokemon, selectedPokemon);
            } else if (attack.equals("Protect")) {
                protectMove(selectedPokemon);
            } else if (attack.equals("Bulk Up")) {
                bulkUpMove(selectedPokemon);
            }

            // Check if the opponent's HP has reached zero
            if (pokemonHP.get(opponentPokemon + "_hash") <= 0) {
                textArea.append("\n");
            } else {
                // If the opponent is still alive, proceed with their turn
                opponentTurn(selectedPokemon);
            }
        }
    }

    static class BattleButtonClickListener implements ActionListener {
        private String pokemon;

        public BattleButtonClickListener(String pokemon) {
            this.pokemon = pokemon;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Implement the actionPerformed method here
            // This method will be called when a battle button is clicked
            JButton button = (JButton) e.getSource();
            String selectedPokemon = button.getText();

            // Call the method to start the battle with the selected Pokemon
            startPokemonBattleframe(selectedPokemon);
        }
    }
}
