package src.ToyORB.Commons;

public abstract class Address {
    public abstract String dest();
    public abstract int port();

    public String toString(){
        return "Address " + dest() + ":" + port();
    }
}
