<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href= "css/index.css" rel= "stylesheet">
<!------ Include the above in your HEAD tag ---------->
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
body {
background-color: #525252;
}
.centered-form
{
	margin-top: 60px;
}

.centered-form .panel
{
	background: rgba(255, 255, 255, 0.8);
	box-shadow: rgba(0, 0, 0, 0.3) 20px 20px 20px;
}
</style>

</head>
<body>
<div class="container">
        <div class="row centered-form">
        <div class="col-xs-12 col-sm-8 col-md-4 col-sm-offset-2 col-md-offset-4">
        	<div class="panel panel-default">
        		<div class="panel-heading">
			    		<h3 class="panel-title">Login Adopet</h3>
			 			</div>
			 			<div class="panel-body">
			    		<form role="form" action= "insert" method = "POST">
			    			<div class="form-group">
			    				<input type="text" name="UserName"  id="UserName" class="form-control input-sm" placeholder="User Name">
			    				</div>
			    			<div class="form-group">
			    				<input type="password" name="Password" id="Password" class="form-control input-sm" placeholder="Email Address">
			    			</div>
			    			<input type="submit" value="Login" class="btn btn-info btn-block">
			    		</form>
			    	</div>
	    		</div>
    </div>
  <!-- Login Section -->
 </body>
</html>
