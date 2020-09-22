<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${branchMemberGroup!=null}">编辑</c:if><c:if test="${branchMemberGroup==null}">添加</c:if>支部委员会</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/branchMemberGroup_au" autocomplete="off" disableautocomplete
          id="modalForm" method="post">
        <input type="hidden" name="id" value="${branchMemberGroup.id}">

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>所属党支部</label>
            <c:if test="${empty branchMemberGroup}">
                <div class="col-xs-8">
                    <select class="form-control" data-width="292" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择所属${_p_partyName}">
                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                    </select>
                    <div style="padding-top: 5px;${(empty branch)?'display: none':''}" id="branchDiv">
                        <select class="form-control" data-rel="select2-ajax"
                                data-ajax-url="${ctx}/branch_selects?auth=1" data-width="292"
                                name="branchId" data-placeholder="请选择党支部">
                            <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                        </select>
                    </div>
                    <script>
                        $.register.party_branch_select($("#modalForm"), "branchDiv",
                                '${cm:getMetaTypeByCode("mt_direct_branch").id}'
                            , "${party.id}", "${party.classId}", null, null ,true);
                    </script>
                </div>
            </c:if>
            <c:if test="${not empty branchMemberGroup}">
                <div class="col-xs-8 label-text">
                    <input type="hidden" name="branchId" value="${branch.id}">
                        ${party.name}-${branch.name}
                </div>
            </c:if>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label">上一届委员会</label>
            <div class="col-xs-8">
                <select class="form-control" name="fid" data-width="292"
                        data-rel="select2-ajax" data-ajax-url="${ctx}/branchMemberGroup_selects?branchId=${branch.id}&isDeleted=1&id=${branchMemberGroup.id}"
                        data-placeholder="请选择委员会">
                    <option value="${fBranchMemberGroup.id}">${fBranchMemberGroup.name}</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
            <div class="col-xs-8" style="width: 312px">
                <textarea required class="form-control" name="name">${branchMemberGroup.name}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>任命时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_appointTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(branchMemberGroup.appointTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label"><span class="star">*</span>应换届时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input required class="form-control date-picker" name="_tranTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(branchMemberGroup.tranTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">实际换届时间</label>
            <div class="col-xs-8">
                <div class="input-group" style="width: 150px">
                    <input class="form-control date-picker" name="_actualTranTime" type="text"
                           data-date-format="yyyy-mm-dd"
                           value="${cm:formatDate(branchMemberGroup.actualTranTime,'yyyy-MM-dd')}"/>
                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                </div>
                <span class="help-block">注：实际换届时填写，完成修改后请撤销该支部委员会</span>
            </div>
        </div>

        <%--<div class="form-group">
            <label class="col-xs-3 control-label">发文</label>
            <div class="col-xs-8">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/dispatchUnit_selects?unitId=${party.unitId}"
                        name="dispatchUnitId" data-placeholder="请选择单位发文">
                    <option value="${branchMemberGroup.dispatchUnitId}">
                        ${cm:getDispatchCode(dispatch.code, dispatch.dispatchTypeId, dispatch.year )}
                    </option>
                </select>
            </div>
        </div>--%>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" type="button" class="btn btn-primary"
            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        ${branchMemberGroup!=null?'确定':'添加'}</button>
</div>

<script>
    $("#modalForm select[name=branchId]").on("change", function(){

        var branchId = $.trim($(this).val());

        $("#modalForm select[name=fid]").data("ajax-url",
                "${ctx}/branchMemberGroup_selects?branchId="+ branchId + "&isDeleted=1");
        $.register.ajax_select("#modalForm select[name=fid]");

        if(branchId==''){
            $("#modalForm select[name=fid]").val(null).trigger("change");
        }
    })
    $.register.ajax_select("#modalForm select[name=fid]");

    $("#modal :checkbox").bootstrapSwitch();
    $.register.date($('.date-picker'));
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        if ("${param.type}" == "view") {
                            _reload();
                            //SysMsg.success('操作成功。', '成功');
                        } else {
                            $("#modal").modal("hide")
                            //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                            $.hashchange();
                            // });
                        }
                    }else{
                        $btn.button('reset');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>