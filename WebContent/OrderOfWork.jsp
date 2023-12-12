<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>List of Orders of Work</title>
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
     <form action="OrderOfWork" method="post">
      <input type = "submit" value = "Click here"/>
    <table border="1" cellpadding="5">
        <caption><h2>List of Orders of Work</h2></caption>
        <tr>
            <th>Order ID</th>
            <th>Request ID</th>
            <th>Client ID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Status</th>
            <th>Number Of Trees Cut</th>
            <th>Date Of Cut</th>
            <th>Contractor ID</th>
        </tr>

        <c:forEach var="order" items="${listOrders}">
            <tr style="text-align:center">
                <td><c:out value="${order.orderID}" /></td>
                <td><c:out value="${order.requestID}" /></td>
                <td><c:out value="${order.clientID}" /></td>
                <td><c:out value="${order.startDate}" /></td>
                <td><c:out value="${order.endDate}" /></td>
                <td><c:out value="${order.status}" /></td>
                <td><c:out value="${order.numberOfTreesCut}" /></td>
                <td><c:out value="${order.dateOfCut}" /></td>
                <td><c:out value="${order.contractorID}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
