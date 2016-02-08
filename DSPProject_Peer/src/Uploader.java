
import java.io.*;
import java.net.*;

public class Uploader extends Thread {

    int portForAcceptingPeer;
    Socket mySocket = null;
    ServerSocket serverSocket = null;
    PeerOperations tcpclient1;

    public Uploader(int portForAcceptingPeer, PeerOperations tcpclient1) {
        this.portForAcceptingPeer = portForAcceptingPeer;
        this.tcpclient1 = tcpclient1;
        System.out.println("accepting peers Started : ");

        try {
            serverSocket = new ServerSocket(portForAcceptingPeer);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        System.out.println("listening download peer started");
        while (true) {
            try {
                mySocket = serverSocket.accept();
                new ChunksUploader(mySocket, tcpclient1).start();

            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
