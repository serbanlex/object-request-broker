package src.ToyORB.MessageMarshaller;
import java.io.Serializable;
import java.util.Arrays;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerMessage implements Message {
    public String methodName;
    public Serializable[] parameters;

    @JsonCreator
    public ServerMessage(
            @JsonProperty("methodName") String methodName,
            @JsonProperty("parameters") Serializable[] parameters) {
        this.methodName = methodName;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
