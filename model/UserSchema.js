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
        required: true
    },
    address: {
        type: Object,
        required: true,
        ref: 'Address',
    }
});
module.exports = UserSchema;