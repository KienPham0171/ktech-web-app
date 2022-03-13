$(document).ready(function() {
    //address reduce;
    var apiTemplate = "http://localhost:8080/api/v1/cartLines/";
    var completed = true;
    var arrReduce = [];
    $(".reduce.cart_30").each(function() { arrReduce.push($(this)) });
    arrReduce.forEach(reduceButton=>{
       reduceButton.click( ()=>{
           if(completed){
               completed = false;
               spanid = reduceButton.attr("id");
               cartLineId =spanid.substring(6,spanid.length);
               inputFieldId = "inputField"+ cartLineId;
               var input = $(`#${inputFieldId}`);
               var quantity = input.val();
               input.val(quantity-1);

               var pricesId = "prices"+ cartLineId;
               priceId = "price"+cartLineId;
               $(`#${pricesId}`).html("$ "+parseFloat($(`#${priceId}`).text())*input.val());

               cart_quantity_id = "cart_quantity"+cartLineId;
               $(`#${cart_quantity_id}`).css("opacity",0.5);
               var api = apiTemplate + `${cartLineId}/${input.val()}`;

               axios.put(api).then(
                   response=>{
                       $(`#${cart_quantity_id}`).css("opacity",1);
                       completed = true;
                       console.log(response);
                   }
               )

           }
       })
    })
    //address increase;
    var arrIncrease = [];
    $(".increase.cart_30").each(function() { arrIncrease.push($(this)) });
    arrIncrease.forEach(increaseButton=>{
        increaseButton.click( ()=>{
            if(completed){
                completed =false;
                spanid = increaseButton.attr("id");
                cartLineId =spanid.substring(8,spanid.length);
                inputFieldId = "inputField"+ cartLineId;
                var input = $(`#${inputFieldId}`);
                var quantity = parseInt(input.val());
                input.val(quantity+1);

                var pricesId = "prices"+ cartLineId;
                priceId = "price"+cartLineId;
                $(`#${pricesId}`).html("$ "+parseFloat($(`#${priceId}`).text())*input.val());

                cart_quantity_id = "cart_quantity"+cartLineId;
                $(`#${cart_quantity_id}`).css("opacity",0.5);

                var api = apiTemplate + `${cartLineId}/${input.val()}`;

                axios.put(api).then(
                    response=>{
                        $(`#${cart_quantity_id}`).css("opacity",1);
                        completed = true;
                        console.log(response);
                    }
                )
            }



        })
    })



    var inputFields = [];
    $(".cart_30.cart_input").each(function(){inputFields.push($(this)) });

    inputFields.forEach(inputField =>{
        inputField.blur(()=>{

            inputFieldId = inputField.attr("id");
            cartLineId = inputFieldId.substring(10,inputFieldId.length);
            var hiddenId = "hidden"+ cartLineId;
            const hiddenInput = $(`#${hiddenId}`);

            if(parseInt(hiddenInput.val()) !== parseInt(inputField.val())){
                const cart_quantity_id = "cart_quantity"+cartLineId;
                $(`#${cart_quantity_id}`).css("opacity",0.5);
                var api = apiTemplate + `${cartLineId}/${inputField.val()}`;
                axios.put(api).then(
                    response=>{
                        $(`#${cart_quantity_id}`).css("opacity",1);
                        hiddenInput.val(inputField.val());
                        var pricesId = "prices"+ cartLineId;
                        priceId = "price"+cartLineId;
                        $(`#${pricesId}`).html("$ "+parseFloat($(`#${priceId}`).text())*inputField.val());
                    }
                )
            }
        })
    })

    var delBtns = [];
    $(".btnForDel").each(function(){delBtns.push($(this)) });
    console.log(delBtns)
    delBtns.forEach( delBtn =>{
        delBtn.click( ()=>{
             console.log("abck");
            var delId = delBtn.attr("id");
            var cartLineId = delId.substring(9,delId.length);

            const rowId = "row" + cartLineId;
            const modalId = "modal" + cartLineId;
            $(".modal-backdrop.fade.show").remove();
            var api = apiTemplate + cartLineId;
            axios.delete(api).then( ()=>{
                $(`#${rowId}`).remove();
            })

        })
    })

    var callBtns = [];
    $(".cart_delete").each(function(){callBtns.push($(this)) });
    callBtns.forEach(btn =>{
        btn.click(()=>{
            var delId = btn.attr("id");
            var cartLineId = delId.substring(6,delId.length);

            const modalId = "modal" + cartLineId;
            $(`#${modalId}`).modal();
        })
    })




})