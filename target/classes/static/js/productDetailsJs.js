$(document).ready(function() {


    var userId = $('#_priUserId').val().toString();
    var productId = $('#_productId').val().toString();

    var arr = [];
    $(".optionSale input").each(function() { arr.push($(this).val()) });
    console.log(arr)
    var optionName = arr[0]; var t2 = arr[5]; var t3 = arr[8];
    var price = arr[1];
    console.log("userId = "+userId+ " productId = "+ productId);
    console.log("optionName = "+optionName+ " price = "+ price);
    $("#"+t2).click(function () {

        optionName = arr[3];
        price = arr[4];
    })

    $("#"+t3).click(function () {
        optionName = arr[6];
        price = arr[7];

    })

    $("#addCartLine").click(function(){
        var api = `{
            "userId": "${userId}",
            "productId": "${productId}",
            "optionName": "${optionName}",
            "price": "${price}"
        }`;


        axios.post('/api/cartline', api).then(response=>{
            console.log(response);
        })

    })
})