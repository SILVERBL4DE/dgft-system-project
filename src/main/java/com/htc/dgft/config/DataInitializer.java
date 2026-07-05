package com.htc.dgft.config;

import com.htc.dgft.entity.Role;
import com.htc.dgft.entity.User;
import com.htc.dgft.entity.DgftPurposeCodeMaster;
import com.htc.dgft.entity.DgftOrmMaster;
import com.htc.dgft.enums.RoleName;
import com.htc.dgft.repository.RoleRepository;
import com.htc.dgft.repository.UserRepository;
import com.htc.dgft.repository.DgftPurposeCodeMasterRepository;
import com.htc.dgft.repository.DgftOrmMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final DgftPurposeCodeMasterRepository purposeCodeRepository;
    private final DgftOrmMasterRepository ormMasterRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedRoles();
        seedUsers();
        seedPurposeCodes();
        seedDummyOrms();
    }

    private void seedRoles() {
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

    private void seedUsers() {
        createUser("admin", "admin123", "Admin User", "admin@dgft.com", RoleName.ADMIN);
        createUser("maker", "maker123", "Maker User", "maker@dgft.com", RoleName.MAKER);
        createUser("checker", "checker123", "Checker User", "checker@dgft.com", RoleName.CHECKER);
        createUser("viewer", "viewer123", "Viewer User", "viewer@dgft.com", RoleName.VIEWER);
    }

    private void createUser(String username, String password, String name, String email, RoleName roleName) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setEmail(email);

            Role role = roleRepository.findByName(roleName).orElseThrow();
            user.getRoles().add(role);

            userRepository.save(user);
        }
    }

    private void seedPurposeCodes() {
        if (purposeCodeRepository.count() == 0) {
            createPurposeCode("P0104", "Export Trade", "ACTIVE");
            createPurposeCode("P0807", "Software Services", "ACTIVE");
            createPurposeCode("S0001", "Import Payments", "ACTIVE");
        }
    }

    private void createPurposeCode(String code, String description, String status) {
        DgftPurposeCodeMaster p = new DgftPurposeCodeMaster();
        p.setCode(code);
        p.setDescription(description);
        p.setStatus(status);
        purposeCodeRepository.save(p);
    }

    private void seedDummyOrms() {
        if (ormMasterRepository.count() == 0) {
            for (int i = 1; i <= 25; i++) {
                DgftOrmMaster orm = new DgftOrmMaster();
                orm.setOrmNumber("ORM" + String.format("%05d", i));
                orm.setOrmAmount(new BigDecimal("1000.00").add(new BigDecimal(i * 10)));
                orm.setOrmDate(LocalDate.now());
                orm.setOrmIssueDate(LocalDate.now());
                orm.setAdCode("1234567");
                orm.setOrmCurrency("USD");
                orm.setIeCode("IE00012345");
                orm.setBeneficiaryName("Global Corp " + i);
                orm.setBeneficiaryCountry("USA");
                orm.setPurposeCode(i % 2 == 0 ? "P0104" : "P0807");
                orm.setIfscCode("HDFC0001234");
                orm.setInrPayableAmount(new BigDecimal("82000.00").add(new BigDecimal(i * 500)));
                
                orm.setStatus("ACTIVE");
                orm.setFlag("N");
                orm.setDgftFlag("F");
                orm.setDgftStatus("Awaiting request initiated");
                
                orm.setAddedBy("maker");
                orm.setAddedDate(LocalDate.now().atStartOfDay());
                
                ormMasterRepository.save(orm);
            }
        }
    }
}
