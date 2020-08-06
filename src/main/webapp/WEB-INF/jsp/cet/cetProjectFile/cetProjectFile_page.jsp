<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>培训课件</h3>
</div>
<div id="cpfDiv" class="modal-body">
    <c:if test="${param.view!=1}">
        <shiro:hasPermission name="cetProject:edit">
            <div class="widget-box">
                <div class="widget-header">
                    <h4 class="widget-title">
                        添加培训课件
                    </h4>
                </div>
                <div class="widget-body">
                    <div class="widget-main">
                        <form class="form-horizontal no-footer" action="${ctx}/cet/cetProjectFile_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                            <input type="hidden" name="projectId" value="${param.projectId}">
                            <div class="form-group">
                                <label class="col-xs-4 control-label"> 课件名称</label>
                                <div class="col-xs-6 label-text">
                                    <input class="form-control" type="text" name="fileName">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label"><span class="star">*</span> 培训课件</label>
                                <div class="col-xs-6" id="hasFile">
                                    <input class="form-control" type="file" name="_file"/>
                                </div>
                            </div>
                            <div class="clearfix form-actions">
                                <div class="col-md-offset-4 col-md-8">
                                    <button id="submitBtn" class="btn btn-info btn-sm" type="submit"
                                            data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
                                        <i class="ace-icon fa fa-check "></i>
                                        确定
                                    </button>
                                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                                    <button id="cpfReset" class="btn btn-default btn-sm" type="reset">
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
        </shiro:hasPermission>
    </c:if>
    <div class="popTableDiv"
         data-url-page="${ctx}/cet/cetProjectFile?projectId=${param.projectId}"
         data-url-del="${ctx}/cet/cetProjectFile_del"
         data-url-co="${ctx}/cet/cetProjectFile_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-center">
                <thead>
                <tr>
                    <th>课件名称</th>
                    <c:if test="${param.view!=1}">
                        <th nowrap width="40">排序</th>
                    </c:if>
                    <th nowrap width="150"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cetProjectFiles}" var="cetProjectFile" varStatus="st">
                    <tr>
                        <td nowrap style="text-align: left">
                                ${cetProjectFile.fileName}
                        </td>
                        <c:if test="${param.view!=1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetProjectFile.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetProjectFile.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <c:if test="${not empty cetProjectFile.filePath}">
                                    <button class='openUrl btn btn-xs btn-primary'
                                            title="PDF文件预览"
                                            data-url='${ctx}/pdf_preview?type=url&path=${cm:encodeURI(cetProjectFile.filePath)}&filename=${cetProjectFile.fileName}'>
                                        <i class="fa fa-search"></i>
                                        预览</button>
                                    <button class='downloadBtn btn btn-xs btn-success' data-type="download"
                                            data-url='${ctx}/res_download?path=${cm:encodeURI(cetProjectFile.filePath)}&filename=${cetProjectFile.fileName}&sign=${cm:sign(cetProjectFile.filePath)}'>
                                        <i class="fa fa-download"></i>
                                        下载</button>
                                </c:if>
                                <c:if test="${param.view!=1}">
                                    <shiro:hasPermission name="cetProject:del">
                                        <button class="delBtn btn btn-danger btn-xs"
                                                data-callback="_reloadProjectList"
                                                data-id="${cetProjectFile.id}">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                            </div>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/cet/cetProjectFile" target="#cpfDiv"
                         pageNum="5"
                         model="3"/>
            </c:if>
        </c:if>
        <c:if test="${commonList.recNum==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
</div>
<script>

    $("#cpfReset").on("click", function () {
        $("#modalForm .remove").click();
    })
    function _reloadProjectList(){
        $("#jqGrid").trigger("reloadGrid");
    }

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            var _file = $("#modalForm input[name=_file]").val();
            if($.trim(_file)==''){
                $.tip({
                    $target: $("#hasFile"),
                    at: 'right center', my: 'left center',
                    msg: "请选择上传的培训课件。"
                });
                return;
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload(function(){
                            _reloadProjectList();
                        });
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $.fileInput($('input[type=file]'),{
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
</script>