<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/analysis/constants.jsp" %>
<div id="cadreMemberDiv" style="height: 160px"></div>
<script>
    var countChart = echarts.init($('#cadreMemberDiv').get(0));
    countChart.showLoading({text: '正在加载数据'});

    var statPoliticalStatusMap = ${cm:toJSONObject(statPoliticalStatusMap)};
    var statGrowMap = ${cm:toJSONObject(statGrowMap)};
    var statPositiveMap = ${cm:toJSONObject(statPositiveMap)};

    var memberData = [];
    var legendTitle = [];
    var countData = [];
    var positive_JZG_count = statPositiveMap[${USER_TYPE_JZG}];
    var grow_JZG_count = statGrowMap[${USER_TYPE_JZG}];
    var count = statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}];

    function addData1() {
        //正式党员  预备党员
        if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] != 0) {
            memberData.push({
                name: '正式党员(' + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + ')',
                value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}],
            });
            legendTitle.push('正式党员(' + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_POSITIVE}] + ')');
        }
        if (statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}] != 0) {
            memberData.push({
                name: '预备党员(' + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}] + ')',
                value: statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}],
            });
            legendTitle.push('预备党员(' + statPoliticalStatusMap[${MEMBER_POLITICAL_STATUS_GROW}] + ')');
        }

        //其他
        if (statPositiveMap[${USER_TYPE_RETIRE}] != 0) {
            countData.push({
                name: '离退休正式党员(' + statPositiveMap[${USER_TYPE_RETIRE}] + ')',
                value: statPositiveMap[${USER_TYPE_RETIRE}]
            });
            legendTitle.push('离退休正式党员(' + statPositiveMap[${USER_TYPE_RETIRE}] + ')');
        }
        if (positive_JZG_count != 0) {
            countData.push({name: '教职工正式党员(' + positive_JZG_count + ')', value: positive_JZG_count});
            legendTitle.push('教职工正式党员(' + positive_JZG_count + ')');
        }
        if (statPositiveMap[${USER_TYPE_BKS}] != 0) {
            countData.push({
                name: '本科生正式党员(' + statPositiveMap[${USER_TYPE_BKS}] + ')',
                value: statPositiveMap[${USER_TYPE_BKS}]
            });
            legendTitle.push('本科生正式党员(' + statPositiveMap[${USER_TYPE_BKS}] + ')');
        }
        if (statPositiveMap[${USER_TYPE_SS}] != 0) {
            countData.push({
                name: '硕士生正式党员(' + statPositiveMap[${USER_TYPE_SS}] + ')',
                value: statPositiveMap[${USER_TYPE_SS}]
            });
            legendTitle.push('硕士生正式党员(' + statPositiveMap[${USER_TYPE_SS}] + ')');
        }
        if (statPositiveMap[${USER_TYPE_BS}] != 0) {
            countData.push({
                name: '博士生正式党员(' + statPositiveMap[${USER_TYPE_BS}] + ')',
                value: statPositiveMap[${USER_TYPE_BS}]
            });
            legendTitle.push('博士生正式党员(' + statPositiveMap[${USER_TYPE_BS}] + ')');
        }
        if (statGrowMap[${USER_TYPE_RETIRE}] != 0) {
            countData.push({
                name: '离退休预备党员(' + statGrowMap[${USER_TYPE_RETIRE}] + ')',
                value: statGrowMap[${USER_TYPE_RETIRE}]
            });
            legendTitle.push('离退休预备党员(' + statGrowMap[${USER_TYPE_RETIRE}] + ')');
        }
        if (grow_JZG_count != 0) {
            countData.push({name: '教职工预备党员(' + grow_JZG_count + ')', value: grow_JZG_count});
            legendTitle.push('教职工预备党员(' + grow_JZG_count + ')');
        }
        if (statGrowMap[${USER_TYPE_BKS}] != 0) {
            countData.push({
                name: '本科生预备党员(' + statGrowMap[${USER_TYPE_BKS}] + ')',
                value: statGrowMap[${USER_TYPE_BKS}]
            });
            legendTitle.push('本科生预备党员(' + statGrowMap[${USER_TYPE_BKS}] + ')');
        }
        if (statGrowMap[${USER_TYPE_SS}] != 0) {
            countData.push({
                name: '硕士生预备党员(' + statGrowMap[${USER_TYPE_SS}] + ')',
                value: statGrowMap[${USER_TYPE_SS}]
            });
            legendTitle.push('硕士生预备党员(' + statGrowMap[${USER_TYPE_SS}] + ')');
        }
        if (statGrowMap[${USER_TYPE_BS}] != 0) {
            countData.push({
                name: '博士生预备党员(' + statGrowMap[${USER_TYPE_BS}] + ')',
                value: statGrowMap[${USER_TYPE_BS}]
            });
            legendTitle.push('博士生预备党员(' + statGrowMap[${USER_TYPE_BS}] + ')');
        }
    }

    addData1();
    var option = {
        title: {
            text: '党员分布(' + count + ')',
            left: 'left',
            textStyle: {
                fontSize: 15
            }
        },
        legend: {
            show: true,
            type: 'scroll',
            orient: 'vertical',
            left: 5,
            top: 30,
            bottom: 20,
            itemWidth: 11,
            itemHeight: 7,
            textStyle: {
                fontSize: 12
            },
            data: legendTitle,
        },
        tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        series: [
            {
                name: '党员数量',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '55%'],
                center: ['75%', '50%'],
                startAngle: 240,
                label: {
                    color: 'white',
                    position: 'inner',
                    fontSize: 8,
                },
                /*   labelLine: {
                       show: false
                   },*/
                data: memberData
            },
            {
                name: '党员数量',
                type: 'pie',
                radius: ['65%', '90%'],
                center: ['75%', '50%'],
                data: countData,
                itemStyle: {            //饼图图形上的文本标签
                    normal: {
                        label: {
                            show: true,
                            position: 'inner',
                            fontSize: 12,
                            align: "left",
                            textStyle: {
                                fontWeight: 300,
                                fontSize: 8,   //文字的字体大小
                                color: 'white'
                            },
                            formatter: '{d}%'
                        }
                    }
                }
            }
        ]
    };

    countChart.setOption(option, true);
    countChart.hideLoading();
</script>