<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row" id="cartogram">
    <shiro:hasPermission name="suspend:branch">
        <c:import url="/suspend_branch?branchId=${branchId}"/>
    </shiro:hasPermission>
    <div class="row">
        <div class="col-xs-12">
            <div class="widget-box transparent">
                <div class="widget-header widget-header-flat">
                    <h4 class="widget-title lighter">
                        <i class="ace-icon fa fa-star green"></i>
                        党建信息统计
                    </h4>
                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-4">
                        <div class="row">
                            <c:import url="/stat_branch_member_count?branchId=${branchId}"/>
                            <c:import url="/stat_branch_member_age?branchId=${branchId}"/>
                            <c:import url="/stat_branch_member_apply?branchId=${branchId}"/>
                        </div>
                    </div><!-- /.widget-main -->
                </div><!-- /.widget-body -->
            </div><!-- /.widget-box -->
        </div><!-- /.col -->
    </div>
</div>
<script>
    $(document).ready(function(){
        var branchAdmin = ${cm:toJSONObject(cm:hasRole("branchAdmin"))};
        if (branchAdmin) {
            $.post("${ctx}/stat_branch_member_remind", {branchId: ${branchId}}, function(ret){
                if (ret.success) {
                    var count = ret.msg;
                    if (count > 0) {
                        SysMsg.confirm( "党员发展中存在"+ count + "位预备党员入党时间已超过一年，请及时进行转正审批操作","提示", function (ret) {
                        });
                    }
                }
            })
        }
    })
</script>