//package com.service.utils;
//
//import com.service.model.response.SegmidImage;
//import com.service.model.response.SegmidImageResponse;
//import lombok.experimental.UtilityClass;
//import org.springframework.core.io.InputStreamResource;
//
//import java.io.ByteArrayInputStream;
//
//@UtilityClass
//public class MapperHelper {
//    public SegmidImageResponse mapSegmidImageToResponse(SegmidImage segmidImage) {
//        return new SegmidImageResponse(segmidImage.getDocId(),
//                new InputStreamResource(new ByteArrayInputStream(segmidImage.getImageBytes())),
//                segmidImage.getMetaData());
//    }
//}
