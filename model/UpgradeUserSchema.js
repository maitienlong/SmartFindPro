let mongoose = require('mongoose');
const UpgradeUserSchema = mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'User'
    },
    identityCard: {
        type: mongoose.Types.ObjectId,
        required: true,
        ref: 'IdentityCard'
    },
    createAt: {
        type: String,
        required: true
    },
    updateAt: {
        type: String,
        required: false
    },
    deleteAt: {
        type: String,
        required: false
    }
});
module.exports = UpgradeUserSchema;