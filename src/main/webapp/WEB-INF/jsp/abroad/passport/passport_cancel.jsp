<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row passport_apply">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <div style="margin-bottom: 8px">

            <div class="buttons">
                <a href="javascript:" class="closeView btn btn-sm btn-success">
                    <i class="ace-icon fa fa-backward"></i>
                    返回
                </a>
                <button id="print" class="btn btn-info btn-sm"
                        style="margin-left: 50px" ><i class="fa fa-print"></i>  打印确认单</button>

                <shiro:hasAnyRoles name="admin,cadreAdmin">
                    <button id="submit" class="btn btn-warning btn-sm"
                            data-rel="tooltip" data-placement="bottom"
                            title="点击即提交签字拍照文件"><i class="fa fa-upload"></i>  确认取消集中管理</button>
                </shiro:hasAnyRoles>
            </div>
        </div>
    </ul>
    <div class="preview" style="margin: 20px 5px 50px 0px;">
        <img src="${ctx}/report/cancel?id=${param.id}" width="595" height="842"/>
    </div>
    <div class="info" style="margin-top: 20px; margin-bottom: 50px; padding-left: 5px">
    <form class="form-horizontal" action="${ctx}/passport_cancel" id="modalForm" method="post"  enctype="multipart/form-data">
        <input type="hidden" name="id" value="${param.id}">
        <div class="form-group">
            <div class="col-xs-12 file" style="height: 842px">
                <input required type="file" name="_cancelPic" />
            </div>
        </div>
    </form>
    </div>
</div>
<style>
    .ace-file-multiple .ace-file-container{
        height: 840px;
    }
    .ace-file-multiple .ace-file-container .ace-file-name .ace-icon{
        line-height: 380px;
    }
    .ace-file-multiple .ace-file-container:before{
        line-height: 220px;
        font-size: 28pt;
    }
</style>
<script src="${ctx}/extend/js/jquery.jqprint-0.3.js"></script>
<script>
    $("#print").click(function(){
        printWindow("${ctx}/report/cancel?id=${param.id}&format=pdf");
    });
    $("#print_proof").click(function(){
        printWindow('${ctx}/pic?path=${fn:replace(passport.cancelPic, "\\","\\/"  )}');
    });

    /*$('#modalForm input[type=file]').ace_file_input({
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
    });*/
    $('input[type=file]').ace_file_input({
        style:'well',
        btn_choose:'请选择签字拍照',
        btn_change:null,
        no_icon:'ace-icon fa fa-picture-o',
        thumbnail:'large',
        droppable:true,
       /* previewWidth: 300,*/
        previewHeight: 840,
        allowExt: ['jpg', 'jpeg', 'png', 'gif'],
        allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
    }).end().find('button[type=reset]').on(ace.click_event, function(){
        $('input[type=file]').ace_file_input('reset_input');
    });
    $("#submit").click(function(){
        if($('input[type=file]').val()==''){
            SysMsg.info("请选择签字拍照");
            return;
        }
        $("#modalForm").ajaxSubmit({
            success:function(ret){
                if(ret.success){
                    //SysMsg.success('提交成功。', '成功', function(){
                        page_reload();
                    //});
                }
            }
        });
    });
    $('[data-rel="tooltip"]').tooltip();
</script>