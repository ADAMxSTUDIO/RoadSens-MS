package ma.emsi.ingestionvideo.service;

import lombok.RequiredArgsConstructor;
import ma.emsi.ingestionvideo.dto.AuthRequest;
import ma.emsi.ingestionvideo.dto.AuthResponse;
import ma.emsi.ingestionvideo.dto.CreateReporterRequest;
import ma.emsi.ingestionvideo.dto.ReporterResponse;
import ma.emsi.ingestionvideo.entity.Reporter;
import ma.emsi.ingestionvideo.entity.ReporterUserDetails;
import ma.emsi.ingestionvideo.exception.DuplicateResourceException;
import ma.emsi.ingestionvideo.exception.ResourceNotFoundException;
import ma.emsi.ingestionvideo.exception.UserNotFoundException;
import ma.emsi.ingestionvideo.repository.ReporterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ReporterRepository reporterRepository;
    private final PasswordEncoder  passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(CreateReporterRequest request) {

        if (reporterRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(String.format("This email %s is already in use", request.getEmail()));
        }

        Reporter reporter = new  Reporter();
        reporter.setFirstName(request.getFirstName());
        reporter.setLastName(request.getLastName());
        reporter.setPhone(request.getPhone());
        reporter.setEmail(request.getEmail());
        reporter.setPassword(passwordEncoder.encode(request.getPassword()));
        // set role

        Reporter savedReporter = reporterRepository.save(reporter);

        ReporterResponse reporterResponse = ReporterResponse.fromEntity(savedReporter);
        String token = jwtService.generateToken(new ReporterUserDetails(savedReporter));

        return new AuthResponse(token, reporterResponse);
    }

    public AuthResponse login(AuthRequest request) {

        Reporter reporter = reporterRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", request.getEmail())));

        if (!passwordEncoder.matches(request.getPassword(), reporter.getPassword())) {
            throw new ResourceNotFoundException(String.format("Wrong password %s", request.getPassword()));
        }

        ReporterUserDetails reporterUserDetails = new ReporterUserDetails(reporter);
        String token = jwtService.generateToken(reporterUserDetails);
        ReporterResponse reporterResponse = ReporterResponse.fromEntity(reporter);

        return new AuthResponse(token, reporterResponse);
    }
}
