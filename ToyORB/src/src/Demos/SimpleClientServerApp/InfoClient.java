package src.Demos.SimpleClientServerApp;

import src.ToyORB.ToyORB;
import src.ServerInterfaces.InfoServer;

public class InfoClient {
    public static void main(String[] args) {
        try{
            InfoServer infoServer = (InfoServer) ToyORB.getObjectReference("Info");
            System.out.println(infoServer.getTimisoaraTemp());
        }
        catch(Exception e){
            System.out.println("Error getting method result of object reference");
            e.printStackTrace();
        }
    }
}
