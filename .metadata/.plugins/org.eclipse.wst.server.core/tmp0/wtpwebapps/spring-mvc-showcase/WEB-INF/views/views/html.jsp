<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
	<title>My HTML View</title>
	<link href="<c:url value="/resources/form.css" />" rel="stylesheet"  type="text/css" />		
</head>
<body>
<div class="success">
	<h3>foo: "${foo}"</h3>
	<h3>fruit: "${fruit}"</h3>
</div>


<table style="font-size: 5; border:8 " border="8">
<tr>
	<td>옵션명</td>
	<td>총수</td>
	<td>평균평점</td>
</tr>

<c:forEach items="${total}" var="item">
	<tr>
		<td>${item.key}</td>
		<td>${item.value.totalCount}</td>
		<td>${item.value.avg}</td>
	</tr>


</c:forEach>

</table>



<br>
<br>
<br>
<br>
<br>
<table style="font-size: 5; border:8; size: 100% " border="8">
<tr>
	<td>상품명</td>
	<td>옵션</td>
	<td>평점</td>
	<td>내용</td>
</tr>

<c:forEach items="${list}" var="map">
	<tr>
		<td>${map["pName"]}</td>
		<td>${map["oName"]}</td>
		<td>${map["score"]}</td>
		<td>${map["contents"]}</td>
	</tr>


</c:forEach>

</table>

</body>
</html>