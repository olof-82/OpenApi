function appendSoundCloudTrack(track) {
    var divId = "soundcloud" + track.id;
    $("#soundCloudItems").append('<div id="' + divId + '"/>');
    SC.oEmbed(track.uri, $('#' + divId).get(0));
}

function setupSoundCloudMusicOnline(artist) {
    SC.get('/tracks', { q: artist}, function(tracks) {
        for (var i = 0; i < Math.min(5, tracks.length); i++) {
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

function setupYouTubeMusicOnline(artist) {
    $.get('http://gdata.youtube.com/feeds/api/videos',
        {q: artist}, function(xml) {
            var ids = $(xml).find('entry id');
            for (var i = 0; i < Math.min(5, ids.length); i++) {
                var id = ids[i];
                var youTubeId = id.textContent.substring(id.textContent.lastIndexOf('/') + 1);
                appendYouTubeTrack(youTubeId);
            }
        });
}

function appendSpotifyTrack(spotifyId) {
    var divId = "spottantrack-" + spotifyId.substring(spotifyId.lastIndexOf(':')+1);
    $("#spotifyItems").append('<div id="' + divId + '"/>');
    $("#" + divId).html('<iframe src="https://embed.spotify.com/?uri=' + spotifyId + '" width="300" height="380" frameborder="0" allowtransparency="true"></iframe>');
}

function setupSpotifyMusicOnline(artist) {
    $.get('http://ws.spotify.com/search/1/track',
        {q: 'artist:' + artist}, function(xml) {
            var ids = $(xml).find('track');
            for (var i = 0; i < Math.min(5, ids.length); i++) {
                var id = ids[i];
                var spotifyId = id.attributes['href'].textContent;
                appendSpotifyTrack(spotifyId);
            }
        });

}

function setupMusicOnline(artist) {
    artist = artist || 'Beardyman';
    setupSpotifyMusicOnline(artist);
    setupYouTubeMusicOnline(artist);
    setupSoundCloudMusicOnline(artist);
}

