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
                <% for(Emp entry : lemp) { %>
                    <tr>
                        <td><% out.println(entry.getId()); %></td>
                        <td><% out.println(entry.getNom()); %></td>
                        <td><% out.println(entry.getPrenom()); %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
</body>

</html>