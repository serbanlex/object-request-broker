package src.Proxies;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.Marshaller;
import src.ToyORB.MessageMarshaller.ResponseMessage;
import src.ToyORB.MessageMarshaller.ServerMessage;
import src.ToyORB.RequestReply.Requestor;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class GenericClientProxy {
    Requestor client = new Requestor();
    Marshaller marshaller = new Marshaller();
    ConcreteAddress serverAddress;

    GenericClientProxy(ConcreteAddress serverAddress){
        if(serverAddress == null){
            System.out.println("For some reason the server address is null in the client proxy");
        }
        this.serverAddress = serverAddress;
    }

    protected ResponseMessage makeRequestToServer(String methodName, Serializable[] params) {
        ServerMessage request = new ServerMessage(methodName, params);
        byte[] requestTemp = marshaller.marshal(request);
        requestTemp = client.deliver_and_wait_feedback(serverAddress, requestTemp);
        if(requestTemp == null){
            System.out.println("Error from server while making request " + request);
            return null;
        }
        ResponseMessage responseMessage = (ResponseMessage) marshaller.unmarshal(requestTemp);
        if(responseMessage.response instanceof BigDecimal){
            responseMessage.response = ((BigDecimal)responseMessage.response).doubleValue();
        }
        return responseMessage;
    }
}
