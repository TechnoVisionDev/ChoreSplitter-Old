<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://kit.fontawesome.com/4b6d728af0.js" crossorigin="anonymous"></script>
    
    <link rel="apple-touch-icon" sizes="180x180" href="assets/favicon/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/favicon/favicon-16x16.png">
    <link rel="manifest" href="assets/favicon/site.webmanifest">
    <link rel="mask-icon" href="assets/favicon/safari-pinned-tab.svg" color="#5bbad5">
    <title>ChoreSplitter</title>
</head>
<body>
	<%
		if (null == session.getAttribute("email")) {
			response.sendRedirect("login.jsp");
		} else if (null == session.getAttribute("group")) {
			response.sendRedirect("group.jsp");
		}
	%>
  	<header>
  		<nav>
  			<a href="landing.jsp" id="home">ChoreSplitter!</a>
  			<c:if test="${empty email}" var="condition">
  				<a href="login.jsp" id="login-container"><button id="login">Login</button></a>
  			</c:if>
  			<c:if test="${!condition}">
				<div id="login-container">
			   	<i class="fas fa-clipboard-list" id="settings-button" onclick="window.location='dashboard';"></i>
			   	<i class="far fa-chart-bar" id="settings-button" onclick="window.location='leaderboard';"></i>
			   	<i class="far fa-comments" id="settings-button" onclick="window.location='chat';"></i>
			   	<i class="fas fa-cog" id="settings-button" onclick="window.location='settings.jsp';"></i>
			   	<button id="login" onclick="window.location='auth';">Logout</button>
			   	</div>
  			</c:if>	
  		</nav>
  	</header>
    <main class="page-centered">
    	<h1 class="page-header">Leaderboard</h1>
    	<div class="leaderboard-container">
	    	<c:forEach var="user" items="${data}" varStatus="loop">
	    		<div class="leaderboard-entry">
	    				<img class="leaderboard-img" src="${user.avatar}" alt="A user's profile picture">
	    			    <div>
	    					<h1 class="chore-name">${user.name}</h1>
	    					<p class="chore-data">${user.points} Points</p>
	    				</div>
	    				<h1 class="leaderboard-position">#${loop.count}</h1>
	    		</div>
	    	</c:forEach>
    	</div>
    	<p class="leaderboard-divider"></p>
    	<h1 class="page-header">Slot of Shame</h1>
    	<div class="shame">
	    	<img class="profile-img" src="${shame.avatar}" alt="Slot of shame profile picture">
	    	<h1 style="margin-bottom: 0px;">${shame.name}</h1>
	    	<p>${shame.points} Points</p>
    	</div>
    </main>
    <footer>
        <span>&copy; 2021 All Rights Reserved.</span>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 250">
            <path fill="#bb0000" fill-opacity="1" d="M0,32L34.3,32C68.6,32,137,32,206,58.7C274.3,85,343,139,411,170.7C480,203,549,213,617,202.7C685.7,192,754,160,823,133.3C891.4,107,960,85,1029,112C1097.1,139,1166,213,1234,224C1302.9,235,1371,181,1406,154.7L1440,128L1440,320L1405.7,320C1371.4,320,1303,320,1234,320C1165.7,320,1097,320,1029,320C960,320,891,320,823,320C754.3,320,686,320,617,320C548.6,320,480,320,411,320C342.9,320,274,320,206,320C137.1,320,69,320,34,320L0,320Z"></path>
        </svg>
    </footer>
</body>
</html>