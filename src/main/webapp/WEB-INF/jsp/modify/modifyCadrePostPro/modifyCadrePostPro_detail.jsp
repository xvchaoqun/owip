<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
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
            <table id="jqGrid_records" class="jqGrid4"></table>
            <div id="jqGridPager_cadrePostPro"></div>
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
                <c:if test="${mta.type==MODIFY_TABLE_APPLY_TYPE_DELETE}">
                    <tr>
                        <td>删除原因</td>
                        <td colspan="5" class="bg-left">${mta.reason}</td>
                    </tr>
                </c:if>
                <tr>
                    <td data-code="isCurrent">是否当前专技岗位</td>
                    <td class="bg-left">${modify.isCurrent?'是':'否'}</td>
                    <td data-code="type">岗位类别</td>
                    <td class="bg-left">${cm:getMetaType(modify.type).name}</td>
                    <td data-code="postLevel">职级</td>
                    <td class="bg-left">${modify.postLevel}</td>
                </tr>
                <tr>
                    <td data-code="post">专业技术职务</td>
                    <td class="bg-left">${cm:getMetaType(modify.post).name}</td>
                    <td data-code="holdTime">专技职务任职时间</td>
                    <td colspan="3" class="bg-left">${cm:formatDate(modify.holdTime,'yyyy.MM')}</td>
                </tr>
                <tr>
                    <td data-code="level">职称级别</td>
                    <td class="bg-left">${cm:getMetaType(modify.level).name}</td>
                    <td data-code="gradeTime">专技岗位分级时间</td>
                    <td colspan="3" class="bg-left">${cm:formatDate(modify.gradeTime,'yyyy.MM')}</td>
                </tr>
                <tr>
                    <td data-code="remark">备注</td>
                    <td colspan="5" class="bg-left">${modify.remark}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<c:if test="${param.opType=='check'}">
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
    <c:if test="${(cm:isPermitted(PERMISSION_CADREADMIN)||_user.id==mta.userId)
    && mta.type != MODIFY_TABLE_APPLY_TYPE_DELETE}">
        <button class="popupBtn btn btn-primary" ${mta.status!=MODIFY_TABLE_APPLY_STATUS_APPLY?'disabled':''}
                data-url="${ctx}/cadrePostPro_au?toApply=1&cadreId=${cadre.id}&_isUpdate=1&opType=${param.opType}&id=${modify.id}&applyId=${mta.id}"
                type="button">
            <i class="ace-icon fa fa-edit"></i>
            编辑
        </button>
        &nbsp;&nbsp;&nbsp;&nbsp;
    </c:if>
    <c:if test="${param.opType=='check'}">
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

<script>
    <c:if test="${mta.type==MODIFY_TABLE_APPLY_TYPE_MODIFY}">
    var modify = ${cm:toJSONObject(modify)};
    var original = ${mta.originalJson}
    $("td[data-code]").each(function(){
        var $this = $(this);
        var code = $this.data("code");
        if($.trim(modify[code])!=$.trim(original[code])){
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

                        $("#jqGrid_records").trigger("reloadGrid");
                        $.hideView();
                    }
                }
            });
        }
    });

    $("#jqGrid_records").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadrePostPro",
        url: '${ctx}/cadrePostPro_data?cadreId=${cadre.id}&${cm:encodeQueryString(pageContext.request.queryString)}',
        multiselect: false,
        colModel: colModels.cadrePostPro,
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
    $.register.fancybox();
</script>