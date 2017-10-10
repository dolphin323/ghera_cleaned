import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class WriteToSocket{

  private static int PORT = 9004;

  public static void main(String[] args){
    try{
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("Listening on port " + PORT + "...");
        Socket s = ss.accept();
        System.out.println("Remote connected on port " + PORT + "...");
        FileInputStream fis = new FileInputStream(new File("myInfo.txt"));
        OutputStream fos = s.getOutputStream();
        int data;
        while((data = fis.read()) != -1){
            fos.write(data);
        }
        fis.close();
        fos.close();
        s.close();
        System.out.println("File written to socket");
    }
    catch(IOException e){
        System.out.println("Exception while opening port");
        e.printStackTrace();
    }
  }
}
