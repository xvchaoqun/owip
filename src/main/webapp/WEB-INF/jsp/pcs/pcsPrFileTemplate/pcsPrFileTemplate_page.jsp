<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="widget-box myTableDiv" style="width: 1000px">
            <div class="widget-header">
                <h4 class="widget-title">
                    大会材料清单
                    <div class="pull-right" style="margin-right: 10px">
                        <a class="popupBtn btn btn-success btn-xs" data-url="${ctx}/pcs/pcsPrFileTemplate_au"><i
                                class="fa fa-upload"></i> 上传模板</a>
                    </div>
                </h4>
            </div>
            <div class="widget-body">
                <div class="widget-main" id="qualification-content">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th width="30">序号</th>
                            <th width="200">材料名称</th>
                            <th width="40">下载</th>
                            <th width="100">备注</th>
                            <th width="100">排序</th>
                            <th width="100"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${records}" var="record" varStatus="vs">
                            <tr>
                                <td>${vs.count}</td>
                                <td style="text-align: left">${record.name}</td>
                                <td>
                                    <c:if test="${not empty record.filePath}">
                                    <a href="${ctx}/attach_download?path=${cm:sign(record.filePath)}&filename=${record.name}">下载</a>
                                    </c:if>
                                </td>
                                <td>${record.remark}</td>
                                <td nowrap>
                                    <a href="javascript:;" data-url="${ctx}/pcs/pcsPrFileTemplate_changeOrder"
                                       data-callback="_reload"
                                       <c:if test="${commonList.pageNo==1 && st.first}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${record.id}" data-direction="1" title="上升">
                                        <i class="fa fa-arrow-up"></i></a>
                                    <input type="text" value="1"
                                           class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
                                           title="修改操作步长">
                                    <a href="javascript:;" data-url="${ctx}/pcs/pcsPrFileTemplate_changeOrder"
                                       data-callback="_reload"
                                       <c:if test="${commonList.pageNo>=commonList.pageNum && st.last}">style="visibility: hidden"</c:if>
                                       class="changeOrderBtn" data-id="${record.id}" data-direction="-1" title="下降"><i
                                            class="fa fa-arrow-down"></i></a></td>
                                </td>
                                <td>
                                    <a class="popupBtn btn btn-primary btn-xs"
                                       data-url="${ctx}/pcs/pcsPrFileTemplate_au?id=${record.id}"><i class="fa fa-edit"></i>
                                        修改</a>
                                    <button class="confirm btn btn-danger btn-xs"
                                            data-url="${ctx}/pcs/pcsPrFileTemplate_del?id=${record.id}"
                                            data-title="删除材料"
                                            data-msg="确定删除该材料？" data-callback="_reload"
                                            ><i class="fa fa-times"></i> 删除
                                    </button>

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
    .table tr th, .table tr td {
        white-space: nowrap;
        text-align: center;
    }
</style>
<script>
    function _reload() {
        $.hashchange();
    }
</script>