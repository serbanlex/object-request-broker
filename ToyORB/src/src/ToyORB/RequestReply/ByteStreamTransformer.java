package src.ToyORB.RequestReply;

import java.lang.reflect.InvocationTargetException;

public interface ByteStreamTransformer
{
	public byte[] transform(byte[] in) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, ClassNotFoundException;
}