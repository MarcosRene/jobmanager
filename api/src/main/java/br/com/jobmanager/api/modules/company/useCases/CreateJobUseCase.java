package br.com.jobmanager.api.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.expections.CompanyNotFoundException;
import br.com.jobmanager.api.modules.company.entites.JobEntity;
import br.com.jobmanager.api.modules.company.repositories.CompanyRepository;
import br.com.jobmanager.api.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {
  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private CompanyRepository companyRepository;
  
  public JobEntity execute(JobEntity jobEntity) {
    companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
      throw new CompanyNotFoundException();
    });
    return this.jobRepository.save(jobEntity);
  }
} 
