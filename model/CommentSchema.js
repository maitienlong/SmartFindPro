let mongoose = require('mongoose');
const CommentSchema = mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    product: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'Product'
    },
    title: {
        type: String,
        required: true
    },
    reply: {
        type: [mongoose.Types.ObjectId],
        required: false,
        ref: 'Comment'
    },
    oldComment: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'Comment'
    },
    favorites: {
        type: [mongoose.Types.ObjectId],
        required: false,
        ref: 'Favorite'
    },
    status: {
        type: String,
        required: false
    },
    deleteAt: {
        type: String,
        required: false
    },
    updateAt: {
        type: String,
        required: false
    },
    createAt: {
        type: String,
        required: true
    }
});
module.exports = CommentSchema;