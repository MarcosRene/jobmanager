package br.com.jobmanager.api.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.expections.UserFoundExpection;
import br.com.jobmanager.api.modules.candidate.entities.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity candidateEntity) {
    this.candidateRepository
      .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new UserFoundExpection();
      });

    var passwordHash = this.passwordEncoder.encode(candidateEntity.getPassword());
    candidateEntity.setPassword(passwordHash);

    return this.candidateRepository.save(candidateEntity);
  }
}
