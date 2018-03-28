<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3>${pmdNorm.name}-额度管理</h3>
</div>
<div class="modal-body">
    <c:if test="${pmdNorm.status!=PMD_NORM_STATUS_ABOLISH}">
        <div class="widget-box">
            <div class="widget-header">
                <h4 class="smaller">
                    新增额度
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main">
                    <form class="form-horizontal no-footer" action="${ctx}/pmd/pmdNormValue_au" id="modalForm"
                          method="post">
                        <input type="hidden" name="normId" value="${pmdNorm.id}">

                        <div class="form-group">
                            <label class="col-xs-4 control-label">额度</label>

                            <div class="col-xs-6">
                                <input required class="form-control float" type="text"
                                       name="amount" placeholder="单位：人民币" maxlength="10">
                            </div>
                        </div>
                        <div class="clearfix form-actions center">
                                <button class="btn btn-info btn-sm" type="submit">
                                    <i class="ace-icon fa fa-plus "></i>
                                    添加
                                </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
    <div class="space-10"></div>
    <div class="popTableDiv"
         data-url-page="${ctx}/pmd/pmdNormValue?normId=${pmdNorm.id}"
         data-url-del="${ctx}/pmd/pmdNormValue_del">
        <c:if test="${commonList.recNum>0}">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th class="col-xs-5">额度</th>
                    <th class="col-xs-5">状态</th>
                    <th nowrap></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pmdNormValues}" var="pmdNormValue" varStatus="st">
                    <tr>
                        <td nowrap>${pmdNormValue.amount}</td>
                        <td nowrap>
                            <c:if test="${pmdNorm.status==PMD_NORM_STATUS_ABOLISH}">
                                -
                            </c:if>
                            <c:if test="${pmdNorm.status!=PMD_NORM_STATUS_ABOLISH}">
                                ${pmdNormValue.isEnabled?'<span class="text-success">使用中'
                                :'<span class="text-danger">未启用'}
                            </c:if>
                        </td>
                        <td nowrap>

                            <div class="hidden-sm hidden-xs action-buttons">
                                <c:if test="${pmdNorm.status!=PMD_NORM_STATUS_ABOLISH}">
                                    <c:if test="${!pmdNormValue.isEnabled}">
                                        <button class="confirm btn btn-success btn-xs"
                                                data-title="启用"
                                                data-msg="确定启用？"
                                                data-callback="_popup_reload"
                                                data-url="${ctx}/pmd/pmdNormValue_use?id=${pmdNormValue.id}">
                                            <i class="fa fa-check"></i> 使用
                                        </button>
                                    </c:if>
                                    <shiro:hasPermission name="pmdNormValue:del">
                                        <button class="delBtn btn btn-danger btn-xs" data-id="${pmdNormValue.id}">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                </c:if>
                                <button data-id="${pmdNormValue.id}" class="useLogBtn btn btn-primary btn-xs">
                                    <i class="fa fa-search"></i> 查看使用记录
                                </button>
                            </div>

                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${!empty commonList && commonList.pageNum>1 }">
                <wo:page commonList="${commonList}" uri="${ctx}/pmd/pmdNormValue" target="#modal .modal-content"
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
<style>
    .qtip{
        max-width: inherit;
    }
</style>
<script>

    $(".useLogBtn").click(function () {
        var $this = $(this);
        var pmdNormValueId = $this.data("id");
        $.get("${ctx}/pmd/pmdNormValueLog",
                {pmdNormValueId: pmdNormValueId}, function (data) {
                    $this.qtip({
                        content: data,
                        style:{
                            //"max-width":500,
                            width:600
                        },
                        show: true, hide: {
                            event: 'unfocus'
                        }, position: {
                            container: $("#modal"),
                            my: 'top right',
                            at: 'bottom center'
                        }
                    });
                })
    })

    function _popup_reload() {
        $("#jqGrid").trigger("reloadGrid");
        pop_reload();
    }
    $('#modalForm [data-rel="select2"]').select2();
    $("#modal button[type=submit]").click(function () {
        $("#modalForm").submit();
        return false;
    })
    $("#modalForm").validate({
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                success: function (ret) {
                    if (ret.success) {
                        _popup_reload();
                        //SysMsg.success('操作成功。', '成功');
                    }
                }
            });
        }
    });
    $.register.user_select($('#modal [data-rel="select2-ajax"]'));
</script>