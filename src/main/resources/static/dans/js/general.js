// Usage: log('inside coolFunc', this, arguments); // paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
window.log = function(){
    log.history = log.history || [];
    log.history.push(arguments);
    arguments.callee = arguments.callee.caller;
    if(this.console) console.log( Array.prototype.slice.call(arguments) );
};

(function(b){function c(){}for(var d="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),a;a=d.pop();)b[a]=b[a]||c})(window.console=window.console||{});

// Make html elements equally high
$.fn.equalizeHeights = function(){
    return this.height( Math.max.apply(this, $(this).map(function(i,e){ return $(e).height() }).get() ) +30  )
}

var general = {

    init : function() {

        // general.get_useragent();

        // Big target
        $('.big-target').on('click', function() {
            var $this = $(this);
            $anchor = $this.find('a[href]:first');
            if($anchor.length){ window.location = $anchor.attr('href'); }
        });

        // Placeholder magic
        if (!Modernizr.input.placeholder) {
            $("input").each( function(){
                if ($(this).val()=="" && $(this).attr("placeholder")!="") {
                    $(this).val($(this).attr("placeholder"));
                    $(this).focus(function(){
                        if($(this).val()==$(this).attr("placeholder")) $(this).val("");
                    });
                    $(this).blur(function(){
                        if($(this).val()=="") $(this).val($(this).attr("placeholder"));
                    });
                }
            });
        }

        // Open outgoing links in new window
        $('a[rel="external"], a[href^=http]').on('click', function(){
            var $this = $(this),
            domain = location.hostname,
            url = $this.attr('href'),
            sameDomain = url.search(domain);
            if (sameDomain == '-1') {
                $(this).attr('target','_blank');
            }
            return true;
        });

        // Form validation
        if(jQuery.validator){
            $('form.validate').each(function() {
                var $this = $(this);
                $this.validate({
                    errorPlacement: function(error, element) {
                        if (element.parents().hasClass('checkbox')) {
                            element = element.parents('.checkbox');
                            error.insertBefore(element);
                        } else if (element.parents().hasClass('radio')) {
                            element = element.parents('.radio');
                            error.insertBefore(element);
                        } else {
                            error.insertAfter(element);
                        }
                    }
                });
            });
        }

        // Use anchor for submit
        $('form a.submit').on('click', function() {
            var $this = $(this);
            var $form = $this.parents('form');
            var $inputSubmit = $form.find('input[type=submit]');
            if ($inputSubmit.length) {
                $inputSubmit.trigger('click');
            } else {
                $form.append('<input type="submit" class="hidden" />').find('input[type=submit]').trigger('click');
            }
            return false;
        });
    },

    get_useragent: function() {
        os = '';
        mobile = '';
        ua = navigator.userAgent;
        checker = {
            iphone: ua.match(/(iPhone|iPod|iPad)/),
            blackberry: ua.match(/BlackBerry/),
            android: ua.match(/Android/),
            mac: ua.match(/Mac/),
            windows: ua.match(/Windows/),
            firefox: ua.match(/Firefox/),
            chrome: ua.match(/Chrome/),
            safari: ua.match(/Safari/)
        };
        if (checker.android) {
            $('body').addClass('android-only');
            os = 'android';
            mobile = true;
        } else if (checker.iphone) {
            $('body').addClass('ios-only');
            os = 'ios';
            mobile = true;
        } else if (checker.blackberry) {
            $('body').addClass('berry-only')
            os = 'blackberry';
            mobile = true;
        } else if (checker.mac) {
            $('body').addClass('mac-only')
            os = 'mac';
            mobile = false;
            if (checker.firefox) {
                $('body').addClass('firefox')
            } else if(checker.safari){
                $('body').addClass('safari')
            } else if(checker.chrome){
                $('body').addClass('chrome')
            }
        } else if (checker.windows) {
            $('body').addClass('windows-only')
            os = 'windows';
            mobile = false;
            if (checker.firefox) {
                $('body').addClass('firefox')
            } else if(checker.safari){
                $('body').addClass('safari')
            } else if(checker.chrome){
                $('body').addClass('chrome')
            }
        } else {
            $('body').addClass('not-mobile not-windows not-mac');
            os = 'not-mobile not-windows not-mac';
            mobile = false;
        }
        // alert(os);
        // alert(mobile);
    }

}

$(function() {
    general.init();
});



var drawer = function(selector) {
    // Grid system and drawer for project page.
    // The responsive design leads to 1, 3 or 4 columns.
    // Clicking an item opens a drawer under the current row.
    // Expects a selector selecting a list element.

    // Some varables.
    var item_list = $(selector + ' li');
    var active_item = false;
    var column_count = 0;
    var drawer = false;
    var template = $("#template").html();
    var speed = 300;
    var body = $("html, body");

    // Initializes the grid
    var initGrid = function() {
        // Remove the drawer if any.
        if (drawer) {
            $(drawer).remove();
            $(active_item).removeClass('active');
        }
        $(item_list).removeClass('active');            
        // Get `column_count` by inspecting the viewport width.
        if (Modernizr.mq('(max-width: 767px)')) {
            column_count = 1
        } else if (Modernizr.mq('(max-width: 991px)')) {
            column_count = 3
        } else {
            column_count = 4
        }
    };

    // On item click.
    item_list.click(function(event) {
        event.preventDefault();
        if (this === active_item) {
            // Second click on the same item makes the drawer slide up.
            drawer.slideUp(speed, function() {
                // And remove the drawer.
                $(this).remove();
            });
            $(item_list).removeClass('active');
            active_item = false;
        } else {
            $(item_list).removeClass('active');                
            active_item = this;
            $(this).addClass('active');
            // Get the index of the clicked element.
            var index = item_list.index( this ) + 1;
            // Calculate the `insert_point` for the `drawer`.
            // The insert point is after the last item of the same row.
            // Therefore round the `index` up to a multiple of `column_count`.
            insert_point = Math.ceil(index / column_count) * column_count;
            // The last row can have less items.
            // The `insert_point` can't be bigger than length of all items.
            // Limit the `insert_point` to `item_list.length`.
            insert_point = Math.min(insert_point, item_list.length);
            // If there is a `drawer` open, close it.
            if (drawer) {
                $(drawer).slideUp(speed);                
            }
            // Get the data.
            var d = $(this).find('a');
            // Clone a new `drawer` from template.
            drawer = $(template).clone();
            // Fill the template
            drawer.find('h3').html(d.data('title'));
            drawer.find('.intro').html(d.data('intro'));
            drawer.find('.description').html(d.data('description'));
            drawer.find('.more').attr({'href':d.data('href')});
            drawer.find('.img').attr({'src':d.data('largesrc')});
            
            // Check and place contactpersoon.
            var contactpersoon = d.data('contact');
            var contactpersoonemail = d.data('contact-email');
            if (contactpersoonemail === 'None') {
              if (contactpersoon === 'None'){
                 drawer.find('.contactpersoonemail').remove();
              } else {
                 drawer.find('.contactpersoonemail').replaceWith($("<span></span>").text(contactpersoon));
              }
            } else {
                drawer.find('.contactpersoonemail').attr('href', 'mailto:' + contactpersoonemail + '?subject=About%20' + d.data('title'));                
                drawer.find('.contactpersoon').html(contactpersoon);                
            }
            var duration = d.data('duration');
            if (duration === 'None') {
                drawer.find('.duration').remove();
            } else {
                drawer.find('.duration').html(duration)
            }
            // Check and place telefoonnummer.
            var phone = d.data('phone');
            if (phone === 'None') {
                drawer.find('.phone').remove();
            } else {
                drawer.find('.phone').html(phone);                
            }
            if(contactpersoon === 'None' && phone === 'None') {
                drawer.find('.contact').remove();
            }
            var morelink = d.data('href');
            if (morelink === 'None') {
                drawer.find('.more').remove();
            }
            // A click on close is like a click on the `active_item` and therefore will close the drawer.
            drawer.find('.og-close').click(function(){ active_item.click(); });       
        
            // Hide the drawer. 
            drawer = $(drawer).hide();
            // Insert the drawer at the `insert_point`.
            item_list.eq(insert_point - 1).after( drawer );
            // Slide the drawer down.
            drawer.slideDown(speed, function() {
                // If one column layout.
                if (column_count === 1) {
                    // Scroll to the top of the current item.
                    // This animation starts after de drawer slide down is finisched.
                    body.animate({ scrollTop : drawer.offset().top }, speed);
                }
            });
        }
    });

    // Initialise the curtain on load.
    $( document ).ready(function() {
        initGrid();
    });

    // On resize initialise again.
    $(window).resize(function () {
        initGrid();
    });
}

// Fix: Translate searchbox placeholder text.
$(function() {
  var lang = $("html").attr("lang");
  var search = $("#SearchableText"); 
  if (lang === 'en') {
    search.attr('placeholder', 'Search this website');
  } 
});


