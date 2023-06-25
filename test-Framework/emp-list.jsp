<%@page import="java.util.*" %>
<%@page import="model.*" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <h1>Tafiditra ato amin ny emp</h1>
<% List<Emp> lemp = (List<Emp>) request.getAttribute("lst"); %>
<table>
    <thead>
        <tr>
            <th>Identifiant</th>
            <th>Nom</th>
            <th>Prenom</th>
        </tr>
    </thead>
    <tbody>
        <% for(int i = 0; i < lemp.size(); i++) { %>
            <tr>
                <td><%= lemp.get(i).getId() %></td>
                <td><%= lemp.get(i).getNom() %></td>
                <td><%= lemp.get(i).getPrenom() %></td>
                <td><a href="test.do?id=<%= lemp.get(i).getId() %>">plus de details</a></td>
            </tr>
        <% } %>
    </tbody>
</table>

</body>

</html>