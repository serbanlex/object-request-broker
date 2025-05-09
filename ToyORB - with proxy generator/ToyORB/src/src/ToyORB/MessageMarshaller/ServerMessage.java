package src.ToyORB.MessageMarshaller;
import java.io.Serializable;
import java.util.Arrays;

public class ServerMessage implements Message
{
    public String methodName;
    public Serializable[] parameters;

    public ServerMessage(String methodName, Serializable[] parameters)
    {
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