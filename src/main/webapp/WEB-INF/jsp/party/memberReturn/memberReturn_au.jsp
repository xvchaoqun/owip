<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${memberReturn!=null}">编辑</c:if><c:if test="${memberReturn==null}">添加</c:if>留学归国人员申请恢复组织生活</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/memberReturn_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${memberReturn.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">用户</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects"
                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                        <option value="${sysUser.id}">${sysUser.realname}</option>
                    </select>
				</div>
			</div>
        <div class="form-group">
            <label class="col-xs-3 control-label">分党委</label>
            <div class="col-xs-6">
                <select required class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects"
                        name="partyId" data-placeholder="请选择">
                    <option value="${party.id}">${party.name}</option>
                </select>
            </div>
        </div>
        <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
            <label class="col-xs-3 control-label">党支部</label>
            <div class="col-xs-6">
                <select class="form-control"  data-rel="select2-ajax" data-ajax-url="${ctx}/branch_selects"
                        name="branchId" data-placeholder="请选择">
                    <option value="${branch.id}">${branch.name}</option>
                </select>
            </div>
        </div>
        <script>
            var $container = $("#modalForm");
            $('select[name=partyId], select[name=branchId]', $container).select2({
                width:300,
                ajax: {
                    dataType: 'json',
                    delay: 300,
                    data: function (params) {
                        return {
                            searchStr: params.term,
                            pageSize: 10,
                            pageNo: params.page,
                            partyId: $('select[name=partyId]', $container).val()
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
            $('select[name=partyId]', $container).on("change", function () {

                var $party_class = $("select[name=partyId]", $container).select2("data")[0].class || "${party.classId}";
                //alert($party_class)
                if($(this).val()>0 && $party_class !='${cm:getMetaTypeByCode("mt_direct_branch").id}'){
                    $("#branchDiv", $container).show();
                }else{
                    $('select[name=branchId]', $container).val(null).trigger("change");
                    $("#branchDiv", $container).hide();
                }
            }).change();
        </script>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为入党积极分子时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_activeTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.activeTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">确定为发展对象时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_candidateTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.candidateTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">入党时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_growTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.growTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">转正时间</label>
				<div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_positiveTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(memberReturn.positiveTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">备注</label>
				<div class="col-xs-6">
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

    $('textarea.limited').inputlimiter();
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })

    $("#modal form").validate({
        submitHandler: function (form) {

            if(!$("#branchDiv").is(":hidden")){
                if($('#modalForm select[name=branchId]').val()=='') {
                    toastr.warning("请选择支部。", "提示");
                    return;
                }
            }

            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        page_reload();
                        toastr.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    function formatState (state) {

        if (!state.id) { return state.text; }
        var $state = state.text;
        if(state.code!=undefined && state.code.length>0)
            $state += '-' + state.code;
        if(state.unit!=undefined && state.unit.length>0){
            $state += '-' + state.unit;
        }
        //console.log($state)
        return $state;
    };

    $('#modalForm select[name=userId]').select2({
        templateResult: formatState,
        ajax: {
            dataType: 'json',
            delay: 200,
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
</script>