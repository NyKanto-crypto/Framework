package model;

import annotation.Urls;
import view.ModelView;
public class Dept {
    @Urls(name = "dept_add.do")
    public ModelView Add() {
        ModelView mv = new ModelView();
        mv.setView("web/dept-list.jsp");
        return mv;
    }
}
