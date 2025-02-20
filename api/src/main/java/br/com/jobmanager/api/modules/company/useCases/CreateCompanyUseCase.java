package br.com.jobmanager.api.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.expections.UserFoundExpection;
import br.com.jobmanager.api.modules.company.entites.CompanyEntity;
import br.com.jobmanager.api.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository
      .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
      .ifPresent((user) -> {
        throw new UserFoundExpection();
      });
    
    return this.companyRepository.save(companyEntity);
  }
}
