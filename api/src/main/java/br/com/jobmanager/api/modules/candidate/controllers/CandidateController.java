package br.com.jobmanager.api.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.candidate.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.CandidateRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
  @Autowired
  private CandidateRepository candidateRepository;

  @PostMapping("/")
  public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity) {
    return this.candidateRepository.save(candidateEntity);
  }
}
