let mongoose = require('mongoose');

const postSchema = new mongoose.Schema({
    category: {type: String},
    information: {
        type: Object,
        required: false,
        ref: 'Information'
    },
    address: {
        type: Object,
        required: false,
        ref: 'Address'
    },
    utilities: {type: [String]},
    content: {type: String},
    userId: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'User'
    },
    status: {type: String},
    deleteAt: {
        type: String,
        default: Date
    },
    updateAt: {
        type: String,
        default: Date
    },
    createAt: {
        type: String,
        default: Date
    },
    linkProduct: {type: String}
});
module.exports = postSchema;