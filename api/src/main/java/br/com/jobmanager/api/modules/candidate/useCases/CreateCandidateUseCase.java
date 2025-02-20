package br.com.jobmanager.api.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.expections.UserFoundExpection;
import br.com.jobmanager.api.modules.candidate.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
      .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new UserFoundExpection();
      });

    return this.candidateRepository.save(candidateEntity);
  }
}
