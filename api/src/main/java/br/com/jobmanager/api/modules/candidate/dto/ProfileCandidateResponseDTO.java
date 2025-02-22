package br.com.jobmanager.api.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {
  private UUID id;

  @Schema(example = "marina")
  private String username;
  
  @Schema(example = "marina@gmail.com")
  private String email;
  
  @Schema(example = "Marina de Lima")
  private String name;
  
  @Schema(example = "Desenvolvedora Java")
  private String description;
}
