package model;

import view.ModelView;
import annotation.Urls;
public class Emp {
    @Urls(name = "emp_list")
    public ModelView List() {
        ModelView mv = new ModelView();
        mv.setView("emp-list.jsp");
        return mv;
    }
}
