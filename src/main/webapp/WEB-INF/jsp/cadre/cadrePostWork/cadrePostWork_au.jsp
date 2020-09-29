<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadrePostWork!=null}">编辑</c:if><c:if test="${cadrePostWork==null}">添加</c:if>工勤岗位过程信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadrePostWork_au?toApply=${param.toApply}&cadreId=${cadre.id}" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadrePostWork.id}">

        <div class="form-group">
            <label class="col-xs-4 control-label">姓名</label>

            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否当前工勤岗位</label>

            <div class="col-xs-6">
                <label>
                    <input name="isCurrent" ${cadrePostWork.isCurrent?"checked":""} type="checkbox"/>
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>工勤岗位等级</label>

            <div class="col-xs-6">
                <select required data-rel="select2" name="level"
                        data-placeholder="请选择">
                    <option></option>
                    <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_post_work_level').id}"/>
                </select>
                <script type="text/javascript">
                    $("#modal form select[name=level]").val(${cadrePostWork.level});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">工勤岗位分级时间</label>

            <div class="col-xs-6">
                <div class="input-group" style="width: 130px">
                    <input class="form-control date-picker" name="_gradeTime" type="text"
                           data-date-min-view-mode="1" placeholder="yyyy.mm"
                           data-date-format="yyyy.mm" value="${cm:formatDate(cadrePostWork.gradeTime,'yyyy.MM')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">备注</label>

            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadrePostWork.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary"
           value="<c:if test="${cadrePostWork!=null}">确定</c:if><c:if test="${cadrePostWork==null}">添加</c:if>"/>
</div>

<script>
    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadrePostWork").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&opType=${param.opType}&applyId=${param.applyId}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        $.hashchange('cls=1&module=${param.module}');
                        </c:if>
                        </c:if>
                    }
                }
            });
        }
    });
    $("#modal :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
</script>