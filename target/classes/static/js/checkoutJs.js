$(document).ready(function() {

    $('#paymentMethod').change(()=>{
       if($('#paymentMethod').val()=="order"){
           $('#paymentForm').attr("action","create-order");
       }
    })
})