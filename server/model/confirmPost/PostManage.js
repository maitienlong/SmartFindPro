class PostManage {
    constructor(unapprovedPost, processingPost, successPost) {
        this.unapprovedPost = unapprovedPost;
        this.processingPost = processingPost;
        this.successPost = successPost;
        this.lengthOfUnapprovedPost = unapprovedPost.length;
        this.lengthOfProcessingPost = processingPost.length;
        this.lengthOfSuccessPost = successPost.length;
        this.lengthOfAllPost = unapprovedPost.length + processingPost.length + successPost.length;
    }
}
module.exports = PostManage;