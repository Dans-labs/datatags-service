$(function() {
    $(window).on("load resize",function(e){
        $('.shortcuts article').css('height','auto');
        if( $('.shortcuts .run-eq:visible').length){
            $('.shortcuts article').equalizeHeights();
        }
    });

    $(document).ready(function() {

         // Hide the link to hide completed projects.
         $('#hide_completed_projects').hide();

         // Load completed projects in current page.
         $('#show_completed_projects').click(function() {
             var url = $(this).attr("href");
             $('.completed_projects_target').hide()
             $.ajax({url: url + '&ajax_load=1', success: function(data){
                 var jq_data = jQuery(data);
                 var completed_projects = jq_data.find('div.projects').unwrap();
                 var target = $('.completed_projects_target')
                 target.html(completed_projects);
                 target.slideDown(200, function() {
                   drawer('.completed_projects_target ul#og-grid');
                 });
             }});
             $('#show_completed_projects').hide();
             $('#hide_completed_projects').show();

             return false;
         });

         // Hide completed projects in current page.
         $('#hide_completed_projects').click(function() {
             $('.completed_projects_target').slideUp(1000);
             $('.completed_projects_target').html('');
             $('#hide_completed_projects').hide();
             $('#show_completed_projects').show();
             return false;
         });

         // Submit narcis search form when clicking on a category.
         $(document).on('click', '.dropdown-menu li a', function() {
             var search_category = $(this).attr("href");
             $('select#narcis_categories').val(search_category);
             $('#narcis_search_button').click();
             return false;
         });

         // Load more news projects in current/actueel page.
         $(document).on('click', '#show_more_news', function() {
             var url = $(this).attr("data-url");
             $.ajax({url: url + '&ajax_load=1', success: function(data){
                 $('#more_news_target').hide()         
                 $('#more_news_target').replaceWith(data).slideDown(1000);
             }});
             return false;
         });
         
         // Load more events in current/actueel page.
         $(document).on('click', '#show_more_events', function() {
             var url = $(this).attr("data-url");
             $.ajax({url: url + '&ajax_load=1', success: function(data){
                 $('#more_events_target').hide()         
                 $('#more_events_target').replaceWith(data).slideDown(1000);
             }});
             return false;
         });
         
         // Activate the overlay on the Nieuwsbrief link
         // This is based on an older version of JQuery shipped with Plone
         jQuery('#dans_mailchimp_link').prepOverlay({subtype: 'ajax'});
     })
});