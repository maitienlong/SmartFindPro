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
    user: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'User'
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