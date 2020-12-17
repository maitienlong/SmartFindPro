let mongoose = require('mongoose');
const FavoriteSchema = mongoose.Schema({
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
    deleteAt: {
        type: String,
        required: false
    },
    createAt: {
        type: String,
        required: true
    },
    status: {
        type: String,
        required: false
    },
});
module.exports = FavoriteSchema;