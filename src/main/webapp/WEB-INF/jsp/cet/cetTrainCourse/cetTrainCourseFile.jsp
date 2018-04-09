<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>教学照片</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加照片
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/cet/cetTrainCourseFile_au" id="modalForm" method="post">
                        <input type="hidden" name="trainCourseId" value="${param.trainCourseId}">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">名称（可不填）</label>
                            <div class="col-xs-6 label-text">
                                <input class="form-control" type="text" name="fileName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label">上传文件(批量)</label>
                            <div class="col-xs-6">
                                <input required class="form-control" multiple="multiple" type="file" name="_files"/>
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info btn-sm" type="submit">
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
         data-url-page="${ctx}/cet/cetTrainCourseFile?trainCourseId=${param.trainCourseId}"
         data-url-del="${ctx}/cet/cetTrainCourseFile_del"
         data-url-co="${ctx}/cet/cetTrainCourseFile_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>名称</th>
                    <th nowrap width="150"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cetTrainCourseFiles}" var="cetTrainCourseFile" varStatus="st">
                    <tr>
                        <td nowrap>
                                ${cetTrainCourseFile.fileName}
                        </td>

                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <button class="various btn btn-warning btn-xs" title="${cetTrainCourseFile.fileName}" data-path="${cetTrainCourseFile.filePath}"
                                        data-fancybox-type="image"
                                        href="${ctx}/pic?path=${cetTrainCourseFile.filePath}"><i class="fa fa-search"></i> 预览</button>
                                <button class='linkBtn btn btn-xs btn-success'
                                        data-url='${ctx}/attach/download?path=${cm:encodeURI(cetTrainCourseFile.filePath)}&filename=${cetTrainCourseFile.fileName}'>
                                    <i class="fa fa-download"></i>
                                    下载</button>
                                <shiro:hasPermission name="cetTrainCourseFile:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${cetTrainCourseFile.id}">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </shiro:hasPermission>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/cet/cetTrainCourseFile" target="#modal .modal-content"
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
    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        //$("#jqGrid").trigger("reloadGrid");
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });

    $.fileInput($('input[type=file]'));

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>