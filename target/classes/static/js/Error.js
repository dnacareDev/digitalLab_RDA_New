function getErrorMessage(err_msg_id){
    $.ajax({
        url:"/err-message",
        type:"GET",
        data:{
            err_msg_id : err_msg_id
        },
        success:function(data){
            alert(data.msg);
        }
    })
}