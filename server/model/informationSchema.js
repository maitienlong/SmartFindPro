let mongoose = require('mongoose');
const informationSchema = mongoose.Schema({
    amountPeople: {type: Number},
    price: {type: Number},
    gender: {type: String},
    unit: {type: String},
    electricBill: {type: Number},
    electricUnit: {type: String},
    waterBill: {type: Number},
    waterUnit: {type: String},
    describe: {type: String},
    image: {type: [String]}
});
module.exports = informationSchema;