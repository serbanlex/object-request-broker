package src.ToyORB.MessageMarshaller;

import java.io.Serializable;

public class ResponseMessage implements Message{
    public Serializable response;

    public ResponseMessage(Serializable response)
    {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "response='" + response + '\'' +
                '}';
    }
}
