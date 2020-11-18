let mongoose = require('mongoose');
const userSchema = mongoose.Schema({
    phone: {type: String},
    fullName: {type: String},
    password: {type: String},
    password2: {type: String}
});
module.exports = userSchema;