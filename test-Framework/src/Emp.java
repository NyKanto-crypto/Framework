package model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import annotation.Urls;
import view.ModelView;
public class Emp {
    private int id;
    private String nom;
    private String prenom;
    private Date dtn;

    public Emp() {}
    
    public Emp(int Id, String Nom, String Prenom, Date Dtn) {
        this.id = Id;
        this.nom = Nom;
        this.prenom = Prenom;
        this.dtn = Dtn;
    }

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String Nom) {
        this.nom = Nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String Prenom) {
        this.prenom = Prenom;
    }
    
    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    @Urls(name = "emp_list")
    public ModelView All() {
        ModelView mv = new ModelView();
        mv.setView("emp-list.jsp");
        List<Emp> listemp = new ArrayList<>();
        Emp a = new Emp(1, "RANDRIA", "Ny Kanto", Date.valueOf("2005-06-23"));
        Emp b = new Emp(2, "ANDRIA", "Notahina", Date.valueOf("2003-08-04"));
        Emp c = new Emp(3, "RAKOTO", "Zo", Date.valueOf("2000-07-29"));
        listemp.add(a);
        listemp.add(b);
        listemp.add(c);
        mv.addItem("lst", listemp);
        return mv;
    }

    @Urls(name = "emp_add")
    public ModelView Add() {
        ModelView mv = new ModelView();
        mv.setView("emp-add.jsp");
        return mv;
    }

    @Urls(name = "save_emp")
    public ModelView save(){
        ModelView mv = new ModelView();
        List<Object> listvalue = new ArrayList<>();
        listvalue.add(this.getId());
        listvalue.add(this.getNom());
        listvalue.add(this.getPrenom());
        listvalue.add(this.getDtn());

        mv.addItem("lstvalue",listvalue);

        mv.setView("save.jsp");

        return mv;
    }
}
