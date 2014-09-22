$(function () { 
    $('#elochart').highcharts({
        chart: {
            type: 'line'
        },
        title: {
            text: 'Chart test'
        },
        xAxis: {
        	type: 'datetime',
            title: {
            	text: 'Time'
            }
        },
        yAxis: {
            title: {
                text: 'ELO-rating'
            }
        },
        series: [{
            name: 'ELO',
            data: chartdata
        }]
    });
});