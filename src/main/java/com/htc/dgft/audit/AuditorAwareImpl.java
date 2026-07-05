package com.htc.dgft.audit;

import com.htc.dgft.security.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(authenticatedUserService.getCurrentUsername());
    }
}
