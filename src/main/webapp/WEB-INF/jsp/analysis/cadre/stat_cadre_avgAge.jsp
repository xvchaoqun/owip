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
                    <div id="container_CJ" style="height:250px; margin: 0 auto;"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="container_KJ" style="height:250px; margin: 0 auto;"></div>
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

        var indicatorData = [];
        var avgAgeData = [];
        var seriesData = [];

        $.each(cadreAvgAge, function (key, value) {
            indicatorData.push({
                text: key+"("+value+")",
                max: 100
            });
            avgAgeData.push(value);
        });
        seriesData.push({
            name: "平均年龄",
            value: avgAgeData
        });

        var  option = {
            tooltip: {},
            legend: {
                left: 10,
                top: 20,
                bottom: 20,
                data: ['平均年龄']
            },
            radar: {
                // shape: 'circle',
                name: {
                    textStyle: {
                        color: '#fff',
                        backgroundColor: '#999',
                        borderRadius: 3,
                        padding: [3, 5]
                    }
                },
                center: ['60%', '60%'],
                indicator: indicatorData
            },
            series: [{
                name: '平均年龄',
                type: 'radar',
                data: seriesData
            }]
        };

        cadreAvgAgeChart.setOption(option, true);
        cadreAvgAgeChart.hideLoading();

    })

</script>