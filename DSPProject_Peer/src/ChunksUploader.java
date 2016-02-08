import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
class ChunksUploader extends Thread {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream myInputStream = null;
    OutputStream myOutputStream = null;
    DataOutputStream myDataOutPutStream;
    Socket clientSocket = null;
    public String chunk_location;
    File myFile;
    byte[] mybytearray;
    int chunk_number;
    public String fileName;
    PeerOperations tcpclient1;

    public ChunksUploader(Socket clientSocket, PeerOperations tcpclient1) {
        this.clientSocket = clientSocket;
        this.tcpclient1 = tcpclient1;
    }

    public void run() {
        try {
            instantiateIOStreams();
            if (this.clientSocket != null && myInputStream != null && myDataOutPutStream != null) {
                System.out.println("new chunk downloader ('" + this.clientSocket.getRemoteSocketAddress() + "') is Connected  with  " + this.clientSocket.getLocalPort());
                this.fileName = myInputStream.readUTF();
                chunk_location = "C:/dspproject/uploads/" + fileName.substring(0, fileName.length() - 4) + "/";
                chunk_number = myInputStream.readInt();
                uploadchunk(chunk_location, chunk_number);
            }
            closeallopened();
        } catch (IOException e) {
        }
    }

    private void uploadchunk(String chunk_location, int chunk_number) throws IOException {
        try {
            myFile = new File(chunk_location + chunk_number);
            mybytearray = new byte[1024];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            for (int i = 0; i < tcpclient1.Chunk_Size / mybytearray.length; i++) {
                bis.read(mybytearray, 0, mybytearray.length);
                myDataOutPutStream.write(mybytearray, 0, mybytearray.length);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChunksUploader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
                bis.close();
            } catch (IOException e) {
                System.out.println("error on closing: uploadchunks in chunksuploader");
            }
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
