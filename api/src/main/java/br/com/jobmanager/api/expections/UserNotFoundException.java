package br.com.jobmanager.api.expections;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("Usuário não encontrado");
  }
}
