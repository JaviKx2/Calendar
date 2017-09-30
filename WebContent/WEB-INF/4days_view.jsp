<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<nav>
			<div id="nav"> 		
				<div class="mainlogo inline">
					<span id="mainlogo" title="Google Calendar">Calendar</span>
				</div>
				<div id="bizq" class="inline">
					<div class="botones inline">
						<a href="../views/FourDaysViewServlet?goTo=today"><button>Hoy</button></a>
						<a href="../views/FourDaysViewServlet?goTo=backward"><button>‹</button></a>
						<a href="../views/FourDaysViewServlet?goTo=forward"><button>›</button></a>
						${startDay} | ${endDay}
					</div>
				</div>
				<div id="bdrcha" class="inline">
					<div class="botones inline">
							<a href="../views/DayViewServlet"><button>Día</button></a>
							<a href="../views/WeekViewServlet"><button>Semana</button></a>
							<a href="../views/MonthViewServlet"><button>Mes</button></a>
							<a href="../views/FourDaysViewServlet"><button>4 días</button></a>
							<a href="../views/AgendaViewServlet"><button>Agenda</button></a>
						<button>Más</button>
						<button><img src="../IMG/engine.png"></button><!--<img src="images\engine.png">-->
					</div>
				</div>
			</div>
		</nav>
		<div id="body">
			<nav id="lateral">
				<div id="crear_c" class="lateral">
					<a href="../views/EventServlet?servlet=${servlet}"><button type="button" id="crear">crear</button></a>
				</div>
				<ul class="lateral">
					<li>Minicalendario</li>
					<li>Mis calendarios</li>
					<li><a href="DeleteCalendarServlet?servlet=${servlet}">Eliminar Calendario</a></li>
					<li>Otros calendarios</li>
				</ul>				
			</nav>
			<div id="content" class="inline">
				<table class="wk-table" cellpadding="0" cellspacing="0">
					<tbody>
						<tr class="wk-daynames">
							<th class="wk-td" rowspan="2">GMT+01</th>
							<c:forEach var="wday" items="${wdays}">
							<th>${wday}</th>
							</c:forEach>

						</tr>
						<tr>
						<c:forEach var="wday" items="${dias}">
							<th class="wk-td">
								${wday}
							</th>
						</c:forEach>
						</tr>
						<c:forEach var="hour" begin="0" end="23">
						<tr>
							<td class="wk-td">${hour}:00</td>
							<c:forEach var="wday" items="${dias}">
							<td class="wk-td">
								<c:forEach var="event" items="${events}">
									<c:if test="${hour == event.hour && wday == event.day}">
										<a href="../views/UpdateEventServlet?id=${event.id}&servlet=${servlet}">${event}</a>
										<a href="../views/DeleteEventServlet?id=${event.id}&servlet=${servlet}">
											<img src="../IMG/equis.png">
										</a><br>
									</c:if>
								</c:forEach>
							</td>
							</c:forEach>
						</tr>
						</c:forEach>
					</tbody>
				</table>				
			</div>
		</div>
	</body>
</html>
