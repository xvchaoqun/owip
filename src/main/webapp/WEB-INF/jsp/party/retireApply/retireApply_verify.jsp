<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党员退休-审核</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/retireApply_verify" id="modalForm" method="post">
            <input type="hidden" name="userId" value="${param.userId}">
            <div class="form-group">
                <label class="col-sm-3 control-label no-padding-right"> 选择党支部</label>
                <div class="col-sm-9">
                    <select required name="classId" data-rel="select2" data-placeholder="请选择">
                        <option></option>
                        <c:forEach items="${partyClassMap}" var="cls">
                            <option value="${cls.key}">${cls.value.name}</option>
                        </c:forEach>
                    </select>
                    <script>
                        $("#modalForm select[name=classId]").val("${party.classId}")
                    </script>
                </div>
            </div>
            <div class="form-group"  id="party" style="${empty party?'display: none;':''}" >
                <div class="col-sm-offset-3 col-sm-9">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                            name="partyId" data-placeholder="请输入分党委名称">
                        <option value="${party.id}">${party.name}</option>
                    </select>
                </div>
            </div>
            <div class="form-group" id="branch" style="${empty branch?'display: none;':''}" >
                <div class="col-sm-offset-3 col-sm-9">
                    <select data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                            name="branchId" data-placeholder="请输入支部名称">
                        <option value="${branch.id}">${branch.name}</option>
                    </select>
                </div>
            </div>
        </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="审核"/>
</div>

<script>
    register_class_party_branch_select($("#modalForm"), "party", "branch",
            '${cm:getMetaTypeByCode("mt_direct_branch").id}', '${party.id}');

    $("#modalForm").validate({
        submitHandler: function (form) {
            if(!$("#party").is(":hidden")){
                if($('#modalForm select[name=partyId]').val()=='') {
                    SysMsg.warning('请选择分党委。', '提示');
                    return;
                }
            }
            if(!$("#branch").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    SysMsg.warning('请选择支部。', '提示');
                    return;
                }
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        //SysMsg.success('审核成功。', '提示');
                    }
                }
            });
        }
    });
</script>