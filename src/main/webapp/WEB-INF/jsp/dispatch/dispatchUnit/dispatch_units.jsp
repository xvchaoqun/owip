<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row dispatch_cadres">
    <div class="dispatch">
        <div class="widget-box">
            <div class="widget-header">

                <h4 class="widget-title">
                    机构调整文件
                    <a href="javascript:;" class="hideView pull-right btn btn-xs btn-success"
                       style="margin-right: 10px; margin-top: 8px;">
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
                    <input style="width: 80px;" class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                           data-date-format="yyyy" data-date-min-view-mode="2" value="${empty dispatch?_thisYear:dispatch.year}"/>
                </div>
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                        name="dispatchTypeId" data-placeholder="请选择发文类型">
                    <option value="${dispatchType.id}">${dispatchType.name}</option>
                </select>
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatch_selects?isCadre=0"
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
            <c:import url="/dispatch_units_admin?dispatchId=${dispatch.id}"/>
        </div>

   </div>
</div>

<script>

    $.register.date($('.date-picker'));
    var $dispatchSelect = $.register.dispatch_select($('.dispatch_cadres select[name=dispatchTypeId]'),
            $(".dispatch_cadres input[name=year]"), $(".dispatch_cadres select[name=dispatchId]"));
    $dispatchSelect.on("change", function () {
        var id = $(this).val();
        if (id > 0) {
            $("#body-content-view").load(ctx + "/dispatch_units?dispatchId=" + id);
            /*$("#dispatch-file-view").load(ctx + "/swf_preview?way=3&id=" + id + "&type=file");
             $("#dispatch-cadres-view").load(ctx + "/dispatch_units_admin?dispatchId=" + id);

             var dispatchType = $(this).select2("data")[0]['type']||'';
             var year = $(this).select2("data")[0]['year']||'';
             $dispatchTypeSelect.val(dispatchType);
             $year.val(year);*/
        }
    });
</script>