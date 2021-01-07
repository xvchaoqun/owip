<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                ${param.cadreType == 1?"处级":"科级"}干部平均年龄分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreType == 1}">
                    <div id="container_CJ" style="height:250px; margin: 0 auto;"></div>
                </c:if>

                <c:if test="${param.cadreType == 2}">
                    <div id="container_KJ" style="height:250px; margin: 0 auto;"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script>
    var dom = $('#container_CJ').get(0);
    var myChart = echarts.init(dom);
    var data =  getData();
    option = {
        tooltip: {},
        legend: {
            left: 10,
            top: 20,
            bottom: 20,
            data: ['处级干部平均年龄']
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
            indicator:data.indicatorData
        },
        series: [{
            name: '处级干部平均年龄',
            type: 'radar',
            data: data.seriesData
        }]
    };
    function getData() {
        var indicatorData = [];
        var avgageData = [];
        var seriesData = [];
        var cadreAvgAge = ${cm:toJSONObject(cadreAvgAge)};
        $.each(cadreAvgAge, function (key, value) {
            indicatorData.push({
                text: key+"("+value+")",
                max: 100
            });
            avgageData.push(value);
        });
        seriesData.push({
            name: "处级干部平均年龄",
            value: avgageData
        });
        return {
            indicatorData:indicatorData,
            seriesData: seriesData,
        };
    };
    if (option && typeof option === "object" && data.indicatorData.length>0) {
        myChart.setOption(option, true);
    }
</script>