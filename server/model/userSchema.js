let mongoose = require('mongoose');
const userSchema = mongoose.Schema({
    userName: {type: String},
    password: {type: String},
    phoneNumber: {type: String},
    identityCard: {
        type: Object,
        required: false,
        ref: 'IdentityCard',
    },
    address: {
        type: Object,
        required: false,
        ref: 'Address',
    }
});
module.exports = userSchema;