package br.com.candidatodebolso.api.persistence.repositories;

import br.com.candidatodebolso.api.persistence.model.user.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);
}
