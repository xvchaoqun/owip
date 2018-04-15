<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent" style="width: 800px">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success" data-url="${ctx}/modifyCadreAuth">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>

        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a href="javascript:;">批量添加权限设置</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <form class="form-horizontal" action="${ctx}/modifyCadreAuth_batchAdd" id="batchForm" method="post">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">请选择干部</label>

                        <div class="col-xs-9">
                            <div id="tree3" style="height: 500px;">
                                <div class="block-loading"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">起始日期</label>

                        <div class="col-xs-6" style="width: 225px;">
                            <div class="input-group">
                                <input class="form-control date-picker" name="_startTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(modifyCadreAuth.startTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                        <span class="help-block inline label-text small">从0点开始</span>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">结束日期</label>

                        <div class="col-xs-6" style="width: 225px;">
                            <div class="input-group">
                                <input class="form-control date-picker" name="_endTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(modifyCadreAuth.endTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                        <span class="help-block inline label-text small">截止24点</span>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">是否永久有效</label>

                        <div class="col-xs-6">
                            <label>
                                <input name="isUnlimited" ${modifyCadreAuth.isUnlimited?"checked":""} type="checkbox"/>
                                <span class="lbl"></span>
                            </label>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="clearfix form-actions center">
        <button class="btn btn-info" type="submit">
            <i class="ace-icon fa fa-check bigger-110"></i>
            提交
        </button>
    </div>
</div>
<script>

    $('#batchForm input[name=isUnlimited]').on('switchChange.bootstrapSwitch', function(event, state) {
        if($(this).bootstrapSwitch("state")) {
            $("#batchForm input[name=_startTime]").val('').prop("disabled", true);
            $("#batchForm input[name=_endTime]").val('').prop("disabled", true);
        }else{
            $("#batchForm input[name=_startTime]").prop("disabled", false);
            $("#batchForm input[name=_endTime]").prop("disabled", false);
        }
    });

    $.getJSON("${ctx}/modifyCadreAuth/selectCadres_tree", {}, function (data) {
        var treeData = data.tree.children;
        $("#tree3").dynatree({
            checkbox: true,
            selectMode: 3,
            children: treeData,
            onSelect: function (select, node) {
                node.expand(node.data.isFolder && node.isSelected());
            },
            cookieId: "dynatree-Cb3",
            idPrefix: "dynatree-Cb3-"
        });
    });

    $("#body-content-view button[type=submit]").click(function(){$("#batchForm").submit(); return false;});
    $("#batchForm").validate({
        submitHandler: function (form) {

            var cadreIds = $.map($("#tree3").dynatree("getSelectedNodes"), function (node) {
                if (!node.data.isFolder && !node.data.hideCheckbox)
                    return node.data.key;
            });
            if(cadreIds.length==0){
                SysMsg.info("请选择干部");
                return;
            }

            if(!$('#batchForm input[name=isUnlimited]').bootstrapSwitch("state")){
                if($.trim($("#batchForm input[name=_startTime]").val())=='' &&
                        $.trim($("#batchForm input[name=_endTime]").val())==''){
                    SysMsg.info("请选择时间范围");
                    return;
                }
            }

            $(form).ajaxSubmit({
                data: {cadreIds: cadreIds},
                success: function (ret) {
                    if (ret.success) {
                        $("#jqGrid").trigger("reloadGrid");
                        $(".hideView").click();
                    }
                }
            });
        }
    });
    $("#batchForm :checkbox").bootstrapSwitch();
    $.register.date($('.date-picker'), {startDate:'${_today}'});
    $.register.user_select($('#batchForm select[name=cadreId]'));
</script>