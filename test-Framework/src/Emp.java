package model;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import annotation.Urls;
import utils.FileUpload;
import view.ModelView;

public class Emp {
    private int id;
    private String nom;
    private String prenom;
    private Date dtn;
    private FileUpload file;

    public Emp() {}
    
    public Emp(int id, String nom, String prenom, Date dtn) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dtn = dtn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public Date getDtn() {
        return dtn;
    }

    public void setDtn(Date dtn) {
        this.dtn = dtn;
    }

    public FileUpload getFile() {
        return file;
    }

    public void setFile(FileUpload file) {
        this.file = file;
    }

    @Urls(name = "emp_list.do")
    public ModelView All() {
        ModelView mv = new ModelView();
        mv.setView("web/emp-list.jsp");
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

    @Urls(name = "emp_add.do")
    public ModelView Add() {
        ModelView mv = new ModelView();
        mv.setView("web/emp-add.jsp");
        return mv;
    }

    @Urls(name = "save.do")
    public ModelView save_file(){
        ModelView mv = new ModelView();
        List<Object> listvalue = new ArrayList<>();
        listvalue.add(this.getId());
        listvalue.add(this.getNom());
        listvalue.add(this.getPrenom());
        listvalue.add(this.getDtn());
        listvalue.add(this.getFile().getNom());
        listvalue.add(this.getFile().getData());

        mv.addItem("lstvalue",listvalue);

        mv.setView("web/save.jsp");

        return mv;
    }

    @Urls(name = "test.do")
    public ModelView test(int id){
        ModelView mv = new ModelView();
        mv.addItem("id",id);

        mv.setView("web/test.jsp");

        return mv;
    }
}
