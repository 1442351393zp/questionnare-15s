$(function () {
    var receivedCount = [];
    var receivedTime = [];
    $.ajax({
        url:"/questionnaire/question/receiveLineChart",
        method:"get",
        async:false,
        data:{
            "reelId":rId
        },
        success:function(res){
            if(res.code == 0){
                for(var i = 0; i<res.data.length; i++){
                    var item = res.data[i];
                    receivedCount.push(item.receivedCount);
                    receivedTime.push(item.receivedTime)
                }
            }
        }
    })
    var mayChart = echarts.init(document.getElementById('lineEcharts'));
    var option = {
        // title:{
        //     text:'本月每天数据采集记录汇总',
        //     subtext:dayTime+"月份",
        // },
        // legend: {
        //     data:['采集记录数'],
        //     show:true
        // },
        // xAxis:{
        //     type:'category',
        //     data:day,
        //     name:"日期"
        // },
        // yAxis:{
        //     type:'value',
        //     name:"每日采集条数"
        // },
        xAxis:{
            // type:'category',
            name:"日期",
            data: receivedTime,
            boundaryGap:false
        },
        yAxis:{
            name:"回收量"
        },
        series:[{
            symbolSize:20,
            data:receivedCount,
            type:'scatter',
            // barWidth:30,
            // itemStyle:{
            //     normal:{
            //         label:{
            //             show:true,
            //             position:'top'
            //         }
            //     }
            // }
        }]
    };
    mayChart.setOption(option);
    // window.onresize = echartsHistogram.resize;
    // $("#histogram").resize(echartsHistogram.resize);
})