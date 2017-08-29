<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="allocateTable">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th width="50" rowspan="2">序号</th>
                    <th rowspan="2">院系级党组织名称</th>
                    <th rowspan="2">正式党员数</th>
                    <th rowspan="2">代表总数</th>
                    <th colspan="3">代表构成</th>
                    <th colspan="3">其中</th>
                </tr>
                <tr>
                    <th width="110">专业技术人员和干部代表</th>
                    <th width="60">学生代表</th>
                    <th width="60">离退休代表</th>
                    <th width="50">妇女代表</th>
                    <th>少数民族代表</th>
                    <th>50岁以下代表</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="positiveCount" value="0"/>
                <c:set var="rowCount" value="0"/>
                <c:set var="proCount" value="0"/>
                <c:set var="stuCount" value="0"/>
                <c:set var="retireCount" value="0"/>
                <c:set var="femaleCount" value="0"/>
                <c:set var="minorityCount" value="0"/>
                <c:set var="underFiftyCount" value="0"/>
                <c:forEach items="${records}" var="record" varStatus="vs">
                    <c:set var="_rowCount" value="${record.proCount + record.stuCount + retireCount}"/>
                    <c:set var="positiveCount" value="${positiveCount + row.positiveCount}"/>
                    <c:set var="rowCount" value="${rowCount + _rowCount}"/>
                    <c:set var="proCount" value="${proCount + row.proCount}"/>
                    <c:set var="stuCount" value="${stuCount + row.stuCount}"/>
                    <c:set var="retireCount" value="${retireCount + row.retireCount}"/>
                    <c:set var="femaleCount" value="${femaleCount + row.femaleCount}"/>
                    <c:set var="minorityCount" value="${minorityCount + row.minorityCount}"/>
                    <c:set var="underFiftyCount" value="${underFiftyCount + row.underFiftyCount}"/>
                    <tr>
                        <td>${vs.count}</td>
                        <td>${record.partyName}</td>
                        <td>${record.positiveCount}</td>
                        <td>
                            <span class="rowCount">${_rowCount}</span>
                        </td>
                        <td>
                            <input type="text" class="num" name="proCount" value="${record.proCount}">
                        </td>
                        <td>
                            <input type="text" class="num" name="stuCount" value="${record.stuCount}">
                        </td>
                        <td>
                            <input type="text" class="num" name="retireCount" value="${record.retireCount}">
                        </td>
                        <td>
                            <input type="text" class="num" name="femaleCount" value="${record.femaleCount}">
                        </td>
                        <td>
                            <input type="text" class="num" name="minorityCount" value="${record.minorityCount}">
                        </td>
                        <td>
                            <input type="text" class="num" name="underFiftyCount" value="${record.underFiftyCount}">
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="2" style="text-align: center">合计</th>
                    <th>${positiveCount}</th>
                    <th>${rowCount}</th>
                    <th>${proCount}</th>
                    <th>${stuCount}</th>
                    <th>${retireCount}</th>
                    <th>${femaleCount}</th>
                    <th>${minorityCount}</th>
                    <th>${underFiftyCount}</th>
                </tr>
                </tfoot>
            </table>
            <div class="modal-footer center" style="margin-top: 20px">

                <button id="submitBtn" data-loading-text="提交中..." data-success-text="已提交成功"
                        autocomplete="off"  ${hasReport?"disabled":""}
                        class="btn btn-success btn-lg"><i class="fa fa-random"></i> 更新
                </button>
            </div>
        </div>
    </div>
</div>
<style>
    .allocateTable {
        margin: 20px;
    }
    .allocateTable input{
        width: 60px;
    }
    .allocateTable .table thead tr {
        background-image: none !important;
        background-color: #f2f2f2 !important;
    }

    .allocateTable .table thead tr th {
        border-bottom-width: inherit;
        text-align: center;
    }

</style>
<script>
    $("#allocateTable").on("change", "input", function () {


    })
</script>