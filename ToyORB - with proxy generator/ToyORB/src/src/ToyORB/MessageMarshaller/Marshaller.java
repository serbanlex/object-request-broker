package src.ToyORB.MessageMarshaller;

import java.io.*;


public class Marshaller
{
	// the marshalled and unmarshalled messages are serializable, so we can use the ByteArrayInput/OutputStream
	public byte[] marshal(Message m) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		ObjectOutputStream output = null;

		try {
			output = new ObjectOutputStream(bytes);
			output.writeObject(m);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bytes.toByteArray();
	}

	public Message unmarshal(byte[] b) {
		ByteArrayInputStream bytes = new ByteArrayInputStream(b);
		ObjectInputStream input = null;
		Message m = null;
		try {
			input = new ObjectInputStream(bytes);
			m = (Message) input.readObject();
			input.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return m;
	}

}





