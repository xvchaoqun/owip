<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="cadre-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="btn btn-mini btn-success" onclick="closeView()">
                        <i class="ace-icon fa fa-backward"></i>
                        返回干部库</a>
                    <%--<i class="ace-icon fa fa-user"></i>干部个人信息--%>
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/cadre_base?id=${param.id}">基本信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreEdu_page?cadreId=${param.id}">学习经历</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreWork_page?cadreId=${param.id}">工作经历</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadrePost_page?cadreId=${param.id}">任职情况</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="">专技岗位过程信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="">管理岗位过程信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="">工勤岗位过程信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreParttime_page?cadreId=${param.id}">社会或学术兼职</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="">培训情况</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreCourse_page?cadreId=${param.id}">教学经历</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreResearch_page?cadreId=${param.id}">科研情况</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreWork_page?cadreId=${param.id}">其他奖励情况</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreWork_page?cadreId=${param.id}">家庭成员信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/cadreWork_page?cadreId=${param.id}">企业兼职情况</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                        <c:import url="/cadre_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
<script>
    $("#cadre-box .nav-tabs li a").click(function(){
        $this = $(this);
        $("#cadre-box .nav-tabs li").removeClass("active");
        $this.closest("li").addClass("active");
        if($(this).data("url")!='')
        $("#cadre-box .tab-content").load($(this).data("url"));
    });
</script>