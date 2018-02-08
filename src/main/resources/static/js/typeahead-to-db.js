$(document).ready(function() {
    var tags = new Bloodhound({
     datumTokenizer: Bloodhound.tokenizers.whitespace,
     queryTokenizer: Bloodhound.tokenizers.whitespace,

     remote: { url:'./tags' }
     });

     $('.typeahead').typeahead(null, {
             name: 'tags',
             source: tags,
             limit: 5 /* Specify max number of suggestions to be displayed */
         });
})