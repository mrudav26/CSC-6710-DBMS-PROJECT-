<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
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
		  text-align: center;
		}
    </style>
</head>
<body>
    <div>
        <p>${errorOne}</p>
        <p>${errorTwo}</p>
        <form action="register" method="post">
            <table border="1" cellpadding="5">
                <tr>
                    <th>First name:</th>
                    <td align="center" colspan="3">
                        <input type="text" name="FirstName" size="45"  onfocus="this.value=''" />                        
                    </td>
                </tr>
                
                <tr>
                    <th>Last name:</th>
                    <td align="center" colspan="3">
                        <input type="text" name="LastName" size="45"  onfocus="this.value=''" />                        
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td align="center" colspan="3">
                       <input type="email" name="Email" size="45" onfocus="this.value=''" />
                    </td>
                </tr>
                <tr>
                    <th>PhoneNumber:</th>
                    <td align="center" colspan="3">
                       <input type="PhoneNumber" name="PhoneNumber" size="45" onfocus="this.value=''" />
                    </td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td align="center" colspan="3">                        
                        <input type="password" name="Password" size="45" onfocus="this.value=''" />                        
                    </td>
                </tr>
                <tr>
                    <th>Password Confirmation:</th>
                    <td align="center" colspan="3">
                       <input type="password" name="confirmation" size="45" onfocus="this.value=''" />
                    </td>
                </tr>
                <tr>
                    <th>CreditCardInfo:</th>
                    <td align="center" colspan="3">
                       <input type="CreditCardInfo" name="CreditCardInfo" size="45" onfocus="this.value=''" />
                    </td>
                </tr>
                <tr>
                    <th>Role:</th>
                    <td align="center" colspan="3">
                        <select name="Role" style="width: 100%;">
                            <option value="Client">Client</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="5">
                        <input type="submit" value="Register" />
                    </td>
                </tr>
            </table>
            <center><p>Already Registered? <a href="login.jsp" target="_self">Return to Login Page</a></p></center>
        </form>
    </div>
</body>
</html>
