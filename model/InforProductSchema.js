let mongoose = require('mongoose');

const InforProductSchema = new mongoose.Schema({
    category: {
        type: String,
        required: true
    },
    information: {
        type: Object,
        required: true,
        ref: 'Information'
    },
    utilities: {
        type: [String],
        required: true
    }
});
module.exports = InforProductSchema;