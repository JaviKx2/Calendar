<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Google Calendar</title>
	<meta name="google" content="notranslate">
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="CSS/reset.css">
	<link rel="stylesheet" type="text/css" href="CSS/main.css">
	<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	<script src="JS/angular.min.js"></script>
	<script src="JS/form.js"></script>
</head>
<body>
	<div class="wrapper">
  		<div id="googlelogo">
  			<img alt="Google" class="logo" src="IMG/applogo.png">
  		</div>
		<div id="eslogan">
			<h1>Una cuenta. Todo Google.</h1>
			<h2>Inicia sesión para ir a Google Calendar</h2>
		</div>
		<div id="login" class="login">
			<img id="profile-img" class="profile-img" src="IMG/profile-img.jpg">
		  	<form method="post" action="LoginServlet">
				<input id="user" name="user" type="text" placeholder="Usuario" value="pedropin" required>
				<input id="password" name="password" type="password" placeholder="Contraseña" value="tomcat" required>
				<input id="signin" name="signin" type="submit" value="Iniciar sesión">
				<p>${messages}</p>		
		  	</form>
		  	<a href="#">¿Necesitas ayuda?</a>
		</div>
		<div class="one-google">
		  	<p class="switch-account">
		  		<a id="account-chooser-link" href="#">Iniciar sesión con una cuenta diferente</a>
		  	</p>
			<p class="tagline">
		  		Una sola cuenta de Google para todos los servicios de Google
			</p>
			<img id="gservices-img" src="IMG/gservices.png">
		</div>
	</div>
</body>
</html>