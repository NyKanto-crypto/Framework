package utils;

public class Utils {
    public String getspliturl(String url){
        String[] url_split = url.split("/");

        return url_split[url_split.length - 1];
    }
}
