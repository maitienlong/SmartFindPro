let mongoose = require('mongoose');

const PostResponseSchema = new mongoose.Schema({
    category: {type: String},
    information: {
        type: Object,
        ref: 'Information',
        required: true
    },
    address: {
        type: Object,
        ref: 'Address',
        required: true
    },
    utilities: {
        type: [String],
        required: true
    },
    content: {
        type: String,
        required: true
    },
    user: {
        type: Object,
        required: false,
        ref: 'User'
    },
    status: {
        type: String,
        required: true
    },
    deleteAt: {
        type: String,
        default: Date,
        required: true
    },
    updateAt: {
        type: String,
        default: Date,
        required: true
    },
    createAt: {
        type: String,
        default: Date,
        required: true
    },
    linkProduct: {
        type: String,
        required: true
    }
});
module.exports = PostResponseSchema;