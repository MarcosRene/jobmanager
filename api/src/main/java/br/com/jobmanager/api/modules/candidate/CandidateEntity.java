package br.com.jobmanager.api.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {
  private UUID id;
  private String name;

  @Pattern(regexp = "^(?!\\s*$).+", message = "o campo [username] não deve conter espaço") 
  private String username;

  @Email(message = "o campo [email] deve conter um e-mail válido")
  private String email;

  @Length(min = 8)
  private String password;
  private String description;
  private String curriculum;
}
