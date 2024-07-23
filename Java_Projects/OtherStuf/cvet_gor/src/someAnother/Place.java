/*package someAnother;

public class Place extends Thread {
    public static void main(String[] args){
        ServerLab se = new ServerLab();
        new Thread(){
          @Override
          public void run() {
              se.receiveUDP();
          }
        }.start();
        ClientLab cl = new ClientLab();
        new Thread(){
            @Override
            public void run(){
                cl.sendUDP();
            }
        }.start();
    }
}*/
