package etu1862.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import etu1862.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Utils;
import view.ModelView;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrls;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Utils u = new Utils();
        // out.println(request.getRequestURI());

        // out.println(url_splited);
        ModelView mv = new ModelView();
        PrintWriter out = response.getWriter();
        Utils u = new Utils();
        String url_splited = u.getspliturl(request.getRequestURI());
        try {
            this.getMapping(url_splited);
            String classname = this.getMapping(url_splited).getClass().getDeclaredMethod("getClassname")
                    .invoke(this.getMapping(url_splited)).toString();
            String method = this.getMapping(url_splited).getClass().getDeclaredMethod("getMethod")
                    .invoke(this.getMapping(url_splited))
                    .toString();
            Class<?> c = Class.forName(classname);
            Object classe = c.newInstance();
            mv = (ModelView) c.getDeclaredMethod(method).invoke(classe);
            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    // Prendre Mapping : Class,Methode,Argument correspondant de L'URL
    public Mapping getMapping(String url) throws Exception {
        for (Map.Entry<String, Mapping> entry : this.MappingUrls.entrySet()) {
            if (entry.getKey().equals(url)) {
                return entry.getValue();
            }
        }
        throw new Exception("URL NOT FOUND");
    }

    @Override
    public void init() throws ServletException {
        Utils u = new Utils();
        try {
            this.MappingUrls = new HashMap<>();
            Vector<String[]> verif = u.verifyClassByAnnot();
            for (String[] strings : verif) {
                Mapping mapping = new Mapping(strings[0], strings[1]);

                this.MappingUrls.put(strings[2], mapping);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}