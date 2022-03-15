$(document).ready(function (){

    //const apiTemplate = "http://localhost:8080/api/v1/admin/orders/"
    const apiTemplate ="https://ktechapp.herokuapp.com/api/v1/admin/orders/";


    var notConfBtns = [];
    $(".status_sm").each(function(){notConfBtns.push($(this)) });

    notConfBtns.forEach( changeBtn =>{
        changeBtn.change((x, y)=>{
            console.log(changeBtn.attr("id"));

            var changeId = changeBtn.attr("id");
            var invoiceId = changeId.substring(7,changeId.length);
            if(changeBtn.val() === "Confirmed"){
                console.log("do something");
                var api = apiTemplate + invoiceId+"/confirmed";
                axios.put(api).then(result =>{
                    const rowId = "invoice_" + invoiceId;

                    const statusId = 'status_' +invoiceId;
                    const somethingId = "something_" + invoiceId;
                    var html = $(`#${rowId}`).html();
                    $(`#${rowId}`).remove();
                    $('#confirmed_table').prepend(`<tr id="confirmed_${invoiceId}">${html}</tr>`)
                    $(`#${statusId}`).val("Confirmed");
                    $(`#${statusId}`).attr("id",somethingId);
                    $(`#${somethingId}`).attr("class","something_cm");

                    const optionId = 'option_' + invoiceId;
                    $(`#${optionId}`).val("Completed");
                    $(`#${optionId}`).text("COMPLETED");
                    $(`#${somethingId}`).each(function(){confBtns.push($(this)) });
                    var button = $(`#${somethingId}`);

                    button.change(function (){
                        var changeId = button.attr("id");
                        var invoiceId = changeId.substring(10,changeId.length);
                        if(button.val() === "Completed"){
                            console.log("confBtns hien tai: ",confBtns);
                            var api = apiTemplate + invoiceId+"/completed";

                            const rowId = "confirmed_" + invoiceId;

                            const statusId = 'something_' +invoiceId;
                            const somethingId = "completed" + invoiceId;

                            const optionId = 'option_' + invoiceId;

                            var html = $(`#${rowId}`).html();
                            $(`#${rowId}`).remove();
                            $(`#${optionId}`).remove();
                            $('#completed_table').prepend(`<tr id="completed_${invoiceId}">${html}</tr>`)
                            $(`#${statusId}`).val("Completed");
                            $(`#${statusId}`).attr("id",somethingId);
                            $(`#${statusId}`).attr("class","completed");

                        }
                    })
                })

            }

        })
    })



    var confBtns = [];
    $(".something_cm").each(function(){confBtns.push($(this)) });

    console.log("confBtns: ",confBtns);
    confBtns.forEach( changeBtn =>{

        changeBtn.change((x, y)=>{
            console.log(changeBtn.attr("id"));

            var changeId = changeBtn.attr("id");
            var invoiceId = changeId.substring(10,changeId.length);

            if(changeBtn.val() === "Completed"){
                console.log("confBtns hien tai: ",confBtns);
                var api = apiTemplate + invoiceId+"/completed";

                axios.put(api).then(()=>{

                    const rowId = "confirmed_" + invoiceId;

                    const statusId = 'something_' +invoiceId;
                    const somethingId = "completed" + invoiceId;

                    const optionId = 'option_' + invoiceId;

                    var html = $(`#${rowId}`).html();
                    $(`#${rowId}`).remove();
                    $(`#${optionId}`).remove();
                    $('#completed_table').prepend(`<tr id="completed_${invoiceId}">${html}</tr>`)
                    $(`#${statusId}`).val("Completed");
                    $(`#${statusId}`).attr("id",somethingId);
                    $(`#${statusId}`).attr("class","completed");
                })

            }

        })
    })



})