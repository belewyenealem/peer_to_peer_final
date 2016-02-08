import java.io.*;
import java.net.*;
import java.util.Random;
class ServerThread extends Thread {
    DataInputStream myInputStream = null;
    OutputStream myOutputStream = null;
    DataOutputStream myDataOutPutStream;
    Socket clientSocket = null;
    ServerOperations serveroperations;
    DatabaseServerPeerOperations[] databaseserverlist=new DatabaseServerPeerOperations[3];
    public ServerThread(Socket clientSocket,DatabaseServerPeerOperations[] databaseserverlist) {
        this.clientSocket = clientSocket;
        this.databaseserverlist=databaseserverlist;
        serveroperations = new ServerOperations();
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
                        splited = line.split("###");
                        if(line.startsWith("0")){                          
                            for(int q=0;q<this.databaseserverlist.length;q++){
                                this.databaseserverlist[q].registerFiles(q+"#-#"+line);
                                System.out.println("q : "+q);
                            }        
                            myDataOutPutStream.writeUTF(serveroperations.RegisterFiles(splited));
                            //System.out.println("file register Request (" + line + ")");
                        }else if (line.startsWith("1")) {
                            //myDataOutPutStream.writeUTF(this.databaseserverlist[1].getListOfFiles());
                            myDataOutPutStream.writeUTF(serveroperations.getListOfFiles());
                            //System.out.println("file list Request (" + line + ")");
                        } else if (line.startsWith("3")) {
                             System.out.println("database servers done");
                             for(int q=0;q<this.databaseserverlist.length;q++){
                                this.databaseserverlist[q].leaveRequest(line);                               
                             } 
                              myDataOutPutStream.writeUTF(serveroperations.leaveRequest(splited[1], splited[2]));
                            //System.out.println("leave Request (" + line + ")");
                        } else if (line.startsWith("4")) {
                            int chunkNumber=Integer.parseInt(splited[3].split(",,,")[2]);
                            for(int q=0;q<this.databaseserverlist.length;q++){
                                if(chunkNumber%3==q%3){
                                    myDataOutPutStream.writeUTF(this.databaseserverlist[q].chunkRegister(line));
                                }                               
                            } 
                            myDataOutPutStream.writeUTF(serveroperations.chunkRegister(splited));
                            //System.out.println("chunk register Request (" + line + ")");
                        } else if (line.startsWith("5")) {
                            int chNo=Integer.parseInt(splited[3]);
                            int rand=new Random().nextInt((databaseserverlist.length - databaseserverlist.length%3)/3);
                                                            
                            myDataOutPutStream.writeUTF(this.databaseserverlist[chNo%3+rand].chunkRequest(line));
                            //System.out.println("chunk register Request (" + line + ")");
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
