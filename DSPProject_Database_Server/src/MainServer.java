import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MainServer {
    //static final int port_number=9999;
    static Socket mySocket = null;
    static ServerSocket serverSocket = null;
    static Scanner sc=new Scanner(System.in);
    static int port_number=9999;
    public static void main(String args[]) {
       System.out.println("port :");
       port_number=sc.nextInt();
       System.out.println("server Started : ");
       try {
           serverSocket = new ServerSocket(port_number);
       } catch (IOException e) {
           System.out.println(e);
       }
        while (true) {
            try {
                mySocket = serverSocket.accept();
                new ServerThread(mySocket,port_number).start();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
