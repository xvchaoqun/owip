<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box" style="width: 500px">
            <div class="widget-header">
                <h4 class="smaller">
                    大会材料清单
                    <div class="pull-right"  style="margin-right: 10px">
                    <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/pcsPrFileTemplate_au"><i class="fa fa-plus"></i> 添加</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="qualification-content">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th width="100">序号</th>
                            <th width="100">材料名称</th>
                            <th width="100">备注</th>
                            <th width="100">排序</th>
                            <th width="100"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${records}" var="record" varStatus="vs">
                            <tr>
                                <td>${vs.count}</td>
                                <td>${record.name}</td>
                                <td>${record.remark}</td>
                                <td nowrap>
                                    <a href="javascript:;" <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaType.id}" data-direction="1" title="上升"><i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top" title="修改操作步长">
                                    <a href="javascript:;" <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if> class="changeOrderBtn" data-id="${metaType.id}" data-direction="-1" title="下降"><i class="fa fa-arrow-down"></i></a>                                </td>

                                </td>
                                <td>
                                    <c:if test="${admin.type==PCS_ADMIN_TYPE_NORMAL}">
                                        <a class="popupBtn btn btn-primary btn-xs"
                                           data-url="${ctx}/pcsPrFileTemplate_au?id=${record.id}"><i class="fa fa-edit"></i> 修改</a>
                                    <button class="confirm btn btn-danger btn-xs"
                                            data-url="${ctx}/pcsPrFileTemplate_del?id=${recprd.id}"
                                            data-title="删除管理员"
                                            data-msg="确定删除该管理员？" data-callback="_reload"
                                            ><i class="fa fa-times"></i> 删除</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .table tr td{
        white-space: nowrap;
    }
</style>
<script>
function _reload(){
    $.hashchange();
}
</script>