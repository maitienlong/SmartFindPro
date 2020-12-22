let mongoose = require('mongoose');
const UserSchema = mongoose.Schema({
    level: {
        type: Number,
        required: true
    },
    password: {
        type: String,
        required: false
    },
    address: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'Address'
    },
    avatar: {
        type: String,
        required: false
    },
    coverImage: {
        type: String,
        required: false
    },
    gender: {
        type: String,
        required: false
    },
    birth: {
        type: String,
        required: false
    },
    full_name: {
        type: String,
        required: true
    },
    phone_number: {
        type: String,
        required: true
    },
    deleteAt: {
        type: String,
        default: Date,
        required: false
    },
    updateAt: {
        type: String,
        default: Date,
        required: false
    },
    createAt: {
        type: String,
        default: Date,
        required: false
    },
    status: {
        type: Boolean,
        required: false
    },
});
module.exports = UserSchema;