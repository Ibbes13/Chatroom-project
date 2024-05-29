import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatClientUI extends JFrame {
    private JTextArea chatArea;
    private JTextArea userArea;
    private JTextField inputField;
    private JButton disconnectButton;
    private ChatClient client;
    private String username;

    public ChatClientUI(String username) {
        this.username = username;
        initializeUI();
        client = new ChatClient(this, username);
        client.addUser(username);
    }

    private void initializeUI() {
        setTitle("Gruppchatt - " + username);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        userArea = new JTextArea();
        userArea.setEditable(false);
        userArea.append("Online just nu:\n");
        JScrollPane userScrollPane = new JScrollPane(userArea);

        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());

        disconnectButton = new JButton("Logga ut");
        disconnectButton.addActionListener(e -> disconnect());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(disconnectButton, BorderLayout.EAST);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScrollPane, userScrollPane);
        splitPane.setDividerLocation(350);

        add(splitPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String message = inputField.getText();
        if (!message.isEmpty()) {
            message = username + ": " + message;
            inputField.setText("");
            client.sendMessage(message);
        }
    }

    private void disconnect() {
        client.disconnect(username);
        System.exit(0);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public void updateUserArea(List<String> users) {
        userArea.setText("Online just nu:\n");
        for (String user : users) {
            userArea.append(user + "\n");
        }
    }

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
