
import javax.swing.*;

public class StartPanel extends JPanel implements GameOverListener {
    private JButton playAgainButton;

    public StartPanel() {
        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> startNewGame());
        playAgainButton.setVisible(false);  // Hide the button initially
        this.add(playAgainButton);
    }

    @Override
    public void onGameOver() {
        // Handle game over: show Play Again button
        playAgainButton.setVisible(true);
    }

    public void startNewGame() {
        // Logic to start a new game (reset game state, switch panels, etc.)
        System.out.println("Starting a new game...");
        // You can add logic to transition back to the GamePanel or reset the game state
    }
}