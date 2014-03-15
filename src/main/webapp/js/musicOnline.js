function appendTrack(track) {
    var divId = "soundcloud" + track.id;
    $("#soundCloudItems").append('<div id="' + divId + '"/>');
    SC.oEmbed(track.uri, $('#' + divId).get(0));
}

function setupMusicOnline() {
    SC.get('/tracks', { q: 'Beardyman'}, function(tracks) {
        for (var i = 0; i < Math.min(3, tracks.length); i++) {
            var item = tracks[i];
//            $("#soundCloudItems").append('<div>' + item.user.username + " / " + item.title + '</div>');
            appendTrack(item);
        }
    });
}

