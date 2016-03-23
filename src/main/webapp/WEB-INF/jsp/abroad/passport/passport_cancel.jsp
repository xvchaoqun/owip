<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <div class="preview">
    <c:if test="${!passport.cancelConfirm}">
        <iframe id="myframe" src="${ctx}/report/cancel?id=${param.id}" width="595" height="842" frameborder="0"  border="0" marginwidth="0" marginheight="0"></iframe>
    </c:if>

    <c:if test="${passport.cancelConfirm}">
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px;width: 595px">
            <img src="${ctx}/img?path=${passport.cancelPic}" style="max-width: 595px"/>
            </div>
    </c:if>
    </div>
    <div class="info">
        <c:if test="${passport.cancelConfirm}">
            <div class="center" style="margin-top: 40px">
                <button id="print_proof" class="btn btn-info btn-block" style="font-size: 30px">打印证明</button>
            </div>
        </c:if>
        <c:if test="${!passport.cancelConfirm}">
        <div class="center" style="margin-top: 40px">
            <button id="print" class="btn btn-info btn-block" style="font-size: 30px">打印确认单</button>
        </div>
        <div style="margin: 30px 0 30px 0;border: 1px dashed #aaaaaa;padding: 20px">
            <form class="form-horizontal" action="${ctx}/passport_cancel" id="modalForm" method="post"  enctype="multipart/form-data">
                <input type="hidden" name="id" value="${param.id}">
                <div class="form-group">
                    <label class="col-xs-3 control-label">签字拍照</label>
                    <div class="col-xs-6">
                        <input class="form-control" type="file" name="_cancelPic" />
                    </div>
                </div>
            </form>
            <div>
                <button id="submit" class="btn btn-success btn-block" style="margin-top:20px;font-size: 20px">确认取消集中管理</button>
            </div>
        </div>
        </c:if>
        <div class="center" style="margin-top: 40px">
            <button class="closeView reload btn btn-default btn-block" style="margin-top:20px;font-size: 30px">返回</button>
        </div>
    </div>
</div>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>
    $("#print").click(function(){
        printWindow("${ctx}/report/cancel?id=${param.id}");
    });
    $("#print_proof").click(function(){
        printWindow('${ctx}/img?path=${fn:replace(passport.cancelPic, "\\","\\/"  )}');
    });

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
    $("#submit").click(function(){
        if($('input[type=file]').val()==''){
            SysMsg.info("请选择签字拍照");
            return;
        }
        $("#modalForm").ajaxSubmit({
            success:function(ret){
                if(ret.success){
                    SysMsg.success('提交成功。', '成功', function(){
                        page_reload();
                    });
                }
            }
        });
    });
</script>