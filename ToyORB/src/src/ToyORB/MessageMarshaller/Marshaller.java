package src.ToyORB.MessageMarshaller;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

public class Marshaller {
	private final ObjectMapper objectMapper;

	public Marshaller() {
		this.objectMapper = new ObjectMapper();
	}


	public byte[] marshal(Message message) {
		try {
			return objectMapper.writeValueAsBytes(message);
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Message unmarshal(byte[] incomingBytes) {
		try {
			return objectMapper.readValue(incomingBytes, Message.class);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}




