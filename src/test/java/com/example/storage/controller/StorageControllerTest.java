package test.java.com.example.storage.controller;

import com.example.storage.service.S3StorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StorageController.class)
public class StorageControllerTest {

    @MockBean
    private S3StorageService s3StorageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSearchFiles() throws Exception {
        when(s3StorageService.searchFiles("sandy", "logistics"))
                .thenReturn(Collections.singletonList("sandy/logistics_report.txt"));

        mockMvc.perform(get("/api/storage/search")
                .param("userName", "sandy")
                .param("searchTerm", "logistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("sandy/logistics_report.txt"));
    }
}
