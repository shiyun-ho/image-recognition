package server.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import server.server.repository.FileUploadRepository;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class FileUploadService {
    @Autowired
    private FileUploadRepository fileUploadRepo;

    public void upload(MultipartFile file, String title, String complain) throws SQLException, IOException {
        this.fileUploadRepo.upload(file, title, complain);
    }

}
