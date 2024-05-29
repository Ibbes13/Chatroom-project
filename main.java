import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog("Ange ditt användarnamn:");
        if (username != null && !username.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                ChatClientUI clientUI = new ChatClientUI(username);
                clientUI.setVisible(true);
            });
        }
    }
}
