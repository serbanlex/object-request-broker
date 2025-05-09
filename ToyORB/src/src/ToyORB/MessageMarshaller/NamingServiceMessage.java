package src.ToyORB.MessageMarshaller;
import src.ToyORB.Commons.ConcreteAddress;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NamingServiceMessage implements Message {
    public String operation;
    public String name;
    public ConcreteAddress address;

    @JsonCreator
    public NamingServiceMessage(
            @JsonProperty("operation") String operation,
            @JsonProperty("name") String name,
            @JsonProperty("address") ConcreteAddress address) {
        this.operation = operation;
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "NamingServiceMessage{" +
                "operation='" + operation + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
