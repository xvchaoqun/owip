<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.SYS_ROLE_TYPE_ADD%>" var="SYS_ROLE_TYPE_ADD"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${uv.realname}-菜单预览</h3>
</div>
<div class="modal-body">
    <div style="float: left; width: 300px;padding-right: 10px;">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><span class="text-primary bolder"><i
                        class="fa fa-user"></i>   系统角色</span>
                </h3>
            </div>
            <div class="collapse in">
                <div class="panel-body" style="max-height: 480px;overflow-y: auto;">
                    <ul class="list-unstyled spaced2">
                        <c:forEach items="${fn:split(uv.roleIds,',')}" var="id"
                                   varStatus="vs">
                            <li style="margin-top: 0">
                                <c:if test="${not empty roleMap.get(cm:toInt(id))}">
                                   <%-- ${roleMap.get(cm:toInt(id)).type==SYS_ROLE_TYPE_ADD?'<i class="ace-icon fa fa-plus-circle green"></i>':'<i class="ace-icon fa fa-minus-circle red"></i>'}--%>
                                    <i class="ace-icon fa fa-plus-circle green"></i>${roleMap.get(cm:toInt(id)).name}
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div style="float: left; width: 250px;padding-right: 10px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="text-success bolder"><i
                    class="fa fa-internet-explorer"></i>   网页端菜单</span>
            </h3>
        </div>
        <div class="collapse in">
            <div class="panel-body">
                <div class="sidebar-review2" style="margin-left: 10px">
                    <div class="sidebar">
                        <c:import url="/menu?username=${uv.username}&isPreview=1&isMobile=0"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div style="float: left; width: 250px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="text-warning bolder"><i
                    class="fa fa-mobile"></i>   手机端菜单</span>
            </h3>
        </div>
        <div class="collapse in">
            <div class="panel-body">
                <div class="sidebar-review2" style="margin-left: 10px">
                    <div class="sidebar">
                        <c:import url="/menu?username=${uv.username}&isPreview=1&isMobile=1"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>
<style>
    .sidebar-review2 .sidebar{
        max-height: 450px;
        overflow-y:auto;
    }
</style>
<script type="text/javascript">
    $(".sidebar-review2 a").removeClass("hashchange").removeAttr("href")
    $(".sidebar-review2 #sidebar-collapse").removeClass("sidebar-collapse")
    $(".sidebar-review2 ul.submenu").each(function () {
        if ($("li", this).length == 0) {
            $(this).closest("li").find(".menu-text").css("color", "red");
            $(this).remove();
        }
    });
    $('.sidebar-review2 .sidebar').ace_sidebar();
</script>