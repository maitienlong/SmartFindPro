let mongoose = require('mongoose');
const UserSchema = mongoose.Schema({
    userName: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    phoneNumber: {
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