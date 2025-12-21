package ma.emsi.ingestionvideo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private ReporterResponse reporter;
}
