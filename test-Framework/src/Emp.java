package model;

import java.util.ArrayList;
import java.util.List;
import view.ModelView;
import annotation.Urls;

public class Emp {
    int id;
    String nom;
    String prenom;

    public Emp() {}
    
    public Emp(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
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

    @Urls(name = "emp_list")
    public ModelView List() {
        ModelView mv = new ModelView();
        mv.setView("emp-list.jsp");
        List<Emp> listemp = new ArrayList<>();
        Emp a = new Emp(1, "RANDRIA", "Ny Kanto");
        Emp b = new Emp(2, "ANDRIA", "Notahina");
        Emp c = new Emp(3, "RAKOTO", "Zo");
        listemp.add(a);
        listemp.add(b);
        listemp.add(c);
        mv.addItem("lst", listemp);
        return mv;
    }
}
