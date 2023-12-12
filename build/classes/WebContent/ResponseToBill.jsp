<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>List of ResponseToBills</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: rgb(34,193,195);
			background: radial-gradient(circle, rgba(34,193,195,1) 21%, rgba(127,49,252,1) 100%);
            margin: 0;
            padding: 0;
        }
        div {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background: rgb(127,49,252);
			background: radial-gradient(circle, rgba(127,49,252,1) 21%, rgba(34,193,195,1) 100%);
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            background-color: #d6cbd3;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        
        tr:hover {
        	background-color: #A584A3;
            
        }
        
        input[type="number"],
        input[type="text"],
        select {
            width: calc(100% - 10px);
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 4px;
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        a{
        color:white;
        }
    </style>
</head>
<body>

<div align="center">
    <form action="ResponseToBill" method="post">
        <input type="submit" value="Click here" />
    </form>
    <br>
    <a href="contractor.jsp" target="_self">Logout</a><br><br>

    <table border="1" cellpadding="5">
        <caption><h2>List of ResponseToBills</h2></caption>
        <tr>
            <th>ResponseToBillID</th>
            <th>BillID</th>
            <th>ClientID</th>
            <th>Amount</th>
            <th>ResponseDate</th>
            <th>Note</th>
        </tr>

        <c:forEach var="responseToBill" items="${listResponseToBills}">
            <tr style="text-align:center">
                <td><c:out value="${responseToBill.responseToBillID}" /></td>
                <td><c:out value="${responseToBill.billID}" /></td>
                <td><c:out value="${responseToBill.clientID}" /></td>
                <td><c:out value="${responseToBill.amount}" /></td>
                <td><c:out value="${responseToBill.responseDate}" /></td>
                <td><c:out value="${responseToBill.note}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
