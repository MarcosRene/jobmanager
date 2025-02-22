package br.com.jobmanager.api.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.jobmanager.api.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jobmanager.api.modules.candidate.useCases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/candidates")
@Tag(name = "Candidato", description = "Informações do candidato")
public class AuthCandidateController {
  @Autowired
  private AuthCandidateUseCase authCandidateUseCase;

  @PostMapping("/auth")
  @Operation(summary = "Autenticação do candidato", description = "Essa função é responsável por autenticar o cantidado.")  
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
    }),
    @ApiResponse(responseCode = "400", description = "O campo [username/password] está incorreto")
  })
  public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
    try { 
      var token = this.authCandidateUseCase.execute(authCandidateRequestDTO);
      return ResponseEntity.ok().body(token);
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
  }
}
