<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/modify/constants.jsp"%>
<c:set value="<%=CadreConstants.CADRE_SCHOOL_TYPE_MAP%>" var="CADRE_SCHOOL_TYPE_MAP"/>
<c:set var="_cadreEdu_needSubject" value="${_pMap['cadreEdu_needSubject']=='true'}"/>
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
            <div id="jqGridPager_cadreEdu"></div>
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
                    <td data-code="eduId">学历</td>
                    <td class="bg-left">${cm:getMetaType(modify.eduId).name}</td>
                    <td data-code="enrolTime">入学时间</td>
                    <td class="bg-left">${cm:formatDate(modify.enrolTime,'yyyy.MM')}</td>
                    <td data-code="finishTime">毕业时间</td>
                    <td class="bg-left">${cm:formatDate(modify.finishTime,'yyyy.MM')}</td>
                </tr>
                <tr>
                    <td data-code="isGraduated">毕业/在读</td>
                    <td class="bg-left">${modify.isGraduated?"毕业":"在读"}</td>
                    <td data-code="isHighEdu">是否最高学历</td>
                    <td class="bg-left">${modify.isHighEdu?"是":"否"}</td>
                    <td data-code="school">毕业/在读学校</td>
                    <td class="bg-left">${modify.school}</td>
                </tr>
                <tr>
                    <td data-code="dep">院系</td>
                    <td class="bg-left">${modify.dep}</td>
                    <td data-code="major">所学专业</td>
                    <td class="bg-left">${modify.major}</td>
                    <td data-code="schoolType">学校类型</td>
                    <td class="bg-left">${CADRE_SCHOOL_TYPE_MAP.get(modify.schoolType)}</td>
                </tr>
                <c:if test="${_cadreEdu_needSubject}">
                <tr>
                    <td data-code="subject">学科门类</td>
                    <td class="bg-left">${layerTypeMap.get(modify.subject).name}</td>
                    <td data-code="firstSubject">一级学科</td>
                    <td class="bg-left" colspan="3">${layerTypeMap.get(modify.firstSubject).name}</td>
                </tr>
                    </c:if>
                <tr>
                    <td data-code="learnStyle">学习方式</td>
                    <td class="bg-left">${cm:getMetaType(modify.learnStyle).name}</td>
                    <td data-code="hasDegree">是否获得学位</td>
                    <td class="bg-left">${modify.hasDegree?"是":"否"}</td>
                    <td data-code="degree">学位</td>
                    <td class="bg-left">${modify.degree}</td>
                </tr>
                <tr>
                    <td data-code="isHighDegree">是否为最高学位</td>
                    <td class="bg-left">${modify.isSecondDegree?'*':''}${modify.isHighDegree?"是":"否"}</td>
                    <td data-code="degreeCountry">学位授予国家</td>
                    <td class="bg-left">${modify.degreeCountry}</td>
                    <td data-code="degreeUnit">学位授予单位</td>
                    <td class="bg-left">${modify.degreeUnit}</td>
                </tr>
                <tr>
                    <td data-code="degreeTime">学位授予日期</td>
                    <td class="bg-left">${cm:formatDate(modify.degreeTime,'yyyy.MM')}</td>
                    <td data-code="tutorName">导师姓名</td>
                    <td class="bg-left">${modify.tutorName}</td>
                    <td data-code="tutorTitle">导师所在单位及职务（职称）</td>
                    <td class="bg-left">${modify.tutorTitle}</td>
                </tr>
                <tr>
                    <td data-code="certificate">学历学位证书</td>
                    <td class="bg-left">
                        <c:if test="${not empty modify.certificate}">
                    <c:forEach items="${fn:split(modify.certificate, ',')}" var="filePath" varStatus="vs">
                            <t:preview filePath="${filePath}" fileName="证件${vs.index+1}" label="证件${vs.index+1}"/>
                        ${vs.last?'':'，'}
                    </c:forEach>
                        </c:if>
                    </td>
                    <td data-code="remark">备注</td>
                    <td class="bg-left">${modify.remark}</td>
                    <td data-code="note">补充说明</td>
                    <td class="bg-left">${modify.note}</td>
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
                    <div class="col-xs-8">
                        <div class="input-group">
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="type" id="type1" value="1">
                                <label for="type1">
                                    通过审核
                                </label>
                            </div>
                            <div class="checkbox checkbox-inline checkbox-sm checkbox-circle">
                                <input required type="radio" name="type" id="type2" value="2">
                                <label for="type2">
                                    未通过审核
                                </label>
                            </div>
                        </div>
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
    && mta.type != MODIFY_TABLE_APPLY_TYPE_DELETE && mta.status==MODIFY_TABLE_APPLY_STATUS_APPLY}">
    <button class="popupBtn btn btn-primary"
            data-url="${ctx}/cadreEdu_au?toApply=1&cadreId=${cadre.id}&_isUpdate=1&opType=${param.opType}&id=${modify.id}&applyId=${mta.id}"
            data-width="900"
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
    if(modify.isSecondDegree!=original.isSecondDegree){
        $("td[data-code=isHighDegree]").addClass("text-danger bolder");
    }
    </c:if>

    $("#approvalBtn").click(function(){$("#approvalForm").submit();return false;})
    $("#approvalForm").validate({
        submitHandler: function (form) {

            var type = $('#approvalForm input[type=radio]:checked').val();

            $(form).ajaxSubmit({
                data:{status:(type==1)},
                success:function(ret){
                    if(ret.success){

                        $("#jqGrid").trigger("reloadGrid");
                        $.hideView();
                    }
                }
            });
        }
    });

    var needTutorEduTypes = ${cm:toJSONArray(needTutorEduTypes)};
    $("#jqGrid_records").jqGrid({
        ondblClickRow: function () {
        },
        pager: "#jqGridPager_cadreEdu",
        url: '${ctx}/cadreEdu_data?cadreId=${cadre.id}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        multiselect: false,
        colModel:colModels.cadreEdu,
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