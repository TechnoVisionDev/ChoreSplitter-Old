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
		if (null != request.getSession().getAttribute("email")) {
			response.sendRedirect("landing.jsp");
		}
	%>
  	<header>
  		<nav>
  			<a href="landing.jsp" id="home">ChoreSplitter!</a>			
		  	<a href="login.jsp" id="login-container"><button id="login">Login</button></a>
  		</nav>
  	</header>
  	<main>
  		<div class="login-form-container">
  			<div>
	  			<form class="login-form" action="auth" method="POST">
	  				<div>
		  				<h1 class="form-header">Login</h1>
		  				<c:if test="${not empty loginError}" var="condition">
		  					<p class="error-message">${loginError}</p>
		  				</c:if>
		  				<c:if test="${!condition}">
		  					<p class="error-message" style="visibility: hidden;">invisible</p>
		  				</c:if>
		  			</div>
	  				<label>Email</label>
	  				<input type="email" class="textbox" name="email" required>
	  				<label>Password</label>
	  				<input type="password" class="textbox" name="password" autoComplete="current-password" required pattern="\S(.*\S)?">
	  				<button type="submit" name="login" class="login-button"><i class="fas fa-sign-in-alt"></i>Sign In</button>
	  			</form>
	  			<p class="divider"></p>
				<div class="g-signin2" data-onsuccess="onSignIn" data-width="425" data-height="40" data-longtitle="true" data-theme="dark"></div>
  			</div>
  			<form class="login-form" action="auth" method="POST">
	  				<div>
		  				<h1 class="form-header">Register</h1>
		  				<c:if test="${not empty registerError}" var="condition">
		  					<p class="error-message">${registerError}</p>
		  				</c:if>
		  				<c:if test="${!condition}">
		  					<p class="error-message" style="visibility: hidden;">invisible</p>
		  				</c:if>
		  			</div>
  				<label>Email</label>
  				<input type="text" class="textbox" name="email" required>
  				<label>Name</label>
  				<input type="text" class="textbox" name="name" required pattern="\S(.*\S)?">
  				<label>Password</label>
  				<input type="password" class="textbox" name="password" autoComplete="new-password" required pattern="\S(.*\S)?">
  				<label>Confirm Password</label>
  				<input type="password" class="textbox" name="confirm_pass" autoComplete="new-password" required pattern="\S(.*\S)?">
  				<label>Profile Picture</label>
  				<input type="text" class="textbox" name="avatar" placeholder="Optional">
  				<div>
	  				<label class="terms">
	  					<input type="checkbox" name="terms" class="terms" required>
	  					I have read and agree to all terms and conditions.
	  				</label>
  				</div>
  				<button type="submit" name="register" class="login-button"><i class="fas fa-user-plus"></i>Create Account</button>
  			</form>
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