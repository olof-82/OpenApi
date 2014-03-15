function appendSoundCloudTrack(track) {
    var divId = "soundcloud" + track.id;
    $("#soundCloudItems").append('<div id="' + divId + '"/>');
    SC.oEmbed(track.uri, $('#' + divId).get(0));
}

function setupSoundCloudMusicOnline() {
    SC.get('/tracks', { q: 'Beardyman'}, function(tracks) {
        for (var i = 0; i < Math.min(3, tracks.length); i++) {
            var item = tracks[i];
//            $("#soundCloudItems").append('<div>' + item.user.username + " / " + item.title + '</div>');
            appendSoundCloudTrack(item);
        }
    });
}

function appendYouTubeTrack(youTubeId) {
    var divId = "jutubtrack-" + youTubeId;
    $("#youTubeItems").append('<div id="' + divId + '"/>');
    $("#" + divId).html('<iframe id="ytplayer" type="text/html" width="640" height="360" src="https://www.youtube.com/embed/' + youTubeId + '" frameborder="0" allowfullscreen>');
}

function setupYouTubeMusicOnline() {
    $.get('http://gdata.youtube.com/feeds/api/videos',
        {q: 'Beardyman'}, function(xml) {
            var ids = $(xml).find('entry id');
            for (var i = 0; i < Math.min(3, ids.length); i++) {
                var id = ids[i];
                var youTubeId = id.textContent.substring(id.textContent.lastIndexOf('/') + 1);
                appendYouTubeTrack(youTubeId);
            }
        });
//    appendYouTubeTrack();
}

function setupMusicOnline() {
    setupYouTubeMusicOnline();
    setupSoundCloudMusicOnline();
}

