window.onload = ()=>{

    let catLabels = [];
    $(".catLabel").each(function() { catLabels.push($(this).text()) });
    let catData = [];
    $(".catData").each(function() { catData.push($(this).text()) });
    draw("myChart",catLabels,catData);
}

const generateColors = ()=>{
    let r =parseInt(Math.random()*255);
    let g =parseInt(Math.random()*255);
    let b =parseInt(Math.random()*255);
    var result = `rgb(${r},${g},${b})`;

    console.log(result);
    return result;
}

function draw(id,labels=[],catData=[]){
    let colors = [];
    for(var i = 0; i< labels.length;i++){
        colors.push(generateColors());
    }
    const data = {
        labels: labels,
        datasets: [{
            label: 'My Shop',
            data: catData,
            backgroundColor: colors,
            hoverOffset: 4
        }]
    };
    const config = {
        type: 'doughnut',
        data: data,
    };
    let ctx =document.getElementById(id).getContext("2d");
    new Chart(ctx, config);
}