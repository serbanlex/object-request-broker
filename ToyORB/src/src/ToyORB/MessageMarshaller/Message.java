package src.ToyORB.MessageMarshaller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NamingServiceMessage.class, name = "NamingServiceMessage"),
        @JsonSubTypes.Type(value = ResponseMessage.class, name = "ResponseMessage"),
        @JsonSubTypes.Type(value = ServerMessage.class, name = "ServerMessage")
})
public interface Message extends Serializable {
    String toString();
}