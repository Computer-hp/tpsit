import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class UDPServer {

    public static void main(String[] args) {
        int serverPort = 8081;
        byte[] receiveBuffer = new byte[1024];

        try {
            DatagramSocket serverSocket = new DatagramSocket(serverPort);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("\nClient message:");

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(clientMessage);

                DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(), receivePacket.getLength(), clientAddress, clientPort);
                serverSocket.send(sendPacket);
                System.out.println("Message sent");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
