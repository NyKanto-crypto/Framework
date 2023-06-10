package utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import annotation.Urls;
import etu1862.framework.servlet.FrontServlet;

public class Utils {

    public String getspliturl(String url){
        String[] url_split = url.split("/");

        return url_split[url_split.length - 1];
    }

    public String getLastUrls(String url) {
          String[] urlsplitted = url.split("/");

        return "/" + urlsplitted[urlsplitted.length - 1];
    }

    public List<Class<?>> loadClassesInProject(String path, String pkg) throws ClassNotFoundException {
        List<Class<?>> loadedClasses = new ArrayList<>();
        File directoryPath = new File(path);
        File[] listfiles = directoryPath.listFiles();
        assert listfiles != null;
        for (File listfile : listfiles) {
            if (listfile.isDirectory()) {
                loadedClasses.addAll(loadClassesInProject(listfile.getAbsolutePath(), pkg + listfile.getName() + "."));
            } else if (listfile.getName().endsWith(".class")) {
                String classname = listfile.getName().replace(".class", "");
                Class<?> c = Class.forName(pkg + classname);
                loadedClasses.add(c);
            }
        }
        return loadedClasses;
    }
    public Vector<String[]> verifyClassByAnnot() throws Exception {
        String p = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        p = URLDecoder.decode(p, "UTF-8");
        List<Class<?>> obj = loadClassesInProject(p, "");
        Class<?extends Annotation> classe = (Class<?extends Annotation>) Class.forName("annotation.Urls");
        Vector<String[]> vs = new Vector<>();
        for (Class<?> aClass : obj) {
            Method[] m = aClass.getDeclaredMethods();
            for (Method method : m) {
                if (method.isAnnotationPresent(classe)) {
                    Urls annotation = method.getAnnotation(Urls.class);
                    String[] s = new String[3];
                    s[0] = aClass.getPackage().getName() + "." + aClass.getSimpleName();
                    s[1] = method.getName();
                    s[2] = annotation.name();
                    vs.add(s);
                }
            }
        }
        return vs;
    }
}
