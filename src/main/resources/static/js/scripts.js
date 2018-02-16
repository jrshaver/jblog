//Toggles arrow up/down when dropping down "Advance Search Options" menu
$(document).on("click", ".options", function() {
    $(".options").toggleClass("dropup");
});

//sorts search
function sortBy(header, order='asc') {
    //sorting function
    function compare(a, b) {
        const x = a[header].toLowerCase();
        const y = b[header].toLowerCase();

        let comparison = 0;
        if (x > y) {
            comparison = 1;
        } else if (x < y) {
            comparison = -1;
        }
        return order == 'desc' ? (comparison * -1) : comparison;
    }

    //Create and fill a list of TR's / search results
    let posts = [];
    for (let post of document.getElementsByClassName("result")) {
        post = {
            title: post.getElementsByClassName("post-title")[0].innerHTML,
            author: post.getElementsByClassName("post-user")[0].innerHTML,
            date: post.getElementsByClassName("post-date")[0].innerHTML
        };
        posts.push(post);
    }

    //sort and replace table data
    posts.sort(compare);
    for (let i = 0; i < posts.length; i++) {

        document.getElementsByClassName("post-title")[i].innerHTML = posts[i].title;;
        document.getElementsByClassName("post-user")[i].innerHTML = posts[i].author;
        document.getElementsByClassName("post-date")[i].innerHTML = posts[i].date;
    }
}