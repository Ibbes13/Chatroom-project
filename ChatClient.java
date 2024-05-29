import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClient {
    private static final int PORT = 12345;
    private static final String MULTICAST_GROUP = "230.0.0.1";
    private MulticastSocket socket;
    private InetAddress group;
    private NetworkInterface networkInterface;
    private ChatClientUI ui;
    private List<String> users;
    private String username;

    public ChatClient(ChatClientUI ui, String username) {
        this.ui = ui;
        this.username = username;
        this.users = new ArrayList<>();
        initializeNetwork();
        startReceiver(); 
    }

    private void initializeNetwork() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(MULTICAST_GROUP);
            networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            socket.joinGroup(new InetSocketAddress(group, PORT), networkInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReceiver() {
        Thread receiverThread = new Thread(() -> {
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    processMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        receiverThread.start();
    }

    public void sendMessage(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) {
        if (message.startsWith("ADDUSER:")) {
            String newUser = message.substring(8);
            if (!users.contains(newUser)) {
                users.add(newUser);
                ui.updateUserArea(users);
                ui.appendMessage(newUser + " är uppkopplad.");
            }
        } else if (message.startsWith("REMOVEUSER:")) {
            String userToRemove = message.substring(11);
            users.remove(userToRemove);
            ui.updateUserArea(users);
            ui.appendMessage(userToRemove + " har lämnat chatten.");
        } else if (message.startsWith("REQUESTUSERLIST:")) {
            String requestingUser = message.substring(16);
            if (!requestingUser.equals(username)) {
                respondToUserListRequest();
            }
        } else if (message.startsWith("USERLIST:")) {
            String[] userList = message.substring(9).split(",");
            users.clear();
            users.addAll(Arrays.asList(userList));
            ui.updateUserArea(users);
        } else {
            ui.appendMessage(message);
        }
    }

    public void addUser(String username) {
        sendMessage("ADDUSER:" + username);
        requestUserList();
    }

    public void requestUserList() {
        sendMessage("REQUESTUSERLIST:" + username);
    }

    public void respondToUserListRequest() {
        String userList = String.join(",", users);
        sendMessage("USERLIST:" + userList);
    }

    public void removeUser(String username) {
        sendMessage("REMOVEUSER:" + username);
    }

    public void disconnect(String username) {
        removeUser(username);
        try {
            socket.leaveGroup(new InetSocketAddress(group, PORT), networkInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }
}