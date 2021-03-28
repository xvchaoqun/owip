
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${qyYear.year}表彰${param.type==1?"方案":"结果"}文件</h3>
</div>
<div class="modal-body" style="overflow:auto">
    <div class="widget-box">
        <div class="widget-header"></div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal" action="${ctx}/qyYear_file" autocomplete="off" disableautocomplete id="modalForm" method="post"
                      enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${param.yearId}">
                    <input type="hidden" name="type" value="${param.type}">
                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span> 上传材料(pdf格式)</label>

                        <div class="col-xs-6">
                            <input class="form-control" type="file" name="pdf"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-4 control-label"><span class="star">*</span> 上传材料(word格式)</label>

                        <div class="col-xs-6">
                            <input class="form-control" type="file" name="word"/>
                        </div>
                    </div>

                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info btn-sm" type="submit" id="submitBtn">
                                <i class="ace-icon fa fa-check "></i>
                                确定
                            </button>
                            &nbsp; &nbsp; &nbsp;
                            <button class="btn btn-default btn-sm" type="reset">
                                <i class="ace-icon fa fa-undo"></i>
                                重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/qyYear_file?yearId=${param.yearId}&type=${param.type}"
        <%-- data-url-del="${ctx}/qyYear_delFile?type=${param.type}&fileType=1"--%>
    >
        <c:if test="${param.type==1}">
            <c:if test="${not empty qyYear.planPdf||not empty qyYear.planWord}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>文件</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                      <c:if test="${not empty qyYear.planPdf}">
                        <tr>

                            <td nowrap>${qyYear.planPdfName}</td>
                            <td nowrap>

                                    <button class='openUrl btn btn-xs btn-primary'
                                            data-url='${ctx}/${qyYear.planPdfName}.pdf?path=${cm:sign(qyYear.planPdf)}'>
                                        <i class="fa fa-search"></i>
                                        预览</button>
                                    <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                                            data-url="${ctx}/attach_download?path=${cm:sign(qyYear.planPdf)}">
                                        <i class="fa fa-download"></i> 下载</button>

                                    <button data-url="${ctx}/qyYear_delFile?id=${param.yearId}&type=${param.type}&fileType=1"
                                            data-title="删除"
                                            data-msg="确定删除？<br/>（删除后无法恢复，请谨慎操作！！）"
                                            data-callback="_reloadDetail"
                                            class="confirm btn btn-danger btn-xs">
                                        <i class="fa fa-times"></i> 删除</button>

                            </td>
                        </tr>
                      </c:if>
                      <c:if test="${not empty qyYear.planWord}">
                        <tr>
                            <td nowrap>${qyYear.planWordName}</td>
                            <td nowrap>

                                   <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                                           data-url="${ctx}/attach_download?path=${cm:sign(qyYear.planWord)}">
                                       <i class="fa fa-download"></i> 下载</button>
                                   <button data-url="${ctx}/qyYear_delFile?id=${param.yearId}&type=${param.type}&fileType=2"
                                        data-title="删除"
                                        data-msg="确定删除？<br/>（删除后无法恢复，请谨慎操作！！）"
                                        data-callback="_reloadDetail"
                                        class="confirm btn btn-danger btn-xs">
                                    <i class="fa fa-times"></i> 删除</button>
                            </td>
                        </tr>
                      </c:if>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty qyYear.planPdf&&empty qyYear.planWord}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </c:if>
        <c:if test="${param.type==2}">
            <c:if test="${not empty qyYear.resultPdf||not empty qyYear.resultWord}">
                <table class="table table-actived table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>文件</th>
                        <th nowrap></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty qyYear.resultPdf}">
                        <tr>

                            <td nowrap>${qyYear.resultPdfName}</td>
                            <td nowrap>
                                <button class='openUrl btn btn-xs btn-primary'
                                        data-url='${ctx}/${qyYear.resultPdfName}.pdf?path=${cm:sign(qyYear.resultPdf)}'>
                                    <i class="fa fa-search"></i>
                                    预览</button>
                                <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                                        data-url="${ctx}/attach_download?path=${cm:sign(qyYear.resultPdf)}">
                                    <i class="fa fa-download"></i> 下载</button>

                                <button data-url="${ctx}/qyYear_delFile?id=${param.yearId}&type=${param.type}&fileType=1"
                                        data-title="删除"
                                        data-msg="确定删除？<br/>（删除后无法恢复，请谨慎操作！！）"
                                        data-callback="_reloadDetail"
                                        class="confirm btn btn-danger btn-xs">
                                    <i class="fa fa-times"></i> 删除</button>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty qyYear.resultWord}">
                        <tr>
                            <td nowrap>${qyYear.resultWordName}</td>
                            <td nowrap>
                                <button class="downloadBtn btn btn-xs btn-info" data-type="download"
                                                data-url="${ctx}/attach_download?path=${cm:sign(qyYear.resultWord)}">
                                            <i class="fa fa-download"></i> 下载</button>
                                <button data-url="${ctx}/qyYear_delFile?id=${param.yearId}&type=${param.type}&fileType=2"
                                                data-title="删除"
                                                data-msg="确定删除？<br/>（删除后无法恢复，请谨慎操作！！）"
                                                data-callback="_reloadDetail"
                                                class="confirm btn btn-danger btn-xs">
                                            <i class="fa fa-times"></i> 删除</button>
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty qyYear.resultPdf&&empty qyYear.resultWord}">
                <div class="well well-lg center">
                    <h4 class="green lighter">暂无记录</h4>
                </div>
            </c:if>
        </c:if>
    </div>
</div>

<script>
    $.fileInput($('#modalForm input[name=pdf]'),{
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
    $.fileInput($("#modalForm input[name=word]"),{
        allowExt: ['doc', 'docx'],
        allowMime: ['application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
    function _reloadDetail(){
        pop_reload();
    }
    $("#submitBtn").click(function(){
       if($.isBlank($('#modalForm input[name=pdf]').val())&&$.isBlank($('#modalForm input[name=word]').val())){
           $('#modalForm input[type=file]').attr("required",true);
       } else{
            $('#modalForm input[type=file]').removeAttr("required");
        }
    });

    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        <%--$("#modal-content").load("${ctx}/qyYear_file?yearId=${param.yearId}&type=${raram.type}");--%>
                         pop_reload();

                    }
                }
            });
        }
    });
</script>

