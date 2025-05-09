package src.ToyORB.Commons;

public abstract class Address {
    public abstract String getDest();
    public abstract int getPort();

    public String toString(){
        return "Address " + getDest() + ":" + getPort();
    }
}
