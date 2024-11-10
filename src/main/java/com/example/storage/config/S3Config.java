package main.java.com.example.storage.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey = "YAKIA5FTZCOYEPXCJOAUI";  

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey = "g9Sc87kF2BI3iWm5np1NFhe/Pen88Zv8ySULP5kp";  

    @Value("${cloud.aws.region.static}")
    private String region = "use1-az5"; 

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName = "my-storage-bucket--use1-az5--x-s3";  
    @Bean
    public AmazonS3 amazonS3() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion(region)  
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public String s3BucketName() {
        return bucketName;
    }
}
