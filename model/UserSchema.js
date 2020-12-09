let mongoose = require('mongoose');
const UserSchema = mongoose.Schema({
    password: {
        type: String,
        required: true
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
    }
});
module.exports = UserSchema;