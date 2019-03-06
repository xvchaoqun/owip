<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        更新丢失证明
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/abroad/updateLostProof" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${passport.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label"><span class="star">*</span>登记丢失日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_lostTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${passport==null?_today:cm:formatDate(passport.lostTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">${passport==null?'*':''}丢失证明</label>
                <div class="col-xs-6">
                    <input  ${passport==null?'required':''} class="form-control" type="file" name="_lostProof" />
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="<c:if test="${passport!=null}">确定</c:if><c:if test="${passport==null}">添加</c:if>"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        //SysMsg.success('提交成功。', '成功',function(){

                            $("#body-content-view").load("${ctx}/abroad/passport_lost_view?id=${passport.id}")
                        //});
                    }
                }
            });
        }
    });
    $.register.date($('.date-picker'))
    $.fileInput($('#modalForm input[type=file]'))
</script>