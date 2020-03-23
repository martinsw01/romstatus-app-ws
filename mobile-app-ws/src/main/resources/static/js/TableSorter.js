/*
Easteregg vi velger å kommentere vekk. Funker ikke for øyeblikket.
function() {
  "use strict";
  // Some key codes that are used
  var up = 38,
    down = 40,
    left = 37,
    right = 39,
    A = 65,
    B = 66;
  // Full Konami Code obtained from: http://en.wikipedia.org/wiki/Konami_Code
  var konamiCode = [up, up, down, down, left, right, left, right, B, A];
  // Deteted sequence. Empty by default
  var konamiDetected = [];

  // Attachs the function on an element (for a certain event)
  function attachCustomEvent(el, eventName, desiredFunction) {
    if (el.addEventListener) {
      el.addEventListener(eventName, desiredFunction, false);
      // Old IE
    } else {
      el.attachEvent('on' + eventName, desiredFunction);
    }
  }

  // Detachs the function on an element (for a certain event)
  function detachCustomEvent(el, eventName, desiredFunction) {
    if (el.removeEventListener) {
      el.removeEventListener(eventName, desiredFunction, false);
      // Old IE
    } else {
      el.detachEvent('on' + eventName, desiredFunction);
    }
  }

  // Function that is invoked after detecting the Konami Code
  function startUpKonami() {
    // Prevent further detection (When removing this line the Konami code can be entered multiple times)
    detachCustomEvent(document, "keydown", isKonamiKey);
    konamiIsDetected();
  }

  // Function to detect whether the pressed key is part of the Konami Code
  function isKonamiKey(e) {
    var evt = e || window.event;
    var key = evt.keyCode ? evt.keyCode : evt.which;
    // Set to true before checking everything
    var codeOk = true;
    // Push the key
    konamiDetected.push(key);
    // Check if the key is valid or not
    if (konamiDetected.length < konamiCode.length) {
      // Check that the values are Ok so far. If not clear the array
      for (var i = 0, max = konamiDetected.length; i < max; i++) {
        if (konamiDetected[i] !== konamiCode[i]) {
          codeOk = false;
        }
      }
      if (!codeOk) {
        // Clean the array
        konamiDetected = [];
        // Push the just detected value inside the array
        konamiDetected.push(key);
      }
    } else if (konamiDetected.length === konamiCode.length) {
      for (var j = 0, max = konamiDetected.length; j < max; j++) {
        if (konamiDetected[j] !== konamiCode[j]) {
          codeOk = false;
        }
      }
      // Clean the array
      konamiDetected = [];
      if (codeOk) {
        startUpKonami();
      }
      // This should never happen, but if it happens we clean the array
    } else {
      konamiDetected = [];
    }
    // After everything has been checked show the resulting array after pressing such key
    // console.log(konamiDetected);
  }

  // Attach the event detection to the whole document
  attachCustomEvent(document, "keydown", isKonamiKey);
})();*/

// Function that is invoked after the konami code has been entered
/*function konamiIsDetected() {
  window.location.href = 'https://commons.wikimedia.org/wiki/File:Bg-easter-eggs.jpg';
}*/


// Bla mellom sidene
function side(n) {
  //Finner elementer
  var a, b, c, d = 0;
  a = document.getElementById("Oversikt");
  b = document.getElementById("kart")
  c = document.getElementById("trengerHjelp");
  d = document.getElementById("omP");
  if (n === 1) {
    //Gjemmer/viser dem 
    a.style.display = "block";  
    b.style.display = "none";
    c.style.display = "none";
    d.style.display = "none";
    //Endrer stilen til elementene i menyen avhengig om de er aktive eller ikke
    document.getElementById("over").className = "active";
    document.getElementById("kartm").className = "inactive";
    document.getElementById("hjelp").className = "inactive";
    document.getElementById("pro").className = "inactive";
  } else if (n === 2) {
    a.style.display = "none";
    b.style.display = "grid";
    c.style.display = "none";
    d.style.display = "none";
    document.getElementById("over").className = "inactive";
    document.getElementById("kartm").className = "active";
    document.getElementById("hjelp").className = "inactive";
    document.getElementById("pro").className = "inactive";
  } else if (n === 3) {
    a.style.display = "none";
    b.style.display = "none";
    c.style.display = "block";
    d.style.display = "none";
    document.getElementById("over").className = "inactive";
    document.getElementById("kartm").className = "inactive";
    document.getElementById("hjelp").className = "active";
    document.getElementById("pro").className = "inactive";
  } else {
    a.style.display = "none";
    b.style.display = "none";
    c.style.display = "none";
    d.style.display = "block";
    document.getElementById("over").className = "inactive";
    document.getElementById("kartm").className = "inactive";
    document.getElementById("hjelp").className = "inactive";
    document.getElementById("pro").className = "active";
  }
}

