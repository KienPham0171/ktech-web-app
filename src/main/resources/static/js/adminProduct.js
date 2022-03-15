$(document).ready(function() {

    //var apiTemplate = "http://localhost:8080/api/v1/admin/products/";
    var apiTemplate = "https://ktechapp.herokuapp.com/api/v1/admin/products"

    var callBtns = [];
    $(".admin_products_delete").each(function(){callBtns.push($(this)) });
    callBtns.forEach(btn =>{
        btn.click(()=>{
            var delId = btn.attr("id");
            var cartLineId = delId.substring(6,delId.length);

            const modalId = "modal" + cartLineId;
            $(`#${modalId}`).modal();
        })
    })

    var delBtns = [];
    $(".adminDel").each(function(){delBtns.push($(this)) });

    delBtns.forEach( delBtn =>{
        delBtn.click( ()=>{
            console.log("abck");
            var delId = delBtn.attr("id");
            var cartLineId = delId.substring(8,delId.length);

            const rowId = "_removeIdIs" + cartLineId;

            $(".modal-backdrop.fade.show").remove();
            $(".modal.fade.show").remove();
            var api = apiTemplate + cartLineId;
            axios.delete(api).then( ()=>{
                $(`#${rowId}`).remove();
            })


        })
    })


})