package br.com.candidatodebolso.api.endpoints;

import br.com.candidatodebolso.api.persistence.model.user.ApplicationUser;
import br.com.candidatodebolso.api.persistence.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class VoterEndpoint {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public VoterEndpoint(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @PostMapping("create-voter")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Validated ApplicationUser user) {
        validateVoter(user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        ApplicationUser save = applicationUserRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    private void validateVoter(ApplicationUser applicationUser) {
        if (applicationUser.getVoter() == null || applicationUser.getAdmin() != null) {
            throw new RuntimeException("Dados Invalidos");
        }
    }
}
