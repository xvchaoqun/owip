<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>党费重新计算</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal no-footer" action="${ctx}/pmd/pmdConfigReset_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择工资月份（缴费计算基数）</label>
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
        <div class="form-group">
            <label class="col-xs-4 control-label">是否重置支部自行设定的额度</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big" name="reset"/>
            </div>
        </div>
        <div class="clearfix form-actions">
            <div class="center">
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
            <div class="text-danger bolder">
                说明：<br/>
                一、两种情况不处理：
                1、缴费方式已设置为现金缴费。
                2、当前缴费月份已缴费或已设置为延迟缴费。 <br/>
                二、如果重置了支部自行设定的额度，那么支部自行设定的额度，需要支部进行确认操作后才可正常缴费（一般用于每年变更缴费工资月份时）
            </div>
        </div>
    </form>

</div>
<div class="space-10"></div>
<div class="popTableDiv"
     data-url-page="${ctx}/pmd/pmdConfigReset">
    <c:if test="${commonList.recNum>0}">
        <table class="table table-actived table-striped table-bordered table-hover table-center">
            <thead>
            <tr>
                <th width="100">工资月份</th>
                <th width="120">是否重置额度</th>
                <th >操作人</th>
                <th >操作时间</th>
                <th >IP</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pmdConfigResets}" var="record" varStatus="st">
                <c:set value="${cm:getUserById(record.userId)}" var="sysUser"/>
                <tr>
                    <td nowrap>${cm:formatDate(record.salaryMonth, "yyyy年MM月")}</td>
                    <td nowrap>
                            ${record.reset?"是":"否"}
                    </td>
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
    $("#modalForm :checkbox").bootstrapSwitch();
    $('#modalForm [data-rel="select2"]').select2();
    $("#modal #submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    })
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