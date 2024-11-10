package com.example.storage;

import com.example.storage.controller.StorageController;
import com.example.storage.service.S3StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StorageApplicationTest {

    @Autowired
    private StorageController storageController;

    @Autowired
    private S3StorageService s3StorageService;

    @Test
    public void contextLoads() {
        // Test to ensure the main components are correctly loaded
        assertThat(storageController).isNotNull();
        assertThat(s3StorageService).isNotNull();
    }
}
