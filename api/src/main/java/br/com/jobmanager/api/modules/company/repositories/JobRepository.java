package br.com.jobmanager.api.modules.company.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jobmanager.api.modules.company.entites.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
  List<JobEntity> findByDescriptionContainingIgnoreCase(String title);
}
