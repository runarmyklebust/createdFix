# createdFix


Updates the createdTime property for content

## Usage

With given date

     createdFix.setCreated(id, createTime);
  
With no date (uses time of update)

      createdFix.setCreated(id);

Array of ids 

     createdFix.setCreated(ids, createTime);


## Sample

  
    var createdFix = require('/lib/createdFix');
    var contentLib = require('/lib/xp/content');

    exports.get = function (req) {

       var result = contentLib.query({
           start: 0,
           count: 1000
       });

       var newCreated = new Date("2048-08-01T10:10:10Z");

       result.hits.forEach(function (hit) {
           createdFix.setCreated(hit._id, newCreated);
       });

       return {
           contentType: 'application/json',
           body: {
               fixed: result.count
           }
       };
     };







