let mongoose = require('mongoose');
const adminSchema = mongoose.Schema({
    username: {type: String},
    password: {type: String},
    name: {type: String},
    phone: {type: String},
    address: {type: String},
});
module.exports = adminSchema;