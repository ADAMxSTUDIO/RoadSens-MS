package ma.emsi.ingestionvideo.dto;

import lombok.Data;
import ma.emsi.ingestionvideo.entity.Reporter;

@Data
public class ReporterResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public static ReporterResponse fromEntity(Reporter reporter){
        ReporterResponse response = new ReporterResponse();
        response.setId(reporter.getId());
        response.setFirstName(reporter.getFirstName());
        response.setLastName(reporter.getLastName());
        response.setPhone(reporter.getPhone());
        response.setEmail(reporter.getEmail());
        return response;
    }
}
