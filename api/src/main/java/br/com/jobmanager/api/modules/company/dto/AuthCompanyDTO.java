package br.com.jobmanager.api.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
 private  String username;
 private  String password;
}
