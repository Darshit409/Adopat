<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Explore</title>
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

	/*Changing Button color for Submit button*/
	.btn-dark,
	.btn-dark:hover{
		background-color : 	#000000;
		border_color: 	#000000;}
	
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }

	
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
        <li><a href="Login.jsp"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container-fluid text-center ">
  <div class="row content">
    <div class="col-sm-2 sidenav">
      <p><a href="PlacePetForAdoption.jsp">Place Pet for Adoption</a></p>
      <p><a href="#">Link</a></p>
    </div>
    <div class="col-sm-8 text-left">
      
      <h1>All Adoptions</h1>
      <div class = "pull-right">
      Filter By Traits:
      <div class="btn-group ">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <span class="caret"></span>
          </a>
  	<ul class="dropdown-menu">
  	<c:forEach var = "T" items = "${Traits}" varStatus = "loop">
	<li class="dropdown-item"><a tabindex="-1" href="TraitList?Traits=<c:out value='${T}' />">${ T }</a></li>
	<li class="divider"></li>
  	</c:forEach>

  	</ul>
  	</li>
	</div>
	</div>
	<%String name = (String)request.getAttribute("trait"); %>
	<% if(name != null){%>
	<h4> List of pets with trait <i>${ trait }</i> </h4>
	<%}else{ %>
	  <h4>List of All Adoptions</h4>
    <%} %>  
      <hr>
      <table class = "table table-hover">
      <thead class = "thead-dark ">
      <tr>
      <th scope = "col">#</th>
      <th scope = "col">Name</th>
      <th scope = "col">Species</th>
      <th scope = "col">Breeder</th>
      <th scope = "col">Price</th>

      </tr>
      </thead>
      <tbody>
      <c:forEach var = "pet" items = "${AlllistPet}" varStatus = "loop">
      <tr>
      <th scope = "row">${loop.index + 1}</th>
      <td><c:out value = "${pet.petName }" /></td>
      <td><c:out value = "${pet.species }" /></td>
      <td><a href ="Userinfo?id=${pet.userId} "><c:out value = "${pet.userName }" /></a></td>
      <td>$ <c:out value = "${pet.adoptionPrice }" /></td>
      
      </tr>
      </c:forEach>
      </tbody>
      </table>
      
      
    </div>
  </div>
</div>
</body>
</html>
