<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="recent-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <i class="ace-icon fa fa-user"></i>学生党员个人信息
                </h4>

                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#base-tab" data-url="${ctx}/memberStudent_base?userId=${param.userId}">基本信息</a>
                        </li>
                        <li>
                            <a href="#member-tab" data-url="${ctx}/memberStudent_member?userId=${param.userId}">党籍信息</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                    <c:import url="/memberStudent_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
                <%--<button class="btn btn-info" type="button">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    修改
                </button>--%>

                &nbsp; &nbsp; &nbsp;
                <button class="btn" type="button" onclick="history.back()">
                    <i class="ace-icon fa fa-undo bigger-110"></i>
                    返回
                </button>
            </div>
        </div>
    </div>

</div>
<script>
    $(".nav-tabs li a").click(function(){
        $this = $(this);
        $(".nav-tabs li").removeClass("active");
        $this.closest("li").addClass("active");
        $(".tab-content").load($(this).data("url"));
    });
</script>