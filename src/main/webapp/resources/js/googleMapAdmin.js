jQuery(document).ready(function ($) {
    var infowindow = null;

    function initialize() {
        var centerLat = getUrlParameter('lat');
        if(centerLat === undefined) {
            centerLat = 50.0752156;
        }

        var centerLng = getUrlParameter('lng');
        if(centerLng === undefined) {
            centerLng = 14.4302564;
        }

        var currentId = getUrlParameter('id');

        var mapCanvas = document.getElementById('map_canvas');
        var mapOptions = {
            center: new google.maps.LatLng(centerLat, centerLng),
            zoom: 13,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }

        var map = new google.maps.Map(mapCanvas, mapOptions);

        $.getJSON('../api/v1/incidents/currentUser', function(data) {

            $.each( data, function(i, value) {
                var image = '../resources/img/map_marker_reported_new.png'
                if(value.state == 'IN_PROGRESS') {
                    image = '../resources/img/map_marker_reported_in_progress.png';
                }

                var title = value.title
                if(title.length > 20) {
                    title = title.substring(0,20) + "...";
                }

                var myLatlng =  new google.maps.LatLng(value.lat, value.lon);
                var marker = new google.maps.Marker({
                    position: myLatlng,
                    map: map,
                    title: title,
                    icon: image
                });


                google.maps.event.addListener(marker, 'click', function(){
                    if(infowindow) {
                        infowindow.close();
                    }

                    infowindow = new google.maps.InfoWindow();
                    infowindow.setContent("<h3>" + title + "</h3><p>Detail incidentu <a href='incident.xhtml?id=" + value.id + "'>ZDE</a></p>");
                    infowindow.open(map, marker);
                });

                if(currentId !== undefined && currentId == value.id) {
                    if(infowindow) {
                        infowindow.close();
                    }

                    infowindow = new google.maps.InfoWindow();
                    infowindow.setContent(getInfoWindowContent(title, value.id));
                    infowindow.open(map, marker);

                    map.setCenter(new google.maps.LatLng(value.lat, value.lon));
                }

            });

        });

    }

    google.maps.event.addDomListener(window, 'load', initialize);
});