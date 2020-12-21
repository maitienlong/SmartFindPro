class UserManage {
    constructor(allUsers, userLV0, userLV1, userLV2, userLV3) {
        this.allUsers = allUsers;
        this.userLV0 = userLV0;
        this.userLV1 = userLV1;
        this.userLV2 = userLV2;
        this.userLV3 = userLV3;
        this.lengthOfUserLV0 = userLV0.length;
        this.lengthOfUserLV1 = userLV1.length;
        this.lengthOfUserLV2 = userLV2.length;
        this.lengthOfUserLV3 = userLV3.length;
    }
}

module.exports = UserManage;