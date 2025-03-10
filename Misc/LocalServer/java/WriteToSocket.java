import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class WriteToSocket {

    public static void main(String[] args) {
        int PORT = 0;
        try{
          PORT = Integer.parseInt(args[0]);
        }
        catch(java.lang.ArrayIndexOutOfBoundsException e){
          System.out.println("PORT not found");
          System.exit(0);
        }
        System.out.println("Listening on port " + PORT + "...");
        try (ServerSocket ss = new ServerSocket(PORT);
             Socket s = ss.accept()) {
            System.out.println("Remote connected on port " + PORT + "...");
            FileInputStream fis = new FileInputStream(new File("myInfo.txt"));
            OutputStream fos = s.getOutputStream();
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }
            fis.close();
            fos.close();
            System.out.println("File written to socket");
        } catch (IOException e) {
            System.out.println("Exception while opening port");
            e.printStackTrace();
        }
    }
}
