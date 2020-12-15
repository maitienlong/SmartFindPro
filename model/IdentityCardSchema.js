let mongoose = require('mongoose');
const IdentityCardSchema = mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'User'
    },
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
    image: {
        previous: {
            type: String,
            required: true
        },
        behind: {
            type: String,
            required: true
        },
        hasFace: {
            type: String,
            required: true
        }
    },
    homeTown: {
        type: String,
        required: true
    },
    resident: {
        type: String,
        required: true
    },
    nationality: {
        type: String,
        required: true
    }
});
module.exports = IdentityCardSchema;