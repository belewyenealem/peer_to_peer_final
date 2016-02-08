import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.JOptionPane;
public class PeerOperations {
    public final static String downloads_location = "C:/dspproject/downloads/";
    public final static String uploads_location = "C:/dspproject/uploads/";
    public final static int Chunk_Size = 1048576;
    public Socket client;
    public OutputStream outToServer;
    public DataOutputStream out;
    public InputStream inFromServer;
    public DataInputStream in;
    public String serverName;
    public int acceptingport;
    public int serverport;
    public String myip = "";
    public Uploader acceptpeers;
    public File myFile;
    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public BufferedOutputStream bos = null;
    public BufferedInputStream bis = null;
    public ChunksDownloader[] downloadChunksList;
    public MainClientProgram mainClientProgram;
    public PeerOperations(MainClientProgram mainClientProgram, String servername, String serverport) {
        this.serverName = servername;
        this.serverport = Integer.parseInt(serverport);
        this.acceptingport = 2000 + (new Random()).nextInt(60000);
        this.mainClientProgram = mainClientProgram;
        try {
            myip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("accepting port = " + acceptingport);
        System.out.println("server  port = " + serverport);
        createDirctories();
        acceptpeers = new Uploader(acceptingport, this);
        acceptpeers.start();
        downloadChunksList = new ChunksDownloader[5];
        connect();
    }
    public void createDirctories() {
        myFile = new File("C:/dspproject");
        myFile.mkdir();
        myFile = new File("C:/dspproject/uploads");
        myFile.mkdir();
        myFile = new File("C:/dspproject/downloads");
        myFile.mkdir();
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
            JOptionPane.showMessageDialog(mainClientProgram, "Could not connnect to server.\nMake Sure that The server is running or ip and port are correct");
        }
    }
    public void close() {
        try {
            this.out.close();
            this.in.close();
            this.client.close();
        } catch (IOException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(mainClientProgram, "Could not connnect to server.\nMake Sure that The server is running or ip and port are correct");
            System.out.println("Server is Closed. make sure Server is running");
        }
    }
    public String registerFiles(File[] files) {
        String s = "0###" + myip + "###" + acceptingport;
        String returnvalue = "Error on registering Files.\n Please Make sure that The server is running\n or Reconnect to Server";
        for (int i = 0; i < files.length; i++) {
            s += "###" + files[i].getName() + ",,," + files[i].length();
            copyToUploads(files[i].getAbsolutePath(), files[i].getName());
        }
        try {
            this.out.writeUTF(s);
            returnvalue = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnvalue;
    }
    public void copyToUploads(String path, String name) {
        try {
            myFile = new File("C:/dspproject/uploads/" + name.substring(0, name.length() - 3));
            myFile.mkdir();
            myFile = new File(path);
            byte[] mybytearray = new byte[Chunk_Size];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            for (int i = 0; i < myFile.length() / Chunk_Size + 1; i++) {
                bis.read(mybytearray, 0, mybytearray.length);
                fos = new FileOutputStream("C:/dspproject/uploads/" + name.substring(0, name.length() - 3) + "/" + i);
                bos = new BufferedOutputStream(fos);
                bos.write(mybytearray);
                bos.flush();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
                bos.close();
            } catch (IOException ex) {
                Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public String[] getListOfFiles() {
        String[] result = null;
        try {
            this.out.writeUTF("1");
            result = in.readUTF().split("###");
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(mainClientProgram, "Error on registering Files.\n Please Make sure that The server is running\n or Reconnect to Server");
        }
        return result;
    }
    public String leaveRequest() {
        String returnvalue = null;
        String s = "3###" + myip + "###" + acceptingport;
        try {
            this.out.writeUTF(s);
            returnvalue = in.readUTF();
        } catch (IOException ex) {
            System.out.println("Error on TcpClient:leaveRequest()");
            //Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException nex) {
            System.out.println("null pointer exception on TcpClient:leaveRequest()");
            new JOptionPane("Could not Find Server Connecction").setVisible(true);
        }
        File f = new File("C:\\dspproject\\uploads");
        String[] uploadedList = f.list();
        for (String folder : uploadedList) {
            cleanChunks(uploads_location + "/" + folder);
        }
        f.delete();
        f.mkdir();
        return returnvalue;
    }
    public synchronized void chunkRegister(String path, String filename, String size, int chunknumber) {
        myFile = new File(path);
        String s = "4###" + myip + "###" + acceptingport + "###" + filename + ",,," + size + ",,," + chunknumber;
        try {
            this.out.writeUTF(s);
            System.out.println(in.readUTF());
        } catch (IOException | NullPointerException   ex) {
            System.out.println("Error on chunk Register : Server Registration ");
        }
        // the code below copy completed chunk to uploads folder
        try {
            myFile = new File("C:/dspproject/uploads/" + filename.substring(0, filename.length() - 3) + "/");
            myFile.mkdir();
            myFile = new File(path);
            byte[] mybytearray = new byte[Chunk_Size];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            fos = new FileOutputStream("C:/dspproject/uploads/" + filename.substring(0, filename.length() - 3) + "/" + chunknumber);
            bos = new BufferedOutputStream(fos);
            bos.write(mybytearray);
            bos.flush();
        } catch (IOException | NullPointerException   ex) {
            System.out.println("Error on chunk Register File Copy");
        }
    }
    public synchronized String[] chunkRequest(String filename, String size,int chNo) {
        String s = "5###" + filename + "###" + size+"###"+chNo;
        String[] result = null;
        try {
            this.out.writeUTF(s);
            result = in.readUTF().split(",,,");
        } catch (IOException | NullPointerException ex) {
            System.out.println("Error on chunk Request ");
        }
        return result;
    }
    public void constructFile(String locationDirectory, String filename, int number_of_chunks) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            File mainFile = new File(downloads_location + "/" + filename);
            byte[] mybytearray = new byte[Chunk_Size];
            bos = new BufferedOutputStream(new FileOutputStream(mainFile, true));
            for (int i = 0; i < number_of_chunks; i++) {
                File currentChunk = new File(locationDirectory + "/" + i);
                bis = new BufferedInputStream(new FileInputStream(currentChunk));
                bis.read(mybytearray, 0, mybytearray.length);
                bos.write(mybytearray);
                bos.flush();
                System.out.println(currentChunk.getAbsolutePath() + "  Added ___________");
                bis.close();
            }
            bos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        cleanChunks(locationDirectory);
    }
    public void cleanChunks(String s) {
        File downloadedFolder = new File(s);
        String[] fileList = downloadedFolder.list();
        for (String filename : fileList) {
            System.out.println("chunk : " + filename + " deleted = " + new File(s + "/" + filename).delete());
        }
        downloadedFolder.delete();
    }
}
