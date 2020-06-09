<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>主讲课程</h3>
</div>
<div class="modal-body">
    <div class="popTableDiv"
         data-url-page="${ctx}/cet/cetExpert_info">
        <table class="table table-actived table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th>课程编号</th>
                <th>课程名称</th>
                <th>设立时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cetCourses}" var="cetCourse" varStatus="st">
                <tr>
                    <td nowrap width=50">${cetCourse.num}</td>
                    <td nowrap width="280">${cetCourse.name}</td>
                    <td nowrap width=80">${cm:formatDate(cetCourse.foundDate, "yyyy.MM.dd")}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="well well-lg center noRecord" hidden>
            <h4 class="green lighter">暂无记录</h4>
        </div>
    </div>
</div>
<script>
    var _cetCourses = ${cm:toJSONObject(cetCourses)};
    if (_cetCourses.length == 0){
        $(".noRecord").show();
    } else {
        $(".noRecord").hide();
    }

</script>