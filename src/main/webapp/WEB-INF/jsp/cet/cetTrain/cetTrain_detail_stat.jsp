<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable" style="margin: 10px 20px; width: 700px">
            <div class="space-4"></div>
            <a href="${ctx}/cet/cetTrain_stat_export?trainId=${param.trainId}">
                <i class="fa fa-download"></i> 下载</a>
            <div class="space-4"></div>
            <table class="table table-bordered table-striped table-unhover2" data-offset-top="101">
                <thead class="multi">
                <tr>
                    <th width="40" rowspan="2">序号</th>
                    <th rowspan="2">参训人员类型</th>
                    <th rowspan="2" width="80">通知人数</th>
                    <th colspan="2">选课情况</th>
                    <th colspan="2">上课情况</th>
                </tr>
                <tr>
                    <th width="90">选课人数</th>
                    <th width="90">选课率</th>
                    <th width="90">上课人数</th>
                    <th width="90">上课率</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="ltitle">1</td>
                    <td class="ltitle">应选代表情况</td>
                    <td>${expectCount}</td>
                    <td>${pcsPrAllocate.proCount}</td>
                    <td>${pcsPrAllocate.stuCount}</td>
                    <td>${pcsPrAllocate.minorityCount}</td>
                    <td>${pcsPrAllocate.underFiftyCount}</td>
                </tr>
                <tr>
                    <td colspan="2" class="ltitle">合计</td>
                    <td>${actualCount}</td>
                    <td>${realPcsPrAllocate.proCount}</td>
                    <td>${realPcsPrAllocate.stuCount}</td>
                    <td>${realPcsPrAllocate.minorityCount}</td>
                    <td>${realPcsPrAllocate.underFiftyCount}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<style>
    .table th, .table td {
        text-align: center;
    }

    .ltitle {
        text-align: center !important;
        vertical-align: middle !important;
        background-color: #f9f9f9 !important;
    }
</style>