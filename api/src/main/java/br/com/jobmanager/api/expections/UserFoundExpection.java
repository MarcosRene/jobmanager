package br.com.jobmanager.api.expections;

public class UserFoundExpection extends RuntimeException {
  public UserFoundExpection() {
    super("Usuário já existe");
  }
}
