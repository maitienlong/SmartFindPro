let mongoose = require('mongoose');
const UserSchema = mongoose.Schema({
    userName: {
        type: String,
        required: false
    },
    password: {
        type: String,
        required: true
    },
    phoneNumber: {
        type: String,
        required: true
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
    address: {
        type: Object,
        required: false,
        ref: 'Address',
    }
});
module.exports = UserSchema;