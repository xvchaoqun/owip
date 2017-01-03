<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreResearch!=null}">编辑</c:if><c:if test="${cadreResearch==null}">添加</c:if>干部${param.researchType==CADRE_RESEARCH_TYPE_DIRECT?"主持":"参与"}科研项目情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreResearch_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
        <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
        <input type="hidden" name="applyId" value="${param.applyId}">
        <input type="hidden" name="id" value="${cadreResearch.id}">
        <input type="hidden" name="researchType" value="${param.researchType}">
        <div class="form-group">
            <label class="col-xs-3 control-label">姓名</label>
            <div class="col-xs-6 label-text">
                ${sysUser.realname}
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">项目起始时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_startTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreResearch.startTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">项目结题时间</label>
            <div class="col-xs-6">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_endTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(cadreResearch.endTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">项目名称</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="name" value="${cadreResearch.name}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">项目类型</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="type" value="${cadreResearch.type}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">委托单位</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="unit" value="${cadreResearch.unit}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">备注</label>
            <div class="col-xs-6">
                <textarea class="form-control" name="remark">${cadreResearch.remark}</textarea>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreResearch!=null}">确定</c:if><c:if test="${cadreResearch==null}">添加</c:if>"/>
</div>

<script>
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${param.toApply!=1}">
                        <c:if test="${param.researchType==CADRE_RESEARCH_TYPE_IN}">
                        $("#jqGrid_cadreResearch_in").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.researchType==CADRE_RESEARCH_TYPE_DIRECT}">
                        $("#jqGrid_cadreResearch_direct").trigger("reloadGrid");
                        </c:if>
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#item-content").load("${ctx}/modifyCadreResearch_detail?applyId=${param.applyId}&researchType=${param.researchType}&module=${param.module}&_="+new Date().getTime())
                        </c:if>
                        <c:if test="${param._isUpdate!=1}">
                        location.href='?cls=1&researchType=${param.researchType}&module=${param.module}';
                        </c:if>
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>