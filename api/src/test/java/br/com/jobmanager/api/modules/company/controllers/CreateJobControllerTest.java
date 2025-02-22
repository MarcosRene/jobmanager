package br.com.jobmanager.api.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.jobmanager.api.modules.company.dto.CreateJobDTO;
import br.com.jobmanager.api.modules.company.entites.CompanyEntity;
import br.com.jobmanager.api.modules.company.repositories.CompanyRepository;
import br.com.jobmanager.api.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CompanyRepository companyRepository;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders
    .webAppContextSetup(webApplicationContext)
    .apply(SecurityMockMvcConfigurers.springSecurity())
    .build();
  }

  @Test
  public void itShouldBeAbleToCreateANewJob() throws Exception {
    var company = CompanyEntity.builder()
      .name("JobManager Company") 
      .username("jobmanager")
      .email("jobmanager@gmail.com")
      .password("12345678")
      .description("JobManager Company")
      .build();

    company = this.companyRepository.saveAndFlush(company);

    var createJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .build();
    
    mockMvc.perform(MockMvcRequestBuilders
      .post("/api/v1/companies/jobs/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(TestUtils.objectToJSON(createJobDTO))
      .header("Authorization", TestUtils.generateToken(company.getId(), "24e84df0f477bdd0b3f2cfb4ef534b1e"))
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void itShouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Throwable {
    var createJobDTO = CreateJobDTO.builder()
      .benefits("BENEFITS_TEST")
      .description("DESCRIPTION_TEST")
      .level("LEVEL_TEST")
      .build();
  
    mockMvc.perform(MockMvcRequestBuilders
      .post("/api/v1/companies/jobs/")
      .contentType(MediaType.APPLICATION_JSON)
      .content(TestUtils.objectToJSON(createJobDTO))
      .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "24e84df0f477bdd0b3f2cfb4ef534b1e"))
    ).andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
