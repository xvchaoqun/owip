<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="space-4"></div>
<div class="row" style="width: 1450px">
<div style="margin: 0px 20px; width: 600px;float: left">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                会议材料清单
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">

                <table class="table table-bordered table-unhover2">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th width="200">材料名称</th>
                        <th>原始模板</th>
                        <th>系统生成</th>
                        <th>正式使用</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>招聘会议程（组长）</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>招聘会议程（专家组成员）</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>应聘报名统计表</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>应聘报名表</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>5</td>
                        <td>专家组推荐票</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>6</td>
                        <td>专家组推荐汇总表</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>招聘会会标</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </div>
    </div>

    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                会务材料清单
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                ${cm:getHtmlFragment("hf_crs_material").content}
            </div>
        </div>
    </div>
</div>
    <div style="width: 600px;float: left">
    <div class="widget-box">
        <div class="widget-header">
            <h4 class="smaller">
                应聘人PPT
            </h4>
        </div>
        <div class="widget-body">
            <div class="widget-main">

                <table class="table table-bordered table-unhover2">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th width="200">应聘人姓名</th>
                        <th>答辩PPT</th>
                        <th>短信提醒</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>

            </div>
        </div>
    </div>

</div>
</div>