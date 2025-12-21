package ma.emsi.ingestionvideo.service;

import lombok.RequiredArgsConstructor;
import ma.emsi.ingestionvideo.entity.Reporter;
import ma.emsi.ingestionvideo.entity.ReporterUserDetails;
import ma.emsi.ingestionvideo.exception.UserNotFoundException;
import ma.emsi.ingestionvideo.repository.ReporterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReporterDetailsService implements UserDetailsService {

    private final ReporterRepository reporterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Reporter reporter = reporterRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("Reporter not found with email %s", email)));
        return new ReporterUserDetails(reporter);
    }
}
