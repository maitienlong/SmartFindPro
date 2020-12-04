let mongoose = require('mongoose');
const InformationSchema = mongoose.Schema({
    amountPeople: {type: Number},
    price: {type: Number},
    deposit: {type: Number},
    gender: {type: String},
    unit: {type: String},
    electricBill: {type: Number},
    electricUnit: {type: String},
    waterBill: {type: Number},
    waterUnit: {type: String},
    describe: {type: String},
    image: {
        type: [String]
    }
});
module.exports = InformationSchema;