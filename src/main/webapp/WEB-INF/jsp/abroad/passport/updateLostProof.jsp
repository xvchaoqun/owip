<%@ page import="sys.utils.DateUtils" %>
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
    <form class="form-horizontal" action="${ctx}/updateLostProof" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${passport.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">登记丢失日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <c:set var="today" value='<%=DateUtils.getCurrentDateTime("yyyy-MM-dd")%>'/>
                        <input required class="form-control date-picker" name="_lostTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${passport==null?today:cm:formatDate(passport.lostTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">丢失证明</label>
                <div class="col-xs-6">
                    <input  ${passport==null?'required':''} class="form-control" type="file" name="_lostProof" />
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
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

                            $("#item-content").load("${ctx}/passport_lost_view?id=${passport.id}")
                        //});
                    }
                }
            });
        }
    });
    $('.date-picker').datepicker({
        language:"zh-CN",
        autoclose: true,
        todayHighlight: true
    })
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
</script>