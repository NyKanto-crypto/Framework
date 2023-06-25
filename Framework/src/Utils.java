package utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import annotation.Urls;

public class Utils {

    public String getspliturl(String url) {
        String[] url_split = url.split("/");

        return url_split[url_split.length - 1];
    }

    public String getLastUrls(String url) {
        String[] urlsplitted = url.split("/");

        return "/" + urlsplitted[urlsplitted.length - 1];
    }

    public static Object cast(String toCast, Class typeOfCast) throws Exception {
        if (typeOfCast == int.class || typeOfCast == Integer.class) {
            return Integer.parseInt(toCast);
        } else if (typeOfCast == double.class || typeOfCast == Double.class) {
            return Double.parseDouble(toCast);
        } else if (typeOfCast == java.util.Date.class) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(toCast);
        } else if (typeOfCast == java.sql.Date.class) {
            return java.sql.Date.valueOf(toCast);
        } else if (typeOfCast == Boolean.class) {
            return Boolean.parseBoolean(toCast);
        }
        return toCast;
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

    public Vector<Object[][]> verifyClassByAnnot() throws ClassNotFoundException, UnsupportedEncodingException {
        String p = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        p = URLDecoder.decode(p, "UTF-8");
        System.out.println(p);
        List<Class<?>> obj = loadClassesInProject(p, "");
        Class<? extends Annotation> classe = (Class<? extends Annotation>) Class.forName("annotation.Urls");
        Vector<Object[][]> vs = new Vector<>();
        for (Class<?> aClass : obj) {
            Method[] m = aClass.getDeclaredMethods();
            for (Method method : m) {
                if (method.isAnnotationPresent(classe)) {
                    Urls annotation = method.getAnnotation(Urls.class);
                    Object[][] s = new Object[6][1];
                    if (method.getParameterCount() != 0) {
                        s = new Object[6][method.getParameterCount()];
                    }
                    s[0][0] = aClass.getName();
                    s[1][0] = method.getName();
                    s[2][0] = annotation.name();
                    s[3][0] = method.getParameterCount();
                    System.out.println("method name " + s[1][0]);
                    for (int index = 0; index < method.getParameterCount(); index++) {
                        s[4][index] = method.getParameterTypes()[index];
                        System.out.println("type parametre " + s[4][index]);
                        s[5][index] = method.getParameters()[index].getName();
                        System.out.println("nom parametre " + method.getParameters()[index].getName());
                    }

                    vs.add(s);
                }
            }
        }
        return vs;
    }
}
