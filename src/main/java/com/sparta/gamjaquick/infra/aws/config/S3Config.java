package com.sparta.gamjaquick.infra.aws.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class S3Config {

    @Value("${aws.credentials.access-key}")
    private String accessKey;
    @Value("${aws.credentials.secret-key}")
    private String accessSecret;
    @Value("${aws.region.static}")
    private String region;
    @Value("${aws.s3.bucket}")
    private String bucket;
    @Value("${aws.s3.upload-path}")
    private String uploadPath;

    @Bean
    public AmazonS3Client amazonS3Client(){
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, accessSecret);
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region).enablePathStyleAccess()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
