<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div id="cadreTrainCollectDiv">
    <div class="modal-header">
        <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
        <h3>提取所有培训记录</h3>
    </div>
    <div class="modal-body">
        <form class="form-inline search-form" id="searchForm_popup" style="float: left">
            <input type="hidden" name="cadreId" value="${param.cadreId}">
            <div class="form-group">
                <label>培训内容</label>
                <input class="form-control search-query" name="name" type="text" value="${param.name}"
                       placeholder="请输入">
            </div>
            <div class="form-group">
                <label>主办单位</label>
                <input class="form-control search-query" name="organizer" type="text" value="${param.organizer}"
                       placeholder="请输入">
            </div>
            <c:set var="_query" value="${not empty param.name || not empty param.organizer}"/>
            <div class="form-group">
                <button type="button" data-url="${ctx}/cadreTrain_collect?cadreId=${param.cadreId}"
                        data-target="#cadreTrainCollectDiv" data-form="#searchForm_popup"
                        class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
                </button>
                <c:if test="${_query}">
                    <button type="button"
                            data-url="${ctx}/cadreTrain_collect?cadreId=${param.cadreId}"
                            data-target="#cadreTrainCollectDiv"
                            class="reloadBtn btn btn-warning btn-sm">
                        <i class="fa fa-reply"></i> 重置
                    </button>
                </c:if>
            </div>
        </form>
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th class="center">
                    <label class="pos-rel">
                        <input type="checkbox" class="ace checkAll" name="checkAll">
                        <span class="lbl"></span>
                    </label>
                </th>
                <th nowrap>培训开始时间</th>
                <th nowrap>培训结束时间</th>
                <th nowrap>培训内容</th>
                <th nowrap>主办单位</th>
                <th nowrap>备注</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${cetRecords}" var="record" varStatus="st">
                <tr>
                    <td class="center" width="50">
                        <label class="pos-rel">
                            <input type="checkbox" name="id"
                                   value="${record.id}"
                                   class="ace"/>
                            <span class="lbl"></span>
                        </label>
                    </td>
                    <td nowrap width="60px">${cm:formatDate(record.startDate, 'yyyy.MM.dd')}</td>
                    <td nowrap width="60px">${cm:formatDate(record.endDate, 'yyyy.MM.dd')}</td>
                    <td nowrap width="260px">${record.name}</td>
                    <td nowrap width="170px">${record.organizer}</td>
                    <td nowrap width="170px">${record.remark}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <c:if test="${fn:length(cetRecords)==0}">
            <div class="well well-lg center">
                <h4 class="green lighter">暂无记录</h4>
            </div>
        </c:if>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default cancel"><i class="fa fa-times"></i> 取消</button>
        <button id="submitBtn" class="btn btn-success"><i class="fa fa-save"></i> 保存</button>
    </div>
</div>
<script>
    $(".cancel").on("click", function () {
        $("#modal").modal('hide');
    })
    var ids = new Array();
    $(document).on("change", "#cadreTrainCollectDiv input[name='id'], #cadreTrainCollectDiv input[name='checkAll']", function(){

        $("input[name='id']", "#cadreTrainCollectDiv").each(function () {
            var id = $(this).val();
            if (this.checked) { //被选中的复选框
                if (ids.toString() == "") {
                    ids.push(id);
                } else {  //判断id数组中是否含有以前存入的元素，没有则添加
                    if ($.inArray(id, ids)<0) {
                        ids.push(id);
                    }
                }
            } else {  //未被选中的复选框
                if ($.inArray(id, ids)>=0) {
                    ids.splice($.inArray(id, ids), id.length);
                }
                $(this).closest("tr").removeClass("active");
            }
        });
    });
    $("#submitBtn").on("click", function () {
        if (ids.length==0){
            $("#modal").modal('hide');
            return;
        }

        $.post("${ctx}/cadreTrain_collect",{cadreId: ${param.cadreId}, ids:ids},function(ret){
            if(ret.success) {
                $("#modal").modal('hide');
                $("#jqGrid_cadreTrain").trigger("reloadGrid");
            }
        });
    })
</script>