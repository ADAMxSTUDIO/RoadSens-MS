package ma.emsi.ingestionvideo.dto;

import lombok.Data;

@Data
public class CreateReporterRequest {
    private String firstName;
    private String lastName;
    private String phone;
}
