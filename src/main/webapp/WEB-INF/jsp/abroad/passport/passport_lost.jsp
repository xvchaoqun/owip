<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>
        证件丢失
    </h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/passport_lost" id="modalForm" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${passport.id}">
            <div class="form-group">
                <label class="col-xs-3 control-label">所属干部</label>
                <div class="col-xs-6 label-text">
                    ${sysUser.realname}
                </div>
            </div>
        <div class="form-group">
            <label class="col-xs-3 control-label">证件名称</label>
            <div class="col-xs-6 label-text">
                ${passportTypeMap.get(passport.classId).name}
            </div>
        </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">丢失日期</label>
                <div class="col-xs-6">
                    <div class="input-group">
                        <input required class="form-control date-picker" name="_lostTime" type="text"
                               data-date-format="yyyy-mm-dd" value="${cm:formatDate(passport.lostTime,'yyyy-MM-dd')}" />
                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-xs-3 control-label">丢失证明</label>
                <div class="col-xs-6">
                    <input class="form-control" type="file" name="_lostProof" />
                </div>
            </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <input type="submit" class="btn btn-primary" value="确定"/>
</div>

<script>
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success:function(ret){
                    if(ret.success){
                        $("#modal").modal('hide');
                        SysMsg.success('提交成功。', '成功',function(){
                            $("#jqGrid").trigger("reloadGrid");
                        });
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