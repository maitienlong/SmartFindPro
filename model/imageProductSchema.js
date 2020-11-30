let mongoose = require('mongoose');
const imageProductSchema = mongoose.Schema({
    id: {
        type: Date,
        default: Date.now
    },
    name: {type: String},
    path: {type: String}
});
module.exports = imageProductSchema;