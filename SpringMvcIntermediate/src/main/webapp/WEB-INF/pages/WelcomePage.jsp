<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<h1>Welcome page</h1>

<h2>ClassName : ${classname}</h2>
<h4>Executed Time : ${executeTime}ms</h4>
<h1>Spring MVC internationalization example</h1>

Language : <a href="?language=en">English</a>|
<a href="?language=zh_CN">Chinese</a>

<h3>
	welcome.springmvc : <spring:message code="welcome.springmvc" text="default text" />
</h3>


Current Locale : ${pageContext.response.locale}

</body>
</html>