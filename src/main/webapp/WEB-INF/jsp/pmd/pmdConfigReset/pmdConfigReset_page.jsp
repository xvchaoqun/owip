<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>缴费基数调整</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal no-footer" action="${ctx}/pmd/pmdConfigReset_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <div class="form-group">
            <label class="col-xs-4 control-label"><span class="star">*</span>选择工资月份（缴费计算基数）</label>
            <div class="col-xs-6">
                <select required data-rel="select2"
                        name="salaryMonth" data-placeholder="请选择">
                    <option></option>
                    <c:forEach items="${jzgSalaryMonthList}" var="salaryMonth">
                        <option value="${salaryMonth}" title="${!salaryMonthSet.contains(salaryMonth)}">${salaryMonth}</option>
                    </c:forEach>
                </select>
                <span class="help-block">*标红的月份缺少离退休人员的相关数据</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">是否重置支部自行设定的额度</label>
            <div class="col-xs-6">
                <input type="checkbox" class="big" name="reset"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">涉及缴费党员范围</label>
            <div class="col-xs-6">
                <select class="form-control" data-width="350" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/party_selects?auth=1"
                            name="partyId" data-placeholder="请选择">
                        <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                    </select>
                <div style="padding-top: 5px;${(empty branch)?'display: none':''}" id="branchDiv">
                    <select class="form-control" data-rel="select2-ajax"
                            data-ajax-url="${ctx}/branch_selects?auth=1" data-width="350"
                            name="branchId" data-placeholder="请选择党支部">
                        <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                    </select>
                </div>
                <script>
                    $.register.party_branch_select($("#modalForm"), "branchDiv",
                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                </script>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-4 control-label">涉及缴费党员</label>
            <div class="col-xs-6">
                <select data-rel="select2-ajax"  data-width="350"
                          data-ajax-url="${ctx}/member_selects?noAuth=1&partyId=${param.partyId}&branchId=${param.branchId}&status=${MEMBER_STATUS_NORMAL}"
                          name="userId" data-placeholder="请输入账号或姓名或学工号">
                    <option></option>
                  </select>
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
            <div class="text-primary bolder">
                说明：<br/>
                <ul>
                    <li style="color: red;font-size: large">
                         如不选择涉及范围，也不选择缴费党员，则此操作会影响所有系统中还未缴费的人员，请谨慎操作！！！
                    </li>
                    <li>
                        如需<span style="color: green">重新计算某位党员的缴纳额度</span>，
                        可在<span style="color: green">【党费收缴管理-党员缴费情况】</span>中删除对应的人员后，再次添加即可。
                    </li>
                    <li>
                        两种情况不处理：
                        1、缴费方式已设置为现金缴费。
                            2、当前缴费月份已缴费或已设置为延迟缴费。 <br/>
                    </li>
                    <li style="color: red">
                        如果选择了重置支部自行设定的额度，那么支部自行设定的额度，需要支部进行确认操作后才可正常缴费（一般用于每年变更缴费工资月份时）
                    </li>
                </ul>
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
                <th >涉及缴费党员</th>
                <th >操作时间</th>
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
                    <td>
                        <c:if test="${empty record.limitedUserId}">
                            ${partyMap.get(record.partyId).name}
                            ${branchMap.get(record.branchId).name}
                        </c:if>
                            ${cm:getUserById(record.limitedUserId).realname}
                    </td>
                    <td nowrap>
                            ${cm:formatDate(record.createTime, "yyyy-MM-dd")}
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
    $.register.user_select($('#modalForm select[name=userId]'));
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
                    $btn.button('reset');
                }
            });
        }
    });
</script>