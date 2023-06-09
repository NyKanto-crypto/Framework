package model;

import annotation.Urls;
import view.ModelView;
public class Dept {
    @Urls(name = "dept_add")
    public ModelView Add() {
        ModelView mv = new ModelView();
        mv.setView("dept-list.jsp");
        return mv;
    }
}
