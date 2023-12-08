package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Document(collection = "useri")
public class User implements UserDetails {

    @Id
    private String id; // MongoDB will generate ObjectId

    private String username;

    private String parola;

    private String email;

    private String rol;

    private Date dataCreari;

    private Date dataUltimeiModificari;

    public User(String username, String parola, String email, String rol) {
        this.username = username;
        this.parola = parola;
        this.email = email;
        this.rol = rol;
        this.dataCreari = new Date();
        this.dataUltimeiModificari = new Date();
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Date getDataCreari() {
        return dataCreari;
    }

    public void setDataCreari(Date dataCreari) {
        this.dataCreari = dataCreari;
    }

    public Date getDataUltimeiModificari() {
        return dataUltimeiModificari;
    }

    public void setDataUltimeiModificari(Date dataUltimeiModificari) {
        this.dataUltimeiModificari = dataUltimeiModificari;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", dataCreari=" + dataCreari +
                ", dataUltimeiModificari=" + dataUltimeiModificari +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER));
        return authorities;
    }
}
