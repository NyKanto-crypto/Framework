package etu1862.framework.servlet;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import etu1862.framework.Mapping;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import utils.*;
import view.ModelView;

@MultipartConfig
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
        // Vérifiez le type de contenu de la requête
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            for (Field field : fields) {
                if (field.getType() == FileUpload.class) {
                    Part filePart = request.getPart(field.getName());
                    if (filePart != null) {
                        field.setAccessible(true);
                        String fileName = filePart.getSubmittedFileName();
                        byte[] fileData = filePart.getInputStream().readAllBytes();
                        FileUpload file = new FileUpload();
                        file.setNom(fileName);
                        file.setData(fileData);
                        field.set(obj, file);
                    }
                } else {
                    String value = request.getParameter(field.getName());
                    if (value != null) {
                        field.setAccessible(true);
                        field.set(obj, Utils.cast(value, field.getType()));
                    }
                }
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
            System.out.println("url " + entry.getKey());
            if (entry.getKey().equals(url)) {
                return entry.getValue();
            }
        }
        throw new Exception("URL NOT FOUND");
    }

    // methode pour prendre les valeurs des arguments de la fonction
    public Object[] argumentValues(HttpServletRequest request, Method method) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] values = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            String paramName = parameters[i].getName();
            String paramValue = request.getParameter(paramName);

            if (paramValue != null) {
                values[i] = Utils.cast(paramValue, type);
            } else {
                // Gérer le cas où la valeur de la chaîne est null
                // Par exemple, renvoyer une valeur par défaut appropriée ou lever une exception
                throw new IllegalArgumentException("Missing value for parameter: " + paramName);
            }
        }

        return values;
    }

    @Override
    public void init() throws ServletException {
        Utils u = new Utils();
        try {
            this.MappingUrls = new HashMap<>();
            Vector<Object[][]> verif = u.verifyClassByAnnot();
            for (Object[][] strings : verif) {
                if (strings[3][0].equals(0)) {
                    Mapping mapping = new Mapping((String) strings[0][0], (String) strings[1][0]);

                    this.MappingUrls.put((String) strings[2][0], mapping);
                } else {
                    Class[] classargument = new Class[strings[4].length];
                    for (int j = 0; j < classargument.length; j++) {
                        classargument[j] = (Class) strings[4][j];
                    }
                    Mapping mapping = new Mapping((String) strings[0][0], (String) strings[1][0], classargument);

                    this.MappingUrls.put((String) strings[2][0], mapping);
                }
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