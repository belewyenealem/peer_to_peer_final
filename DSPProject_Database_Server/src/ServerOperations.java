import java.sql.*;
import java.util.Random;
import java.util.logging.*;
public class ServerOperations {
    public final static int Chunk_Size = 1048576;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/dspproject?zeroDateTimeBehavior=convertToNull";
    static final String USER = "root";
    static final String PASS = "";
    public String table_name="`dspproject`.`chunk_list";
    public Connection conn = null;
    public Statement stmt = null;
    public String sql;
    public ServerOperations() {
        
    }
    public void CreateConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerOperations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CloseConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se2) {
        }// nothing we can do
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }
    public void createDBandTable() {
        sql = "CREATE DATABASE IF NOT EXISTS dspproject ;";
        CreateConnection();
        try {
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS "+table_name+" (\n"
                    + " id MEDIUMINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,\n"
                    + " ipaddress VARCHAR(255) NOT NULL,\n"
                    + "	port VARCHAR(255) NOT NULL,\n"
                    + "	filename VARCHAR(255) NOT NULL,\n"
                    + "	size VARCHAR(255) NOT NULL,\n"
                    + "	chunknumber VARCHAR(255) NOT NULL,\n"
                    + "	Unique(ipaddress,port,filename,size,chunknumber));";
            stmt.executeUpdate(sql);
            System.out.println(" table "+ table_name +"  created");
            
            
        } catch (SQLException ex) {
            System.out.println("Error on ServerOperations:createDBandTable");
            Logger.getLogger(ServerOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        CloseConnection();
    }
    public String RegisterFiles(String[] list,int fragmentNumber) {
        CreateConnection();
        String result = "";
        String[] row;
        sql = "";
        for (int i = 3; i < list.length; i++) {
            row = list[i].split(",,,");
            for (int j = 0; j < Integer.parseInt(row[1]) / Chunk_Size + 1; j++) {
                if(fragmentNumber%3==j%3){
                    sql = "INSERT INTO "+table_name+"(`ipaddress`, `port`, `filename`, `size`, `chunknumber`) VALUES ('" + list[1] + "', '" + list[2] + "', '" + row[0] + "', '" + row[1] + "', '" + j + "');";
                    try {
                      stmt.executeUpdate(sql);
                    } catch (SQLException ex) {
                     System.out.println("sql exception @ server operations RegisterFiles method");
                    }
                }
            }
            result += "'" + row[0] + "' (" + row[1] + " bytes ) is SuccessFully registered!\n";
        }
        CloseConnection();
        return result;
    }
    public String getListOfFiles() {
        CreateConnection();
        String result = "";
        ResultSet rs = null;
        sql = "select DISTINCT `filename`,`size` from "+table_name+" ORDER BY `filename`";
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String filename = rs.getString("filename");
                String size = rs.getString("size");
                result += filename + ",,," + size + "###";
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error on getting list of files(Server Operation:getlistoffiles()");
        }
        CloseConnection();
        return result;
    }
    public String leaveRequest(String ip, String port) {
        CreateConnection();
        String result = "";
        sql = "DELETE FROM "+table_name+" Where `ipaddress`= '" + ip + "' and `port`= '" + port + "'";
        //System.out.println(sql);
        try {
            stmt.executeUpdate(sql);
            result = "Leave Request Success! ";
        } catch (SQLException ex) {
            System.out.println("Error on ServerOperation:leaveRequest()");
        }
        CloseConnection();
        return result;
    }
    public String chunkRegister(String[] list) {
        CreateConnection();
        String result = "";
        String[] row;
        sql = "";
        for (int i = 3; i < list.length; i++) {
            row = list[i].split(",,,");
            sql = "INSERT INTO "+table_name+"( `ipaddress`, `port`, `filename`, `size`, `chunknumber`) VALUES ('" + list[1] + "', '" + list[2] + "', '" + row[0] + "', '" + row[1] + "', '" + row[2] + "');";
            try {
                stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("sql exception @ server operations chunkRegister method");
            }
            result += "'" + row[0] + "' (" + row[1] + " bytes ) is SuccessFully registered!\n";
        }
        CloseConnection();
        return result;
    }
    public String chunkRequest(String filename, String size,String chunkNo) {
        CreateConnection();
        String result = "";
        String current = "";
        String[] Chunklist;
        ResultSet rs = null;
        sql = "select * from "+table_name+" Where `filename`= '" + filename + "' and `size`= '" + size+ "' and `chunknumber`= '" + chunkNo+ "'";
        System.out.println("chunk Request");
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                current = rs.getString("filename") + ",,," + rs.getString("size") + ",,," + rs.getString("ipaddress") + ",,," + rs.getString("port") + ",,," + rs.getString("chunknumber") + "###";
                System.out.println(current);
                result += current;
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Error on ServerOperation:getFileLocation()");
        }
        Chunklist = result.split("###");
        CloseConnection();
        return Chunklist[new Random().nextInt(Chunklist.length)];
    }
}
