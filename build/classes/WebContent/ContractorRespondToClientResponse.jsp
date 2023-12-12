<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Quote Request Details</title>
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
     <form action="ContractorRespondToClientResponse" method="post">
      <input type = "submit" value = "Click here"/>
	</form>
    <table border="1" cellpadding="5">
        <caption style="display: inline;" > <h2 style="display: inline;">List of Client Responses</h2></caption>
        <table border="1">
            <tr>
                <th>Contractor Response ID</th>
                <th>Contractor ID</th>
                <th>ClientResponse ID</th>  
                <th>Status</th>         
                <th>Response Date</th>
                <th>Note</th>
                <th>ModifiedPrice</th>
                <th>ModifiedWorkPeriodFrom</th>
                <th>ModifiedWorkPeriodTo</th>   
            </tr>
            <c:forEach var="response" items="${listClientResponses}">
                <tr>
                    <td><c:out value="${response.contractorResponseID}" /></td>
                    <td><c:out value="${response.contractorID}" /></td>
                    <td><c:out value="${response.clientResponseID}" /></td> 
                    <td><c:out value="${response.status}" /></td>                   
                    <td><c:out value="${response.responseDate}" /></td>                   
                    <td><c:out value="${response.note}" /></td>
                    <td><c:out value="${response.modifiedPrice}" /></td>
                    <td><c:out value="${response.modifiedWorkPeriodFrom}" /></td>
                    <td><c:out value="${response.modifiedWorkPeriodTo}" /></td>   
                </tr>
            </c:forEach>
        </table>
</body>
</html>