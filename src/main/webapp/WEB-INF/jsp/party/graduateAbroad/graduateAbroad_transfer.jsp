<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>分党委审核通过</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/graduateAbroad_transfer" id="modalForm" method="post">
        <input type="hidden" name="ids[]" value="${param['ids[]']}">
        <c:set var="len" value="${fn:length(fn:split(param['ids[]'],','))}"/>
        <c:if test="${len>1}">
        <div class="form-group">
            <label class="col-xs-4 control-label">申请记录</label>
            <div class="col-xs-6 label-text">
                ${len} 条
            </div>
        </div>
        </c:if>
        <c:if test="${len==1}">
            <div class="form-group">
                <label class="col-xs-4 control-label">申请人</label>
                <div class="col-xs-6 label-text">
                       ${cm:getUserById(graduateAbroad.userId).realname}
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-4 control-label">原支部</label>
                <div class="col-xs-6 label-text">
                        ${branchMap.get(graduateAbroad.branchId).name}
                </div>
            </div>
        </c:if>
        <div class="form-group">
            <label class="col-xs-4 control-label">请选择暂留所在党支部</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax"
                        data-ajax-url="${ctx}/branch_selects?partyId=${party.id}"
                        name="branchId" data-placeholder="请选择" data-width="320">
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">原支部负责人</label>
            <div class="col-xs-6">
                <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                        name="orgBranchAdminId" data-placeholder="请输入账号或姓名或学工号">
                    <option value=""></option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">原支部负责人联系电话</label>
            <div class="col-xs-6">
                <input required class="form-control" type="text" name="orgBranchAdminPhone">
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">

    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $('#modalForm [data-rel="select2-ajax"]').select2({
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                return {results: data.options,  pagination: {
                    more: (params.page * 10) < data.totalCount
                }};
            },
            cache: true
        }
    });
    register_date($('.date-picker'));
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal("hide");
                        SysMsg.success('操作成功。', '成功', function () {
                            goto_next(${param.goToNext==1});
                        });
                    }
                }
            });
        }
    });
</script>