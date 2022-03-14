$(document).ready(function() {

    var domain = "http://localhost:8080/";

    st =document.getElementsByClassName("optionSale");
    st[0].classList.add("active");

    var userId = $('#_priUserId').val().toString();
    console.log("userId: ",userId);
    var productId = $('#_productId').val().toString();

    var arr = [];
    $(".optionSale input").each(function() { arr.push($(this).val()) });
    console.log(arr)
    var optionName = arr[0]; var t2 = arr[5]; var t3 = arr[8]; var t1 = arr[2];
    var price = arr[1];
    console.log("userId = "+userId+ " productId = "+ productId);
    console.log("optionName = "+optionName+ " price = "+ price);

    $("#"+t1).click(function () {
        for (var i = 0; i < st.length; i++) {
            st[i].classList.remove("active");
        }
        document.getElementById(t1).classList.add("active")
        optionName = arr[0];
        price = arr[1];
    })

    $("#"+t2).click(function () {
        for (var i = 0; i < st.length; i++) {
            st[i].classList.remove("active");
        }
        document.getElementById(t2).classList.add("active")
        optionName = arr[3];
        price = arr[4];
    })

    $("#"+t3).click(function () {

        for (var i = 0; i < st.length; i++) {
            st[i].classList.remove("active");
        }
        document.getElementById(t3).classList.add("active")

        optionName = arr[6];
        price = arr[7];

    })


    $("#addCartLine").click(function(){
        console.log("userId: ",userId);
        var api = `{
            "userId": "${userId}",
            "productId": "${productId}",
            "optionName": "${optionName}",
            "price": "${price}"
        }`;


        axios.post('/api/cartline', api).then(response=>{
            console.log(response);
        })

    });
    $("#btn_send_comment").click(()=>{
        userId = $("#userId").val();
        productId = $("#productId").val();
        comment =$("#comment").val();
        var bodyData = {
            content: comment,
            userId: userId,
            productId: productId

        }
        console.log(bodyData);
        api = domain+ "api/comments";
        axios.post(api,bodyData).then( response =>{
            console.log(response);
            html = `
                <div class="dt_comments cmt_center" style="width: 20%;">
                    <i style="color:darkslateblue;font-size: 30px" class="far fa-user"></i> <br>
                    <span style="color:cadetblue">${response.data.user.fullName}</span>
                </div>
                <div  class="dt_comments cmt_content" style="width: 79%">
                    <span >${response.data.content}</span>
                    <p id="${response.data.id}" class="p_absolute"> <span style="color:#0082ca">Reply</span>-
                        <span >${response.data.createdDate}</span>
                      
                    </p>

                </div>
           `;
            $("#cmt_div").append(html);
        });

    });

    btnReplies = [];
    $(".p_absolute").each(function() { btnReplies.push($(this)) });
    btnReplies.forEach( rep =>{
        rep.click(()=>{
            console.log(rep.attr("id"));
        })
    })

})