package src.Demos.SimpleClientServerApp;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.ToyORB;

public class InfoServer {
    public static void main(String[] args){
        try{
            ToyORB.register("Info", new ConcreteAddress("127.0.0.1", 1111));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
