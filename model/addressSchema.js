let mongoose = require('mongoose');
const addressSchema = mongoose.Schema({
    addressId: {
        type: mongoose.Types.ObjectId,
        required: false,
        ref: 'Address',
    },
    provinceCity: {type: String},
    districtsTowns: {type: String},
    communeWardTown: {type: String},
    detailAddress: {type: String},
    location: {
        latitude: {
            type: String
        },
        longitude: {
            type: String
        }
    }
});
module.exports = addressSchema;