package ma.emsi.ingestionvideo.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class ReporterUserDetails implements UserDetails {

    private final Reporter reporter;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return reporter.getPassword();
    }

    @Override
    public String getUsername() {
        return reporter.getEmail(); // username = email
    }

    public Long getId() {
        return reporter.getId();
    }
}
