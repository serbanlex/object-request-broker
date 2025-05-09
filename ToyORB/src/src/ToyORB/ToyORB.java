package src.ToyORB;

import src.ToyORB.Commons.Address;
import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.Marshaller;
import src.ToyORB.MessageMarshaller.NamingServiceMessage;
import src.Proxies.GenericServerProxy;
import src.ToyORB.RequestReply.Requestor;

import java.lang.reflect.Constructor;

public class ToyORB {
    private static final Address NamingServiceAddress = new ConcreteAddress("localhost", 8000);
    private static final Requestor requestor = new Requestor();
    private static final Marshaller marshaller = new Marshaller();

    public static void register(String name, ConcreteAddress serverAddress) {
        NamingServiceMessage registerServerMessage = new NamingServiceMessage(
                "register", name, serverAddress
        );
        sendNameServiceRequest(registerServerMessage);
        GenericServerProxy proxy = new GenericServerProxy(name);
        proxy.run();
    }

    private static NamingServiceMessage sendNameServiceRequest(NamingServiceMessage message) {
        byte[] answer = requestor.deliver_and_wait_feedback(
                NamingServiceAddress,
                marshaller.marshal(message)
        );
        if(answer == null){
            System.out.println("Error at server registration to name service for +" + message.name + "\n");
            return null;
        }
        return (NamingServiceMessage)marshaller.unmarshal(answer);
    }

    public static Object getObjectReference(String name) {
        NamingServiceMessage getServerAddressResponse = getServerAddress(name);
        if (getServerAddressResponse == null) return null;

        String clientProxyClassName = "src.Proxies." + getServerAddressResponse.name + "ClientProxy";

        Class<?> clientProxyClass;
        Object clientProxyReference = null;
        try {
            clientProxyClass = Class.forName(clientProxyClassName);
            System.out.println(clientProxyClass);
            Constructor<?> constructor = clientProxyClass.getConstructor(ConcreteAddress.class);
            clientProxyReference = constructor.newInstance(getServerAddressResponse.address);
        } catch (ClassNotFoundException e) {
            System.out.println("Could create the client proxy class");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientProxyReference;
    }

    private static NamingServiceMessage getServerAddress(String name) {
        NamingServiceMessage getServerAddressRequest = new NamingServiceMessage(
                "getAddress", name, null
        );
        NamingServiceMessage getServerAddressResponse = sendNameServiceRequest(getServerAddressRequest);
        if(getServerAddressResponse == null){
            System.out.println("No response from name server");
            return null;
        }
        if (getServerAddressResponse.address == null){
            System.out.println("No server named '" + name + "' has been registered. No object reference to give.");
            return null;
        }
        return getServerAddressResponse;
    }


}