// Sorter tabellen
/*
Sorterer først oppover. Hvis den har sortert gjennom hele uten at noe er blitt forandret, sorterer den nedover.

*/
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("table");
  switching = true;
  // Setter retning oppover:
  dir = "asc";
  
  while (switching) {
    //Starter med å si at ingen bytting har blitt gjort:
    switching = false;
    rows = table.rows;
    /*Looper gjennom alle radene utenom den øverste: */
    for (i = 1; i < (rows.length - 1); i++) {
      //Antar at det ikke burde byttes:
      shouldSwitch = false;
      /* Finner to elementer, den ene fra raden over den andre */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Sjekker om de burde bytte plass avhengig av retning */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // Hvis retningen er oppover, og den over er mindre enn den under, burde de bytte
          shouldSwitch = true;
          break; //avbryter for-loopen
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* Hvis vi kom fram til at de burde bytte plass: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true; //Sier at vi har byttet en gang
      // Hver gang vi bytter, øker switchcount med 1:
      switchcount++;
    } else {
      /* Hvis ingen rader har byttet plass, har ikke switchcount økt. Da prøver vi å sortere andre veien. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

//Not used, but effective
/*function sortByStatusAndQuality() {
  sortTable(4);
  sortTable(3);
}*/


//Does not work with new classes, names, titles, etc.:
//oppdatere Kart etter tabell
function oppdatereKartDel(id, n) {
  var table, rows, status, boks, LuftKval = 0;
  table = document.getElementById('myTable');
  rows = table.rows;
  status = rows[n].getElementsByTagName("TD")[3].innerHTML;  //Finner string i den tredje kollonen i den n. raden
  LuftKval = rows[n].getElementsByTagName("TD")[4].innerHTML;
  boks = document.getElementById(id + 'K');
  boks.getElementsByTagName('td')[1].innerHTML = status;    //Finner string i celle i tabell i kart og erstatter med status
  boks.getElementsByTagName('td')[3].innerHTML = LuftKval;
  if (status == 'Ledig') {  //endrer farge hvis ledig
    if (LuftKval == 'Hoy') {  
      boks.style.backgroundColor = '#57BB3E'; //Til grønn
    }
    else if (LuftKval == 'Middels') {
      boks.style.backgroundColor = '#FFBB00'; //Til gul
    }
    else if (LuftKval == 'Lav') {
      boks.style.backgroundColor = '#FF7440';  //Til rød
    }
  }
}

function statusFarge2() {   //Kaller funksjonen oppdatereKartDel for alle rom
  for (var i = 0; i < 5; i++) {
    oppdatereKartDel(i + 111, i + 1);
  }
  for (var i = 0; i < 9; i++) {
    oppdatereKartDel(i + 209, i + 6);
  }
}

//

function hover(etasje1, etasje2) {  //Hvis du peker på en av etasjene i kartet, utvides de
  var x, y = 0;
  x = document.getElementById(etasje1); //etasje1 er ikke første etasje, men etasjen du peker på
  y = document.getElementById(etasje2); //etasjen du ikke peker på hvis du peker på en etasje
  x.style.width = '38%';
  y.style.width = '20%';
}


function hoverNot() {   //Etasjene går tilbake til vanlig størelse hvis du ikke peker på dem
  var x, y = 0;
  x = document.getElementById('etasje1');
  y = document.getElementById('etasje2');
  x.style.width = '28%';
  x.style.transition = '0.3s';
  y.style.width = '30%';
  y.style.transition = '0.3s';
  y.style.height = '100%';
  x.style.height = '100%';
}

function myFunction(x) {  //Forkorter tabelloverskrifter hvis skjrmen blir for liten
  if (x.matches) { // If media query matches
    document.getElementById("romnummer").innerHTML = "Romnr.";
    document.getElementById("luftkvalitet").innerHTML = "Luftkval.";
  } else {
    document.getElementById("romnummer").innerHTML = "Romnummer";
    document.getElementById("luftkvalitet").innerHTML = "Luftkvalitet";
  }
}

var x = window.matchMedia("(max-width: 700px)");
myFunction(x); // Call listener function at run time
x.addListener(myFunction); // Attach listener function on state changes
statusFarge2();
luftStatus();

//easteregg2
