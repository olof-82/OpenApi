function setupMusicOnline() {
    SC.get('/tracks', { q: 'Beardyman'}, function(tracks) {
        for (var i = 0; i < tracks.length; i++) {
            var item = tracks[i];
            $("#soundCloudItems").append('<div>' + item.user.username + " / " + item.title + '</div>');
        }
    });
}

