<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row dispatch_cadres">
    <div class="dispatch">
        <div class="widget-box">
            <div class="widget-header">

                <h4 class="smaller">
                    任免文件
                    <a href="javascript:;" class="hideView pull-right btn btn-xs btn-success"
                       style="margin-right: 10px; top: -5px;">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
        <div class="select-form">
            <div class="form-inline">
                <div class="input-group">
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    <input class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatch.year}"/>
                </div>
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                        name="dispatchTypeId" data-placeholder="请选择发文类型">
                    <option value="${dispatchType.id}">${dispatchType.name}</option>
                </select>
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects"
                        name="dispatchId" data-placeholder="请选择发文">
                    <option value="${dispatch.id}">${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year)}</option>
                </select>
            </div>
        </div>
        <div id="dispatch-file-view">
            <c:import url="${ctx}/swf/preview?type=html&path=${cm:encodeURI(dispatch.file)}"/>
        </div>
                    </div></div></div>
    </div>
    <div class="cadres">
        <div id="dispatch-cadres-view">
            <c:import url="/dispatch_cadres_admin?dispatchId=${dispatch.id}"/>
        </div>
        <shiro:hasPermission name="dispatch:check">
            <c:if test="${param.check==1}">
        <div class="clearfix form-actions center">
            <button class="btn btn-primary" onclick="_check()">
                <i class="ace-icon fa fa-check "></i>
                复核完成，信息确认
            </button>
        </div>
            </c:if>
        </shiro:hasPermission>
   </div>
</div>

<script>
    <shiro:hasPermission name="dispatch:check">
    function _check(){
        bootbox.confirm("确认复核完成？", function(result){
            if(result){
                $.post("${ctx}/dispatch_check",{id:'${dispatch.id}'}, function(ret){
                    if(ret.success){
                        $.hideView();
                    }
                })
            }
        });
    }
    </shiro:hasPermission>
    register_date($('.date-picker'));
    register_dispatch_select($('.dispatch_cadres select[name=dispatchTypeId]'),
            $(".dispatch_cadres input[name=year]"), $(".dispatch_cadres select[name=dispatchId]"));
</script>