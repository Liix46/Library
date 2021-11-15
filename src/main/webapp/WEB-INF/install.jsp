<%@ page import="com.example.library.utils.Db" %><%--
  Created by IntelliJ IDEA.
  User: bojcev_a
  Date: 11/10/2021
  Time: 7:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String confirm = request.getParameter("confirm");
%>
<html>
<head>
    <title>Library</title>
</head>
<body>
<h1>DB is empty</h1>
<% if(confirm == null) { %>

<a href="?confirm=true">INSTALL</a>

<% } else if( Db.getBookDb().createTable() ) { %>

<b>CREATED</b>
<script>
    setTimeout(
        ()=>{window.location.href = window.location.pathname},
        1000
    );
</script>
<% } else { %>

<i>CREATE ERROR see server logs</i>

<% } %>

</body>
</html>

