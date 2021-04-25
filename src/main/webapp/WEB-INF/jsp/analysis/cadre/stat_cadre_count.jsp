<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                ${param.cadreCategory == 1?"处级":"科级"}干部人数统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="cadreCount-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="cadreCount-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    var $div = $("${param.cadreCategory == 1?'#cadreCount-placeholder_CJ':'#cadreCount-placeholder_KJ'}");
    (function($displayDiv, cadreCategory){

        var cadreCountChart= echarts.init($displayDiv);
        cadreCountChart.showLoading({text: '正在加载数据'});
        $.get("${ctx}/stat_cadre_count_data", {cadreCategory:cadreCategory}, function (statCadreCountMap) {

            var legendData = [];
            var seriesData1 = [];
            var seriesData2 = [];
            var totalCount = 0;

            $.each(statCadreCountMap, function (key, value) {
                totalCount += value;
                var item = key + '(' + value + ')';
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
                title: {
                    text: totalCount > 0 ? '（干部总数：' + totalCount + '）' : '',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {d}%'
                },
                legend: {
                    show: true,
                    type: 'scroll',
                    orient: 'vertical',
                    left: 10,
                    top: 20,
                    bottom: 20,
                    data: legendData,
                },
                series: [
                    {
                        name: '行政级别',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData2
                    },
                    {
                        name: '行政级别',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData1,
                        itemStyle: {            //饼图图形上的文本标签
                            normal: {
                                label: {
                                    show: true,
                                    position: 'inner',
                                    fontSize: 12,
                                    align: "left",
                                    textStyle: {
                                        fontWeight: 300,
                                        fontSize: 12,   //文字的字体大小
                                        color: 'white'
                                    },
                                    formatter: '{d}%'
                                }
                            }
                        }
                    }
                ]
            };
            cadreCountChart.setOption(option, true);
            cadreCountChart.hideLoading();

            <shiro:hasPermission name="cadre:list">
            cadreCountChart.on('click', function (params) {
                var status = ${param.cadreCategory == 1 ? 1 : 8};
                var str = "";
                for (var i in seriesData1) {
                    if (seriesData1[i]._type != '其他') {
                        str += seriesData1[i]._type;
                    }
                }
                var name = params.data._type;
                var map = ${cm:toJSONObject(cm:getMetaTypes("mc_admin_level"))};
                var adminLevels = [];
                var isOther = (name == '其他');
                for (var i in map) {
                    if (isOther) {
                        if (str.indexOf(map[i].name) < 0) {
                            adminLevels.push(i);
                        }
                    }
                    if (map[i].name == name) {
                        adminLevels.push(i);
                    }
                }
                var url = "";
                if (isOther) {
                    url = "#${ctx}/cadre?adminLevels={0}&_type={1}&status={2}".format($.trim(adminLevels), "其他", status);
                } else {
                    url = "#${ctx}/cadre?adminLevels={0}&status={1}".format($.trim(adminLevels), status);
                }
                window.open(url, "_blank");
            });
            </shiro:hasPermission>
        })


    })($div[0], ${param.cadreCategory});
</script>