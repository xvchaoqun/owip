<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${leader!=null}">编辑</c:if><c:if test="${leader==null}">添加</c:if>校领导</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/leader_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${leader.id}">
			<div class="form-group">
				<label class="col-xs-3 control-label">校领导</label>
				<div class="col-xs-6">
                    <select data-rel="select2-ajax" data-ajax--url="${ctx}/cadre_selects"
                            name="cadreId" data-placeholder="请选择干部">
                        <option value="${cadre.id}">${sysUser.username}</option>
                    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">类别</label>
				<div class="col-xs-6">
                    <select data-rel="select2" name="typeId" data-placeholder="请选择类别">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_leader_type"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=typeId]").val(${leader.typeId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">分管工作</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="job" value="${leader.job}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${leader!=null}">确定</c:if><c:if test="${leader==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
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
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    function formatState (state) {

        if (!state.id) { return state.text; }
        var $state = state.text;
        if(state.realname!=undefined && state.realname.length>0){
            $state += '-' + state.realname;
        }
        if(state.code!=undefined && state.code.length>0){
            $state += '-' + state.code;
        }
        //console.log($state)
        return $state;
    };

    $('[data-rel="select2-ajax"]').select2({
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