package src.ToyORB.RequestReply;

import src.ToyORB.Commons.Address;
import src.ToyORB.Commons.ConcreteAddress;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

public class Replyer
{
	private ServerSocket serverSocket;
	private Socket socket;
	private InputStream inputStream;
	private OutputStream outputStream;
	private Address address;

	public Replyer(Address theAddr) {
		address = theAddr;
		try {
			serverSocket = new ServerSocket(address.getPort(), 1000);
			System.out.println("New replyer on server socket:" + serverSocket);
		} catch (Exception e) {
			System.out.println("Error opening server socket");
		}
	}


	public void receive_transform_and_send_feedback(ByteStreamTransformer t)
	{
		int messageLength;
		byte buffer[] = null;
		try
		{
			//accept the connection(blocking until a connection is made)
			socket = serverSocket.accept();
			System.out.println("Replyer accepted a connection from socket: "+ socket);

			inputStream = socket.getInputStream();

			byte len[] = new byte[4];
			inputStream.read(len);
			messageLength = ByteBuffer.wrap(len).getInt(); //make an int from those bytes

			//read the actual message
			buffer = new byte[messageLength];
			inputStream.read(buffer);

			//send the message to the ByteStreamTransformer for processing
			byte[] data = t.transform(buffer);

			//get output stream
			outputStream = socket.getOutputStream();

			//first 4 byte make the length of the message
			len = ByteBuffer.allocate(4).putInt(data.length).array();
			//write length
			outputStream.write(len);
			//write data
			outputStream.write(data);
			outputStream.flush();

			//close
			outputStream.close();
			inputStream.close();
			socket.close();

		}
		catch (IOException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("IOException in receive_transform_and_feedback");
			e.printStackTrace();
		}

	}

	protected void finalize() throws Throwable {
		super.finalize();
		serverSocket.close();
	}

	public Address getAddress() {
		return new ConcreteAddress(serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort());
	}
}