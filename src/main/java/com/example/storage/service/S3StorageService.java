package main.java.com.example.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3StorageService {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public S3StorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public List<String> searchFiles(String userName, String searchTerm) {
        String userFolder = userName + "/";
        ObjectListing objectListing = s3Client.listObjects(bucketName, userFolder);

        return objectListing.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .filter(key -> key.contains(searchTerm))
                .collect(Collectors.toList());
    }

    public S3Object downloadFile(String userName, String fileName) {
        String filePath = userName + "/" + fileName;
        return s3Client.getObject(bucketName, filePath);
    }

    public void uploadFile(String userName, String fileName, InputStream inputStream) {
        String filePath = userName + "/" + fileName;
        s3Client.putObject(bucketName, filePath, inputStream, null);
    }
}
