<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div id="userInfo">
    <table class="table table-striped table-condensed table-bordered" data-offset-top="132">
        <thead>
            <th width="50" style="text-align: center">序号</th>
            <th width="150">参数名称</th>
            <th width="250">描述</th>
            <th>值</th>
        </thead>
        <tbody>
         <c:forEach items="${columnBeanMap}" var="cbEntity" varStatus="vs">
            <tr>
                <td style="text-align: center">${vs.count}</td>
                <td>${cbEntity.value.name}</td>
                <td>${cbEntity.value.comments}</td>
                <td>${valuesMap.get(cbEntity.key)}</td>
            </tr>
         </c:forEach>
        </tbody>
    </table>
</div>
<script>
  stickheader();
</script>