<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${dispatchType!=null}">编辑</c:if><c:if test="${dispatchType==null}">添加</c:if>发文类型</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/dispatchType_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${dispatchType.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required autocomplete="off" class="form-control date-picker" placeholder="请选择年份" name="year" type="text"
                               data-date-format="yyyy" data-date-min-view-mode="2" value="${dispatchType.year}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>名称</label>
				<div class="col-xs-6">
                        <input required class="form-control" type="text" name="name" value="${dispatchType.name}">
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-3 control-label"><span class="star">*</span>发文属性</label>
				<div class="col-xs-6">
                    <select required data-rel="select2" name="attr"
                            data-width="272"
                            data-placeholder="请选择属性">
                        <option></option>
                        <c:import url="/metaTypes?__code=mc_dispatch_attr"/>
                    </select>
                    <script type="text/javascript">
                        $("#modalForm select[name=attr]").val(${dispatchType.attr});
                    </script>
                     <%--   <input required class="form-control" type="text" name="attr" value="${dispatchType.attr}">--%>
				</div>
			</div>

    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${dispatchType!=null}">确定</c:if><c:if test="${dispatchType==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                       // });
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'));
    $('#modalForm [data-rel="select2"]').select2();
</script>