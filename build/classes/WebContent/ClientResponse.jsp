<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Client Response a Quote Response</title>
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
    </style>
</head>
<body>
<div align="center">
    <h1>Client Response a Quote Response</h1>
    <form action="ClientResponse" method="post">
        <table border="1" cellpadding="5">
        
                    
            <tr>
                <th>ContractorID:</th>
                <td align="center" colspan="3">
                    <input type="number" name="ContractorID" size="45"  onfocus="this.value=''" />                        
                </td>
            </tr>
        
            <tr>
                <th>ResponseID:</th>
                <td align="center" colspan="3">
                    <input type="number" name="ResponseID" size="45"  onfocus="this.value=''" />                        
                </td>
            </tr>
            
            <tr>
                <th>ResponseDate:</th>
                <td align="center" colspan="3">
                    <input type="text" name="ResponseDate" size="45"  onfocus="this.value=''" />                        
                </td>
            </tr>
           
            <tr>
                <th>Status:</th>
                <td align="center" colspan="3">
                    <select name="Status" style="width: 100%;">
                        <option value="Accepted">Accepted</option>
                        <option value="Rejected">Rejected</option>
                        <option value="Pending">Pending</option>
                        <option value="RequestRevision">Request Revision</option>
                    </select>
                </td>
            </tr>
                                 
            <tr>
                <th>Note:</th>
                <td align="center" colspan="3">
                    <input type="text" name="Note" size="45"  onfocus="this.value=''" />                        
                </td>
            </tr>
            
            <tr>
                <td align="center" colspan="5">
                    <input type="submit" value="ClientResponse" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
