package src.Proxies;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.Marshaller;
import src.ToyORB.MessageMarshaller.NamingServiceMessage;
import src.ToyORB.MessageMarshaller.ResponseMessage;
import src.ToyORB.MessageMarshaller.ServerMessage;
import src.ToyORB.RequestReply.ByteStreamTransformer;
import src.ToyORB.RequestReply.Replyer;
import src.ToyORB.RequestReply.Requestor;
import src.ToyORB.NamingService;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


class ServerTransformer implements ByteStreamTransformer {
    private final MessageServer originalServer;

    public ServerTransformer(MessageServer s) {
        originalServer = s;
    }

    @Override
    public byte[] transform(byte[] in) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException {
        Marshaller marshaller = new Marshaller();
        ServerMessage request = (ServerMessage) marshaller.unmarshal(in);

        ResponseMessage answer = null;
        try {
            answer = originalServer.respond(request);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        byte[] bytes = marshaller.marshal(answer);
        return bytes;
    }
}


class MessageServer {
    String proxiedClassName;
    public MessageServer(String proxiedClassName) {
        this.proxiedClassName = proxiedClassName;
    }

    public ResponseMessage respond(ServerMessage request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        System.out.println("Server received request " + request);
        Object result = callMethodIssuedBy(request);
        ResponseMessage reply = new ResponseMessage((Serializable) result);
        System.out.println("Server replies with " + reply);
        return reply;
    }

    private Object callMethodIssuedBy(ServerMessage request) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> proxiedClass = Class.forName(this.proxiedClassName);
        Object proxiedInstance = proxiedClass.getDeclaredConstructor().newInstance();
        Object result;
        if (request.parameters == null) {
            Method method = proxiedClass.getMethod(request.methodName);
            result = method.invoke(proxiedInstance);
        } else {
            Method method = proxiedClass.getMethod(request.methodName, getParameterTypes(request.parameters)); // get the method with the given name and parameters
            result = method.invoke(proxiedInstance, request.parameters); // invoke the method on the instance with the given parameters
        }
        return result;
    }

    private Class<?>[] getParameterTypes(Serializable[] parameters) throws ClassNotFoundException {
        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> parameterType = Class.forName(parameters[i].getClass().getName());
            parameterType = getPrimitiveType(parameterType);
            parameterTypes[i] = parameterType; // get the parameter type for each parameter
        }
        return parameterTypes;
    }

    private Class<?> getPrimitiveType(Class<?> clazz) {
        Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVE_TYPE_MAP = new HashMap<Class<?>, Class<?>>() {{
            put(Boolean.class, boolean.class);
            put(Byte.class, byte.class);
            put(Character.class, char.class);
            put(Short.class, short.class);
            put(Integer.class, int.class);
            put(Long.class, long.class);
            put(Float.class, float.class);
            put(Double.class, double.class);
        }};
        return WRAPPER_TO_PRIMITIVE_TYPE_MAP.getOrDefault(clazz, clazz);
    }





}

public class GenericServerProxy {
    ByteStreamTransformer transformer;
    ConcreteAddress proxyAddress;
    Replyer proxy;
    protected final ConcreteAddress namingServiceAddress;

    public GenericServerProxy(String name) {
        namingServiceAddress = new ConcreteAddress("localhost", NamingService.runningPort);
        transformer = new ServerTransformer(new MessageServer("src.ServerImplementations." + name + "Server"));
        proxyAddress = getProxyAddress(name);
        proxy = new Replyer(proxyAddress);
    }


    public void run() {
        Thread t = new Thread(() -> {
            while (true) {
                proxy.receive_transform_and_send_feedback(transformer);
            }
        });
        t.start();
    }

    private ConcreteAddress getProxyAddress(String name) {
        Requestor requestor = new Requestor();
        Marshaller marshaller = new Marshaller();

        NamingServiceMessage serverAddressRequest = new NamingServiceMessage(
                "getAddress", name, null
        );

        byte[] nameServiceResponse = requestor.deliver_and_wait_feedback(
                namingServiceAddress,
                marshaller.marshal(serverAddressRequest)
        );
        return ((NamingServiceMessage) marshaller.unmarshal(nameServiceResponse)).address;
    }
}
