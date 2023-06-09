package etu1862.framework;

public class Mapping {
    String classname;
    String method;

    public Mapping(String classname, String method) {
        this.classname = classname;
        this.method = method;
    }

    public String getClassname() {
        return classname;
    }

    public String getMethod() {
        return method;
    }
}
