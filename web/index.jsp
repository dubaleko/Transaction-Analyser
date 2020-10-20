<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <form method="post" action="servlet" enctype="multipart/form-data">
    <p>Merchant:<input name="merchant" type="text"/></p>
    <p>From Date:<input type="datetime-local" name="fromDate"></p>
    <p>To Date:<input type="datetime-local" name="toDate"></p>
    <p>File:<input type="file" name="file"></p>
    <input type="submit" value="GetData"/>
  </form>
  </body>
</html>
