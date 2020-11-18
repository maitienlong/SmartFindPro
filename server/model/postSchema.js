const informationSchema = require('../model/informationSchema')
const addressSchema = require('../model/addressSchema')
let mongoose = require('mongoose');

const postSchema = mongoose.Schema({
    category: {type: String},
    information: {type: informationSchema},
    address: {type: addressSchema},
    utilities: {type: [String]},
    content: {type: String},
    idUser: {type: String},
    status: {type: String},
    time: {type: Date},
    linkProduct: {type: String}
});
module.exports = postSchema;