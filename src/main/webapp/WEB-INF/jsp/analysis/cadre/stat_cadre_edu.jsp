<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                 ${param.cadreCategory == 1?"处级":"科级"}干部学历分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="cadreEdu-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="cadreEdu-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    var $div = $("${param.cadreCategory == 1?'#cadreEdu-placeholder_CJ':'#cadreEdu-placeholder_KJ'}");

    (function($displayDiv, cadreCategory){
        var cadreEduChart = echarts.init($displayDiv);
        cadreEduChart.showLoading({text: '正在加载数据'});

        $.get("${ctx}/stat_cadreEdu_count_data", {cadreCategory:cadreCategory}, function (cadreEduMap) {

            var legendData = [];
            var seriesData1 = [];
            var seriesData2 = [];

            $.each(cadreEduMap, function (key, value) {
                var item=key+'('+value+')';
                legendData.push(item);
                seriesData1.push({
                    name: item,
                    value: value
                });
                seriesData2.push({
                    name: item,
                    value: value
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
                        name: '学历',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData2,
                    },
                    {
                        name: '学历',
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
                                },
                                labelLine:{
                                    show : false //显示饼状图上的文本时，指示线不显示，在第一个data时显示指示线
                                }
                            }
                        }
                    }
                ]
            };

             cadreEduChart.setOption(option, true);
             cadreEduChart.hideLoading();

        })
    })($div[0], ${param.cadreCategory});
</script>