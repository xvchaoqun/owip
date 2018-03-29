<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${uv.realname}-菜单预览</h3>
</div>
<div class="modal-body">
    <div style="float: left; width: 300px;padding-right: 20px;">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><span class="text-primary bolder"><i
                        class="fa fa-user"></i>   系统角色</span>
                </h3>
            </div>
            <div class="collapse in">
                <div class="panel-body">
                    <ul class="list-unstyled spaced2">
                        <c:forEach items="${fn:split(uv.roleIds,',')}" var="id"
                                   varStatus="vs">
                            <li style="margin-top: 0">
                                <i class="ace-icon fa fa-circle green"></i>
                                    ${roleMap.get(cm:toInt(id)).description}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div style="float: left; width: 250px;">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title"><span class="text-success bolder"><i
                    class="fa fa-list"></i>   系统菜单</span>
            </h3>
        </div>
        <div class="collapse in">
            <div class="panel-body">
                <div id="sidebar-review2" style="margin-left: 15px">
                    <div class="sidebar">
                        <c:import url="/menu?username=${uv.username}"/>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $("#sidebar-review2 a").removeClass("hashchange").removeAttr("href")
            $("#sidebar-review2 ul.submenu").each(function () {
                if ($("li", this).length == 0) {
                    $(this).closest("li").find(".menu-text").css("color", "red");
                    $(this).remove();
                }
            });
            $('#sidebar-review2 .sidebar').ace_sidebar();
        </script>
    </div>
    </div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">关闭</a>
</div>