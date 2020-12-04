let mongoose = require('mongoose');
const UserSchema = mongoose.Schema({
    userName: {type: String},
    password: {type: String},
    phoneNumber: {type: String},
    address: {
        type: Object,
        required: false,
        ref: 'Address',
    }
});
module.exports = UserSchema;