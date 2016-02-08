import java.io.*;
import java.net.*;
class ServerThread extends Thread {
    DataInputStream myInputStream = null;
    OutputStream myOutputStream = null;
    DataOutputStream myDataOutPutStream;
    Socket clientSocket = null;
    ServerOperations serveroperations;
    public ServerThread(Socket clientSocket,int port_number) {
        this.clientSocket = clientSocket;
        serveroperations = new ServerOperations();
        serveroperations.table_name=serveroperations.table_name+"_"+port_number+"`";
        serveroperations.createDBandTable();
    }
    public void run() {
        String line;
        String[] splited;
        try {
            instantiateIOStreams();
            if (this.clientSocket != null && myInputStream != null && myDataOutPutStream != null) {
                System.out.println("Client At " + this.clientSocket.getRemoteSocketAddress() + " is Connected  with Port " + this.clientSocket.getLocalPort());
                while (true) {
                    try {
                        line = myInputStream.readUTF();
                        int fragmentNumber;
                        
                        if(line.split("#-#").length>1){
                            fragmentNumber =Integer.parseInt(line.split("#-#")[0]);
                            line=line.split("#-#")[1];
                        }
                        else{
                            fragmentNumber=0;
                        }
                        
                        splited = line.split("###");
                        if (line.startsWith("0")) {
                            myDataOutPutStream.writeUTF(serveroperations.RegisterFiles(splited,fragmentNumber));
                            System.out.println("file register Request (" + line + ")");
                        } else if (line.startsWith("1")) {
                            myDataOutPutStream.writeUTF(serveroperations.getListOfFiles());
                            System.out.println("file list Request (" + line + ")");
                        } 
                        else if (line.startsWith("3")) {
                            System.out.println("leave Request (" + splited[1]+","+splited[2]+ ")");
                            myDataOutPutStream.writeUTF(serveroperations.leaveRequest(splited[1], splited[2]));
                        } else if (line.startsWith("4")) {
                            myDataOutPutStream.writeUTF(serveroperations.chunkRegister(splited));
                            System.out.println("chunk register Request (" + line + ")");
                        } else if (line.startsWith("5")) {
                            myDataOutPutStream.writeUTF(serveroperations.chunkRequest(splited[1],splited[2],splited[3]));
                            System.out.println("chunk register Request (" + line + ")");
                        }
                    } catch (EOFException eofe) {
                        System.out.println(this.clientSocket.getRemoteSocketAddress() + " is Disconnected");
                        line = null;
                        break;
                    } catch (SocketException se) {
                        System.out.println(this.clientSocket.getRemoteSocketAddress() + " is Disconnected");
                        line = null;
                        break;
                    }
                    //System.out.println(line);
                }
                closeallopened();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void instantiateIOStreams() throws IOException {
        myInputStream = new DataInputStream(clientSocket.getInputStream());
        myOutputStream = new PrintStream(clientSocket.getOutputStream());
        myDataOutPutStream = new DataOutputStream(myOutputStream);
    }
    public void closeallopened() throws IOException {
        myInputStream.close();
        myOutputStream.close();
        clientSocket.close();
    }
}
