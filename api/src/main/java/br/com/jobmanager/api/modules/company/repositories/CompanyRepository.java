package br.com.jobmanager.api.modules.company.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jobmanager.api.modules.company.entites.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
  Optional<CompanyRepository> findByUsernameOrEmail(String username, String email);
}
