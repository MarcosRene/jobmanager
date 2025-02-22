package br.com.jobmanager.api.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.modules.company.entites.JobEntity;
import br.com.jobmanager.api.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {
  @Autowired
  private JobRepository jobRepository;

  public List<JobEntity> execute(String filter) {
    return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
  }
}
