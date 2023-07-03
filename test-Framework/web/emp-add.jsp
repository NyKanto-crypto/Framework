<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <form action="save.do" method="post" enctype="multipart/form-data">
        <p>Id: <input type="number" name="id"></p>
        <p>Nom: <input type="text" name="nom"></p>
        <p>Prenom: <input type="text" name="prenom"></p>
        <p>Date de naissance: <input type="date" name="dtn"></p>
        <p>Fichier: <input type="file" name="file"></p>
        <button type="submit">Valider</button>
    </form>
</body>

</html>