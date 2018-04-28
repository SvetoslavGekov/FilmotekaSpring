<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New product creation</title>
<base href="http://localhost:8080/FilmotekaSpring/">
</head>
<body>
	<form:form commandName="product" enctype="multipart/form-data">
      <table>
      	  <tr>
	      	  <td>
	      	  	<input type="hidden" name="category" value="${category}">
	      	  	<input type="hidden" name="productID" value="${product.id}">
	      	  </td>
      	  </tr>
          <tr>
              <td>Product Name:</td>
              <td><form:input path="name" type="text" required="required"/></td>
          </tr>
          <tr>
              <td>Release Date:</td>
              <td><form:input path="releaseDate" type="date" name="releaseDate" required="required" /></td>
          </tr>
          <tr>
              <td>Pg rating:</td>
              <td><form:input path="pgRating" type="text" required="required"/></td>
          </tr>
          <tr>
              <td>Duration:</td>
              <td><form:input path="duration" type="number" required="required" min="1"/></td>
          </tr>
          <tr>
              <td>Buy cost:</td>
              <td><form:input path="originalBuyCost" type="number" required="required" min="1"/></td>
          </tr>                    
           <tr>
              <td>Rent cost:</td>
              <td><form:input path="originalRentCost" type="number" required="required" min="1"/></td>
          </tr>  
          <tr>
              <td>Description:</td>
              <td><form:input path="description" type="textarea"/><br></td>
              
          </tr>
          <tr>
              <td>Poster:</td>
              <td><input type="file" name="posterFile" accept=".jpg"/><br>
              	${product.poster}
              </td>
              
          </tr>
          <tr>
              <td>Trailer:</td>
              <td><input type="file" name="trailerFile" accept=".avi"/>
              	${product.trailer}
              </td>
              
          </tr>
          <tr>
              <td>Writers:</td>
              <td><form:input path="writers" type="text"/></td>
          </tr>
          <tr>
              <td>Actors:</td>
              <td><form:input path="actors" type="text"/></td>
          </tr>
          <tr>
              <td>Sale Percent</td>
              <td><form:input path="salePercent" type="number"/></td>
          </tr>
          <tr>
          	<td>saleValidity</td>
          	<td><form:input path="saleValidity" type="date"/></td>
          </tr>
          
          <!-- Category specific info -->
          <c:set var = "categoryID" value = "${product.productCategory.id}"/>
          <c:choose>
          	<c:when test="${categoryID == 1}">
	          <tr>
	          	<td>Director</td>
	          	<td><form:input path="director" type="text"/></td>
	          </tr>
          	</c:when>
          	
           	<c:when test="${categoryID == 2}">
	          <tr>
	          	<td>Season</td>
	          	<td><form:input path="season" type="number"/></td>
	          </tr>
	          <tr>
	          	<td>Finished Airing</td>
	          	<td><form:input path="finishedAiring" type="date"/></td>
	          </tr>
          	</c:when>         	
          </c:choose>
          <tr>
          	<td>Genres:</td>
              <td>
                  <%-- Property is of an array or of type java.util.Collection --%>
                  <form:checkboxes path="genres" items="${genres}" itemLabel="value" itemValue="id"/>
              </td>
          </tr>

          <tr>
              <td colspan="2">
                  <input type="submit" value="Create product" />
              </td>
          </tr>
      </table>
  </form:form>
</body>
</html>