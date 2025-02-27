package br.com.jobmanager.api.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jobmanager.api.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.jobmanager.api.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jobmanager.api.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {
  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
      .orElseThrow(() -> {
        throw new UsernameNotFoundException("O campo [username/password] está incorreto");  
      });

    var passwordMatchs = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());
    if (!passwordMatchs) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT.create().withIssuer("jobmanager")
      .withExpiresAt(expiresIn)
      .withClaim("roles", Arrays.asList("CANDIDATE"))
      .withSubject(candidate.getId().toString())
      .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder()
      .access_token(token)
      .expires_in(expiresIn.toEpochMilli())
      .build();  
  
    return authCandidateResponse;
  }
}
