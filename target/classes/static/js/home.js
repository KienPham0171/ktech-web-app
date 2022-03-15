$(document).ready(function() {

    var templateUrl = "http://localhost:8080";

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
            var api = templateUrl+ '/api/v1/products/' + key;
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
        $('#_result_Search').animate({opacity:0});
    })

//sticky header



    var fetchData=true;
    window.onscroll = function(){
        var cats = [];
        $('.category_item').each(function() { cats.push($(this).attr('id')) });

        cats.forEach(
            cat=>{
                var api = templateUrl + '/api/v1/categories/products/' + cat;
                console.log(api)
            }
        )
        // var cat = $('.category_item').attr('id');
        // var api = templateUrl + '/api/v1/categories/products/' + cat;
        if(window.pageYOffset >1000){
            cats.forEach(
                cat=>{
                    var api = templateUrl + '/api/v1/categories/products/' + cat;
                    if(fetchData)
                    {

                        axios.get(api)
                            .then(response => {
                                var id = '#'+cat;
                                var name = $(`#cat_name_id${cat}`).val();
                                var html = `
                                     <div class="category_Name">
                                        <h3 class="_title">${name.toUpperCase()}
                                            <a class="_right_title" href="#">All Items...</a>
                                        </h3>
                                        
                                     </div>
                                     <hr>


                                     <div id="books_inside_id${cat}" class="books_inside ">
                <!-- <div class="_onebookItem"></div> -->
                                     </div>
                                `
                                $(id).html(html);
                                $(id).removeClass('cat_loading');
                                $(id).removeClass('cat_loading');
                                return response.data;
                            })
                            .then(data => {
                                html = '';
                                if(data.length > 0) {
                                    data.map(book => {
                                        var link = `productDetails/${book.id}`;
                                        html += `
                 <a href='${link}'>   
                    <div class="_onebookItem">
                        <img class="_hover_scale" style='width:100%;height:100%;' src='${book.image}' />
                    </div>
                 </a>`
                                    })
                                }

                                $(`#books_inside_id${cat}`).html(html);
                            })
                    }
                }
            )
            fetchData = false;
            // if(fetchData)
            // {
            //     fetchData = false;
            //     axios.get(api)
            //         .then(response => {
            //             $(".category_Name").html(`<h3>${cat.toUpperCase()}</h3>`)
            //             $('.category_Name').removeClass('cat_loading');
            //             $('.books_inside').removeClass('cat_loading');
            //             return response.data;
            //         })
            //         .then(data => {
            //             html = '';
            //             data.map(book => {
            //                 var link = `productDetails/${book.id}`;
            //                 html+=`
            //      <a href='${link}'>
            //         <div class="_onebookItem">
            //             <img style='width:100%;height:100%;' src='${book.image}' />
            //         </div>
            //      </a>`
            //             })
            //             $('.books_inside').html(html);
            //         })
            // }
        }

    }



})

