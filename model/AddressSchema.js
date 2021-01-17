let mongoose = require('mongoose');
const AddressSchema = mongoose.Schema({
    provinceCity: {
        type: String,
        required: false
    },
    districtsTowns: {
        type: String,
        required: false
    },
    communeWardTown: {
        type: String,
        required: false
    },
    detailAddress: {
        type: String,
        required: false
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