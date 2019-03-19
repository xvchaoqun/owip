<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${applyOpenTime!=null}">编辑</c:if><c:if test="${applyOpenTime==null}">添加</c:if>党员申请开放时间段</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/applyOpenTime_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${applyOpenTime.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属${_p_partyName}</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-3 control-label">所属党支部</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择党支部">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            $.register.party_branch_select($("#modalForm"), "branchDiv",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
        </script>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>开始时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_startTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(applyOpenTime.startTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>结束时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_endTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(applyOpenTime.endTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>生效阶段</label>
				<div class="col-xs-6">
                    <select required name="type" data-rel="select2" data-placeholder="请选择阶段">
                        <option></option>
                        <c:forEach items="${OW_APPLY_STAGE_MAP}" var="type">
                            <c:if test="${type.key>0}">
                            <option value="${type.key}">${type.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=type]").val(${applyOpenTime.type});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否全局</label>
				<div class="col-xs-6">
                    <label>
                        <input name="isGlobal" ${applyOpenTime.isGlobal?"checked":""}  type="checkbox" />
                        <span class="lbl"></span>
                    </label>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${applyOpenTime!=null}">确定</c:if><c:if test="${applyOpenTime==null}">添加</c:if>"/>
</div>

<script>
    $("#modal :checkbox").bootstrapSwitch();
    $.register.date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

</script>