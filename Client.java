import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[]buf;

    public Client() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public static void main(String[] args) throws IOException {
       Client client= new Client();
        Scanner in = new Scanner(System.in);
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            client.sendEcho(x, y, z);
        }
    }

    public void sendEcho(int x, int y,int z ) throws IOException {
        buf = ByteBuffer.allocate(4).putInt(x).array();;
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);

        socket.send(packet);
        buf = ByteBuffer.allocate(4).putInt(y).array();
        socket.send(packet);
        buf = ByteBuffer.allocate(4).putInt(z).array();
        socket.send(packet);

        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        System.out.print(received);
    }

    public void close() {
        socket.close();
    }
}