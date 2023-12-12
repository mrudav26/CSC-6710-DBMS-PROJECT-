<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>List of Good Clients</title>
        <style>
        body {
            font-family: Arial, sans-serif;
            background: -webkit-linear-gradient(left, #25c481, #25b7c4);
  background: linear-gradient(to right, #25c481, #25b7c4);
            
            margin: 0;
            padding: 0;
        }

        div {
            text-align: center;
            margin: 20px;
        }

        form {
            margin-bottom: 20px;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
            background-color: #d6cbd3;
        }

        th {
            background-color: #d6cbd3;
            font-weight: bold;
        }
        
        th:hover{
            background-color: #A584A3;
            color:white;
        }
        
        td:hover{
            background-color: #A584A3;
            color:white;
        }
        

        h2 {
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #d6cbd3;
        }

        tr:hover {
            background-color: #ddd;
        }

        td[colspan="8"] {
            text-align: center;
        }

        a {
            text-decoration: none;
            color: #333;
            margin-right: 10px;
        }

        a:hover {
            color: #4caf50;
        }

        caption {
            font-size: 24px;
            margin-bottom: 10px;
        }

        input[type="submit"] {
            background-color: #d6cbd3;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin-bottom: 10px;
            cursor: pointer;
            color: black;
        }

        input[type="submit"]:hover {
            background-color: #A584A3;
            color:white;
        }
    </style>
</head>
<body>

<div align="center">
    <form action="getGoodClients" method="post">
        <input type="submit" value="Click here" />
    </form>
    <table border="1" cellpadding="5">
        <caption style="display: inline;" > <h2 style="display: inline;">List of Good Clients</h2></caption>
        <table border="1">
        <tr>
            <th>ClientID</th>
            <th>FirstName</th>
            <th>LastName</th>
            <th>GeneratedDate</th>
            <th>BillStatus</th>
            <th>PaymentStatus</th>
        </tr>

        <c:forEach var="client" items="${goodClients}">
            <tr style="text-align:center">
                <td><c:out value="${client.clientID}" /></td>
                <td><c:out value="${client.firstName}" /></td>
                <td><c:out value="${client.lastName}" /></td>
                <td><c:out value="${client.generatedDate}" /></td>
                <td><c:out value="${client.billStatus}" /></td>
                <td><c:out value="${client.paymentStatus}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
