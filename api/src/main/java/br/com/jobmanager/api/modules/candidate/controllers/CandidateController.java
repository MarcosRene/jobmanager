package br.com.jobmanager.api.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.jobmanager.api.modules.candidate.entities.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.jobmanager.api.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.jobmanager.api.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.jobmanager.api.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.jobmanager.api.modules.company.entites.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/candidates")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;
  
  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;
 
  @Autowired
  private ApplyJobCandidateUseCase  applyJobCandidateUseCase; 

  @PostMapping("/")
  @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável cadastar um candidato")  
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = CandidateEntity.class))
    }),
    @ApiResponse(responseCode = "400", description = "Usuário já existe")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateUseCase.execute(candidateEntity); 
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
    }),
    @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var candidateId = request.getAttribute("candidate_id");
      
    try {
      var result = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString())); 
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Lista de vagas disponível para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis, baseado no filtro.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(
        array = @ArraySchema(
          schema = @Schema(implementation = JobEntity.class)
        )
      )
    })
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> findJobByFilter(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }

  @PostMapping("/jobs/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Inscrição de um candidato para uma vaga", description = "Essa função é responsável por realizar a inscrção do candidato em um vaga.") 
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID jobId) {
    var candidateId = request.getAttribute("candidate_id");

    try {
      var result = this.applyJobCandidateUseCase.execute(UUID.fromString(candidateId.toString()), jobId);
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }
}
