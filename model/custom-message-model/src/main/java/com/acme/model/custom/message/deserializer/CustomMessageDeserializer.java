package com.acme.model.custom.message.deserializer;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.acme.model.custom.message.entity.CustomMessage;
import com.acme.model.custom.message.factory.CustomMessageFactory;

public class CustomMessageDeserializer implements Deserializer<CustomMessage>{
	
	private String ENCODING_UTF8 = "UTF8";

	@Override
	public CustomMessage deserialize(String topic, byte[] data) {
		try {
			
            if (data == null){
                System.out.println("Null recieved at deserialize");
                return null;
            }
            
            ByteBuffer buf = ByteBuffer.wrap(data);
            int id = buf.getInt();

            //Message
            int sizeOfMessage = buf.getInt();
            byte[] messageBytes = new byte[sizeOfMessage];
            
            buf.get(messageBytes);
            String deserializedMessage = new String(messageBytes, ENCODING_UTF8);

            //Created Date
            int sizeOfCreatedDate = buf.getInt();
            byte[] createDateBytes = new byte[sizeOfCreatedDate];
            
            buf.get(createDateBytes);
            String createdDateString = new String(createDateBytes, ENCODING_UTF8);

            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

            return CustomMessageFactory.create(id,deserializedMessage,df.parse(createdDateString));



        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Supplier");
        }
	}

}
