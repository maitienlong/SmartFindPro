let mongoose = require('mongoose');
const LoginSchema = mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    devices: {
        type: [String],
        required: true
    }
});
module.exports = LoginSchema;