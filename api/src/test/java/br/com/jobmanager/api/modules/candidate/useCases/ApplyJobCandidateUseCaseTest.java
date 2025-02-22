package br.com.jobmanager.api.modules.candidate.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jobmanager.api.expections.JobNotFoundException;
import br.com.jobmanager.api.expections.UserNotFoundException;
import br.com.jobmanager.api.modules.candidate.entities.ApplyJobEntity;
import br.com.jobmanager.api.modules.candidate.entities.CandidateEntity;
import br.com.jobmanager.api.modules.candidate.repositories.ApplyJobRepository;
import br.com.jobmanager.api.modules.candidate.repositories.CandidateRepository;
import br.com.jobmanager.api.modules.company.entites.JobEntity;
import br.com.jobmanager.api.modules.company.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

  @InjectMocks
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;
  
  @Mock
  private CandidateRepository candidateRepository;
  
  @Mock
  private JobRepository jobRepository;
 
  @Mock
  private ApplyJobRepository applyJobRepository;

  @Test
  @DisplayName("It should be able to apply job with candidate not found")
  public void itShouldNotBeAbleToApplyJobWithCandidateNotFound() {
    try {
      applyJobCandidateUseCase.execute(null, null);
    } catch (Exception exception) {
      assertThat(exception).isInstanceOf(UserNotFoundException.class);    
    }
  }

  @Test
  @DisplayName("It should be able to apply job with job not found")
  public void itShouldNotBeAbleToApplyJobWithJobNotFound() {
    var candidateId = UUID.randomUUID();
    var candidate = new CandidateEntity();
    candidate.setId(candidateId);
    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

    try {
      applyJobCandidateUseCase.execute(candidateId, null);
    } catch (Exception exception) {
      assertThat(exception).isInstanceOf(JobNotFoundException.class);    
    }
  }

  @Test
  @DisplayName("It should be able to create a new apply job")
  public void itShouldBeAbleToCreateANewApplyJob() {
    var candidateId = UUID.randomUUID();
    var jobId = UUID.randomUUID();
    var applyJob = ApplyJobEntity.builder()
      .candidateId(candidateId)
      .jobId(jobId)
      .build();

    var applyJobCreated = ApplyJobEntity.builder()
      .id(UUID.randomUUID())
      .build();
          
    when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
    when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));
   
    when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

    var result = applyJobCandidateUseCase.execute(candidateId, jobId);
    assertThat(result).hasFieldOrProperty("id");
    assertNotNull(result.getId());
  }
}
