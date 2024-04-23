import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PokemonBack{

    static ArrayList<String> selectedButtons = new ArrayList<>();
    static ArrayList<String> remainingPokemons = new ArrayList<>();
    private static JTextArea textArea;

    public static void main(String[] args) {
        // Define the names for each button
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"src/Pokemon/charizard.png", "src/Pokemon/venusaur-f.png", "src/Pokemon/blastoise.png", "src/Pokemon/pikachu-f.png", "src/Pokemon/mewtwo.png", "src/Pokemon/eevee.png"};
        
        // Add all Pokémon names to the remainingPokemons list
        remainingPokemons.addAll(Arrays.asList(buttonNames));

        //font
        Font customFont = loadFont("src/Pokemon/font.ttf");
       
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

            //Setting color
            source.setBackground(Color.GRAY);
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
        Font customFont = loadFont("src/Pokemon/font.ttf"); 
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
        textArea.setColumns(25);
        textArea.setFont(customFont);// Set JTextArea to be 2  by 1
        firstpickframe.add(new JScrollPane(textArea), BorderLayout.EAST);

        // Add panel to frame
        firstpickframe.getContentPane().add(panel);

        // Set frame size, make it visible
        firstpickframe.setSize(900, 600);
        firstpickframe.setVisible(true);
    }

    public static String getImagePath(String pokemon) {
        String[] buttonNames = {"Charizard", "Venusaur", "Blastoise", "Pikachu", "Mewtwo", "Eevee"};
        String[] imagePaths = {"src/Pokemon/charizard.png", "src/Pokemon/venusaur-f.png", "src/Pokemon/blastoise.png", "src/Pokemon/pikachu-f.png", "src/Pokemon/mewtwo.png", "src/Pokemon/eevee.png"};

        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonNames[i].equals(pokemon)) {
                return imagePaths[i];
            }
        }
        return null; // Handle if pokemonName is not found
    }

    static void startPokemonBattleframe(String selectedPokemon) {
    	Font customFont = loadFont("src/Pokemon/font.ttf");
        JFrame battleFrame = new JFrame("Pokemon Battle Mode");
        battleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        // Textbox for console
        textArea = new JTextArea();
        textArea.setEditable(false); // Make it non-editable
        textArea.setRows(2);
        textArea.setColumns(25);
        textArea.setFont(customFont);// Set Width by just sayin gthe amount of columns
        JScrollPane textbox = new JScrollPane(textArea);
        textbox.setBounds(600, 0, 300, 600); // Adjusted bounds

        // Create a JPanel to hold the background and Pokémon images
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
        
        
        
        // Add selected Pokemon to the battle frame
        ImageIcon selectedPokemonBackIcon = new ImageIcon(getBackImagePath(selectedPokemon));
        JLabel selectedPokemonBackLabel = new JLabel(selectedPokemonBackIcon);
        selectedPokemonBackLabel.setBounds(100, 400, selectedPokemonBackIcon.getIconWidth(), selectedPokemonBackIcon.getIconHeight());

        // Randomly select a Pokémon from remainingPokemons and add it to the panel
        if (!PokemonBack.remainingPokemons.isEmpty()) {
            Random rand = new Random();
            String opponentPokemon = PokemonBack.remainingPokemons.get(rand.nextInt(PokemonBack.remainingPokemons.size()));
            ImageIcon opponentIcon = new ImageIcon(getImagePath(opponentPokemon));
            JLabel opponentPokemonLabel = new JLabel(opponentIcon);
            opponentPokemonLabel.setBounds(400, 250, opponentIcon.getIconWidth(), opponentIcon.getIconHeight());
            mainPanel.add(opponentPokemonLabel);

            // Add the opponent's selected Pokemon name to the text area
            textArea.append("\nOpponent selected: " + opponentPokemon);
        }

        // Create JPanel to hold buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2)); // 2 rows, 2 columns

        // Create and add buttons
        String[] attackButtons = {"Tackle", "Protect", "Growl", "Bulk Up"};
        for (int i = 0; i < 4; i++) {
            JButton moveButton = new JButton(attackButtons[i]);
            buttonPanel.add(moveButton);
        }

        // Add buttonPanel to mainPanel
        buttonPanel.setBounds(0, 470, 600, 100);
        mainPanel.add(buttonPanel);
        mainPanel.add(textbox);
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
}
// Moved outside of the Pokemonopens3rdwindow class
class BattleButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String buttonText = source.getText();

        if (PokemonBack.selectedButtons.contains(buttonText)) {
            // Display error message
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(source);
            frame.dispose();
            PokemonBack.startPokemonBattleframe(buttonText);
        }
    }
}