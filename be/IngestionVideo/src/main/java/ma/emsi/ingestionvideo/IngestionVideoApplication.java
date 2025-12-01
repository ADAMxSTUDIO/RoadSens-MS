package ma.emsi.ingestionvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class IngestionVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngestionVideoApplication.class, args);
    }

}
