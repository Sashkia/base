<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
 
</head>
<body>
<form name="form" action="j_spring_security_check" method="post" class="form-signin">
   

    
  

    <label for="inputEmail" class="sr-only"><spring:message code="email" text="Email"/></label>
    <input id="inputEmail" class="form-control" name="j_username" value="superuser@outlook.com" required autofocus/>

    <label for="inputPassword" class="sr-only"><spring:message code="pass" text="Password"/></label>
    <input type="password" id="inputPassword" class="form-control" name="j_password" value="12345" required/>

    <div class="checkbox">
        <label>
            <input type="checkbox" id="rememberme"  name="_spring_security_remember_me"/>Запомнить меня
        </label>
    </div>
    <input type="submit" value="Войти" class="btn btn-lg btn-primary btn-block" >
    <br/>
    <a href="javascript:history.back()">Назад</a>

    <br /><br />
   
    <p>Доступные роли:</p>

    <b>ROLE_SUPER_USER</b><br />
    Login:<span style="color: royalblue">superuser@outlook.com</span> Password: <span style="color: royalblue">12345</span> <br />
    <b>ROLE_ADMIN</b> <br />
    Login:<span style="color: royalblue">admin@gmail.com</span> Password: <span style="color: royalblue">12345</span> <br />
    <b>ROLE_USER</b> <br />
    Login: <span style="color: royalblue">roleuser@outlook.com</span> Password: <span style="color: royalblue">12345</span>
</form>
</body>

</html>
