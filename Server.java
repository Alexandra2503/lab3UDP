import java.io.*;
import java.net.*;
import java.lang.Math;
import java.nio.ByteBuffer;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[]buf = new byte[4];

    public Server() throws SocketException {
        socket = new DatagramSocket(4445);
    }
    public static void main(String[] args) throws IOException {
        new Server().start();
    }
    public void run() {
        running = true;
        try {
        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);

            socket.receive(packet);
            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            int x =ByteBuffer.wrap(buf).getInt(0);
            socket.receive(packet);
            int y =ByteBuffer.wrap(buf).getInt(0);
            socket.receive(packet);
            int z =ByteBuffer.wrap(buf).getInt(0);

            double rezult=Math.pow(Math.abs(Math.cos(x)-Math.pow(Math.exp(1),y)),
                    1+2*Math.pow(Math.log10(y),2))*(1+z+z*z/2+z*z*z/3);
            String rez=Double.toString(rezult);
            packet = new DatagramPacket(rez.getBytes(), rez.length(), address, port);
                socket.send(packet);
                System.out.println(rezult);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();
    }
}