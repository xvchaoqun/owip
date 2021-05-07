<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="col-sm-4" id="stat_cadre_count" style="width:550px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="fa fa-pie-chart"></i>
                ${param.cadreCategory == 1?"处级":"科级"}干部职称分布
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <c:if test="${param.cadreCategory == 1}">
                    <div id="cadrePost-placeholder_CJ" style="height: 250px"></div>
                </c:if>

                <c:if test="${param.cadreCategory == 2}">
                    <div id="cadrePost-placeholder_KJ" style="height: 250px"></div>
                </c:if>
            </div>
        </div>
    </div>
</div>
<script>

    var $div = $("${param.cadreCategory == 1?'#cadrePost-placeholder_CJ':'#cadrePost-placeholder_KJ'}");

    (function($displayDiv, cadreCategory){
        var cadrePostChart = echarts.init($displayDiv);
        cadrePostChart.showLoading({text: '正在加载数据'});

        $.get("${ctx}/stat_cadrePost_count_data", {cadreCategory:cadreCategory}, function (cadrePostMap) {
            var legendData = [];
            var seriesData1 = [];
            var seriesData2 = [];

            $.each(cadrePostMap, function (key, value) {
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
                        name: '职称',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '50%'],
                        data: seriesData2
                    },
                    {
                        name: '职称',
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

            cadrePostChart.setOption(option, true);
            cadrePostChart.hideLoading();

            <shiro:hasPermission name="cadre:list">
            cadrePostChart.on('click', function (params) {
                var status = ${param.cadreCategory == 1 ? 1 : 8};
                var url = "";
                var data = [];
                var param = params.data._type;
                var isOther = (param == '其他');
                if (param == '中（初）级') {
                    data.push("中级");
                    data.push("初级");
                } else if(isOther) {
                    data.push("其他");
                } else {
                    data.push(param);
                }
                url = isOther ? '#${ctx}/cadre?_type={0}&proPostLevels={1}&status={2}'.format("其他", $.trim(data), status) : '#${ctx}/cadre?proPostLevels={0}&status={1}'.format($.trim(data), status);
                window.open(url, "_blank");
            });
            </shiro:hasPermission>

        })
    })($div[0], ${param.cadreCategory});
</script>