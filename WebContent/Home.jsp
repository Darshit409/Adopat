<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Home</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <style>
    /* Remove the navbar's default margin-bottom and rounded borders */
    .navbar {
      margin-bottom: 0;
      border-radius: 0;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust as needed) */
    .row.content {height: 450px}

    /* Set gray background color and 100% height */
    .sidenav {
      padding-top: 20px;
      background-color: #f1f1f1;
      min-height: 50px;
      height: 100%;
    }

    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
	/*Changing Button color for Submit button*/
	.btn-dark,
	.btn-dark:hover{
		background-color : 	#000000;
		border_color: 	#000000;}
	
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height:auto;}
    }
  </style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="Index.jsp">AdoPet</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="Home.jsp">Home</a></li>
        <li><a href="Explore">Explore</a></li>
        <li><a href="MyAdoptions">My Adoptions</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	<li><a href = "addCart"><span class="glyphicon glyphicon-shopping-cart"></span>Cart</a></li>
        <li><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid text-center ">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="PlacePetForAdoption.jsp">Place Pet for Adoption</a></p>
      <p><a href="FavoritePet">Favorite pets</a></p>
	  <p><a href="FavoriteBreeder">Favorite Breeders</a></p>
	  <p><a href = "AdoptionBySpecies">Adoption By Species</a></p>
	  <p><a href ="TopReviewers">Top Reviewers</a></p>
	  <p><a href = "CommonFavPets">Common Pets in Favorites</a></p>
	  <p><a href = "GoodPets">Good Reviews Pets</a></p>
	  <p><a href = "CrayCrayReviewExceptions">Breeders not posted Cray_cray review</a></p>
      <p><a href = "NoCrayCrayReviewExceptions">Breeder's pet with no Cray_cray review</a></p>
      <p><a href = "goodUser">Breeder with no bad reviews</a></p>
      <p><a href = "UserWithGreatPets">Breeder with no Ridic Adorbs</a></p>
      
    </div>
    <div class="col-sm-8 text-left">
      <h1>Welcome ${ FirstName } ${ LastName }</h1>
	  <h4> Welcome to Home Page of Adopet</h4>
      <hr>
    </div>
  </div>
</div>
</body>
</html>
