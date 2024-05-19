package edu.school21.models;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private boolean authenticationSuccessStatus; //(true - authenticated, false - not authenticated)

    public User(Long id, String login, String password, boolean authenticationSuccessStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticationSuccessStatus = authenticationSuccessStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticationSuccessStatus() {
        return authenticationSuccessStatus;
    }

    public void setAuthenticationSuccessStatus(boolean authenticationSuccessStatus) {
        this.authenticationSuccessStatus = authenticationSuccessStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return authenticationSuccessStatus == user.authenticationSuccessStatus && Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, authenticationSuccessStatus);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", authenticationSuccessStatus=" + authenticationSuccessStatus +
                '}';
    }
}
