<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<form class="form-horizontal" id="modalForm" method="post">
    <div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="member-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <i class="ace-icon fa fa-user"></i>教职工党员个人信息
                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a data-url="${ctx}/memberTeacher_base?userId=${param.userId}">基本信息</a>
                        </li>
                        <li>
                            <a data-url="${ctx}/memberTeacher_member?userId=${param.userId}">党籍信息</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                    <c:import url="/memberTeacher_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>

    <div class="modal-footer draggable">
        <a href="#" data-dismiss="modal" class="btn btn-default">关闭</a>
    </div>
</form>
<script>
    $("#member-box .nav-tabs li a").click(function(){
        $this = $(this);
        $("#member-box  .nav-tabs li").removeClass("active");
        $this.closest("li").addClass("active");
        $("#member-box  .tab-content").load($(this).data("url"));
    });
</script>