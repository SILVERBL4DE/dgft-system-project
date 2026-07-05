package com.htc.dgft.security;

import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("ormSecurity")
@RequiredArgsConstructor
public class OrmSecurity {

    private final DgftOrmMasterRepository ormMasterRepository;

    public boolean canModify(String ormId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return true;
        }

        DgftOrmMaster orm = ormMasterRepository.findById(ormId).orElse(null);
        if (orm == null) {
            return false;
        }

        String currentUsername = authentication.getName();
        return currentUsername.equals(orm.getAddedBy());
    }
}
