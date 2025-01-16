import java.net.*;
import java.util.*;


public class UDPClient {

    public static void main(String[] args) {
        int serverPort = 8081;
        byte[] sendBuffer;
        byte[] receiveBuffer = new byte[1024];
        String serverName = "localhost";

        try {
            InetAddress serverAddress = InetAddress.getByName(serverName);
            DatagramSocket clientSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String message;

            while (true) {
                System.out.println("\n1. Message: ");
                System.out.println("2. Exit: ");
                message = scanner.nextLine();

                if (message.equals("2")) {
                    System.out.println("Exiting.");
                    System.exit(0);
                }

                if (!message.equals("1")) continue;

                System.out.println("Enter message: ");
                message = scanner.nextLine();

                sendBuffer = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);
                System.out.println("Message sent");

                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                System.out.println("Receive message:");
                String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(msg);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
