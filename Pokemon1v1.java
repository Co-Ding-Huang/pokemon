import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Pokemon1v1 {
    static Font customFont = loadFont("src/Pokemon/font.ttf");
    private static HashMap<String, Integer> pokemonHP = new HashMap<>();
    private static HashMap<String, Integer> pokemonDEF = new HashMap<>();
    private static HashMap<String, Integer> pokemonATK = new HashMap<>();
    static ArrayList<String> selectedButtons = new ArrayList<>();
    static ArrayList<String> remainingPokemons = new ArrayList<>();
    private static JTextArea textArea;
    private static String opponentPokemon;
    private static JPanel mainPanel;
    

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

            // Check if the button has already been selected
            if (selectedButtons.contains(buttonText)) {
                // Display a message if the button is clicked again
                textArea.append("You've already selected " + buttonText + ".\n");
                return;
            }

            // If not already selected and less than 3 buttons are selected, proceed
            if (selectedButtons.size() < 3) {
                selectedButtons.add(buttonText);
                remainingPokemons.remove(buttonText); // Remove the selected Pokémon from remainingPokemons
                source.setBackground(Color.GRAY); // Set color to gray when clicked
            }

            // If exactly 3 buttons are selected, close the current window and proceed to the next step
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

        // Create JPanel to hold buttons
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 2 rows, 3 columns

        JLabel instructionLabel = new JLabel("Select your Pokemon for the 1v1!!", SwingConstants.CENTER);
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

    private static void startPokemonBattleframe(String selectedPokemon) {
        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold the Pokémon image and empty space
        mainPanel = new JPanel() {
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
        //Add back images
        ImageIcon selectedPokemonBackIcon = new ImageIcon(getBackImagePath(selectedPokemon));
        JLabel selectedPokemonBackLabel = new JLabel(selectedPokemonBackIcon);
        selectedPokemonBackLabel.setBounds(100, 400, selectedPokemonBackIcon.getIconWidth(), selectedPokemonBackIcon.getIconHeight());
        //Add backpokemon to main panel
        playerPanel.add(selectedPokemonBackLabel, BorderLayout.CENTER);

        // Create a panel to hold the opponent's Pokémon
        JPanel opponentPanel = new JPanel();
        opponentPanel.setLayout(new BorderLayout()); // Border layout
        //Add back images
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
        //Add to main panel of back opponent.
        mainPanel.add(opponentPanel, BorderLayout.EAST);

        // Create JPanel to hold buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2)); // 2 rows, 2 columns

        // Create and add buttons for battle moves
        String[] attackButtons = {"Tackle", "Protect", "Growl", "Bulk Up"};
        for (String attack : attackButtons) {
            JButton moveButton = new JButton(attack);
            moveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int opponentDefense = pokemonDEF.get(opponentPokemon + "_hash");
                    int selectedPokemonAttack = pokemonATK.get(selectedPokemon + "_hash");

                    double damageDouble = ((double) opponentDefense / selectedPokemonAttack) * 10;
                    int damage = (int) Math.round(damageDouble);

                    int currentHP = pokemonHP.get(opponentPokemon + "_hash");
                    int newHP = currentHP - damage;
                    pokemonHP.put(opponentPokemon + "_hash", newHP);

                    textArea.append(selectedPokemon + " used " + attack + ". " + opponentPokemon + " took " + damage + " damage.\n");
                    textArea.append(opponentPokemon + "'s HP: " + newHP + "\n");
                    
                    if (newHP < 1) {
                        textArea.append(opponentPokemon + " has fainted.\n");
                        textArea.append("Congratulations for winning!!\n");

                        // Clear existing buttons
                        buttonPanel.removeAll();

                        // Create panel for play again prompt
                        JPanel playAgainPanel = new JPanel(new GridLayout(1, 1));
                        JLabel playAgainLabel = new JLabel("Play Again?");
                        playAgainPanel.add(playAgainLabel);

                        // Create panel for Yes and No buttons
                        JPanel yesNoPanel = new JPanel(new GridLayout(1, 2));
                        JButton yesButton = new JButton("Yes");
                        JButton noButton = new JButton("No");

                        // Add action listeners to buttons
                        yesButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Restart the game to select new Pokémon
                                battleFrame.dispose(); // Close the battle frame
                                resetPokemonSelection(); // Restart the game
                            }
                        });
                        noButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Close the application
                                System.exit(0);
                            }
                        });

                        // Add Yes and No buttons to the yesNoPanel
                        yesNoPanel.add(yesButton);
                        yesNoPanel.add(noButton);

                        // Add panels to button panel
                        buttonPanel.add(playAgainPanel);
                        buttonPanel.add(yesNoPanel);

                        // Revalidate the panel to update button display
                        buttonPanel.revalidate();
                    }
                }
            });
            buttonPanel.add(moveButton);
        }

        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setFont(customFont);
        textArea.setColumns(50); // Set JTextArea to be 2 by 1

        // Add components to the battleFrame
        battleFrame.add(new JScrollPane(textArea), BorderLayout.EAST);
        buttonPanel.setBounds(0, 470, 600, 100);
        mainPanel.add(buttonPanel);
        mainPanel.add(selectedPokemonBackLabel); // Add Pokémon image

        // Add mainPanel to the frame
        battleFrame.add(mainPanel);

        // Size and visibility
        battleFrame.setSize(900, 600);
        battleFrame.setVisible(true);
    }

    private static void resetPokemonSelection() {
        selectedButtons.clear(); // Clear the selected Pokémon list
        remainingPokemons.clear(); // Clear the remaining Pokémon list

        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"src/Pokemon/charizard.png", "src/Pokemon/venusaur-f.png", "src/Pokemon/blastoise.png", "src/Pokemon/pikachu-f.png", "src/Pokemon/mewtwo.png", "src/Pokemon/eevee.png"};

        // Add all Pokémon names to the remainingPokemons list
        remainingPokemons.addAll(Arrays.asList(buttonNames));

        // Create new frame for Pokémon selection
        JFrame frame = new JFrame("Pokemon Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 3)); // 0 rows, 3 columns

        JLabel instructionLabel = new JLabel("Select 3 Pokémon", SwingConstants.CENTER);
        mainPanel.add(instructionLabel, BorderLayout.NORTH);

        // Create and add buttons with their respective names
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i], new ImageIcon(imagePaths[i]));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setColumns(25); // Set JTextArea to be 2 by 1
        textArea.setFont(customFont);
        mainPanel.add(new JScrollPane(textArea), BorderLayout.EAST);

        frame.getContentPane().add(mainPanel);

        frame.setSize(900, 600);
        frame.setVisible(true);
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

    static class BattleButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (Pokemon1v1.selectedButtons.contains(buttonText)) {
                // Display error message
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
                frame.dispose();
                Pokemon1v1.startPokemonBattleframe(buttonText);
            }
        }
    }
}
