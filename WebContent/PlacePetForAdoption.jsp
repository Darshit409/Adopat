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
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#">Explore</a></li>
        <li><a href="#">My Adoptions</a></li>
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
	  <h4> Place Pet For Adoption
	  </h4>
      <hr>
      <form role="form" action = "petInsert" method = "POST">
    <div class="form-group">
      <label for="exampleFormControlInput1">Name</label>
      <input type= "text" class="form-control" id="petName" name = "petName" placeHolder = "Enter pet Name">
    </div>
    <div class="form-group">
      <label for="exampleFormControlInput1">Species</label>
      <input type= "text" class="form-control" id="species" name = "species" placeHolder = "Enter pet's species">
    </div>
  <div class="form-group">
    <label for="exampleFormControlInput1">Date of Birth</label>
    <input type= "date" class="form-control" id="birthDate" name = "birthDate" placeHolder = "mm/dd/yyyy" >
  </div>
  <div class="form-group">
    <label for="exampleFormControlInput1">Price For Adoption</label>
    <input type= "number" class="form-control" id="adoptionPrice" name= "adoptionPrice" placeHolder = "00.00">
  </div>
  <div class="form-group">
    <label for="exampleFormControlTextarea1">Traits</label>
    <textarea class="form-control" id="traits" name = "traits" rows="3" placeHolder = "Enter some Traits seperated by space or commas"></textarea>
  </div>
  <div class = "form_group">
  <button type = "submit" class = "btn btn-info btn-block btn-dark" data-toggle="modal" data-target="#myModal">Submit</button>
  </div>
</form>

<%String name = (String)request.getAttribute("success"); %>
<% if(name != null){%>
<div class="modal fade" id="myModal" role="dialog" >
    <div class="modal-dialog modal-sm ">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Pet Registration</h4>
        </div>
        <div class="modal-body">
          <p>${ success }</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default btn-dark" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>

<script>
$("#myModal").modal("show");
</script>
<% }%>
</body>
</html>

