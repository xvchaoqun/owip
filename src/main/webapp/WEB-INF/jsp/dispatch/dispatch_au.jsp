<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="scDispatch:list">
<div class="widget-box">
    <div class="widget-header">
        <h4 class="smaller">
            文件签发稿
            <div class="buttons pull-right ">
                <button style="margin-right: 10px;top: -5px;"
                        class="popupBtn btn btn-primary btn-xs" type="button"
                        data-width="1200"
                        data-url="${ctx}/sc/scDispatch_popup?year=${_thisYear}">
                    <i class="fa fa-plus-circle"></i> 从“文件起草签发”中选择
                </button>
            </div>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main" id="scDispatchDiv">

        </div>
    </div>
</div>
</shiro:hasPermission>
<div class="widget-box">
    <div class="widget-header">
        <h4 class="smaller">
            ${dispatch!=null?"修改":"添加"}发文
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/dispatch_au" id="modalForm" method="post"
                  enctype="multipart/form-data">
                <div class="row">
                    <input type="hidden" name="id" value="${dispatch.id}">
                    <input type="hidden" name="scDispatchId" value="${dispatch.scDispatchId}">

                    <div class="form-group">
                        <label class="col-xs-3 control-label">年份</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" placeholder="请选择年份" name="year"
                                       type="text"
                                       data-date-format="yyyy" data-date-min-view-mode="2" value="${year}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">发文类型</label>

                        <div class="col-xs-6">
                            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchType_selects"
                                    name="dispatchTypeId" data-placeholder="请选择发文类型" data-width="240">
                                <option value="${dispatchType.id}">${dispatchType.name}</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">发文号</label>

                        <div class="col-xs-6">
                            <input class="form-control" type="text" name="code" value="${dispatch.code}">
                            <span class="label-inline"> * 留空自动生成</span>
                        </div>
                    </div>
                    <div class="form-group" id="scCommitteesDiv" style="display: none">
                        <label class="col-xs-3 control-label">党委常委会</label>
                        <div class="col-xs-8 label-text">

                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">党委常委会日期</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input class="form-control date-picker" name="_meetingTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(dispatch.meetingTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">发文日期</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" name="_pubTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(dispatch.pubTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">任免日期</label>

                        <div class="col-xs-6">
                            <div class="input-group">
                                <input required class="form-control date-picker" name="_workTime" type="text"
                                       data-date-format="yyyy-mm-dd"
                                       value="${cm:formatDate(dispatch.workTime,'yyyy-MM-dd')}"/>
                                <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">任命人数</label>

                        <div class="col-xs-6">
                            <input required class="form-control digits" type="text" name="appointCount"
                                   value="${dispatch.appointCount}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">免职人数</label>

                        <div class="col-xs-6">
                            <input required class="form-control digits" type="text" name="dismissCount"
                                   value="${dispatch.dismissCount}">
                        </div>
                    </div>
                    <input type="hidden" name="file">
                    <input type="hidden" name="fileName">
                    <%--<div class="form-group">
                        <label class="col-xs-3 control-label">任免文件</label>
                        <div class="col-xs-6">
                            <input class="form-control" type="file" name="_file" />
                        </div>
                    </div>--%>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">上会ppt</label>

                        <div class="col-xs-6">
                            <input class="form-control" type="file" name="_ppt"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>

                        <div class="col-xs-6">
                            <textarea class="form-control limited" name="remark">${dispatch.remark}</textarea>
                        </div>
                    </div>
                </div>
                <div class="clearfix form-actions center">
                    <button id="submitBtn" class="btn btn-info btn-sm" type="button"
                            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
                        <i class="ace-icon fa fa-check "></i>
                        ${dispatch!=null?"修改":"添加"}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/template" id="scDispatchTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>年份</td>
            <td>发文类型</td>
            <td>发文号</td>
            <td width="60"></td>
        </tr>
        </thead>
        <tbody>
        <tr data-id="{{sd.id}}">
            <td>{{=sd.year}}</td>
            <td>{{=sd.dispatchType.name}}</td>
            <td>{{=sd.dispatchCode}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
            </td>
        </tr>
        </tbody>
    </table>
</script>
<script type="text/template" id="scDispatchTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>年份</td>
            <td>发文类型</td>
            <td>发文号</td>
            <td width="60"></td>
        </tr>
        </thead>
        <tbody>
        <tr data-id="{{sd.id}}">
            <td>{{=sd.year}}</td>
            <td>{{=sd.dispatchType.name}}</td>
            <td>{{=sd.dispatchCode}}</td>
            <td>
                <a href="javasciprt:;" class="del">移除</a>
            </td>
        </tr>
        </tbody>
    </table>
</script>

<script type="text/template" id="scCommitteesTpl">
    <table class="table table-striped table-bordered table-condensed table-unhover2">
        <thead>
        <tr>
            <td>年份</td>
            <td>党委常委会编号</td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        {{_.each(scCommittees, function(item, idx){ }}
        <tr>
            <td>{{=item.year}}</td>
            <td>{{=item.code}}</td>
            <td>
                <button class="linkBtn btn btn-xs btn-primary"
                        data-url="${ctx}#/sc/scCommittee?year={{=item.year}}&holdDate={{=$.date(item.holdDate, 'yyyy-MM-dd')}}"
                        data-target="_blank">
                    <i class="fa fa-search"></i> 查看</button>
            </td>
        </tr>
        {{});}}
        </tbody>
    </table>
</script>
<script>
    <c:if test="${empty dispatch.scDispatchId}">
    $("#scDispatchDiv").html('<div class="well">未选择签发文件</div>');
    </c:if>
    <c:if test="${not empty dispatch.scDispatchId}">
    $.post("${ctx}/sc/scDispatch_select", {id:'${dispatch.scDispatchId}'}, function(data){
        if(data.success) {
            var sd = data.scDispatch;
            $("#scDispatchDiv").html(_.template($("#scDispatchTpl").html())({sd: sd}));
            $("#scCommitteesDiv .label-text").html(_.template($("#scCommitteesTpl").html())({scCommittees: data.scDispatch.scCommittees}));
            $("#scCommitteesDiv").show();

            $("#modalForm input[name=year]").prop("disabled", true);
            var $dispatchTypeId = $("#modalForm select[name=dispatchTypeId]");
            $dispatchTypeId.select2({theme: "default"}).prop("disabled", true);
            if(sd.code>0) {
                $("#modalForm input[name=code]").prop("disabled", true);
            }
            $("#modalForm input[name=_meetingTime]").prop("disabled", true);
            $("#modalForm input[name=appointCount]").prop("disabled", true);
            $("#modalForm input[name=dismissCount]").prop("disabled", true);
        }
    })
    </c:if>
    $(document).on('click', "#scDispatchDiv .del", function(){

        $("#scDispatchDiv").html('<div class="well">未选择签发文件</div>');
        $("#scCommitteesDiv .label-text").html('')
        $("#scCommitteesDiv").hide();

        $("#modalForm input[name=scDispatchId]").val('');

        $("#modalForm input[name=year]").prop("disabled", false);

        var $dispatchTypeId = $("#modalForm select[name=dispatchTypeId]");
        $dispatchTypeId.select2({theme: "classic"}).removeAttr("disabled").trigger("change");
        $.register.dispatchType_select($('#modalForm select[name=dispatchTypeId]'), $("#modalForm input[name=year]"));

        $("#modalForm input[name=code]").prop("disabled", false);

        $("#modalForm input[name=_meetingTime]").prop("disabled", false);
        $("#modalForm input[name=appointCount]").prop("disabled", false);
        $("#modalForm input[name=dismissCount]").prop("disabled", false);
    })

    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();

    $('textarea.limited').inputlimiter();
    $.fileInput($('#modalForm input[type=file]'));

    $.register.dispatchType_select($('#modalForm select[name=dispatchTypeId]'), $("#modalForm input[name=year]"));
    $.register.date($('.date-picker'));

    $("#submitBtn").click(function(){$("#modalForm").submit();return false;});
    $("#modalForm").validate({
        rules: {
            code: {
                digits: true
            }
        },
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        bootbox.confirm({
                            buttons: {
                                confirm: {
                                    label: '是',
                                    className: 'btn-success'
                                },
                                cancel: {
                                    label: '否',
                                    className: 'btn-default btn-show'
                                }
                            },
                            message: '<div class="msg info">是否继续添加干部任免信息？</div>',
                            callback: function(result) {
                                if (result) {
                                    $("#body-content-view").load("${ctx}/dispatch_cadres?dispatchId=" + ret.id);
                                } else {
                                    $.hideView();
                                }
                            },
                            title: '<div class="msg title text-success"><i class="fa fa-check-square-o"></i> 操作成功</div>',
                        }).draggable({handle: ".modal-header"});
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>