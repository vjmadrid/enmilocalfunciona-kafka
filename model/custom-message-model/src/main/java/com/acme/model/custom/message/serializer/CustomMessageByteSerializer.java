package com.acme.model.custom.message.serializer;

import java.nio.ByteBuffer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.acme.model.custom.message.entity.CustomMessage;

public class CustomMessageByteSerializer implements Serializer<CustomMessage> {
	
	private String ENCODING_UTF8 = "UTF8";

	private final byte[] intToByteArray(int value) {
	    return new byte[] {
	            (byte)(value >>> 24),
	            (byte)(value >>> 16),
	            (byte)(value >>> 8),
	            (byte)value};
	}
	
	@Override
	public byte[] serialize(String topic, CustomMessage data) {
		
        byte[] serializedIdentifier;
        byte[] serializedMessage;
        byte[] serializedCreatedDate;
        
		int sizeOfIndentifier;
        int sizeOfMessage;
        int sizeOfCreatedDate;
        

        try {
        	
            if (data == null)
                return null;
            
            serializedIdentifier = intToByteArray(data.getId());
            sizeOfIndentifier = serializedIdentifier.length;
            
            serializedMessage = data.getMessage().getBytes(ENCODING_UTF8);
            sizeOfMessage = serializedMessage.length;
            
            serializedCreatedDate = data.getCreatedDate().toString().getBytes(ENCODING_UTF8);
            sizeOfCreatedDate = serializedCreatedDate.length;

            ByteBuffer buf = ByteBuffer.allocate(4+4+sizeOfMessage+4+sizeOfCreatedDate);
            buf.putInt(data.getId());
            buf.putInt(sizeOfMessage);
            buf.put(serializedMessage);
            buf.putInt(sizeOfCreatedDate);
            buf.put(serializedCreatedDate);


            return buf.array();

        } catch (Exception e) {
            throw new SerializationException("Error when serializing CustomMessage to byte[]");
        }
	}

}
