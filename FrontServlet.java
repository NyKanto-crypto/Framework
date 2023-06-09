package etu1862.framework.servlet;

import etu1862.framework.Mapping;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.Utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class FrontServlet extends HttpServlet {
    HashMap<String,Mapping> MappingUrls;
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("processRequest");
        Utils u = new Utils();
    //        out.println(request.getRequestURI());
        String url_splited = u.getspliturl(request.getRequestURI());

        out.println(url_splited);
    }
//
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}