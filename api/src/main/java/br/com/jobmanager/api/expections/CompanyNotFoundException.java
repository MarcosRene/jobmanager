package br.com.jobmanager.api.expections;

public class CompanyNotFoundException extends RuntimeException {
  public CompanyNotFoundException() {
    super("Empresa não encontrada");
  }
}
