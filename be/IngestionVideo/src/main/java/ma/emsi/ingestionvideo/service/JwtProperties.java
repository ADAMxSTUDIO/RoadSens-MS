package ma.emsi.ingestionvideo.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtProperties {
    private String secret;
    private long expirationMs;
}

