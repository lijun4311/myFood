package com.mhs66.consts;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:system.properties")
public class FileUploadConsts {
    @Getter
    private static String imageUserFaceLocation;
    @Getter
    private static String imageServerUrl;


    public void setImageUserFaceLocation(String imageUserFaceLocation) {
        FileUploadConsts.imageUserFaceLocation = imageUserFaceLocation;
    }

    public void setImageServerUrl(String imageServerUrl) {
        FileUploadConsts.imageServerUrl = imageServerUrl;
    }
}
