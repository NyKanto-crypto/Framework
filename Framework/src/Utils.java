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

    public Vector<String[]> verifyClassByAnnot() throws ClassNotFoundException, UnsupportedEncodingException {
        String p = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        p = URLDecoder.decode(p, "UTF-8");
        System.out.println(p);
        List<Class<?>> obj = loadClassesInProject(p, "");
        Class<? extends Annotation> classe = (Class<? extends Annotation>) Class.forName("annotation.Urls");
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
