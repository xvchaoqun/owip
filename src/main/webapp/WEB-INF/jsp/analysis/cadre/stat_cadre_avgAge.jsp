<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                领导干部平均年龄分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="container_CJ" style="height:280px; margin: 0 auto;"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="container_KJ" style="height:280px; margin: 0 auto;"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script>
    var $div = $('#container_CJ');
    var cadreAvgAgeChart = echarts.init($div[0]);
    cadreAvgAgeChart.showLoading({text: '正在加载数据'});

    $.get("${ctx}/stat_cadre_avgAge_data", {cadreCategory:${param.cadreCategory}}, function (cadreAvgAge) {

        var levelData = [];
        var avgAgeData = [];

        $.each(cadreAvgAge, function (key, value) {
            levelData.push(key);
            avgAgeData.push(value);
        });

        option = {
            xAxis: {
                type: 'category',
                data: levelData
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: avgAgeData,
                type: 'bar',
                barMaxWidth:40,//最大宽度
                label: {
                    show: true,
                    formatter: '{c}'
                },
            }],
            grid: {
                top:'7%',
                bottom: '7%',
            }
        };

        cadreAvgAgeChart.setOption(option, true);
        cadreAvgAgeChart.hideLoading();

    })

</script>