package server.server.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${DO_STORAGE_BUCKETNAME}")
    private String bucketName;

    public String upload(MultipartFile file, String title, String complain) throws IOException {
        //creating a hashmap of user data details and file details
        Map<String, String> userData = new HashMap<>();
        userData.put("name", "Ken");
        userData.put("uploadDateTime", LocalDateTime.now().toString());
        userData.put("originalFileName", file.getOriginalFilename());
        userData.put("title", title);
        userData.put("complain", complain);

        //setting the metadata of object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        //check for original file name
        System.out.println(">>>>>> Original File Name: " + file.getOriginalFilename());

        //original method logic: NOT ENCOURAGED by Java docs
            //Sub-token is divided according to the delimiter "."
            //Logic for checking:
                //if there are more tokens, fileNameExt = tk.nextToken();
                //e.g. this helps in checking for file extension name
        String fileName = file.getOriginalFilename();

        //split the filename according to xxxx.yyyy.zzz -> [xxx, yyyy, zzz]
        assert fileName != null;
        String[] fileNameArray = fileName.split("\\.");

        int index = 0;
        String fileNameExt = "";


        //extension will be the last element of array
        fileNameExt = fileNameArray[fileNameArray.length - 1];

        if (fileNameExt.equals("blob")) {
            fileNameExt = fileNameExt + ".png";
        }

        //key for generating file name
        String key = UUID.randomUUID().toString().substring(0, 8);

        //put the request using amazon s3 client
        PutObjectRequest putRequest = new PutObjectRequest(bucketName, "myobject%s.%s".formatted(key, fileNameExt),
                file.getInputStream(), metadata);

        putRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3Client.putObject(putRequest);

        return "myobject%s.%s".formatted(key, fileNameExt);
    }

}
