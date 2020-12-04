let mongoose = require('mongoose');
const AdminSchema = mongoose.Schema({
    username: {type: String},
    password: {type: String},
    name: {type: String},
    phone: {type: String},
    address: {type: String},
});
module.exports = AdminSchema;