package etu1862.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.Enumeration;
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

    public HashMap<String, Mapping> getMappingUrls() {
        return MappingUrls;
    }

    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        MappingUrls = mappingUrls;
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Utils u = new Utils();
        String url_splited = u.getspliturl(request.getRequestURI());
        try {
            Mapping mp = this.getMapping(url_splited);
            String classname = mp.getClassname();
            String method = mp.getMethod();
            Object obj = Class.forName(classname).newInstance();
            sendData(request, obj);
            ModelView mv = getModelView(request, mp, obj);
            addData(request, mv);
            RequestDispatcher dispatcher = request.getRequestDispatcher(mv.getView());
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void addData(HttpServletRequest req, ModelView mv) {
        for (Map.Entry<String, Object> obj : mv.getData().entrySet()) {
            req.setAttribute(obj.getKey(), obj.getValue());
        }
    }

    // request -> data
    public void sendData(HttpServletRequest request, Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            String value = request.getParameter(field.getName());
            if (value != null) {
                field.setAccessible(true);
                field.set(obj, Utils.cast(value, field.getType()));
            }
        }
    }

    // appeler la fonction correspondant a l'url
    public ModelView getModelView(HttpServletRequest request, Mapping map, Object obj) throws Exception {

        Method method = obj.getClass().getDeclaredMethod(map.getMethod(), map.getClassArgument());
        method.setAccessible(true);
        Object[] argument = argumentValues(request, method);
        ModelView mv = (ModelView) method.invoke(obj, argument);

        return mv;
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

    // methode pour prendre les valeurs des arguments de la fonction
    public Object[] argumentValues(HttpServletRequest request, Method method) throws Exception {
        Parameter[] param = method.getParameters();

        Object[] values = new Object[param.length];
        for (int i = 0; i < values.length; i++) {
            Class type = param[i].getClass();
            
            String value = request.getParameter(param[i].getName());
            values[i] = Utils.cast(value, type);
        }
        return values;
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