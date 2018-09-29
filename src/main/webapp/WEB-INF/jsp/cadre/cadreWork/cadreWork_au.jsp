<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreWork!=null}">编辑</c:if><c:if test="${cadreWork==null}">添加</c:if><c:if test="${not empty param.fid}">其间</c:if>工作经历</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreWork_au?toApply=${param.toApply}&cadreId=${cadre.id}" id="modalForm" method="post">
            <input type="hidden" name="_isUpdate" value="${param._isUpdate}">
            <input type="hidden" name="applyId" value="${param.applyId}">
            <input type="hidden" name="id" value="${cadreWork.id}">
            <input  type="hidden" name="fid" value="${empty cadreWork?param.fid:cadreWork.fid}">
			<div class="form-group">
				<label class="col-xs-4 control-label">姓名</label>
				<div class="col-xs-6 label-text">
                    ${sysUser.realname}
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">开始日期</label>
				<div class="col-xs-6">
                    <div class="input-group" style="width: 120px">
                        <input required autocomplete="off" disableautocomplete
                               class="form-control date-picker" name="_startTime" type="text"
                               data-date-min-view-mode="1"
                               data-date-format="yyyy.mm" value="${cm:formatDate(cadreWork.startTime,'yyyy.MM')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">结束日期</label>
				<div class="col-xs-7">
                    <div class="input-group date date-picker" style="width: 120px"
                         data-date-min-view-mode="1"
                            <c:if test="${not empty param.fid}">
                                data-date-start-date="'${cm:formatDate(topCadreWork.startTime,'yyyy.MM')}'"
                                data-date-end-date="'${cm:formatDate(topCadreWork.endTime,'yyyy.MM')}'"
                            </c:if>
                         data-date-format="yyyy.mm">
                        <input ${not empty topCadreWork.endTime?"required":""}  autocomplete="off" disableautocomplete
                                                                                class="form-control" name="_endTime" type="text" value="${cm:formatDate(cadreWork.endTime,'yyyy.MM')}"/>
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                <c:if test="${not empty param.fid}">
                    <span class="help-block red">注：结束日期要求在主要工作经历的日期范围之内</span>
                </c:if>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">工作单位及担任职务<br/>（或专技职务）</label>
				<div class="col-xs-7">
                    <input required class="form-control" type="text" name="detail" value="${cadreWork.detail}">
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
            <label class="col-xs-4 control-label">是否担任领导职务</label>
            <div class="col-xs-6">
                <label>
                    <input name="isCadre" ${cadreWork.isCadre?"checked":""}  type="checkbox" />
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
   <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreWork!=null}">确定</c:if><c:if test="${cadreWork==null}">添加</c:if>"/>
</div>
<script>
    $("#modal :checkbox").bootstrapSwitch();
    $('textarea.limited').inputlimiter();
    $.register.date($('.date-picker'));
    //$.register.date($('.date-picker'), {startDate:'${cm:formatDate(topCadreWork.startTime, "yyyy.MM")}', endDate:'${cm:formatDate(topCadreWork.endTime, "yyyy.MM")}'});
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){

                        currentExpandRows = [];
                        $("#modal").modal("hide");
                        // 如果添加主要工作经历，那么添加之后所有的其间工作经历都是隐藏状态；
                        // 如果添加其间工作经历，那么添加之后，只有它所在的主要工作经历的其间工作经历为打开状态，其他主要工作经历的其间工作经历为隐藏状态。
                        <c:if test="${empty param.fid}">
                        currentExpandRows = [];
                        </c:if>
                        <c:if test="${not empty param.fid}">
                        currentExpandRows.push("${param.fid}");
                        </c:if>

                        <%--<c:if test="${topCadreWork.subWorkCount==0 || empty param.fid}">
                            $("#jqGrid_cadreWork").trigger("reloadGrid");
                            _reloadSubGrid(${param.fid});
                        </c:if>
                        <c:if test="${topCadreWork.subWorkCount>0 && not empty param.fid}">
                            _reloadSubGrid(${param.fid});
                        </c:if>--%>

                        <c:if test="${param.toApply!=1}">
                        $("#jqGrid_cadreWork").trigger("reloadGrid");
                        </c:if>
                        <c:if test="${param.toApply==1}">
                        <c:if test="${param._isUpdate==1}">
                        $("#body-content-view").load("${ctx}/modifyTableApply_detail?module=${param.module}&applyId=${param.applyId}&_="+new Date().getTime())
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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>