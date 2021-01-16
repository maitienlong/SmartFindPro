let mongoose = require('mongoose');

const ConfirmPostSchema = new mongoose.Schema({
    product: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'Product'
    },
    admin: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'Admin'
    },
    user_w: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'User'
    },
    user: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'User'
    },
    status_code: {
        type: String,
        required: true,
    },
    status: {
        type: String,
        required: true,
    },
    createAt: {
        required: true,
        type: String,
        default: Date
    }
});
module.exports = ConfirmPostSchema;