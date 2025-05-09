package src.ToyORB.MessageMarshaller;

import src.ToyORB.Commons.ConcreteAddress;

public class NamingServiceMessage implements Message
{
    public String operation;
    public String name;
    public ConcreteAddress address;

    public NamingServiceMessage(String operation, String name, ConcreteAddress address) {
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