package ma.emsi.ingestionvideo.service;

import lombok.RequiredArgsConstructor;
import ma.emsi.ingestionvideo.dto.CreateReporterRequest;
import ma.emsi.ingestionvideo.dto.ReporterResponse;
import ma.emsi.ingestionvideo.entity.Reporter;
import ma.emsi.ingestionvideo.exception.DuplicateResourceException;
import ma.emsi.ingestionvideo.repository.ReporterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporterService {

    final ReporterRepository repository;

    public ReporterResponse createReporter(CreateReporterRequest request) {

        // Check whether the reporter already exists having an existing phone number
        if (repository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException(String.format("The reporter with same phone: %s alredy exists", request.getPhone()));
        }

        // Create a new reporter
        Reporter reporter = Reporter.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();

        // Persist the reporter in DB
        Reporter savedReporter = repository.save(reporter);

        // Cast the reporter into DTO for response
        return ReporterResponse.fromEntity(savedReporter);
    }
}
