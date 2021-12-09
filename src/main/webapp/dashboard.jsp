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
    <main>
    	<div id="dashboard-header">
    		<p id="dashboard-code"><b>Group:</b>${group}</p>
    		<h1 class="dashboard-header">Dashboard</h1>
    	</div>
    	<c:if test="${not empty data}">
    		<c:if test="${data.size() < 50}">
    			<button id="add-chore-button" onclick="window.location='addChore.jsp';">Add Chore</button>
    		</c:if>
    		<c:if test="${data.size() >= 50}">
    			<button id="cant-add-chore">Chore list is full. Please complete or delete chores in order to add new ones.</button>
    		</c:if>
	    	<div class="chore-container">
	    		<c:forEach var="chore" items="${data}" varStatus="theCount">
	    			<div class="chore">
	    				<div>
	    					<h1 class="chore-name">${chore.name}</h1>
	    					<p class="chore-data">${chore.description}</p>
	    				</div>
					    <c:if test="${not empty chore.claimed}">
					    	<img class="claimed-img" src="${chore.avi}" alt="A user's profile picture">
					    </c:if>
			    		<aside>
			    			<p class="chore-data">${chore.points} Points</p>
			    			<form id="chore-buttons" action="chore" method="POST">
			    				<c:if test="${empty chore.claimed}">
			    					<button id="claim-chore-button" name="claim" value="${theCount.index}"><i class="fas fa-user-check"></i></button>
			    					<button id="delete-chore-button" name="delete" value="${theCount.index}"><i class="fas fa-times"></i></button>
			    				</c:if>
			    				<c:if test="${not empty chore.claimed && chore.claimed==email}">
			    					<button id="finish-chore-button" name="finish" value="${theCount.index}"><i class="fas fa-check"></i></button>
			    					<button id="delete-chore-button" name="unclaim" value="${theCount.index}"><i class="fas fa-times"></i></button>
			    				</c:if>
			    			</form>
			    		</aside>
	    			</div>
	    		</c:forEach>
	    	</div>
    	</c:if>
    	<c:if test="${empty data}">
    		<button id="add-first-chore-button" onclick="window.location='addChore.jsp';">Add Chore</button>
    	</c:if>
    	<p class="divider" id="dashboard-separator"></p>
    	<div id="bottom-dashboard">
    		<img src="assets/dashboard/decoration1.png" alt="Man doing chores" id="chores-decoration">
    		<div id="profile">
	    		<img class="profile-img" src="<%=request.getAttribute("avatar")%>" alt="Your profile picture" onclick="window.location='settings.jsp';">
	    		<h1 style="margin-bottom: 0px;"><%=request.getAttribute("name")%></h1>
	    		<p><%=request.getAttribute("points")%> Points</p>
	    		<button id="leaderboard-button" onclick="window.location='leaderboard';">Leaderboard</button>
	    		<button id="chat-button" onclick="window.location='chat';">Group Chat</button>
    		</div>
    		<img src="assets/dashboard/decoration2.png" alt="Man doing chores" id="chores-decoration">
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