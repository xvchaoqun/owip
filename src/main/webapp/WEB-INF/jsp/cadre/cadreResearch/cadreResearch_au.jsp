<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${cadreResearch!=null}">编辑</c:if><c:if test="${cadreResearch==null}">添加</c:if>干部科研情况</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/cadreResearch_au" id="modalForm" method="post">
        <input type="hidden" name="id" value="${cadreResearch.id}">
        <input type="hidden" name="cadreId" value="${cadre.id}">
        <div class="form-group">
            <label class="col-xs-4 control-label">所属干部</label>
            <div class="col-xs-6">
                <input type="text" value="${sysUser.realname}" disabled>
            </div>
        </div>
			<div class="form-group">
				<label class="col-xs-4 control-label">主持科研项目情况</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_chairFile" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">参与科研项目情况</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_joinFile" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">出版著作及发表论文等情况</label>
				<div class="col-xs-6">
                    <input class="form-control" type="file" name="_publishFile" />
				</div>
			</div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${cadreResearch!=null}">确定</c:if><c:if test="${cadreResearch==null}">添加</c:if>"/>
</div>

<script>

    $('#modalForm input[type=file]').ace_file_input({
        no_file:'请选择文件 ...',
        btn_choose:'选择',
        btn_change:'更改',
        droppable:false,
        onchange:null,
        thumbnail:false //| true | large
        //whitelist:'gif|png|jpg|jpeg'
        //blacklist:'exe|php'
        //onchange:''
        //
    });

    $("#modal form").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        _reload();
                        SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $('#modalForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>