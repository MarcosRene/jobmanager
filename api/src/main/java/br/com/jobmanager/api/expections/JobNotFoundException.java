package br.com.jobmanager.api.expections;

public class JobNotFoundException extends RuntimeException{
  public JobNotFoundException() {
    super("Vaga não encontrado");
  }
}
