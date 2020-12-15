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
        default: Date,
        required: true
    }
});
module.exports = UpgradeUserSchema;