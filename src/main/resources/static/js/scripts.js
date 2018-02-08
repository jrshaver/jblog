//Toggles arrow up/down when dropping down "Advance Search Options" menu
$(document).on("click", ".options", function() {
    $(".options").toggleClass("dropup");
});

//sorts search
//function sortByTitle() {
    var titlesA = document.getElementsByClassName("post-title");
    let titles = [];
    for (title of titlesA) {
        titles.push(title.textContent);
    }
//    for (let i=0; i < titles.length-1; i++) {
//        if (titles[i].innerHTML.toLowerCase() > title[i+1].innerHTML.toLowerCase()) {
//            let temp = titles[i];
//            titles[i] = titles[i+1];
//            titles[i+1] = titles;
//        }
//    }
