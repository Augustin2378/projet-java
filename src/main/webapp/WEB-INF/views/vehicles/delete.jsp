<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation de suppression</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header bg-danger text-white">
                    Confirmation de suppression
                </div>
                <div class="card-body">
                    <p>Êtes-vous sûr de vouloir supprimer ce véhicule ? Cette action est irréversible.</p>
                    <form method="post" action="/rentmanager/cars/delete">
                        <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
                        <button type="submit" class="btn btn-danger">Confirmer la suppression</button>
                        <a href="/rentmanager/cars" class="btn btn-secondary">Annuler</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
