package server.server.controller;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import server.server.services.FileUploadService;
import server.server.services.S3Service;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class FileUploadController {

    @Autowired
    private S3Service S3Svc;

    @Autowired
    private FileUploadService fileUploadService;

    private static final String BASE64_PREFIX = "data:image/png;base64";

    //upload image
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@RequestPart MultipartFile file, @RequestPart String title, @RequestPart String complain){
        String key = "";

        try {
            key = S3Svc.upload(file, title, complain);
            this.fileUploadService.upload(file, title, complain);
        } catch (IOException | SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

        JsonObject payload = Json.createObjectBuilder()
                .add("imageKey", key)
                .build();

        return ResponseEntity.ok(payload.toString());
    }
}
