package src.Proxies;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.ResponseMessage;
import src.ServerInterfaces.MathServer;

import java.io.Serializable;

public class MathClientProxy extends GenericClientProxy implements MathServer {
    public MathClientProxy(ConcreteAddress serverAddress){
        super(serverAddress);
    }

    @Override
    public double do_sqrt(
		double arg0
	) {
        ResponseMessage serverResponse = makeRequestToServer("do_sqrt", new Serializable[] {arg0});
        return (double) serverResponse.response;
    }

    @Override
    public float do_add(
		float arg0, float arg1
	) {
        ResponseMessage serverResponse = makeRequestToServer("do_add", new Serializable[] {arg0, arg1});
        return (float) serverResponse.response;
    }

}
