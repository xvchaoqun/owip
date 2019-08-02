<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="file" value="${param.fileType==1?scSubsidy.hrFilePath:scSubsidy.feFilePath}"/>
<c:set var="code" value="${param.fileType==1?scSubsidy.hrCode:scSubsidy.feCode}"/>
<div class="row dispatch_au">
    <div class="preview">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    ${param.fileType==1?'发人事处通知':'发财经处通知'}
                    <div style="position: absolute; left:125px;top:8px;">
                        <form action="${ctx}/sc/scSubsidy_upload"
                              enctype="multipart/form-data" method="post"
                              class="btn-upload-form">
                            <button type="button"
                                    data-loading-text="<i class='fa fa-spinner fa-spin '></i> 上传中..."
                                    class="btn btn-xs btn-primary">
                                <i class="ace-icon fa fa-upload"></i>
                                上传正式反馈版
                            </button>
                            <input type="file" name="file" id="upload-file"/>
                        </form>
                    </div>
                    <div class="buttons pull-right ">
                        <c:if test="${not empty file}">
                        <button style="margin-right: 15px;" type="button" data-url="${ctx}/attach_download?path=${cm:encodeURI(file)}&filename=${cm:encodeURI(code)}"
                                class="downloadBtn btn btn-xs btn-warning">
                            <i class="ace-icon fa fa-download"></i>
                            下载
                        </button>
                        </c:if>
                        <a href="javascript:;" class="hideView btn btn-xs btn-success"
                           style="margin-right: 10px; top: -5px;">
                            <i class="ace-icon fa fa-backward"></i>
                            返回</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <div id="dispatch-file-view">
                        <c:import url="${ctx}/pdf_preview?type=html&path=${file}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    $("#upload-file").change(function () {
        if ($("#upload-file").val() != "") {
            var $this = $(this);
            var $form = $this.closest("form");
            var $btn = $("button", $form).button('loading');
            var viewHtml = $("#dispatch-file-view").html()
            $("#dispatch-file-view").html('<img src="${ctx}/img/loading.gif"/>')
            $form.ajaxSubmit({
                data:{id:'${param.id}', fileType:'${param.fileType}'},
                success: function (ret) {
                    if (ret.success) {
                        //console.log(ret)
                        $("#dispatch-file-view").load("${ctx}/pdf_preview?type=html&path=" + encodeURI(ret.file));

                        $("#modalForm input[name=file]").val(ret.file);
                        $("#modalForm input[name=fileName]").val(ret.fileName);
                    }else{
                        $("#dispatch-file-view").html(viewHtml)
                    }
                    $btn.button('reset');
                    $this.removeAttr("disabled");
                }
            });
            $this.attr("disabled", "disabled");
        }
    });
</script>