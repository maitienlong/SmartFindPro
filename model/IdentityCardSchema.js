let mongoose = require('mongoose');
const IdentityCardSchema = mongoose.Schema({
    type: {type: String},
    code: {type: String},
    name: {type: String},
    date: {type: String},
    gender: {type: String},
    issuedBy: {type: String},
    expiryDate: {type: String},
    createAt: {type: String},
    image: {type: [String]},
    homeTown: {
        type: Object,
        required: false,
        ref: 'Address',
    },
    resident: {
        type: Object,
        required: false,
        ref: 'Address',
    }
});
module.exports = IdentityCardSchema;