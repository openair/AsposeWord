$(function() {

   // $('#side-menu').metisMenu();

});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        topOffset = 84;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    
    
    $(".nav-list li a").click(function(e){
        if($(this).parent().hasClass("nav-header")==false && $(this).hasClass("active")==false){
            $(".nav-list li a").removeClass("active");
            $(this).addClass("active");
            $(".admin-panels").hide();
            var selectedLi = $(this).attr("data-key");
            $("."+selectedLi).fadeIn();
            $(".page-header").html($(this).text());
            if(selectedLi=="backup-database"){
                loadDB(e)
            }
            else if(selectedLi=="restore-database"){
                loadData('db','yearList', e)
                
            }else if(selectedLi=="backup-website"){
                loadWS(e)
                
            }else if(selectedLi=="restore-website"){
                loadData('ws','yearListWS', e)
            }
        }
    })
});
