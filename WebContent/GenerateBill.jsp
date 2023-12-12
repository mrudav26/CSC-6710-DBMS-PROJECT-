<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Generate Bill</title>
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
    <h1>Generate a Bill</h1>
    <form action="GenerateBill" method="post">
        <table border="1" cellpadding="5">
        
            <tr>
                <th>OrderID:</th>
                    <td align="center" colspan="3">
                        <input type="number" name="OrderID" size="45"  onfocus="this.value=''" />                        
                    </td>
            </tr>
        
            <tr>
                <th>ContractorID:</th>
                    <td align="center" colspan="3">
                        <input type="number" name="ContractorID" size="45"  onfocus="this.value=''" />                        
                    </td>
            </tr>
        
            <tr>
                <th>Amount:</th>
                    <td align="center" colspan="3">
                        <input type="text" name="Amount" size="45"  onfocus="this.value=''" />                        
                    </td>
            </tr>
           
            <tr>
                <th>Generated Date:</th>
                    <td align="center" colspan="3">
                        <input type="number" name="GeneratedDate" size="45"  onfocus="this.value=''" />                        
                    </td>
            </tr>
             
            <tr>
                <th>Bill Status:</th>
                    <td colspan="3">
                        <select name="Status">
                            <option value="Issued">Issued</option>
                            <option value="Pending">Pending</option>
                            <option value="Paid">Paid</option>
                            <option value="Cancelled">Cancelled</option>
                        </select>
                    </td>
            </tr>
             
            <tr>
                <th>Payment Status:</th>
                    <td colspan="3">
                        <select name="Status">
                            <option value="Paid">Paid</option>
                            <option value="Unpaid">Unpaid</option>
                            <option value="Processing">Processing</option>
                            <option value="Overdue">Overdue</option>
                            <option value="Failed">Failed</option>
                        </select>
                    </td>
            </tr>

            <tr>
                <td align="center" colspan="5">
                    <input type="submit" value="Generate Bill" />
                </td>
         </tr>
        </table>
    </form>
    </div>
</body>
</html>