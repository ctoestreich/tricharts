<script type="text/javascript">
    google.load('visualization', '1', {'packages':['corechart'], 'callback':drawVisualization});
    function drawVisualization() {
        visualization_data = new google.visualization.DataTable();
    <g:each in="${columns}" var="item">
        visualization_data.addColumn('${item.type}', '${item.name}');
    </g:each>
    <g:each in="${data}" var="item">
        visualization_data.addRow(${item});
    </g:each>
        visualization = new google.visualization.LineChart(document.getElementById('${id}'));
        visualization.draw(visualization_data, {
            legend:{ position:'none'},
            hAxis:{format:'mm:ss'},
            animation:{
                duration: 1000,
                easing: 'out'
            },
            title:'${title}',
            width:'100%',
            height:${height}});
    }
</script>

<div id="${id}"></div>