<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
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
                <th>专业技术人员和干部代表</th>
                <th>学生代表</th>
                <th>离退休代表</th>
                <th>妇女代表</th>
                <th>少数民族代表</th>
                <th>50岁以下代表</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${records}" var="record" varStatus="vs">
                <tr>
                    <td>${vs.count}</td>
                    <td>${record.name}</td>
                    <td>${record.memberCount}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="2" style="text-align: center">合计</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>