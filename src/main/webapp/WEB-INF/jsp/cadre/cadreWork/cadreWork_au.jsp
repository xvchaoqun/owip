<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreWork!=null}">编辑</c:if><c:if test="${cadreWork==null}">添加</c:if><c:if test="${not empty param.fid}">期间</c:if>工作经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreWork_au" id="modalForm" method="post">
            <input type="hidden" name="id" value="${cadreWork.id}">
            <c:if test="${not empty cadreWork.fid}">
            <input  type="hidden" name="fid" value="${cadreWork.fid}">
            </c:if>
            <c:if test="${empty cadreWork.fid}">
            <input  type="hidden" name="fid" value="${param.fid}">
             </c:if>
                <c:if test="${not empty param.fid}">
                    <input  type="hidden" name="cadreId" value="${param.cadreId}">
                </c:if>
            <c:if test="${empty param.fid}">
			<div class="form-group">
				<label class="col-xs-4 control-label">所属干部</label>
				<div class="col-xs-6 label-text">
                    <input  type="hidden" name="cadreId" value="${cadre.id}">
                        ${sysUser.realname}
				</div>
			</div>
                </c:if>
			<div class="form-group">
				<label class="col-xs-4 control-label">开始日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreWork.startTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">结束日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input class="form-control date-picker" name="_endTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreWork.endTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">工作单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreWork.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">行政职务或者专技职务</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="post" value="${cadreWork.post}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">行政级别</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="typeId" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_admin_level"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=typeId]").val(${cadreWork.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">工作类型</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="workType" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_cadre_work_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=workType]").val(${cadreWork.workType});
                    </script>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-4 control-label">干部任职</label>
            <div class="col-xs-6">
                <label>
                    <input name="isCadre" ${cadreEdu.isCadre?"checked":""}  type="checkbox" />
                    <span class="lbl"></span>
                </label>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6">
                        <textarea class="form-control limited" type="text" name="remark" >${cadreWork.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
   <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreWork!=null}">确定</c:if><c:if test="${cadreWork==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'), {startDate:'${cm:formatDate(topCadreWork.startTime, "yyyy.MM")}', endDate:'${cm:formatDate(topCadreWork.endTime, "yyyy.MM")}'});
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        <c:if test="${not empty param.fid}">
                            _reloadSubGrid(${param.fid});
                        </c:if>
                        <c:if test="${empty param.fid}">
                            $("#jqGrid_cadreWork").trigger("reloadGrid");
                        </c:if>
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>