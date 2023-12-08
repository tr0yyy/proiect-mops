package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "articole")
public class Articol {
    @Id
    private String id; // MongoDB will generate ObjectId

    @DBRef
    private User autor;

    private String categorie;

    private String titlu;

    private String continut;

    private Date dataCreari;

    private Date dataUltimeiModificari;

    private String versiune;

    public Articol(String id, User autor, String categorie, String titlu, String continut, Date dataCreari, Date dataUltimeiModificari, String versiune) {
        this.id = id;
        this.autor = autor;
        this.categorie = categorie;
        this.titlu = titlu;
        this.continut = continut;
        this.dataCreari = dataCreari;
        this.dataUltimeiModificari = dataUltimeiModificari;
        this.versiune = versiune;
    }

    public Articol() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
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

    public String getVersiune() {
        return versiune;
    }

    public void setVersiune(String versiune) {
        this.versiune = versiune;
    }

    @Override
    public String toString() {
        return "Articol{" +
                "id='" + id + '\'' +
                ", autor=" + autor.getUsername() +
                ", categorie='" + categorie + '\'' +
                ", titlu='" + titlu + '\'' +
                ", continut='" + continut + '\'' +
                ", dataCreari=" + dataCreari +
                ", dataUltimeiModificari=" + dataUltimeiModificari +
                ", versiune='" + versiune + '\'' +
                '}';
    }
}
