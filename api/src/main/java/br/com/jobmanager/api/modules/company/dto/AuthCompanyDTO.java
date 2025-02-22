package br.com.jobmanager.api.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
  @Schema(example = "jobmanager", requiredMode = RequiredMode.REQUIRED) 
  private  String username;

  @Schema(example = "12345678", requiredMode = RequiredMode.REQUIRED) 
  private  String password;
}
