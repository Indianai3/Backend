//package com.service.utils;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import org.springframework.core.io.InputStreamResource;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//public class InputStreamResourceSerializer extends JsonSerializer<InputStreamResource> {
//
//    // todo: clean this class
//    @Override
//    public void serialize(InputStreamResource inputStreamResource, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        InputStream inputStream = inputStreamResource.getInputStream();
//        // Read the InputStream and convert it to a byte array or other suitable representation
//        // For simplicity, let's assume you have a method to convert InputStream to byte[]
//        byte[] byteArray = readInputStream(inputStream);
//
//        // Serialize the byte array instead of InputStreamResource
//        jsonGenerator.writeBinary(byteArray);
//    }
//
//    private byte[] readInputStream(InputStream inputStream) throws IOException {
//        // Implement a method to read the InputStream and convert it to a byte array
//        // This is just a placeholder, you need to implement the logic based on your requirements
//        // Example: Using Apache Commons IO library
//        return org.apache.commons.io.IOUtils.toByteArray(inputStream);
//    }
//}
