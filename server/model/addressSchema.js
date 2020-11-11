let mongoose = require('mongoose');
const addressSchema = mongoose.Schema({
    provinceCity: {type: String},
    districtsTowns: {type: String},
    communeWardTown: {type: String},
    detailAddress: {type: String}
});
module.exports = addressSchema;