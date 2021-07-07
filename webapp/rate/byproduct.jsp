<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lan" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Rate info</title>
        <meta charset="UTF-8">
    </head>
    <body>

<p><button onclick="sortName(false)"><lan:print message="rate.byproduct.jsp.button.sort_name"/></button></p>
<p><button onclick="sortName(true)"><lan:print message="rate.byproduct.jsp.button.sort_name_inverse"/></button></p>
<p><button onclick="sortPrice(false)"><lan:print message="rate.byproduct.jsp.button.sort_prise"/></button></p>
<p><button onclick="sortPrice(true)"><lan:print message="rate.byproduct.jsp.button.sort_prise_inverse"/></button></p>

<table id="rates">
  <tr>
    <th><lan:print message="rate.buproduct.jsp.table.name"/></th>
    <th><lan:print message="rate.buproduct.jsp.table.price"/></th>
  </tr>
  <c:forEach items="${rates}" var="rate">
  <tr>
    <td>${rate.name}</td>
    <td>
                    <fmt:setLocale value = "en_US"/>
                    <fmt:formatNumber value="${rate.price}" type="currency"/>
                </td>

    <c:if test = "${sessionScope.user.userRole == 'OPERATOR'}">
        <td>
            <form method="GET" action="/telecom/service/rate/info">
                <input type="hidden" name="rateId" value="${rate.id}"/>
                <input type="submit" value='<lan:print message="rate.byproduct.jsp.button.rate_info"/>'>
            </form>
         </td>
    </c:if>
    <c:if test = "${sessionScope.user.userRole == 'SUBSCRIBER'}">
        <c:if test = "${ratesMap.get(rate) == 'false'}">
            <c:if test = "${subscriber.lock == 'false'}">
                <c:if test = "${rate.unusable == 'false'}">
                <td>
                    <form method="POST" action="/telecom/service/add/subscribing">
                        <input type="hidden" name="rateId" value="${rate.id}"/>
                        <input type="hidden" name="productId" value="${productId}"/>
                        <input type="submit" value='<lan:print message="rate.byproduct.jsp.button.subscribing"/>'>
                    </form>
                </td>
                </c:if>
            </c:if>
        </c:if>
        <c:if test = "${ratesMap.get(rate) == 'true'}">
           <td><lan:print message="rate.buproduct.jsp.table.using_rate"/></td>
        </c:if>
    </c:if>
  </tr>
  </c:forEach>
</table>
<c:if test = "${sessionScope.user.userRole == 'OPERATOR'}">

      <form method="GET" action="/telecom/service/rate/add">
            <input type="hidden" name="productId" value="${productId}"/>
            <input type="submit" value='<lan:print message="rate.byproduct.jsp.button.add_rate"/>'>
      </form>

    </c:if>

     <form method="GET" action="/telecom/service/download/rate">
                <input type="hidden" name="productId" value="${productId}"/>
                <input type="submit" value='<lan:print message="rate.byproduct.jsp.button.download"/>'>
     </form>
          <c:if test = "${sessionScope.user.userRole == 'OPERATOR'}">
          <form method="GET" action="/telecom/operator/home.jsp">
                      <input type="submit" value='<lan:print message="rate.unusable.jsp.button.home"/>'>
                  </form>
                  </c:if>
<c:if test = "${sessionScope.user.userRole == 'SUBSCRIBER'}">
<form method="GET" action="/telecom/subscriber/home.jsp">
        <input type="submit" value='<lan:print message="subscriber.lock.jsp.button.home"/>'>
    </form>
</c:if>

<script>
function sortName(inverse) {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("rates");
  switching = true;
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName('TD')[0];
      y = rows[i + 1].getElementsByTagName('TD')[0];
      //check if the two rows should switch place:
      if ((x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) ^ inverse) {
        //if so, mark as a switch and break the loop:
        shouldSwitch = true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}

function sortPrice(inverse) {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("rates");
  switching = true;
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName('TD')[1];
      y = rows[i + 1].getElementsByTagName('TD')[1];
      //check if the two rows should switch place:
      if ((parseInt(x.innerHTML, 10) > parseInt(y.innerHTML, 10)) ^ inverse) {
        //if so, mark as a switch and break the loop:
        shouldSwitch = true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}
        </script>
    </body>
</html>