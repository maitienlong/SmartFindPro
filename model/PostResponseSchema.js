let mongoose = require('mongoose');

const PostResponseSchema = new mongoose.Schema({
    category: {type: String},
    information: {
        type: Object,
        ref: 'Information'
    },
    address: {
        type: Object,
        ref: 'Address'
    },
    utilities: {type: [String]},
    content: {type: String},
    user: {
        type: Object,
        required: false,
        ref: 'User'
    },
    status: {type: String},
    deleteAt: {
        type: String,
        default: Date
    },
    updateAt: {
        type: String,
        default: Date
    },
    createAt: {
        type: String,
        default: Date
    },
    linkProduct: {type: String}
});
module.exports = PostResponseSchema;