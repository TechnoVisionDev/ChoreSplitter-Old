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
		if (null == request.getSession().getAttribute("email")) {
			response.sendRedirect("login.jsp");
		}
	%>
  	<header>
  		<nav>
  			<a href="landing.jsp" id="home">ChoreSplitter!</a>			
	  		<%
	  			if (null == request.getSession().getAttribute("email")) {
			        out.println("<a href=\"login.jsp\" id=\"login-container\"><button id=\"login\">Login</button></a>");
			   	} else {
			   		out.println("<a href=\"auth\" id=\"login-container\"><button id=\"login\">Logout</button></a>");
			    }
			%>
  		</nav>
  	</header>
    <main>
    	<h1 class="page-header">Select a Housing Group!</h1>
    	<p>Enter a group code or create your own.</p>
    	<div id="group-body">
    		<img src="assets/group/decoration1.png" alt="Woman doing chores" id="chores-decoration">
	    	<div id="group-forms">
		    	<form class="join-group-form" action="group" method="POST">
		    		<input type="text" class="textbox" name="group-code" placeholder="Group Code" maxlength="6" required>
		    		<button type="submit" name="join-group" class="join-button"><i class="fas fa-user-plus"></i> Join Group</button>
		    	</form>
		    	<p id="divider"></p>
		    	<form class="join-group-form" action="group" method="POST">
		    		<button type="submit" name="create-group" class="create-button"><i class="fas fa-users"></i> Create New Group</button>
		    	</form>
	    	</div>
	    	<img src="assets/group/decoration2.png" alt="Woman doing chores" id="chores-decoration">
    	</div>
    </main>
    <footer>
        <span>&copy; 2021 All Rights Reserved.</span>
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 250">
            <linearGradient id="grad" x1="0%" y1="0%" x2="100%" y2="0%">
                <stop offset="0%" style="stop-color:#bb0000; stop-opacity:1" />
                <stop offset="100%" style="stop-color:#bb0000; stop-opacity:1" />
            </linearGradient>
            <path fill="url(#grad)" fill-opacity="1"
                d="M0,32L34.3,32C68.6,32,137,32,206,58.7C274.3,85,343,139,411,170.7C480,203,549,213,617,202.7C685.7,192,754,160,823,133.3C891.4,107,960,85,1029,112C1097.1,139,1166,213,1234,224C1302.9,235,1371,181,1406,154.7L1440,128L1440,320L1405.7,320C1371.4,320,1303,320,1234,320C1165.7,320,1097,320,1029,320C960,320,891,320,823,320C754.3,320,686,320,617,320C548.6,320,480,320,411,320C342.9,320,274,320,206,320C137.1,320,69,320,34,320L0,320Z">
            </path>
        </svg>
    </footer>
</body>
</html>