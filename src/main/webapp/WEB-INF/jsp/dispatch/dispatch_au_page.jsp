<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    任免文件预览
                    <div style="position: absolute; left:125px;top:8px;">
                        <form action="${ctx}/dispatch_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <a href="javascript:;" class="closeView btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传任免文件</a>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">

                        <a href="javascript:;" class="closeView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/swf/preview?type=html&path=${dispatch.file}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="au">
        <div id="dispatch-cadres-view">
            <c:import url="/dispatch_au?id=${dispatch.id}"/>
        </div>
    </div>
</div>
<script>
    register_date($('.date-picker'));
    register_dispatch_select($('.dispatch_cadres select[name=dispatchTypeId]'),
            $(".dispatch_cadres input[name=year]"), $(".dispatch_cadres select[name=dispatchId]"));
    $("#upload-file").change(function () {
        $(".swf-file").html('<img src="${ctx}/img/loading.gif"/>')
        $(this).closest("form").ajaxSubmit({
            success: function (ret) {
                if (ret.success) {
                    console.log(ret)
                    $("#dispatch-file-view").load("${ctx}/swf/preview?type=html&path="+ret.file);
                    $("#modalForm input[name=file]").val(ret.file);
                    $("#modalForm input[name=fileName]").val(ret.fileName);
                }
            }
        });
    });
</script>