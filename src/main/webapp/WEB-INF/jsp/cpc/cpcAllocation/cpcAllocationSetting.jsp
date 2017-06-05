<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h4 class="widget-title lighter smaller">
    <a href="javascript:" class="hideView btn btn-xs btn-success">
        <i class="ace-icon fa fa-backward"></i>
        返回</a>
</h4>
<form id="genForm" method="post" action="${ctx}/cpcAllocationSetting">
    <div class="component">
        <table class="table table-bordered  table-striped">
            <thead>
            <tr>
                <th style="width: 400px;">单位/行政级别</th>
                <c:forEach items="${adminLevels}" var="adminLevel">
                    <th style="width: 10px;">${adminLevel.name}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${units}" var="unit">
                <tr>
                    <th>${unit.name}</th>
                    <c:forEach items="${adminLevels}" var="adminLevel">
                        <c:set var="totalKey" value="total_${unit.id}_${adminLevel.id}"/>
                        <td>
                            <input class="digits" type="text" name="total_${unit.id}_${adminLevel.id}"
                                   value="${requestScope[totalKey]}">
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="4">
                    <div class="modal-footer center">
                        <a href="javascript:;" class="hideView btn btn-default">取消</a>
                        <input id="add_entity" type="button" class="btn btn-primary" value="设置">
                    </div>
                </th>
            </tr>
            </tfoot>
        </table>

    </div>
</form>

<style>
    <!--


    .component input {
        width: 100px;
    }

    .component .table {
        width: auto;
    }

    .component table.table > tbody > tr > td {
        text-align: center;
        vertical-align: middle;
    }

    /* table.table > thead> tr th{
        white-space: nowrap;
    } */
    .component .table thead th {
        vertical-align: top;
    }

    -->
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/css/component.css"/>
<script src="${ctx}/js/jquery.ba-throttle-debounce.js"></script>
<script src="${ctx}/js/jquery.stickyheader.js"></script>
<script>
    $(function () {
        stickheader();
        $("#add_entity").click(function () {$("#genForm").submit();return false;});
        $("#genForm").validate({
            submitHandler: function (form) {
                $(form).ajaxSubmit({
                    success: function (ret) {
                        if (ret.success) {
                           $.hideView("${ctx}/cpcAllocation");
                        }
                    }
                });
            }
            /* 重写错误显示消息方法,以alert方式弹出错误消息 */
            , showErrors: function (errorMap, errorList) {
                $.each(errorList, function (i, v) {
                    $(v.element).qtip({content: v.message, show: true});
                });
            },
            /* 失去焦点时验证 */
            onfocusout: function (element) {
                $(element).valid();
            }
        });
        $(window).resize();
    });
</script>
