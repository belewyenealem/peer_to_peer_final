import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
public class DatabaseServerPeerOperations {
    public Socket client;
    public OutputStream outToServer;
    public DataOutputStream out;
    public InputStream inFromServer;
    public DataInputStream in;
    public String serverName;
    public int acceptingport;
    public int serverport;
    public String myip = "";
    public DatabaseServerPeerOperations(String servername, String serverport) {
        this.serverName = servername;
        this.serverport = Integer.parseInt(serverport);
        this.acceptingport = 2000 + (new Random()).nextInt(60000);
        try {
            myip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatabaseServerPeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("accepting port = " + acceptingport);
        System.out.println("server  port = " + serverport);
        connect();
    }
    public void connect() {
        try {
            System.out.println("Connecting to " + serverName + " on port " + serverport);
            client = new Socket(serverName, this.serverport);
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);
            System.out.println("Just connected to " + client.getRemoteSocketAddress() + " with " + client.getLocalPort());
        } catch (IOException ex) {
            System.out.println( "Could not connnect to server.\nMake Sure that The server is running or ip and port are correct");
        }
    }
    public void close() {
        try {
            this.out.close();
            this.in.close();
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseServerPeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println("Could not connnect to server.\nMake Sure that The server is running or ip and port are correct");
            System.out.println("Server is Closed. make sure Server is running");
        }
    }
    public String registerFiles(String s) {
        String returnvalue = "Error on registering Files.\n Please Make sure that The server is running\n or Reconnect to Server";
        try {
            this.out.writeUTF(s);
            returnvalue = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseServerPeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnvalue;
    }
    public String getListOfFiles() {
        String result = null;
        try {
            this.out.writeUTF("1");
            result = in.readUTF();
        } catch (IOException | NullPointerException ex) {
            System.out.println("Error on registering Files.\n Please Make sure that The server is running\n or Reconnect to Server");
        }
        return result;
    }
    public String leaveRequest(String s) {
        String returnvalue = null;
        try {
            this.out.writeUTF(s);
            System.out.println("db server send leave request ");
            returnvalue = in.readUTF();            
            System.out.println("db server recived leave request response");
        } catch (IOException ex) {
            System.out.println("Error on TcpClient:leaveRequest()");
            //Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException nex) {
            System.out.println("null pointer exception on TcpClient:leaveRequest()");
            System.out.println("Could not Find Server Connecction");
        }       
        return returnvalue;
    }
    public synchronized String chunkRegister(String s) {
        String returnvalue="";
        try {
            this.out.writeUTF(s);
            returnvalue=(in.readUTF());
        } catch (IOException | NullPointerException   ex) {
            System.out.println("Error on chunk Register : Server Registration ");
        }
        return returnvalue;
    }
    public synchronized String chunkRequest(String s) {
        String result = null;
        try {
            this.out.writeUTF(s);
            result = in.readUTF();
        } catch (IOException | NullPointerException ex) {
            System.out.println("Error on chunk Request ");
        }
        return result;
    }    
}
