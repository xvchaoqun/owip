<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党员退休</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/retireApply_au" id="modalForm" method="post">
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
            </div>
        </div>
        <div class="form-group"  id="party" style="display: none;" >
            <div class="col-sm-offset-3 col-sm-9">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请输入分党委名称">
                    <option></option>
                </select>
            </div>
        </div>
        <div class="form-group" id="branch" style="display: none;" >
            <div class="col-sm-offset-3 col-sm-9">
                <select data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请输入支部名称">
                    <option></option>
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="提交"/>
</div>

<script>

    $('#modalForm [data-rel="select2"]').select2({width:200}).on("change", function () {
        if($(this).val()>0){
            $("#party").show();
        }else{
            $('#modalForm select[name=partyId]').val(null).trigger("change");
            $('#modalForm select[name=branchId]').val(null).trigger("change");
            $("#party, #branch").hide();
        }
    });

    $('#modalForm [data-rel="select2-ajax"]').select2({
        width:400,
        ajax: {
            dataType: 'json',
            delay: 300,
            data: function (params) {
                return {
                    searchStr: params.term,
                    pageSize: 10,
                    pageNo: params.page,
                    classId: $('select[name=classId]').val()
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

    $('#modalForm select[name=partyId]').on("change", function () {

        if($(this).val()>0 && $('select[name=classId]').val()!='${cm:getMetaTypeByCode("mt_direct_branch").id}'){
            $("#branch").show();
        }else{
            $('#modalForm select[name=branchId]').val(null).trigger("change");
            $("#branch").hide();
        }
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            if(!$("#party").is(":hidden")){
                if($('#modalForm select[name=partyId]').val()=='') {
                    toastr.warning('请选择分党委。', '提示');
                    return;
                }
            }
            if(!$("#branch").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    toastr.warning('请选择支部。', '提示');
                    return;
                }
            }
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('提交成功。', '提示');
                    }
                }
            });
        }
    });
</script>