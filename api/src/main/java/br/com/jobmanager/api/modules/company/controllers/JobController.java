package br.com.jobmanager.api.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.company.dto.CreateJobDTO;
import br.com.jobmanager.api.modules.company.entites.JobEntity;
import br.com.jobmanager.api.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/companies/jobs")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    var jobEntity = JobEntity.builder()
      .companyId(UUID.fromString(companyId.toString()))
      .benefits(createJobDTO.getBenefits())
      .description(createJobDTO.getDescription())
      .level(createJobDTO.getLevel())
      .build();

    return this.createJobUseCase.execute(jobEntity);
  }
}
