let mongoose = require('mongoose');
const IdentityCardSchema = mongoose.Schema({
    type: {
        type: String,
        required: true
    },
    code: {
        type: String,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    date: {
        type: String,
        required: true
    },
    gender: {
        type: String,
        required: true
    },
    issuedBy: {
        type: String,
        required: true
    },
    expiryDate: {
        type: String,
        required: true
    },
    createAt: {
        type: String,
        required: true
    },
    image: {type: [String]},
    homeTown: {
        type: Object,
        required: true,
        ref: 'Address',
    },
    resident: {
        type: Object,
        required: true,
        ref: 'Address',
    }
});
module.exports = IdentityCardSchema;