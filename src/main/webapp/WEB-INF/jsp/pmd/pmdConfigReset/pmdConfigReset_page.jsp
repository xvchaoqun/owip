<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党费重新计算</h3>
</div>
<div class="modal-body">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    党费重新计算
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/pmd/pmdConfigReset_au" id="modalForm" method="post">

                        <div class="form-group">
                            <label class="col-xs-3 control-label">选择工资月份</label>
                            <div class="col-xs-6">
                                <select required data-rel="select2"
                                        name="salaryMonth" data-placeholder="请选择">
                                    <option></option>
                                    <c:forEach items="${salaryMonthList}" var="salaryMonth">
                                        <option value="${salaryMonth}">${salaryMonth}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button id="submitBtn" class="btn btn-info btn-sm"
                                        data-loading-text="<i class='fa fa-spinner fa-spin '></i> 同步数据中，请稍后"
                                        type="button">
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
         data-url-page="${ctx}/pmd/pmdConfigReset">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-actived table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">工资月份</th>
                    <th class="col-xs-5">操作人</th>
                    <th class="col-xs-5">操作时间</th>
                    <th class="col-xs-5">IP</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pmdConfigResets}" var="record" varStatus="st">
                    <c:set value="${cm:getUserById(record.userId)}" var="sysUser"/>
                    <tr>
                        <td nowrap>${cm:formatDate(record.salaryMonth, "yyyy年MM月")}</td>
                        <td nowrap>
                            ${sysUser.realname}
                        </td>
                        <td nowrap>
                            ${cm:formatDate(record.createTime, "yyyy-MM-dd mm:HH:ss")}
                        </td>
                        <td nowrap>
                            ${record.ip}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/pmd/pmdConfigReset" target="#modal .modal-content"
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
    $("#modal #submitBtn").click(function(){$("#modalForm").submit(); return false;})
    $("#modalForm").validate({
        submitHandler: function (form) {

            var $btn = $("#submitBtn").button('loading');

            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        $btn.button("success").addClass("btn-success");
                        //pop_reload();
                        $("#modal").modal('hide');
                        SysMsg.success('操作成功。', '成功');
                        $("#jqGrid").trigger("reloadGrid");
                    }
                }
            });
        }
    });
</script>