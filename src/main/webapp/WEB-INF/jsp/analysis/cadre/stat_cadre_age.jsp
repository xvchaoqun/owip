<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                 ${param.cadreCategory == 1?"处级":"科级"}干部年龄分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="cadreAge-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="cadreAge-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    var $div = $("${param.cadreCategory == 1?'#cadreAge-placeholder_CJ':'#cadreAge-placeholder_KJ'}");

    (function($displayDiv, cadreCategory){
        var cadreAgeChart = echarts.init($displayDiv);
        cadreAgeChart.showLoading({text: '正在加载数据'});

        $.get("${ctx}/stat_cadre_age_data", {cadreCategory:cadreCategory}, function (map) {
            var cadreAgeMap = map.cadreAgeMap;
            var legendData = [];
            var seriesData1= [];
            var seriesData2 = [];
            $.each(cadreAgeMap, function (key, value) {
                var item=key+'('+value+')';
                legendData.push(item);
                seriesData1.push({
                    name: item,
                    value: value,
                    _type: key
                });
                seriesData2.push({
                    name: key,
                    value: value,
                    _type: key
                });
            });
            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {d}%'
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    left: 10,
                    top: 20,
                    bottom: 20,
                    data: legendData,
                },
                series: [
                    {
                        name: '年龄',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData2,
                    },
                    {
                        name: '年龄',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData1,
                        itemStyle:{            //饼图图形上的文本标签
                            normal: {
                                label: {
                                    show: true,
                                    position: 'inner',
                                    fontSize: 12,
                                    align: "left",
                                    textStyle: {
                                        fontWeight: 300,
                                        fontSize: 12,   //文字的字体大小
                                        color:'white'
                                    },
                                    formatter: '{d}%'
                                }
                            }
                        }
                    }
                ]
            };

            cadreAgeChart.setOption(option, true);
            cadreAgeChart.hideLoading();

            <shiro:hasPermission name="cadre:list">
            cadreAgeChart.on('click', function (params) {
                var status = ${param.cadreCategory == 1 ? 1 : 8};
                var ages = map[params.data._type];
                var startAge = 0;
                var endAge = 0;
                if (ages != undefined) {
                    if (ages[0] != undefined) {
                        startAge = ages[0];
                    }
                    if (ages[1] != undefined) {
                        endAge = ages[1];
                    }
                }
                var url = ""
                if (startAge == 55) {
                    url = "#${ctx}/cadre?startAge=" + startAge + "&status=" + status;
                } else if (startAge == 0 && endAge == 0){
                    url = "#${ctx}/cadre?otherAge=1" + "&_type=其他" + "&status=" + status;
                } else {
                    url = "#${ctx}/cadre?startAge=" + startAge + "&endAge=" + endAge + "&_type=其他" + "&status=" + status;
                }
                window.open(url, "_blank");
            });
            </shiro:hasPermission>

        })
    })($div[0], ${param.cadreCategory});
</script>