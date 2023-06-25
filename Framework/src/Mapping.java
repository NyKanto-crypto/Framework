package etu1862.framework;

public class Mapping {
    String classname;
    String method;
    Class[] classArgument;

    public Mapping(String classname, String method) {
        this.classname = classname;
        this.method = method;
    }

    public Mapping(String classname, String method, Class[] classArgument) {
        this.classname = classname;
        this.method = method;
        this.classArgument = classArgument;
    }

    public String getClassname() {
        return classname;
    }

    public String getMethod() {
        return method;
    }

    public Class[] getClassArgument() {
        return this.classArgument;
    }

    public void setClassArgument(Class[] classArgument) {
        this.classArgument = classArgument;
    }
}
