//https://stackoverflow.com/questions/43043113/how-to-force-reloading-a-page-when-using-browser-back-button
window.addEventListener( "pageshow", function ( event ) {
    var historyTraversal = event.persisted ||
        ( typeof window.performance != "undefined" &&
            window.performance.navigation.type === 2 );
    if ( historyTraversal ) {
        // Handle page restore.
        window.location.reload();
    }
});

window.onload = function() {
}

function vyberPeniaze() {
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/prevezmiPeniaze',
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            document.getElementById("prevziatButton").classList.add("hidden");
            document.getElementById("hlaskaPoStlaceniTlacidla").classList.remove("hidden");
        },
        error: function() {
            alert("error");
        }
    });
}

function vratPeniaze() {
    $.ajax ({
        type: "POST",
        url: 'http://localhost:8080/X-Tipping/vratPeniaze',
        dataType: "json",
        contentType: "application/json",
        success: function(data) {
            document.getElementById("vratitPeniazeButton").classList.add("hidden");
            document.getElementById("vratenePeniazeHlaska").classList.remove("hidden");
        },
        error: function() {
            alert("error");
        }
    });
}