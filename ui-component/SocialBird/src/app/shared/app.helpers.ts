

declare var jQuery:any;
 

export function detectBody() {
    if (jQuery(document).width() < 769) {
        jQuery('body').addClass('body-small')
    } else {
        jQuery('body').removeClass('body-small')
    }
}
