<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
    <div class="col-xs-12">

        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-block alert-success">
                    <i class="ace-icon fa fa-check green"></i>
                    欢迎使用${_plantform_name}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 infobox-container">
                    <shiro:hasPermission name="m:applySelfList:*">
                    <div class="ahref infobox infobox-blue2" data-url="${ctx}/m/abroad/applySelfList?status=0">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-history"></i>
                        </div>
                        <div class="infobox-data">
                            <span class="infobox-data-number">${notApprovalCount} <span style="font-size: 10pt;">待审批</span></span>
                            <div class="infobox-content">因私出国（境）申请</div>

                        </div>
                    </div>
                    <div class="ahref infobox infobox-green" data-url="${ctx}/m/abroad/applySelfList?status=1">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-check-square-o"></i>
                        </div>
                        <div class="infobox-data">
                            <span class="infobox-data-number">${hasApprovalCount}  <span style="font-size: 10pt;">已审批</span></span>
                            <div class="infobox-content">因私出国（境）申请</div>
                        </div>
                    </div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="m:applySelf:*">
                    <div class="ahref infobox infobox-blue2" data-url="${ctx}/m/abroad/applySelf?status=0">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-history"></i>
                        </div>
                        <div class="infobox-data">
                            <span class="infobox-data-number">${notApprovalCount} <span style="font-size: 10pt;">未完成审批</span></span>
                            <div class="infobox-content">因私出国（境）申请</div>

                        </div>
                    </div>
                    <div class="ahref infobox infobox-green" data-url="${ctx}/m/abroad/applySelf?status=1">
                        <div class="infobox-icon">
                            <i class="ace-icon fa fa-check-square-o"></i>
                        </div>
                        <div class="infobox-data">
                            <span class="infobox-data-number">${hasApprovalCount}  <span style="font-size: 10pt;">已完成审批</span></span>
                            <div class="infobox-content">因私出国（境）申请</div>
                        </div>
                    </div>
                    </shiro:hasPermission>
                </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
</div>