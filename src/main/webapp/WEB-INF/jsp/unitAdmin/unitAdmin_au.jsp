<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" onclick='unitAdmin_page("${param.groupId}")'  aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${unitAdmin!=null}">编辑</c:if><c:if test="${unitAdmin==null}">添加</c:if>行政班子成员信息</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/unitAdmin_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${unitAdmin.id}">
        <input type="hidden" name="groupId" value="${unitAdminGroup.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属班子</label>
            <div class="col-xs-6">
                <input type="text" disabled value="${unitAdminGroup.name}">
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">关联干部</label>
				<div class="col-xs-6">
                    <select required data-rel="select2-ajax" data-ajax--url="${ctx}/unitCadre_selects?unitId=${unit.id}"
                            name="cadreId" data-placeholder="请选择">
                        <option value="${cadre.id}">${sysUser.realname}</option>
                    </select>
				</div>
			</div>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-6">
                    * 此处显示的是“所在单位”或“兼职单位”为当前单位的干部
                    </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">职务属性</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="postId" data-placeholder="请选择">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_post"/>
                    </select>
                    <script type="text/javascript">
                        $("#modal form select[name=postId]").val(${unitAdmin.postId});
                    </script>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">是否管理员</label>
				<div class="col-xs-6">
                    <label>
                        <input name="isAdmin" ${unitAdmin.isAdmin?"checked":""} class="ace ace-switch ace-switch-5" type="checkbox" />
                        <span class="lbl"></span>
                    </label>
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" onclick='unitAdmin_page("${param.groupId}")' class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${unitAdmin!=null}">确定</c:if><c:if test="${unitAdmin==null}">添加</c:if>"/>
</div>

<script>
    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        unitAdmin_page("${param.groupId}");
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

    $('#modalForm [data-rel="select2-ajax"]').select2({
        templateResult: formatState,
        //minimumInputLength: 2,
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
</script>