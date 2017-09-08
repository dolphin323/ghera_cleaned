import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

class ReadFromSocket{

  private static String HOST = "10.131.179.85";
  private static int PORT = 9004;

  public static void main(String[] args){
    try{
        SocketAddress saddr = new InetSocketAddress(HOST,PORT);
        Socket s = new Socket();
        s.connect(saddr);
        if(s.isConnected()){
            System.out.println("Connected to " + HOST + "at port " + PORT);
            InputStream is = s.getInputStream();
            int data;
            StringBuffer bf = new StringBuffer();
            while((data = is.read()) != -1){
                System.out.println("Result = " + (char)data);
                bf.append((char)data);
            }
            System.out.println("Final Result = " + bf.toString());
        }
        else System.out.println("Connected refused by " + HOST + "at port " + PORT);
    }
    catch(IOException e){
        System.out.println("error while connecting to socket endpoint...");
        e.printStackTrace();
    }
  }
}
