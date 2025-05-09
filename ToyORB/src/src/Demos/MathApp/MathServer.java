package src.Demos.MathApp;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.ToyORB;

public class MathServer {
    public static void main(String[] args){
        try{
            ToyORB.register("Math", new ConcreteAddress("127.0.0.1", 1111));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
