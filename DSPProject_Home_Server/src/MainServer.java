import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MainServer {
    static final int port_number=8888;
    static Socket mySocket = null;
    static ServerSocket serverSocket = null;
    public static DatabaseServerPeerOperations[] databaseserverlist;
    public static Scanner sc;
    public static void main(String args[]) {
       System.out.println("------------ server Started -----------------");
       sc=new Scanner(System.in);
       System.out.println("Enter Number of Database Servers: ");
       int i=sc.nextInt();
       databaseserverlist=new DatabaseServerPeerOperations[i];
       for(int j=0;j<i;j++){
           System.out.println("------------------------ \n Enter Ip Address of "+(j+1)+"th DB Server : ");
           String ip=sc.nextLine();
           System.out.println("------------------------ \n Enter recieving port of "+(j+1)+"th DB Server : ");
           String port=sc.nextLine();
           databaseserverlist[j]= new DatabaseServerPeerOperations(ip, port);
       }
       try {
        serverSocket = new ServerSocket(port_number);
       } catch (IOException e) {
           System.out.println(e);
       }
       System.out.println("Waiting clients to connect ...");
          
       while (true) {
        try {
            mySocket = serverSocket.accept();
            new ServerThread(mySocket,databaseserverlist).start();
            
        } catch (IOException e) {
            System.out.println(e);
        }
       }
    }
}
