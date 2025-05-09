package src.ToyORB.Commons;

import java.io.Serializable;

public class ConcreteAddress extends Address implements Serializable {

    private String dest;
    private int port;

    public ConcreteAddress(String dest, int port) {
        this.dest = dest;
        this.port = port;
    }

    @Override
    public String dest() {
        return dest;
    }

    @Override
    public int port() {
        return port;
    }

}