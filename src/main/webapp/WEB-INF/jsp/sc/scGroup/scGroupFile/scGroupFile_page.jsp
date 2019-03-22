<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>管理文件</h3>
</div>
<div class="modal-body">
    <shiro:hasPermission name="scGroupFile:edit">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    添加文件
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/sc/scGroupFile_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
                        <div class="form-group">
                            <label class="col-xs-3 control-label">文件名称</label>
                            <div class="col-xs-6 label-text">
                                <input class="form-control" type="text" name="fileName">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 control-label"><span class="star">*</span>上传文件(批量)</label>
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
    </shiro:hasPermission>
    <div class="popTableDiv"
         data-url-page="${ctx}/sc/scGroupFile_page?recordId=${param.recordId}&type=${type}"
         data-url-del="${ctx}/sc/scGroupFile_del"
         data-url-co="${ctx}/sc/scGroupFile_changeOrder">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>文件名称</th>
                    <c:if test="${!_query && commonList.recNum>1}">
                        <th nowrap width="40">排序</th>
                    </c:if>
                    <th nowrap width="150"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${scGroupFiles}" var="scGroupFile" varStatus="st">
                    <tr>
                        <td nowrap>
                                ${scGroupFile.fileName}
                        </td>

                        <c:if test="${!_query && commonList.recNum>1}">
                            <td nowrap>
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${scGroupFile.id}" data-direction="1" title="上升"><i
                                        class="fa fa-arrow-up"></i></a>
                                <input type="text" value="1"
                                       class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                       title="修改操作步长">
                                <a href="javascript:;"
                                   <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                   class="changeOrderBtn" data-id="${scGroupFile.id}" data-direction="-1"
                                   title="下降"><i class="fa fa-arrow-down"></i></a></td>
                            </td>
                        </c:if>

                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <c:if test="${scGroupFile.isCurrent}">
                                    已应用
                                </c:if>
                                <c:if test="${!scGroupFile.isCurrent}">
                                <button class="confirm btn btn-success btn-xs"
                                        data-msg="确定应用该文件？"
                                        data-callback="_setCurrent"
                                        data-url="${ctx}/sc/scGroupFile_setCurrent?id=${scGroupFile.id}">
                                    <i class="fa fa-check"></i> 应用
                                </button>
                                </c:if>
                                <button class='openUrl btn btn-xs btn-primary'
                                        data-url='${ctx}/swf/preview?type=url&path=${cm:encodeURI(scGroupFile.filePath)}&filename=${scGroupFile.fileName}'>
                                    <i class="fa fa-search"></i>
                                    预览</button>
                                <shiro:hasPermission name="scGroupFile:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${scGroupFile.id}">
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
                <wo:page commonList="${commonList}" uri="${ctx}/sc/scGroupFile_page" target="#modal .modal-content"
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

    function _setCurrent(){
        //pop_reload();
        $.hashchange();
    }

    $.fileInput($('input[type=file]'),{
        no_file: '请上传pdf文件...',
        allowExt: ['pdf'],
        allowMime: ['application/pdf']
    });
</script>