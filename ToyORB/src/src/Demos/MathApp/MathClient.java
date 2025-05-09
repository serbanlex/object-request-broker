package src.Demos.MathApp;

import src.ServerInterfaces.MathServer;
import src.ToyORB.ToyORB;

public class MathClient {
    public static void main(String[] args) {
        try{
            MathServer mathServer = (MathServer) ToyORB.getObjectReference("Math");
            System.out.println(mathServer.do_sqrt(49));
            System.out.println(mathServer.do_add(1, 2));
        }
        catch(Exception e){
            System.out.println("Error getting method result of object reference");
            e.printStackTrace();
        }
    }
}
