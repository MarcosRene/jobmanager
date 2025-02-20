package br.com.jobmanager.api.modules.company.controllers;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jobmanager.api.modules.company.dto.AuthCompanyDTO;
import br.com.jobmanager.api.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthCompanyController {
  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/companies")
  public ResponseEntity<Object> auth(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    try {
      var result = this.authCompanyUseCase.execute(authCompanyDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception exception) { 
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }   
  }
}
