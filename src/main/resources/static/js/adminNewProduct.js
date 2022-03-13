$(document).ready(function() {
   var id = $("#checkId").val();
   if(id != -1){
        console.log("id khac -1");
         $('#formId').attr('action', '/admin/updateProduct');

   }
})