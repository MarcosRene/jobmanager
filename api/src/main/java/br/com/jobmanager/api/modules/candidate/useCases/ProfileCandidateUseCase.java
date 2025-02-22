package br.com.jobmanager.api.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.expections.UserNotFoundException;
import br.com.jobmanager.api.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.jobmanager.api.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID candidateId) {
    var candidate = this.candidateRepository.findById(candidateId)
      .orElseThrow(() -> {
        throw new UserNotFoundException();
      });

    var candidateDTO  = ProfileCandidateResponseDTO.builder()
      .id(candidate.getId())
      .username(candidate.getUsername())
      .email(candidate.getEmail())
      .name(candidate.getName())
      .description(candidate.getDescription())
      .build();

    return candidateDTO;
  } 
}
