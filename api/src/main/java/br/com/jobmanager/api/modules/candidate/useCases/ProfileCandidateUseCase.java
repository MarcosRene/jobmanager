package br.com.jobmanager.api.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jobmanager.api.modules.candidate.CandidateRepository;
import br.com.jobmanager.api.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {
  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
      .orElseThrow(() -> {
        throw new UsernameNotFoundException("Usuário não encontrado");
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
