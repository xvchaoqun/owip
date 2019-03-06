<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>学习内容</h3>
</div>
<div class="modal-body">
    <c:if test="${param.view!=1}">
    <shiro:hasPermission name="cetCourse:edit">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加学习内容
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/cet/cetCourseFile_au" id="modalForm" method="post">
                        <input type="hidden" name="courseId" value="${param.courseId}">
                        <div class="form-group">
                            <label class="col-xs-4 control-label"><span class="star">*</span>材料名称</label>
                            <div class="col-xs-6 label-text">
                                <input required class="form-control" type="text" name="fileName">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">上传电子学习材料</label>
                            <div class="col-xs-6">
                                <input class="form-control" type="file" name="_file"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">纸质学习材料</label>
                            <div class="col-xs-3" id="hasPaper">
                                <input type="checkbox" class="big" name="hasPaper" data-on-text="有" data-off-text="无">
                            </div>
                        </div>
                        <div class="form-group" id="paperNote" style="display:none">
                            <label class="col-xs-4 control-label">纸质学习材料说明</label>
                            <div class="col-xs-6">
                                <input class="form-control" type="text" name="paperNote" value="已发放纸质学习材料">
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button id="submitBtn" class="btn btn-info btn-sm" type="submit"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
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
    </shiro:hasPermission>
    </c:if>
    <div class="popTableDiv"
         data-url-page="${ctx}/cet/cetCourseFile?courseId=${param.courseId}"
         data-url-del="${ctx}/cet/cetCourseFile_del"
         data-url-co="${ctx}/cet/cetCourseFile_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-center">
                <thead>
                <tr>
                    <th>材料名称</th>
                    <th width="90">备注</th>
                    <c:if test="${param.view!=1}">
                        <th nowrap width="40">排序</th>
                    </c:if>
                    <th nowrap width="150"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cetCourseFiles}" var="cetCourseFile" varStatus="st">
                    <tr>
                        <td nowrap style="text-align: left">
                                ${cetCourseFile.fileName}
                        </td>
                        <td nowrap>
                                ${cetCourseFile.hasPaper?cetCourseFile.paperNote:'--'}
                        </td>
                        <c:if test="${param.view!=1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetCourseFile.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${cetCourseFile.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                            </c:if>
                            <td nowrap>
                                <div class="hidden-sm hidden-xs action-buttons">

                                    <c:if test="${not empty cetCourseFile.filePath}">
                                    <button class='openUrl btn btn-xs btn-primary'
                                            data-url='${ctx}/swf/preview?type=url&path=${cm:encodeURI(cetCourseFile.filePath)}&filename=${cetCourseFile.fileName}'>
                                        <i class="fa fa-search"></i>
                                        预览</button>
                                    <button class='linkBtn btn btn-xs btn-success'
                                            data-url='${ctx}/attach/download?path=${cm:encodeURI(cetCourseFile.filePath)}&filename=${cetCourseFile.fileName}'>
                                        <i class="fa fa-download"></i>
                                        下载</button>
                                    </c:if>
                                    <c:if test="${param.view!=1}">
                                    <shiro:hasPermission name="cetCourse:del">
                                        <button class="delBtn btn btn-danger btn-xs" data-id="${cetCourseFile.id}">
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
                <wo:page commonList="${commonList}" uri="${ctx}/cet/cetCourseFile" target="#modal .modal-content"
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
    $('input[name=hasPaper]').on('switchChange.bootstrapSwitch', function(event, state) {
        if(!$("input[name=hasPaper]").bootstrapSwitch("state")) {
            $("#paperNote").hide();
            $("input[name=paperNote]").prop("disabled", true).removeAttr("required");
        }else {
            $("#paperNote").show();
            $("input[name=paperNote]").prop("disabled", false).attr("required", "required");
        }
    });

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;});
    $("#modalForm").validate({
        submitHandler: function (form) {

            var hasPaper = $("#modalForm input[name=hasPaper]").bootstrapSwitch("state")
            var _file = $("#modalForm input[name=_file]").val();
            if(!hasPaper && $.trim(_file)==''){
                $.tip({
                    $target: $("#hasPaper"),
                    at: 'right center', my: 'left center',
                    msg: "上传文件和发放纸质学习材料两项内容至少有一个。"
                });
                return;
            }
            var $btn = $("#submitBtn").button('loading');
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        //$("#jqGrid").trigger("reloadGrid");
                        //SysMsg.success('操作成功。', '成功');
                    }
                    $btn.button('reset');
                }
            });
        }
    });
    $("#modalForm :checkbox").bootstrapSwitch();
    $.fileInput($('input[type=file]'),{
        no_file: '请上传pdf或word文件...',
        allowExt: ['pdf', 'doc', 'docx'],
        allowMime: ['application/pdf', 'application/msword','application/vnd.openxmlformats-officedocument.wordprocessingml.document']
    });
</script>