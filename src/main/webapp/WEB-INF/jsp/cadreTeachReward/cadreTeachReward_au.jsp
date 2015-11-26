<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreTeachReward!=null}">编辑</c:if><c:if test="${cadreTeachReward==null}">添加</c:if>干部教学奖励</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreTeachReward_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreTeachReward.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-3 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-3 control-label">日期</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${cadreTeachReward.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">获得奖项</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="type" value="${cadreTeachReward.type}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">颁奖单位</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="unit" value="${cadreTeachReward.unit}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label">排名</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="rank" value="${cadreTeachReward.rank}">
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreTeachReward!=null}">确定</c:if><c:if test="${cadreTeachReward==null}">添加</c:if>"/>
</div>

<script>

    function _au(id) {
        url = "${ctx}/cadreParttime_au?cadreId=${param.cadreId}";
        if (id > 0)  url += "&id=" + id;
        loadModal(url);
    }

    function _del(id){
        bootbox.confirm("确定删除该记录吗？", function (result) {
            if (result) {
                $.post("${ctx}/cadreParttime_del", {id: id}, function (ret) {
                    if (ret.success) {
                        _reload();
                        toastr.success('操作成功。', '成功');
                    }
                });
            }
        });
    }
    function _reload(){
        $("#modal").modal('hide');
        $("#cadre-box .tab-content").load("${ctx}/cadreParttime_page?${pageContext.request.queryString}");
    }

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
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $('#modalForm [data-rel="select2-ajax"]').select2({
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