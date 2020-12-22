let mongoose = require('mongoose');

const PostSchema = new mongoose.Schema({
    product: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'InforProduct'
    },
    address: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'Address'
    },
    user: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    content: {
        type: String,
        required: true
    },
    total_people_lease: {
        type: Number,
        required: false
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
module.exports = PostSchema;