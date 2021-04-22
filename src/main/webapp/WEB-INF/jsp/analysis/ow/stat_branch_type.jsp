<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div class="col-sm-4" id="stat_ow_branch_type" style="width:750px;float: left;">
    <div class="widget-box">
        <div class="widget-header widget-header-flat widget-header-small">
            <h5 class="widget-title">
                <i class="ace-icon fa fa-pie-chart"></i>
                党支部类型统计
            </h5>
        </div>
        <div class="widget-body">
            <div class="widget-main">
                <div id="branchType-placeholder" style="height: 252px;"></div>
            </div>
        </div>
    </div>
</div>
<script>
        $("#stat_ow_branch_type ul li").click(function () {

            $.get("${ctx}/stat_ow_branch_type", {type: $(this).data('type')}, function (html) {
                $("#stat_ow_branch_type").replaceWith(html);
            });
        });

        var branchTypeChart = echarts.init($('#branchType-placeholder').get(0));
        branchTypeChart.showLoading({text: '正在加载数据'});
        var url = "${ctx}/${empty partyId?'stat_branch_type_data':'stat_party_branch_type_data'}";

        $.get(url, {partyId:"${partyId}"}, function (branchTypeMap) {

            var legendData = [];
            var seriesData = [];
            var totalCount = 0;
            $.each(branchTypeMap, function (key, value) {
                /*console.log(key);*/
                totalCount += value;
                var item = key + '(' + value + ')';
                legendData.push(item);
                seriesData.push({
                    name: item,
                    value: value,
                    _type:key
                });
            });

            var option = {
                title: {
                    text: '（党支部总数：'+ totalCount +'）',
                    left: 'right'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b}:{d}%'
                },
                legend: {
                    orient: 'vertical',
                    left:-5,
                    top: -5,
                    bottom: 5,
                    data: legendData
                },
                series: [
                    {
                        name: '党支部类型',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '60%'],
                        data:seriesData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur:10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    },
                    {
                        name: '党支部类型',
                        type: 'pie',
                        radius: '60%',
                        center: ['60%', '60%'],
                        data: seriesData,
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
            branchTypeChart.setOption(option,true);
            branchTypeChart.hideLoading();

            <shiro:hasPermission name="branch:list">
            branchTypeChart.on('click', function (params) {
                //console.log(params.data);
                var url = "#${ctx}/branch?partyId=${partyId}&_type={0}"
                    .format($.trim(params.data._type));
                window.open(url, "_blank");
            });
            </shiro:hasPermission>
        })
</script>