<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="MEMBER_POLITICAL_STATUS_MAP" value="<%=SystemConstants.MEMBER_POLITICAL_STATUS_MAP%>"/>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberReturn!=null}">编辑</c:if><c:if test="${memberReturn==null}">添加</c:if>留学归国人员申请恢复组织生活</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReturn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReturn.id}">
			<div class="form-group">
				<label class="col-xs-4 control-label">用户</label>
                <c:if test="${not empty userBean}">
                    <div class="col-xs-6 label-text">
                        <input type="hidden" name="userId" value="${userBean.userId}">
                            ${userBean.realname}
                    </div>
                </c:if>
                <c:if test="${empty userBean}">
                    <div class="col-xs-6">
                        <select required data-rel="select2-ajax" data-ajax-url="${ctx}/notMember_selects"
                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                            <option value="${userBean.userId}">${userBean.realname}</option>
                        </select>
                    </div>
                </c:if>
			</div>
        <div class="form-group">
            <label class="col-xs-4 control-label">分党委</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/party_selects?auth=1" data-width="350"
                        name="partyId" data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-4 control-label">党支部</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/branch_selects?auth=1"  data-width="350"
                        name="branchId" data-placeholder="请选择">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            register_party_branch_select($("#modalForm"), "branchDiv",
                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}" );
        </script>
        <div class="form-group">
            <label class="col-xs-4 control-label">政治${memberReturn.politicalStatus}面貌</label>
            <div class="col-xs-6">
                <select required data-rel="select2" name="politicalStatus" data-placeholder="请选择"  data-width="120">
                    <option></option>
                    <c:forEach items="${MEMBER_POLITICAL_STATUS_MAP}" var="_status">
                        <option value="${_status.key}">${_status.value}</option>
                    </c:forEach>
                </select>
                <script>
                    $("#modalForm select[name=politicalStatus]").val(${memberReturn.politicalStatus});
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">提交恢复组织生活申请时间</label>
            <div class="col-xs-6" style="width: 220px">
                <div class="input-group">
                    <input required class="form-control date-picker" name="_returnApplyTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.returnApplyTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">提交书面申请书时间</label>
            <div class="col-xs-6" style="width: 220px">
                <div class="input-group">
                    <input required class="form-control date-picker" name="_applyTime" type="text"
                           data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.applyTime,'yyyy-MM-dd')}" />
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-4 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6" style="width: 220px">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_activeTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">确定为发展对象时间</label>
				<div class="col-xs-6" style="width: 220px">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_candidateTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">入党时间</label>
				<div class="col-xs-6" style="width: 220px">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">转正时间</label>
				<div class="col-xs-6" style="width: 220px">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_positiveTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">备注</label>
				<div class="col-xs-6"  style="width: 250px">
                        <textarea class="form-control limited" name="remark" rows="5">${memberReturn.remark}</textarea>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${memberReturn!=null}">确定</c:if><c:if test="${memberReturn==null}">添加</c:if>"/>
</div>

<script>
    jgrid_left = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollLeft();
    jgrid_top = $("#jqGrid").closest(".ui-jqgrid-bdiv").scrollTop();
    $('textarea.limited').inputlimiter();
    register_date($('.date-picker'));

    $("#modal form").validate({
        submitHandler: function (form) {

            if(!$("#branchDiv").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    SysMsg.warning("请选择支部。", "提示");
                    return;
                }
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        });
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    register_user_select($('#modalForm select[name=userId]'));
</script>