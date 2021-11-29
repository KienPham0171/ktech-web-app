$(document).ready(function() {

    let timeout= null;
    $('#_searchinput').on('change keyup paste',event => {
        resultSearch = document.getElementById("_result_Search");
        resultSearch.style="display:block;border-radius:3px;";
        var heading = $('.heading');
        heading.removeClass("loading");
        var resultFor = "Result for "+ $('#_searchinput').val();
        heading.text(resultFor);
        clearTimeout(timeout);
        timeout =  setTimeout(()=>{
            let key = $('#_searchinput').val();
            var api = 'http://localhost:8080/api/v1/products/' + key;
            axios.get(api)
                .then((response) =>{
                    $('.img').removeClass('loading');
                    return response.data;
                })
                .then(data => {
                    var html = '';
                    data.map(item=>{

                        var add = `<a href="">
                    <div id="shortItem">
                      <div class="shortItemChild _st1">
                         <img class='smallProduct' src="${item.image}" alt=""> 
                      </div>
                      <div class="shortItemChild _st2">
                        <h5>${item.productName}</h5>
                        <p id='_11px'>Author: ${item.description}</p>
                      </div>
                      <div class="shortItemChild _st3">
                        <h5>Price </h5>
                        <h6 style='color:red;'>$ ${item.price}</h6>
                      </div>
                      
                    </div>
                  </a>`;
                        html+=add;
                    })
                    $('.content').html(html);
                });
            console.log(api);
        },1000);


    });
    $('body').click(function(){
        console.log('done')
        $('#_result_Search').animate({opacity:0});
    })

//sticky header



    var fetchData=true;
    window.onscroll = function(){
        console.log("goi api")
        var cat = $('.category_item').attr('id');
        var api = 'http://localhost:8080/api/v1/categories/products/' + cat;
        if(window.pageYOffset >1000){
            if(fetchData)
            {
                fetchData = false;
                axios.get(api)
                    .then(response => {
                        $(".category_Name").html(`<h3>${cat.toUpperCase()}</h3>`)
                        $('.category_Name').removeClass('cat_loading');
                        $('.books_inside').removeClass('cat_loading');
                        return response.data;
                    })
                    .then(data => {
                        html = '';
                        data.map(book => {
                            html+=`
                 <a href=''>   
                    <div class="_onebookItem">
                        <img style='width:100%;height:100%;' src='${book.image}' />
                    </div>
                 </a>`
                        })
                        $('.books_inside').html(html);
                    })
            }
        }

    }



})

