<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${cm:getUserById(cadre.userId).realname}-兼审单位</h3>
</div>
<div class="modal-body">
<shiro:hasPermission name="cadreAdditionalPost:edit">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                兼审单位——仅用于因私出国（境）审批人身份设定
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <form class="form-horizontal no-footer" action="${ctx}/cadreAdditionalPost_au?cadreId=${cadre.id}" id="modalForm" method="post">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">姓名</label>
                        <div class="col-xs-6 label-text">
                            ${cadre.realname}
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">职务属性</label>
                        <div class="col-xs-6">
                            <select required data-rel="select2" name="postId" data-placeholder="请选择职务属性">
                                <option></option>
                                <jsp:include page="/metaTypes?__code=mc_post"/>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">所属单位</label>
                        <div class="col-xs-8">
                            <select required class="form-control" name="unitId" data-rel="select2" data-placeholder="请选择所属单位">
                                <option></option>
                                <c:forEach items="${unitMap}" var="unit">
                                    <option value="${unit.key}">${unit.value.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>
                        <div class="col-xs-6">
                            <textarea class="form-control limited" name="remark" rows="5"></textarea>
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
    </shiro:hasPermission>
    <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/cadre_additional_post?id=${cadre.id}"
         data-url-del="${ctx}/cadreAdditionalPost_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>职务属性</th>
                    <th >所属单位</th>
                    <th >备注</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${cadreAdditionalPosts}" var="cadreAdditionalPost" varStatus="st">
                    <tr>
                        <td nowrap>${cm:getMetaType(cadreAdditionalPost.postId).name}</td>
                        <td nowrap>
                                ${unitMap.get(cadreAdditionalPost.unitId).name}
                        </td>
                        <td>${cadreAdditionalPost.remark}</td>
                        <td nowrap>
                            <div class="hidden-sm hidden-xs action-buttons">
                                <shiro:hasPermission name="cadreAdditionalPost:del">
                                    <button class="delBtn btn btn-danger btn-xs" data-id="${cadreAdditionalPost.id}">
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
                <wo:page commonList="${commonList}" uri="${ctx}/cadre_additional_post" target="#modal .modal-content"
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
    $('#modalForm [data-rel="select2"]').select2();

    $("#modal button[type=submit]").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        pop_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
</script>