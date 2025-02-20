package br.com.jobmanager.api.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.candidate.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.useCases.CreateCandidateUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity); 
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }
}
