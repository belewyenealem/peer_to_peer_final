
import java.io.*;
import java.net.*;

class ChunksDownloader extends Thread {
    public int chunk_uploaderPeer_port;
    public String uploaderPeerAddress;
    public String FILE_TO_RECEIVED;
    public String filename;
    public String size;
    public String locationDirectory;
    public int chunk_number;
    public FileDownloader fileDownloader;
    public byte[] mybytearray;
    public DataInputStream dis;
    public DataOutputStream dos;
    private Socket sock;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    public String[] ChunkInfo;

    public ChunksDownloader(FileDownloader fileDownloader, String filename, String size) {
        this.fileDownloader = fileDownloader;
        this.filename = filename;
        this.size = size;
        locationDirectory = PeerOperations.downloads_location + filename.substring(0, filename.length() - 4);

    }

    public void run() {
        System.out.println("ChunksDownloader Thread started ... ");
        fileDownloader.downloadFinished = fileDownloader.downloaded == Integer.parseInt(size) / PeerOperations.Chunk_Size + 1;
        fileDownloader.updateStatus();
        while (!fileDownloader.downloadFinished) {
            fileDownloader.downloadFinished = fileDownloader.downloaded == Integer.parseInt(size) / PeerOperations.Chunk_Size;
            int chNo;
            if((chNo=itHasnt(new File(locationDirectory), Integer.parseInt(size)))!=-1){
                ChunkInfo = fileDownloader.tcpclient1.chunkRequest(filename, size,chNo);
            }
            else{
                continue;
            }
            if(ChunkInfo.length<4){
                continue;
            }
            uploaderPeerAddress = ChunkInfo[2];
            chunk_uploaderPeer_port = Integer.parseInt(ChunkInfo[3]);
            if (fileDownloader.downloadFinished) {
                break;
            }
            try {
                sock = new Socket(uploaderPeerAddress, chunk_uploaderPeer_port);
                chunk_number = Integer.parseInt(ChunkInfo[4]);
                mybytearray = new byte[1024];
                downloadChunk();
                ++fileDownloader.downloaded;
                System.out.println("downloaded chunk : " + ChunkInfo[4]);
                System.out.println("downloaded chunks : " + fileDownloader.downloaded);
                fileDownloader.updateStatus();
            } catch (IOException ex) {
                System.out.println("Could not connect to peer(" + uploaderPeerAddress + ":" + chunk_uploaderPeer_port + ")");
            }
        }
        System.out.println("thread finished. \nDownloaded : " + fileDownloader.downloaded + "\nNumber of Chunks : " + (Integer.parseInt(size) / PeerOperations.Chunk_Size));
    }
    public synchronized void downloadChunk() throws IOException {
        this.FILE_TO_RECEIVED = locationDirectory + "/" + chunk_number;
        instantiateIOStreams();
        dos.writeUTF(filename);
        dos.writeInt(chunk_number);
        for (int i = 0; i < PeerOperations.Chunk_Size / mybytearray.length; i++) {
            dis.read(mybytearray);
            bos.write(mybytearray, 0, mybytearray.length);
            bos.flush();
            mybytearray = new byte[mybytearray.length];
        }

        this.fileDownloader.tcpclient1.chunkRegister(FILE_TO_RECEIVED, filename, size, chunk_number);
        closeOpened();
    }

    public static boolean itHas(String[] array, String s) {
        boolean ithas = false;
        for (String array1 : array) {
            if (array1.equals(s)) {
                ithas = true;
            }
        }
        return ithas;
    }

    public static int itHasnt(File f,int totalNoChunks){
        String[] fileList=f.list();
        int x=-1;
        for(int j=0;j<totalNoChunks;j++){
            if(!itHas(fileList, ""+j)){
               x=j;
               break;
            }            
        }        
        return x;
        
        
    }
    public void instantiateIOStreams() throws IOException {
        fos = new FileOutputStream(FILE_TO_RECEIVED, false);
        bos = new BufferedOutputStream(fos);

        dis = new DataInputStream(sock.getInputStream());
        dos = new DataOutputStream(sock.getOutputStream());

    }

    public void closeOpened() throws IOException {
        if (fos != null) {
            fos.close();
        }
        if (bos != null) {
            bos.close();
        }
        if (sock != null) {
            sock.close();
        }

    }
}
