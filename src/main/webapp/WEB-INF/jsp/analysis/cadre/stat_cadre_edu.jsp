<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_APPLY_STAGE_MAP" value="<%=OwConstants.OW_APPLY_STAGE_MAP%>"/>
<c:set var="PIE_COLOR_MAP" value="<%=SystemConstants.PIE_COLOR_MAP%>"/>
<c:set var="OW_APPLY_STAGE_INIT" value="<%=OwConstants.OW_APPLY_STAGE_INIT%>"/>
<c:set var="OW_APPLY_STAGE_DRAW" value="<%=OwConstants.OW_APPLY_STAGE_DRAW%>"/>

<div class="col-sm-4" id="stat_cadre_count" style="width:500px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                 ${param.cadreType == 1?"处级":"科级"}干部学历分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main" style="height: 250px">
                <c:if test="${param.cadreType == 1}">
                    <div id="cadreEdu-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreType == 2}">
                    <div id="cadreEdu-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
        var dom;
        if(${param.cadreType == 1}){
            dom = $('#cadreEdu-placeholder_CJ').get(0);
        }else if(${param.cadreType == 2}){
            dom = $('#cadreEdu-placeholder_KJ').get(0);
        }
        var myChart = echarts.init(dom);
        option = null;
        var data =  getData();

        //console.dir(data)
        option = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {d}%'
            },
            legend: {
                type: 'scroll',
                orient: 'vertical',
                right: 10,
                top: 20,
                bottom: 20,
                data: data.legendData,
            },
            series: [
                {
                    name: '学历',
                    type: 'pie',
                    radius: '60%',
                    center: ['40%', '50%'],
                    data: data.seriesData2,
                },
                {
                    name: '学历',
                    type: 'pie',
                    radius: '60%',
                    center: ['40%', '50%'],
                    data: data.seriesData1,
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

        function getData() {
            var legendData = [];
            var seriesData1 = [];
            var seriesData2 = [];
            var cadreEduMap = ${cm:toJSONObject(cadreEduMap)};
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
            return {
                legendData: legendData,
                seriesData1: seriesData1,
                seriesData2: seriesData2,

            };
        };
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
</script>