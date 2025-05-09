package src.ToyORB.Commons;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcreteAddress extends Address implements Serializable {
    private final String dest;
    private final int port;

    @JsonCreator
    public ConcreteAddress(
            @JsonProperty("dest") String dest,
            @JsonProperty("port") int port) {
        this.dest = dest;
        this.port = port;
    }

    @Override
    public String getDest() {
        return dest;
    }

    @Override
    public int getPort() {
        return port;
    }
}
