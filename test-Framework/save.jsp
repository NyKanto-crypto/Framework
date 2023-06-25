<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% List<Object> lvalues = (List<Object>) request.getAttribute("lstvalue");
        for (int i = 0; i < lvalues.size(); i++) { %>
            <p><% out.println(lvalues.get(i)); %></p>
        <% } %>
    </body>
</html>