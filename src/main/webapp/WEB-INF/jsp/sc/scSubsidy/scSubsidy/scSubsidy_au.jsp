<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3><c:if test="${scSubsidy!=null}">编辑</c:if><c:if test="${scSubsidy==null}">添加</c:if>干部津贴变动</h3>
</div>
<div class="modal-body">
    <form class="form-horizontal" action="${ctx}/sc/scSubsidy_au" autocomplete="off" disableautocomplete id="modalForm" method="post">
        <input type="hidden" name="id" value="${scSubsidy.id}">
        <div class="col-xs-12">
            <div class="col-xs-6">
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>年份</label>
                    <div class="col-xs-8">
                        <div class="input-group">
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                            <input required style="width: 80px;" autocomplete="off" class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text"
                                   data-date-format="yyyy" data-date-min-view-mode="2" value="${empty scSubsidy?_thisYear:scSubsidy.year}"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>发人事处通知文号</label>

                    <div class="col-xs-9" style="padding-top: 10px;">
                        <select data-rel="select2-ajax"
                                data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                name="hrType" data-placeholder="请选择文号" data-width="225">
                            <option value="${hrAnnualType.id}">${hrAnnualType.name}</option>
                        </select>
                        <span class="help-block">注：请先在【干部津贴调整-文号管理】中维护文号</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>发人事处通知编号</label>

                    <div class="col-xs-8" style="padding-top: 10px;">
                        <input class="form-control num" type="text" name="hrNum" value="${scSubsidy.hrNum}">
                        <span class="help-block">注：请填数字</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>发财经处通知文号</label>

                    <div class="col-xs-9" style="padding-top: 10px;">
                        <select data-rel="select2-ajax"
                                data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                name="feType" data-placeholder="请选择文号" data-width="225">
                            <option value="${feAnnualType.id}">${feAnnualType.name}</option>
                        </select>
                        <span class="help-block">注：请先在【干部津贴调整-文号管理】中维护文号</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>发财经处通知编号</label>

                    <div class="col-xs-8" style="padding-top: 10px;">
                        <input class="form-control num" type="text" name="feNum" value="${scSubsidy.feNum}">
                        <span class="help-block">注：请填数字</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label"><span class="star">*</span>通知日期</label>

                    <div class="col-xs-8">
                        <div class="input-group">
                            <input required class="form-control date-picker" autocomplete="off" name="infoDate" type="text"
                                   data-date-format="yyyy-mm-dd" value="${empty scSubsidy.infoDate?_today:(cm:formatDate(scSubsidy.infoDate,'yyyy-MM-dd'))}"/>
                            <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-xs-3 control-label">备注</label>

                    <div class="col-xs-8">
                        <textarea class="form-control limited" rows="5" name="remark">${scSubsidy.remark}</textarea>
                    </div>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="form-group">
                    <div class="col-xs-12">
                        <div id="tree3" style="height: 440px;overflow-y: scroll"></div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <c:if test="${not empty scSubsidy}"><div class="text-danger" style="text-align: left">注：修改操作将同步最新的任免文件信息和干部信息</div></c:if>
    <a href="#" data-dismiss="modal" class="btn btn-default">取消</a>
    <button id="submitBtn" class="btn btn-primary" data-loading-text="<i class='fa fa-spinner fa-spin '></i> 提交中，请不要关闭此窗口">
        <i class="fa fa-check"></i> <c:if test="${scSubsidy!=null}">修改</c:if><c:if
            test="${scSubsidy==null}">添加</c:if></button>
</div>
<script>
    $("#submitBtn").click(function () {
        $("#modalForm").submit();
        return false;
    });
    $("#modalForm").validate({
        submitHandler: function (form) {
            var $btn = $("#submitBtn").button('loading');
            var dispatchIds = $.map($("#tree3").dynatree("getSelectedNodes"), function(node){
                if(!node.data.isFolder)
                    return node.data.key;
            });

            $(form).ajaxSubmit({
                    data:{dispatchIds:dispatchIds},
                    success: function (ret) {
                    if (ret.success) {
                        $("#modal").modal('hide');
                        $("#jqGrid").trigger("reloadGrid");
                    }

                    $btn.button('reset');
                }
            });
        }
    });

    $.register.date($('.date-picker'));
    $("#modalForm input[name=year]").on('changeDate',function(ev){
        var year = $(this).val();
        //console.log(year)
        if(year>0){
            $.getJSON("${ctx}/sc/scSubsidy_selectDispatch_tree",{year:year, subsidyId:'${scSubsidy.id}'},function(data){
                var treeData = data.tree;
                //console.log(treeData)
                $("#tree3").dynatree({
                    checkbox: true,
                    selectMode: 3,
                    children: treeData,
                    onSelect: function(select, node) {

                        //node.expand(node.data.isFolder && node.isSelected());
                    },
                    cookieId: "dynatree-Cb3",
                    idPrefix: "dynatree-Cb3-"
                });
                $("#tree3").dynatree("getTree").reload();
            });
        }
    }).trigger("changeDate");

    $.register.dispatchType_select($('#modalForm select[name=hrType]'), $("#modalForm input[name=year]"));
    $.register.dispatchType_select($('#modalForm select[name=feType]'), $("#modalForm input[name=year]"));
    //$("#modalForm :checkbox").bootstrapSwitch();
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#modalForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $('textarea.limited').inputlimiter();

</script>