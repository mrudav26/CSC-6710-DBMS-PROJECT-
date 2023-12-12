<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login to Database</title>
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
        p a {
		  color: white;
		}
    </style>
</head>
<body>
    <center><h1>Welcome to DS's Login page</h1></center>
    <div align="center">
        <p>${loginFailedStr}</p>
        <form action="login" method="post">
            <table border="1" cellpadding="5">
                <tr>
                    <th>Username:</th>
                    <td>
                        <input type="text" name="username" size="45" autofocus>
                    </td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td>
                        <input type="password" name="password" size="45">
                    </td>
                </tr>
                <tr>
                    <th>Role:</th>
                    <td>
                    
                         <select name="role" style="width: 100%;">
                            <option value="Client">Client</option>
                            <option value="Administrator">Administrator</option>
                            <option value="Contractor">Contractor</option>
                           
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Login">
                    </td>
                </tr>
            </table>
            <p>Don't have an account? 
            <a href="register.jsp" target="_self">Register Here</a> </p>
        </form>
</body>
</html>
