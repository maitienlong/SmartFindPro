let mongoose = require('mongoose');
const AddressSchema = mongoose.Schema({
    provinceCity: {
        type: String,
        required: true
    },
    districtsTowns: {
        type: String,
        required: true
    },
    communeWardTown: {
        type: String,
        required: true
    },
    detailAddress: {
        type: String,
        required: true
    },
    location: {
        latitude: {
            type: String
        },
        longitude: {
            type: String
        }
    }
});
module.exports = AddressSchema;