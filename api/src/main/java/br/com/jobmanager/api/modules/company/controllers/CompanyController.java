package br.com.jobmanager.api.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.candidate.CandidateEntity;
import br.com.jobmanager.api.modules.company.entites.CompanyEntity;
import br.com.jobmanager.api.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {
  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @PostMapping("/")
  @Tag(name = "Empresa", description = "Informações da empresa")
  @Operation(summary = "Cadastro de uma empresa", description = "Essa função é responsável cadastar uma empresa.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", content = {
      @Content(schema = @Schema(implementation = CompanyEntity.class))
    }),
    @ApiResponse(responseCode = "400", description = "Usuário já existe")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
    try {
      var result = this.createCompanyUseCase.execute(companyEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(exception.getMessage());
    }
  }
}
