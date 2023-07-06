package server.server.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //setup value for app.properties environment variables
    @Value("${DO_STORAGE_KEY}")
    private String accessKey;

    @Value("${DO_STORAGE_SECRETKEY}")
    private String secretKey;

    @Value("${DO_STORAGE_ENDPOINT}")
    private String endPoint;

    @Value("${DO_STORAGE_ENDPOINT_REGION}")
    private String endPointRegion;

    @Bean
    public AmazonS3 createS3Client(){
        //instantiate new basic awsCredentials
        BasicAWSCredentials cred = new BasicAWSCredentials(accessKey, secretKey);

        AwsClientBuilder.EndpointConfiguration ep = new AwsClientBuilder.EndpointConfiguration(endPoint, endPointRegion);

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(ep)
                .withCredentials(new AWSStaticCredentialsProvider(cred))
                .build();
    }



}
