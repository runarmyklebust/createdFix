var bean = __.newBean('me.myklebust.createdfix.CreatedFixBean');

exports.setCreated = function (ids, created) {

    if (!created) {
        created = new Date();
    }

    if (!(created instanceof Date)) {
        throw "Parameter 'created' must be of type 'Date'";
    }

    var result = bean.setCreated(ids, created.toISOString());

    return __.toNativeObject(result);
};
