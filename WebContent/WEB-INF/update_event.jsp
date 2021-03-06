<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Google Calendar</title>
		<meta name="google" content="notranslate">
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="../CSS/reset.css">
		<link rel="stylesheet" type="text/css" href="../CSS/main.css">
		<link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
	</head>
	<body >
		<header>
			<div id="header">
				<div id="glogo" class="inline">
					<a href="../views/DayViewServlet" title="Calendar"><img src="../IMG/applogo.png"></a>			
				</div>
				<div id="gsearch" class="inline">
					<!--<input type="search" placeholder="Buscar en Calendar"><img src="images\search.png">-->
					<img src="../IMG/gsearch.png" alt="Google Logo" title="Calendar">
				</div>
				<div id="gbuttons" class="inline">
					<a href="../LogoutServlet"><img src="../IMG/nav_buttons.png" alt="Google Logo" title="Calendar"></a>
				</div>
			</div>
		</header>	
		
		<div id="body">
					<nav>
						<div id="nav"> 		
							<div class="botones inline">
								<a href="../views/${servlet}"><button>Atrás</button></a>
								<a href="../views/${servlet}"><button>Descartar</button></a>
								<a href="../views/DeleteEventServlet?id=${event.id}&servlet=${servlet}"><button>Eliminar</button></a>
							</div>
						</div>
					</nav>
				<form id="event-form" method="post" action="UpdateEventServlet?id=${event.id}&servlet=${servlet}">
					
					<input type="submit" id="crear" value="Actualizar">
					<fieldset class="fs-form">
						<input type="text" name="name" placeholder="Evento sin título" value="${event.name}" required>
					</fieldset>
					<fieldset class="fs-form">
						<input class="inline" name="init-date" type="date" value="${event.startDate}" required>
						<input class="inline" name="init-time" type="time" value="${event.startTime}" required>
						<span>a</span>
						<input class="inline" name="end-time" type="time" value="${event.endTime}" required>
						<input class="inline" name="end-date" type="date" value="${event.endDate}" required>
					</fieldset>
					<fieldset class="fs-form">
						<input class="inline" name="duration"  type="checkbox">
						<label for="duration">Todo el día</label>
						<input class="inline" name="repeat"  type="checkbox">
						<label for="repeat">Repetir...</label>
					</fieldset>
					<fieldset class="fs-form">
						<legend id="event-info">Información del evento</legend>
						<label for="location">Lugar</label>
						<input type="text" name="location" placeholder="Introduce una ubicación"><br>
						<label for="owner">Calendario</label>
						<select name="owner">
							<option value="Javier Reyes">Javier Reyes</option>
						</select>	<br>	
						<label for="desc">Descripción</label>
						<textarea name="desc">Descripcion</textarea><br>
					</fieldset>
					<fieldset class="fs-form">
						<label>Color del evento</label>
						<input class="inline" name="event-color"  type="color"><br>
						<label>Notificaciones</label>
						<select>
							<option value="Correo">Correo</option>
							<option value="Ventana emergente">Ventana emergente</option>
						</select><br>
					</fieldset>
					<fieldset class="fs-form">
						<label>Mostrar como</label>
						<input class="inline" name="availability"  type="radio" value="Disponible">Disponible
		  				<input class="inline" name="availability"  type="radio" value="Ocupado">Ocupado<br>
		  				<label>Visibilidad</label>
						<input class="inline" name="visibility"  type="radio" value="Valor predeterminado de Calendar">
						<label for="visibility">Valor predeterminado de Calendar</label> 
						<input class="inline" name="visibility"  type="radio" value="Público">
						<label for="visibility">Público</label>
		  				<input class="inline" name="visibility"  type="radio" value="Privado">
		  				<label for="visibility">Privado</label><br>
	  				</fieldset>
				</form>
		</div>

	</body>
</html>