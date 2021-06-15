<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                ${param.cadreCategory == 1?"处级":"科级"}干部政治面貌统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="cadreDp-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="cadreDp-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>
    var $div = $("${param.cadreCategory == 1?'#cadreDp-placeholder_CJ':'#cadreDp-placeholder_KJ'}");

    (function($displayDiv, cadreCategory){

        var cadreDpChart = echarts.init($displayDiv);
        cadreDpChart.showLoading({text: '正在加载数据'});
        $.get("${ctx}/stat_cadreDp_count_data", {cadreCategory:cadreCategory}, function (cadreDpMap) {
            var legendData = [];
            var seriesData1 = [];
            var seriesData2 = [];
            $.each(cadreDpMap, function (key, value) {
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
                        name: '政治面貌',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData2
                    },
                    {
                        name: '政治面貌',
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

            cadreDpChart.setOption(option, true);
            cadreDpChart.hideLoading();

            <shiro:hasPermission name="cadre:list">
            cadreDpChart.on('click', function (params) {
                var status = ${param.cadreCategory == 1 ? 1 : 8};
                var url = "";
                var type = params.data._type;
                var ids = [];
                var isPer = (type == '群众');
                if (type == '民主党派' || isPer) {
                    var map = ${cm:toJSONObject(cm:getMetaTypes("mc_democratic_party"))};
                    for (var i in map) {
                        if (!isPer) {
                            if (map[i].name == '群众') {
                                continue;
                            }
                            ids.push(map[i].id);
                        } else {
                            if (map[i].name == '群众') {
                                ids.push(map[i].id);
                            }
                        }
                    }
                    url = "#${ctx}/cadre?isEngage=${param.isEngage}&dpTypes=" + ids + "&status=" + status;
                } else if (type == '中共党员') {
                    ids.push(0);
                } else {
                    ids.push(-1);
                }
                if (url == "") {
                    url = "#${ctx}/cadre?isEngage=${param.isEngage}&isKeepSalary=${param.isKeepSalary}&dpTypes=" + ids + "&status=" + status;
                }
                window.open(url, "_blank");
            });
            </shiro:hasPermission>


        })
    })($div[0], ${param.cadreCategory});
</script>