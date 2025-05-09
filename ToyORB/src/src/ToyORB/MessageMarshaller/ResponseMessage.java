package src.ToyORB.MessageMarshaller;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ResponseMessage implements Message {
    public Serializable response;

    @JsonCreator
    public ResponseMessage(@JsonProperty("response") Serializable response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "response='" + response + '\'' +
                '}';
    }
}
