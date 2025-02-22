package br.com.jobmanager.api.modules.company.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "company")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(example = "Jobmanager Company", requiredMode = RequiredMode.REQUIRED)
  private String name;

  @NotBlank()
  @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
  @Schema(example = "jobmanager", requiredMode = RequiredMode.REQUIRED)
  private String username;

  @Email(message = "O campo [email] deve conter um e-mail válido")
  @Schema(example = "jobmanager@gmail.com", requiredMode = RequiredMode.REQUIRED)
  private String email;

  @Length(min = 8, max = 100, message = "A senha deve conter entre (8) e (100) caracteres") 
  @Schema(example = "12345678", minLength = 8, maxLength = 100, requiredMode = RequiredMode.REQUIRED)  
  private String password;
  
  @Schema(example = "https://www.jobmanager.com.br")
  private String website;

  @Schema(example = "JobManager Company", requiredMode = RequiredMode.REQUIRED)
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
