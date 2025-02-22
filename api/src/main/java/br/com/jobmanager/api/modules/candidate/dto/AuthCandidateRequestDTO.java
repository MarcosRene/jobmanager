package br.com.jobmanager.api.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AuthCandidateRequestDTO(
  @Schema(example = "marcosdavi", requiredMode = RequiredMode.REQUIRED) String username,
  @Schema(example = "12345678", requiredMode = RequiredMode.REQUIRED) String password
) {}
