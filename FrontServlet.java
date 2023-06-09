package etu1862.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import utils.Utils;

import etu1862.framework.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> MappingUrls;

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // Utils u = new Utils();
        // out.println(request.getRequestURI());
        // String url_splited = u.getspliturl(request.getRequestURI());

        // out.println(url_splited);
        PrintWriter out = response.getWriter();
        Utils u = new Utils();
        try {
            for (Map.Entry<String, Mapping> entry : this.MappingUrls.entrySet()) {
                out.println("key:" + entry.getKey());
                String classname = entry.getValue().getClass().getDeclaredMethod("getClassname")
                        .invoke(entry.getValue()).toString();
                String method = entry.getValue().getClass().getDeclaredMethod("getMethod").invoke(entry.getValue())
                        .toString();
                out.println("classname:" + classname);
                out.println("method:" + method);
                out.println("-------------------------------");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
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