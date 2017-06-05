<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="jqgrid-vertical-offset clearfix" style="background-color: #f5f5f5;padding: 5px 0 5px 0">
    <div class="col-md-9">
        <button class="hideView btn btn-success btn-sm" type="button">
            <i class="ace-icon fa fa-backward bigger-110"></i>
            返回
        </button>
    </div>
</div>
<div class="jqgrid-vertical-offset widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="ace-icon fa fa-list blue "></i> ${MODIFY_TABLE_APPLY_MODULE_MAP.get(mta.module)}列表</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-down"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table id="jqGrid_cadreWork" class="jqGrid4"></table>
        </div>
    </div>
</div>

<div class="jqgrid-vertical-offset widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="ace-icon fa fa-edit blue "></i>
        ${MODIFY_TABLE_APPLY_TYPE_MAP.get(mta.type)}${MODIFY_TABLE_APPLY_MODULE_MAP.get(mta.module)}内容（申请时间：${cm:formatDate(mta.createTime, "yyyy-MM-dd")}）</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-down"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <table class="table  table-unhover table-bordered table-striped">
                <tr>
                    <td data-code="startTime">开始日期</td>
                    <td class="bg-left">${cm:formatDate(modify.startTime,'yyyy.MM')}</td>
                    <td data-code="endTime">结束日期</td>
                    <td class="bg-left">${cm:formatDate(modify.endTime,'yyyy.MM')}</td>
                    <td data-code="unit">工作单位</td>
                    <td class="bg-left">${modify.unit}</td>
                </tr>
                <tr>
                    <td data-code="post">担任职务或者专技职务</td>
                    <td class="bg-left">${modify.post}</td>
                    <td data-code="workType">工作类型</td>
                    <td class="bg-left">${cm:getMetaType(modify.workType).name}</td>
                    <td data-code="isCadre">是否担任领导职务</td>
                    <td class="bg-left">${modify.isCadre?"是":"否"}</td>
                </tr>
                <tr>
                    <td data-code="remark">备注</td>
                    <td colspan="5" class="bg-left">${modify.remark}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<c:if test="${param.type=='check'}">
<shiro:hasPermission name="modifyTableApply:approval">
<div class="jqgrid-vertical-offset widget-box">
    <div class="widget-header">
        <h4 class="widget-title"><i class="ace-icon fa fa-check-circle blue "></i> 管理员审核</h4>
        <div class="widget-toolbar">
            <a href="javascript:;" data-action="collapse">
                <i class="ace-icon fa fa-chevron-down"></i>
            </a>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main">
            <form class="form-horizontal" action="${ctx}/modifyTableApply_approval" id="approvalForm" method="post">
                <input type="hidden" name="id" value="${mta.id}">
                <div class="form-group">
                    <label class="col-xs-3 control-label">审核意见</label>
                    <div class="col-xs-8 label-text"  style="font-size: 15px;">
                        <input type="checkbox" class="big" value="1"/> 通过审核
                        <input type="checkbox"  class="big" value="2"/> 未通过审核
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">依据</label>
                    <div class="col-xs-6">
                        <textarea class="form-control limited" type="text" name="checkReason" rows="2"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">备注</label>
                    <div class="col-xs-6">
                        <textarea class="form-control limited" type="text" name="checkRemark" rows="2"></textarea>
                    </div>
                </div>
        </form>
        </div>
    </div>
</div>
</shiro:hasPermission>
</c:if>

<div class="clearfix form-actions center">
    <shiro:hasAnyRoles name="${ROLE_CADRE},${ROLE_CADRERESERVE}">
        <c:if test="${_user.id==mta.userId && mta.type != MODIFY_TABLE_APPLY_TYPE_DELETE}">
        <button class="popupBtn btn btn-primary"
                data-url="${ctx}/cadreWork_au?toApply=1&cadreId=${cadre.id}&_isUpdate=1&id=${modify.id}&applyId=${mta.id}"
                data-width="900"
                type="button">
            <i class="ace-icon fa fa-edit"></i>
            编辑
        </button>
        </c:if>
    </shiro:hasAnyRoles>
    <c:if test="${param.type=='check'}">
    <shiro:hasPermission name="modifyTableApply:approval">
    <button class="btn btn-success" type="button" id="approvalBtn">
        <i class="ace-icon fa fa-check"></i>
        审核
    </button>
    </shiro:hasPermission>
    </c:if>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button class="hideView btn btn-default" type="button">
        <i class="ace-icon fa fa-undo"></i>
        返回
    </button>
</div>

<script type="text/template" id="dispatch_select_tpl">
    <button class="popupBtn btn {{=(count>0)?'btn-warning':'btn-success'}} btn-xs"
            data-url="${ctx}/cadreWork_addDispatchs?id={{=id}}&cadreId={{=cadreId}}"
            data-width="1000"><i class="fa fa-link"></i>
        任免文件({{=count}})
    </button>
</script>
<script>
    <c:if test="${mta.type==MODIFY_TABLE_APPLY_TYPE_MODIFY}">
    var modify = ${cm:toJSONObject(modify)};
    var original = ${mta.originalJson}
    $("td[data-code]").each(function(){
        var $this = $(this);
        var code = $this.data("code");
        if(modify[code]!=original[code]){
            $this.addClass("text-danger bolder");
        }
    });
    </c:if>

    $("#approvalBtn").click(function(){$("#approvalForm").submit();return false;})
    $("#approvalForm").validate({
        submitHandler: function (form) {

            var type = $('#approvalForm input[type=checkbox]:checked').val();
            if(type!=1&&type!=2){
                SysMsg.warning("请选择审核意见");
                return;
            }

            $(form).ajaxSubmit({
                data:{status:(type==1)},
                success:function(ret){
                    if(ret.success){

                        $.hashchange();
                    }
                }
            });
        }
    });

    $("#jqGrid_cadreWork").jqGrid({
        pager: null,
        ondblClickRow: function () {
        },
        datatype: "local",
        data:${cm:toJSONArray(cadreWorks)},
        multiselect: false,
        colModel: [
            {label: '开始日期', name: 'startTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '结束日期', name: 'endTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}},
            {label: '工作单位', name: 'unit', width: 280},
            {label: '担任职务或者专技职务', name: 'post', width: 170},
            /*     {
             label: '行政级别', name: 'typeId', formatter: $.jgrid.formatter.MetaType
             },*/
            {label: '工作类型', name: 'workType', width: 140, formatter: $.jgrid.formatter.MetaType},
            {label: '是否担任领导职务', name: 'isCadre', width: 150, formatter: $.jgrid.formatter.TRUEFALSE},
            {label: '备注', name: 'remark', width: 150},

            {
                label: '干部任免文件', name: 'dispatchCadreRelates', formatter: function (cellvalue, options, rowObject) {
                var count = cellvalue.length;
                <shiro:lacksPermission name="${PERMISSION_CADREADMIN}">
                if(count==0) return ''
                </shiro:lacksPermission>
                return  _.template($("#dispatch_select_tpl").html().NoMultiSpace())
                ({id: rowObject.id, cadreId: rowObject.cadreId, count: count});
            }, width: 120
            },{hidden:true, name:'id'}],
        rowattr: function(rowData, currentObj, rowId)
        {
            //console.log(rowData.id + '-${mta.originalId}')
            if(rowData.id=='${mta.originalId}') {
                //console.log(rowData)
                return {'class':'info'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid4');
</script>