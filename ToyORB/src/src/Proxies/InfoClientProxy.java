package src.Proxies;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.ResponseMessage;
import src.ServerInterfaces.InfoServer;

import java.io.Serializable;

public class InfoClientProxy extends GenericClientProxy implements InfoServer {
    public InfoClientProxy(ConcreteAddress serverAddress){
        super(serverAddress);
    }

    @Override
    public String getTimisoaraTemp(

	) {
        ResponseMessage serverResponse = makeRequestToServer("getTimisoaraTemp", new Serializable[] {});
        return (String) serverResponse.response;
    }

}
