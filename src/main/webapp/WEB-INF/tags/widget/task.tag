<%@ attribute name="task" required="true" type="fr.todooz.domain.Task" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div>
	<p><fmt:formatDate value="${task.date}" pattern="dd MMM yyyy"/></p>
	<span class="lead"><a href="/edit/${task.id}">${fn:escapeXml(task.title)}</a></span>
	<c:forEach var="tag" items="${task.tagArray}">
		<span class="badge badge-info">${fn:escapeXml(tag)}</span>
	</c:forEach>
	<p>${task.text}</p>
	<a href="/edit/${task.id}/delete"><button class="btn btn-danger">Delete</button></a>
</div>