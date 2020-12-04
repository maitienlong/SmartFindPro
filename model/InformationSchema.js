let mongoose = require('mongoose');
const InformationSchema = mongoose.Schema({
    amountPeople: {
        type: Number,
        required: true
    },
    price: {
        type: Number,
        required: true
    },
    deposit: {
        type: Number,
        required: true
    },
    gender: {
        type: String,
        required: true
    },
    unit: {
        type: String,
        required: true
    },
    electricBill: {
        type: Number,
        required: true
    },
    electricUnit: {
        type: String,
        required: true
    },
    waterBill: {
        type: Number,
        required: true
    },
    waterUnit: {
        type: String,
        required: true
    },
    describe: {
        type: String,
        required: true
    },
    image: {
        type: [String]
    }
});
module.exports = InformationSchema;