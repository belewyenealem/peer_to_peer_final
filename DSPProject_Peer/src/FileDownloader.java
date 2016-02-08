import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
public class FileDownloader extends Thread {
    public static final int maxChunkDownload = 5;
    ChunksDownloader[] downloadchunkslist;
    PeerOperations tcpclient1;
    String filename, size;
    int numberofChunks;
    boolean downloadFinished;
    public int downloaded;
    public String locationDirectory;
    JTable jTableDownloads;
    int onTableRow;
    public FileDownloader(PeerOperations tcpclient1, JTable jTableDownloads, int onTableRow, String filename, String size) {
        downloadchunkslist = new ChunksDownloader[maxChunkDownload];
        downloadFinished = false;
        this.tcpclient1 = tcpclient1;
        this.filename = filename;
        this.size = size;
        numberofChunks = Integer.parseInt(size) / PeerOperations.Chunk_Size;
        this.jTableDownloads = jTableDownloads;
        this.onTableRow = onTableRow;
        locationDirectory = PeerOperations.downloads_location + filename.substring(0, filename.length() - 4);
        new File(locationDirectory).mkdir();
    }   
    @Override
    public void run() {
        System.out.println("Downloading File : '" + filename + "'");
        String[] downloadedList = new File(locationDirectory).list();
        if (downloadedList == null) {
            downloaded = 0;
        } else {
            downloaded = downloadedList.length - 1;
        }
        for (int i = 0; i < downloadchunkslist.length; i++) {
            downloadchunkslist[i] = new ChunksDownloader(this, filename, size);
            downloadchunkslist[i].start();
            try {
                downloadchunkslist[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (downloadFinished) {
            tcpclient1.constructFile(locationDirectory, filename, downloaded);
        }

    }

    public void updateStatus() {
        jTableDownloads.setValueAt(((downloaded * 100) / numberofChunks) + "%", onTableRow, 2);
        jTableDownloads.setValueAt(downloaded + "/" + numberofChunks, onTableRow, 3);
    }
}
