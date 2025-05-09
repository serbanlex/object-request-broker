package src.ToyORB;

import src.ToyORB.Commons.ConcreteAddress;
import src.ToyORB.MessageMarshaller.Marshaller;
import src.ToyORB.MessageMarshaller.NamingServiceMessage;
import src.ToyORB.RequestReply.ByteStreamTransformer;
import src.ToyORB.RequestReply.Replyer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NamingService implements ByteStreamTransformer {
	private static Map<String, ConcreteAddress> servers = new HashMap<>();
	private static NamingService _singletonInstance;
	public static final int runningPort = 8000;

	public static NamingService instance()
	{
		if (_singletonInstance == null)
			_singletonInstance = new NamingService();
		return _singletonInstance;
	}


	public synchronized static void register(String name, ConcreteAddress a) {
		servers.put(name, a);
		System.out.println(servers);
		System.out.println("INFO (Naming service @ port " + runningPort + "): Registered server " + name + " on address " + a);
	}

	public synchronized static ConcreteAddress getAddress(String name) {
		System.out.println(servers);
		System.out.println("INFO: address of server " + name + " was queried. Result: " + servers.get(name));
		return servers.get(name);
	}

	public byte[] transform(byte[] in) {
		Marshaller marshaller = new Marshaller();
		try{
			System.out.println("***" + marshaller.unmarshal(in));
			NamingServiceMessage receivedMessage = (NamingServiceMessage) marshaller.unmarshal(in);
			System.out.println("INFO: Got a message -> " + receivedMessage);

			ConcreteAddress address;

			if(receivedMessage.operation.equals("register")){
				register(receivedMessage.name, receivedMessage.address);
				return marshaller.marshal(receivedMessage);
			}

			else if(receivedMessage.operation.equals("getAddress")){
				address = getAddress(receivedMessage.name);
				if (address != null) {
					receivedMessage.address = address;
				}
				System.out.println("***" + receivedMessage);
				return marshaller.marshal(receivedMessage);
			}

			System.out.println("Received unknown message on naming service: " + receivedMessage + ". Doing nothing.");
			return null;
		}
		catch(Exception e){
			System.out.println("Couldn't transform given bytes: " + Arrays.toString(in));
			e.printStackTrace();
			return "422".getBytes();
		}
	}

	public static void main(String[] args)
	{
		NamingService namingService = new NamingService();
		Replyer replyer = new Replyer(new ConcreteAddress("localhost", runningPort));
		System.out.println("INFO: NamingService started on port 8000.");
		while (true) {
			replyer.receive_transform_and_send_feedback(namingService);
		}

	}

}
