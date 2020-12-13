//import thu vien
const express = require('express');
const hbs = require('express-handlebars');
const db = require('mongoose');
const multer = require('multer');
const body = require('body-parser');
const fs = require('fs');
const moment = require('moment');
const crypto = require('crypto');
const request = require('request');
const Handlebars = require('handlebars');
const buffer = require('buffer').Buffer;

//anh xa model

const PostManage = require('./model/confirmPost/PostManage');
const confirmPostSchema = require('./model/ConfirmPostSchema');
const ConfirmPost = db.model('ConfirmPost', confirmPostSchema, 'confirmPost');

const postSchema = require('./model/PostSchema');
const Product = db.model('Product', postSchema, 'products');

const userSchema = require('./model/UserSchema');
const User = db.model('User', userSchema, 'users');

const adminSchema = require('./model/AdminSchema');
const Admin = db.model('Admin', adminSchema, 'admin');

const addressSchema = require('./model/AddressSchema');
const Address = db.model('Address', addressSchema, 'address');

const inforProductSchema = require('./model/InforProductSchema');
const InforProduct = db.model('InforProduct', inforProductSchema, 'product');

const informationSchema = require('./model/InformationSchema');
const Information = db.model('Information', informationSchema);
const identityCardSchema = require('./model/IdentityCardSchema');
const IdentityCard = db.model('IdentityCard', identityCardSchema);
const postResponseSchema = require('./model/PostResponseSchema');
const PostResponseSchema = db.model('PostResponseSchema', postResponseSchema);

//conect mongodb
db.connect('mongodb+srv://Nhom5qlda14351:quanlyduan123@cluster0-z9led.mongodb.net/TimtroDatabase?retryWrites=true&w=majority', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false
}).then(function () {
    console.log('Mongoose is connected');
});

//khai bao can thiet
const app = express();
const path = require('path');
const {json} = require('body-parser');

app.use('/public', express.static(path.join(__dirname, 'public')));
app.use(body.json());
app.use(body.urlencoded({extended: true}));
app.engine('.hbs', hbs({
    extname: 'hbs',
    defaultLayout: '',
    layoutsDir: ''
}));
app.set('view engine', '.hbs');

//chạy lên local host với port là localNumber
const localNumber = 9090;
app.listen(localNumber);
console.log('Localhost: ' + localNumber);

//import switch
Handlebars.registerHelper('switch', function (value, options) {
    this.switch_value = value;
    return options.fn(this);
});
Handlebars.registerHelper('case', function (value, options) {
    if (value == this.switch_value) {
        return options.fn(this);
    }
});

//new khoi tao noi luu tru
storage = multer.diskStorage({
    destination: './public/uploads/',
    filename: function (req, file, cb) {
        return crypto.pseudoRandomBytes(16, function (err, raw) {
            if (err) {
                return cb(err);
            }
            return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
        });
    }
});

//cac ham tro giup
function getResponse(name, resCode, resMess, res) {
    let resData = {
        response_header: {
            res_name: name,
            res_code: resCode,
            res_message: resMess
        },
        response_body: res
    }
    console.log('[' + name + '] \n' + 'res_code: ' + resCode + '\nres_mess: ' + resMess + '\n-------->')
    return resData
}

function phonenumber(inputtxt) {
    var phoneno = /^\d{10}$/;
    if (inputtxt.match(phoneno)) {
        return true;
    } else {
        return false;
    }
}

function checkData(data) {
    console.log(JSON.stringify(data))
    if (data == undefined) {
        return false
    }
    if (data == null) {
        return false
    }
    if (data == []) {
        return false
    }
    if (data == '') {
        return false
    }
    return true
}

async function getProducts(successPost) {
    let products = [];

    for (let i = 0; i < successPost.length; i++) {
        let index = successPost[i]
        let inforProduct = await InforProduct.find({_id: index.product}).lean();
        let address = await Address.find({_id: index.address}).lean();
        let user = await User.find({_id: index.userId}).lean();

        if (checkData(inforProduct) && checkData(address) && checkData(user)) {
            inforProduct = inforProduct[0]
            address = address[0]
            user = user[0]
            let prd = new PostResponseSchema({
                _id: index._id,
                category: inforProduct.category,
                information: inforProduct.information,
                address: address,
                utilities: inforProduct.utilities,
                content: index.content,
                user: user,
                status: index.status,
                createAt: index.createAt,
                updateAt: index.updateAt,
                deleteAt: index.deleteAt,
                linkProduct: index.linkProduct
            })
            if (prd) {
                console.log('id: ' + prd._id)
                products.push(prd)
            }
        }
    }
    if (products.length <= 0) {
        product = null
    }
    console.log(products.length)
    return products
}

//API
//post anh
app.post("/upload-photo", multer({storage: storage}).single('photo'), function (req, res) {
    let name = 'UPLOAD-PHOTO';
    try {
        var jsonResult = [];
        jsonResult.push(req.file.path)
        console.log(jsonResult)
        return res.status(200).json({
            addressImage: jsonResult
        })
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
app.post("/upload-photo-array", multer({storage: storage}).array('photo', 5), function (req, res) {
    let name = 'UPLOAD-PHOTO-ARRAY';
    try {
        var jsonResult = [];
        console.log(req.files)
        for (var i in req.files) {
            jsonResult.push(req.files[i].path)
        }
        let res_body = {addressImage: jsonResult}
        return res.status(200).json(getResponse(name, 200, sttOK, res_body))
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// App
//login
app.post('/login', async function (request, response) {
    let name = 'LOGIN'
    let res_body = {type: 'PHONE_NUMBER', user: null}
    try {
        let id = request.body.id;
        let account = request.body.account;
        let password = request.body.password;
        if (checkData(account) &&
            checkData(password)) {
            let userByPhone = await User.find({
                phone_number: account,
                password: password
            }).populate(['address']).lean();

            if (userByPhone.length > 0) {
                userByPhone = userByPhone[0];
                if (userByPhone) {
                    let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                    let confirm = await ConfirmPost({
                        product: id,
                        admin: null,
                        user: userByPhone,
                        status: 'LOGIN',
                        createAt: createAt
                    });
                    let confirPrd = await confirm.save();
                    res_body = {type: 'PHONE_NUMBER', user: null}
                    response.json(getResponse(name, 200, sttOK, res_body))
                } else {
                    response.json(getResponse(name, 200, 'Fail', res_body))
                }
            } else {
                response.json(getResponse(name, 404, 'Unknown user or password incorrect.', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// kiem tra so dien thoai
app.post('/check-phone-number', async function (request, response) {
    let name = 'CHECK-PHONE-NUMBER'
    try {
        let phoneNumber = request.body.phoneNumber;
        if (checkData(phoneNumber)) {
            let res_body = {status: null}
            let user = await User.find({phone_number: phoneNumber}).lean();
            if (user.length > 0) {
                res_body = {status: 'Fail'}
                response.json(getResponse(name, 200, 'Fail', res_body))
            } else {
                res_body = {status: sttOK}
                response.json(getResponse(name, 200, sttOK, res_body))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
//tim kiem nguoi dung
app.post('/find-user', async function (request, response) {
    let name = 'FIND-USER'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            let user = await User.find({_id: id}).populate(['address']).lean();
            if (user.length > 0) {
                user = user[0];
                let res_body = {user: null}
                if (user) {
                    res_body = {user: user}
                    response.json(getResponse(name, 200, sttOK, res_body))
                } else {
                    response.json(getResponse(name, 200, 'Fail', res_body))
                }
            } else {
                response.json(getResponse(name, 404, 'User not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// them bai nguoi dung
app.post('/init-user', async function (request, response) {
    let name = 'INIT-USER'
    try {
        let fullName = request.body.fullName;
        let password = request.body.password;
        let phoneNumber = request.body.phoneNumber;
        if (checkData(fullName) &&
            checkData(password) &&
            checkData(phoneNumber)) {
            let newProductAddress = new Address({
                provinceCity: "Hà Nội",
                districtsTowns: "Ba Đình",
                communeWardTown: "Điện Bàn",
                detailAddress: "2 Hùng Vương",
                location: {
                    latitude: "21.0228161",
                    longitude: "105.8019438"
                }
            });
            let address = await newProductAddress.save();
            //luu data vao cac bang phu
            let level = 0;
            let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
            let newUser = new User({
                level: level,
                password: password,
                address: address._id,
                avatar: '',
                coverImage: '',
                gender: '',
                birth: '',
                full_name: fullName,
                phone_number: phoneNumber,
                deleteAt: '',
                updateAt: '',
                createAt: createAt
            });
            let user = await newUser.save();
            let res_body = {status: null};
            if (address && user) {
                res_body = {status: sttOK}
                response.json(getResponse(name, 200, sttOK, res_body))
            } else {
                res_body = {status: 'Fail'}
                response.json(getResponse(name, 200, 'Fail', res_body))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// cap nhat nguoi dung
app.post('/update-user', async function (request, response) {
    let name = 'UPDATE-USER'
    try {
        let userId = request.body.userId;
        let password = request.body.password;
        let address = request.body.address;
        let avatar = request.body.avatar;
        let converImage = request.body.coverImage;
        let gender = request.body.gender;
        let birth = request.body.birth;
        let fullName = request.body.full_name;
        let phoneNumber = request.body.phone_number;
        if (checkData(userId)) {
            let user = await User.find({_id: userId}).lean();

            let res_body = {status: null};
            if (user.length > 0) {
                user = user[0];
                let address = await Address.findByIdAndUpdate(user.address._id, {
                    provinceCity: mAddress.provinceCity,
                    districtsTowns: mAddress.districtsTowns,
                    communeWardTown: mAddress.communeWardTown,
                    detailAddress: mAddress.detailAddress,
                    location: {
                        latitude: mAddress.location.latitude,
                        longitude: mAddress.location.longitude
                    }
                });
                // update data vao bang chinh
                let updateAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                let updateUser = await Product.findByIdAndUpdate(userId, {
                    password: checkData(password) ? password : user.password,
                    address: checkData(address) ? address : user.address,
                    avatar: checkData(avatar) ? avatar : user.avatar,
                    coverImage: checkData(coverImage) ? coverImage : user.coverImage,
                    gender: checkData(gender) ? gender : user.gender,
                    birth: checkData(birth) ? birth : user.birth,
                    full_name: checkData(fullName) ? fullName : user.full_name,
                    phone_number: checkData(phoneNumber) ? phoneNumber : user.phone_number,
                    deleteAt: user.deleteAt,
                    updateAt: updateAt,
                    createAt: createAt
                })
                if (address && updateUser) {
                    let confirm = await ConfirmPost({
                        product: null,
                        admin: null,
                        user: userId,
                        status: 'UPDATE_USER',
                        createAt: updateAt
                    });
                    console.log(JSON.stringify(confirm));
                    let confirPrd = await confirm.save();
                    res_body = {status: sttOK};
                    response.json(getResponse(name, 200, sttOK, res_body));
                } else {
                    res_body = {status: "Fail"};
                    response.json(getResponse(name, 200, 'Fail', res_body))
                }
            } else {
                response.json(getResponse(name, 404, 'User not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch
        (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});

//tim kiem bai dang
app.post('/find-product', async function (request, response) {
    let name = 'FIND-PRODUCT'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            let prd = await Product.find({
                deleteAt: ''
            }).populate(['address', 'product'])
                .populate({
                    path: 'user',
                    populate: {
                        path: 'address'
                    }
                })
                .lean();

            let res_body = {products: null};
            if (prd.length <= 0) {
                response.json(getResponse(name, 404, 'Product not found', res_body))
            } else {
                let product = prd[0];
                let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                let confirm = await ConfirmPost({
                    product: id,
                    admin: null,
                    user: product.user._id,
                    status: 'FIND_PRODUCT',
                    createAt: createAt
                });
                console.log(JSON.stringify(confirm));
                let confirPrd = await confirm.save();
                res_body = {products: product};
                response.json(getResponse(name, 200, sttOK, res_body))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// them bai dang
app.post('/init-product', async function (request, response) {
    let name = 'INIT-PRODUCT'
    try {
        let id = request.body.id;
        let utilities = request.body.utilities;
        let category = request.body.category;
        let information = request.body.information;
        let mAddress = request.body.address;
        let content = request.body.content;
        let linkProduct = 'chua co link';
        if (checkData(id) &&
            checkData(utilities) &&
            checkData(category) &&
            checkData(information) &&
            checkData(mAddress) &&
            checkData(content) &&
            checkData(linkProduct)) {
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                //luu data vao cac bang phu
                let newInforProduct = new InforProduct({
                    category: category,
                    information: information,
                    utilities: utilities
                });
                let newProductAddress = new Address({
                    provinceCity: mAddress.provinceCity,
                    districtsTowns: mAddress.districtsTowns,
                    communeWardTown: mAddress.communeWardTown,
                    detailAddress: mAddress.detailAddress,
                    location: {
                        latitude: mAddress.location.latitude,
                        longitude: mAddress.location.longitude
                    }
                });

                let product = await newInforProduct.save();
                console.log('product: ', product._id)
                let address = await newProductAddress.save();
                // luu data vao bang chinh
                let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                let status = '-1'
                let newProduct = new Product({
                    product: product._id,
                    address: address._id,
                    user: id,
                    content: content,
                    status: status,
                    createAt: createAt,
                    updateAt: "",
                    deleteAt: "",
                    linkProduct: ""
                });
                let initProduct = await newProduct.save();
                if (product && address && initProduct) {
                    let confirm = await ConfirmPost({
                        product: initProduct._id,
                        admin: null,
                        user: id,
                        status: 'INIT_PRODUCT',
                        createAt: createAt
                    });
                    console.log(JSON.stringify(confirm));
                    let confirPrd = await confirm.save();
                    let res_body = {status: sttOK};
                    response.json(getResponse(name, 200, sttOK, res_body));
                } else {
                    let res_body = {status: 'Fail'};
                    response.json(getResponse(name, 200, 'Fail', res_body));
                }
            } else {
                response.json(getResponse(name, 404, 'User not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// sua bai dang
app.post('/update-product', async function (request, response) {
    let name = 'UPDATE-PRODUCT'
    try {
        let userId = request.body.userId;
        let id = request.body.id;
        let utilities = request.body.utilities;
        let category = request.body.category;
        let information = request.body.information;
        let mAddress = request.body.address;
        let content = request.body.content;
        let linkProduct = 'chua co link';
        if (checkData(userId) &&
            checkData(id) &&
            checkData(utilities) &&
            checkData(category) &&
            checkData(information) &&
            checkData(mAddress) &&
            checkData(content) &&
            checkData(linkProduct)) {
            let oldProduct = await Product.find({_id: id}).lean();
            if (oldProduct.length > 0) {
                oldProduct = oldProduct[0];
                if (oldProduct.user._id == userId) {
                    let product = await InforProduct.findByIdAndUpdate(oldProduct.product._id, {
                        category: category,
                        information: information,
                        utilities: utilities
                    });
                    let address = await Address.findByIdAndUpdate(oldProduct.address._id, {
                        provinceCity: mAddress.provinceCity,
                        districtsTowns: mAddress.districtsTowns,
                        communeWardTown: mAddress.communeWardTown,
                        detailAddress: mAddress.detailAddress,
                        location: {
                            latitude: mAddress.location.latitude,
                            longitude: mAddress.location.longitude
                        }
                    });
                    // update data vao bang chinh
                    let updateAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                    let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                        product: product._id,
                        address: address._id,
                        user: oldProduct.user,
                        content: content,
                        status: oldProduct.status,
                        createAt: oldProduct.createAt,
                        updateAt: updateAt,
                        deleteAt: oldProduct.deleteAt,
                        linkProduct: oldProduct.linkProduct
                    })
                    if (updateProduct && product && address) {
                        let confirm = await ConfirmPost({
                            product: id,
                            admin: null,
                            user: userId,
                            status: 'UPDATE_PRODUCT',
                            createAt: updateAt
                        });
                        console.log(JSON.stringify(confirm));
                        let confirPrd = await confirm.save();
                        let res_body = {status: sttOK};
                        response.json(getResponse(name, 200, sttOK, res_body))
                    } else {
                        let res_body = {status: 'Fail'};
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    response.json(getResponse(name, 404, 'User not found', null))
                }
            } else {
                response.json(getResponse(name, 404, 'Product not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// xoa bai dang
app.post('/delete-product', async function (request, response) {
    let name = 'DELETE-PRODUCT'
    try {
        let id = request.body.id;
        let userId = request.body.userId;
        let adminId = request.body.adminId;
        if (checkData(id)) {
            let product = await Product.find({_id: id}).lean();
            if (product.length > 0) {
                product = product[0];
                if (checkData(userId)) {
                    if (product.user._id == userId) {
                        let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                        let deleteProduct = await Product.findByIdAndDelete(product._id);
                        let deleteInforProduct = await InforProduct.findByIdAndDelete(product.product._id);
                        let deleteAddress = await Address.findByIdAndDelete(product.address._id);

                        if (deleteProduct && deleteInforProduct && deleteAddress) {
                            response.json(getResponse(name, 200, sttOK, null))
                            let confirm = await ConfirmPost({
                                product: id,
                                admin: null,
                                user: userId,
                                status: 'DELETE_PRODUCT',
                                createAt: createAt
                            });
                            console.log(JSON.stringify(confirm));
                            let confirPrd = await confirm.save();
                            let res_body = {status: sttOK};
                            response.json(getResponse(name, 200, sttOK, res_body));
                        } else {
                            let res_body = {status: 'Fail'};
                            response.json(getResponse(name, 200, 'Fail', res_body));
                        }
                    } else {
                        response.json(getResponse(name, 404, 'Product not found', null))
                    }
                } else if (checkData(adminId)) {
                    let createAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                    let deleteProduct = await Product.findByIdAndDelete(product._id);
                    let deleteInforProduct = await InforProduct.findByIdAndDelete(product.product._id);
                    let deleteAddress = await Address.findByIdAndDelete(product.address._id);
                    if (deleteProduct && deleteInforProduct && deleteAddress) {
                        let confirm = await ConfirmPost({
                            product: id,
                            admin: adminId,
                            user: null,
                            status: 'DELETE_PRODUCT',
                            createAt: createAt
                        });
                        console.log(JSON.stringify(confirm));
                        let confirPrd = await confirm.save();
                        let res_body = {status: sttOK};
                        response.json(getResponse(name, 200, sttOK, res_body))
                    } else {
                        let res_body = {status: 'Fail'};
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    response.json(getResponse(name, 400, 'Bad request', null))
                    return
                }

            } else {
                response.json(getResponse(name, 404, 'Product not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});

//trả ve danh sách bài đăng cua nguoi dung
app.post('/user-product', async function (request, response) {
    let name = 'USER-PRODUCT'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            let userNo = await User.find({_id: id}).lean();
            if (userNo.length > 0) {
                let allProduct = await Product.find({
                    deleteAt: '', user: id
                }).populate(['address', 'product'])
                    .populate({
                        path: 'user',
                        populate: {
                            path: 'address'
                        }
                    })
                    .lean();
                let res_body = {products: null};
                if (allProduct) {
                    res_body = {products: allProduct};
                    response.json(getResponse(name, 200, sttOK, res_body));
                } else {
                    res_body = {products: null};
                    response.json(getResponse(name, 200, 'Fail', res_body));
                }
            } else {
                response.json(getResponse(name, 404, 'User not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
//trả ve danh sách bài đăng
app.post('/list-product', async function (request, response) {
    let name = 'LIST-PRODUCT'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                let allProduct = await Product.find({
                    status: '1',
                    deleteAt: ''
                }).populate(['address', 'product'])
                    .populate({
                        path: 'user',
                        populate: {
                            path: 'address'
                        }
                    })
                    .lean();
                if (allProduct) {
                    let res_body = {products: allProduct};
                    response.json(getResponse(name, 200, sttOK, res_body));
                } else {
                    let res_body = {products: null};
                    response.json(getResponse(name, 200, sttOK, res_body));
                }
            } else {
                response.json(getResponse(name, 404, 'User not found', null));
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null));
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});

//Web
//Khai bao bien web
const sttOK = 'Success'
let nameDN = '', allAdmin = '';
//start
app.get('/', function (request, response) {
    response.render('login', {status: 'none', user: '', pass: ''});
});
// kiểm tra đăng nhập nếu đúng hiện trang trủ index
app.get('/index', async function (request, response) {

    let listAdmin = await Admin.find({}).lean();   //dk

    allAdmin = listAdmin.length;

    let user = request.query.user;
    let pass = request.query.pass;
    let sm = request.query.sm;

    if (sm == 1) {
        nameDN = user;
        console.log(user + " " + sm);
    }

    let admins = await Admin.find({username: user, password: pass}).lean();   //dk
    console.log(admins);
    if (admins.length <= 0 && sm == 1) {
        response.render('login', {
            status: 'block',
            data: 'Không thể đăng nhập, kiểm tra lại tài khoản và mật khẩu của bạn.',
            user: '',
            pass: ''
        });
    } else {
        var allProduct = await Product.find({
            deleteAt: ''
        }).populate(['address', 'product'])
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .lean();
        var unapprovedPost = await Product.find({
            status: '-1',
            deleteAt: ''
        }).populate(['address', 'product'])
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .lean();
        var processingPost = await Product.find({
            status: '0',
            deleteAt: ''
        }).populate(['address', 'product'])
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .lean();
        var successPost = await Product.find({
            status: '1',
            deleteAt: ''
        }).populate(['address', 'product'])
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .lean();
        let dataProduct = new PostManage(allProduct.reverse(), unapprovedPost.reverse(), processingPost.reverse(), successPost.reverse())
        let dataUser = await User.find({}).populate(['address']).lean();
        let dataAdmin = await Admin.find({}).lean();
        dataUser = dataUser.length;
        dataAdmin = dataAdmin.length;
        response.render('index', {
            status: 'none',
            user: nameDN,
            pass: pass,
            dataProduct: dataProduct,
            dataUser: dataUser,
            dataAdmin: dataAdmin
        });
    }
});

// quản lý admin, tạo admin
app.get('/createAdAc', async function (request, response) {
    let a = await Admin.find({}).lean();   //dk
    let search = request.query.search;
    let nameSP = request.query.nameSP;
    if (search == 1 && nameSP) {
        let seachAdmin = await Admin.find({username: nameSP}).lean();
        response.render('createAdAc', {
            status: 'none',
            data: seachAdmin,
        });
    } else {
        let sm = request.query.sm;
        let del = request.query.del;
        let edit = request.query.ud;
        if (sm == 1) {
            let nUser = request.query.nUser;
            let nPass = request.query.nPass;
            let nName = request.query.nName;
            let nPhone = request.query.nPhone;
            let nAddress = request.query.nAddress;

            let findAdmin = await Admin.find({username: nUser}).lean();   //dk

            if (findAdmin.length <= 0) {
                let newAdmin = new Admin({
                    username: nUser,
                    password: nPass,
                    name: nName,
                    phone: nPhone,
                    address: nAddress,
                });
                let status = await newAdmin.save();
                let admins = await Admin.find({}).lean();   //dk

                if (status) {
                    response.render('createAdAc', {
                        status: 'block',
                        textAlert: 'Tạo tài khoản thành công.',
                        data: admins,
                    });
                } else {
                    response.render('createAdAc', {
                        status: 'block',
                        textAlert: 'Tạo tài khoản thất bại.',
                        data: admins,
                    });
                }
            } else {
                response.render('createAdAc', {
                    status: 'block',
                    textAlert: 'Tài khoản đã tồn tại.Mời tạo tài khoản khác !',
                    data: a,
                });
            }
            //xóa nhân viên
        } else if (del == 1) {
            console.log('del ad ' + request.query.idAD);
            let status = await Admin.findByIdAndDelete(request.query.idAD);
            let admins = await Admin.find({}).lean();   //dk
            if (status) {
                response.render('createAdAc', {
                    status: 'block',
                    textAlert: 'Xóa tài khoản thành công.',
                    data: admins,
                });
            } else {
                response.render('createAdAc', {
                    status: 'block',
                    textAlert: 'Xóa tài khoản thất bại.',
                    data: admins,
                });
            }
        } else if (edit == 1) {
            let nUser = request.query.nUser;
            let nPass = request.query.nPass;
            let nName = request.query.nName;
            let nPhone = request.query.nPhone;
            let nAddress = request.query.nAddress;

            console.log('edit ad ' + request.query.nId);

            let admins = await Admin.find({username: nUser, password: nPass}).lean();   //dk

            if (admins.length >= 0) {
                // console.log(nId + "edit ad");
                let status = await Admin.findByIdAndUpdate(request.query.nId, {
                    username: nUser,
                    password: nPass,
                    name: nName,
                    phone: nPhone,
                    address: nAddress,
                });
                let nAdmins = await Admin.find({}).lean();
                if (status) {
                    response.render('createAdAc', {
                        status: 'block',
                        textAlert: 'Cập nhật tài khoản thành công.',
                        data: nAdmins,
                    });
                } else {
                    response.render('createAdAc', {
                        status: 'block',
                        textAlert: 'Cập nhật tài khoản thất bại.',
                        data: nAdmins,
                    });
                }
            } else {
                let nAdmins = await Admin.find({}).lean();
                response.render('createAdAc', {
                    status: 'block',
                    textAlert: 'Cập nhật tài khoản thất bại. Tài khoản đã tồn tại.',
                    data: nAdmins,
                });
            }

        } else {
            del = 0;
            edit = 0;
            response.render('createAdAc', {
                status: 'none',
                data: a,
            });
        }
    }
});
// cập nhật admin
app.get('/updateAdAc', async function (request, response) {
    let userAD = request.query.userAD;
    let passAD = request.query.passAD;
    let idAD = request.query.idAD;
    let nName = request.query.nameAD;
    let nPhone = request.query.phoneAD;
    let nAddress = request.query.addressAD;
    // let admins = await Admin.find({username: userAD, password: passAD}).lean();   //dk
    response.render('updateAdAc', {
        status: 'none',
        user: userAD,
        pass: passAD,
        id: idAD,
        // name:admins.name,
        // phone:admins.phone,
        // address:admins.address,
        name: nName,
        phone: nPhone,
        address: nAddress,
    });

});

// quản lý bài viết
app.get('/postManage', async function (request, response) {
    //  let obj = await getArea('P', '');
    //console.log('object: ' + obj);
    var allProduct = await Product.find({
        deleteAt: ''
    }).populate(['address', 'product'])
        .populate({
            path: 'user',
            populate: {
                path: 'address'
            }
        })
        .lean();
    var unapprovedPost = await Product.find({
        status: '-1',
        deleteAt: ''
    }).populate(['address', 'product'])
        .populate({
            path: 'user',
            populate: {
                path: 'address'
            }
        })
        .lean();
    var processingPost = await Product.find({
        status: '0',
        deleteAt: ''
    }).populate(['address', 'product'])
        .populate({
            path: 'user',
            populate: {
                path: 'address'
            }
        })
        .lean();
    var successPost = await Product.find({
        status: '1',
        deleteAt: ''
    }).populate(['address', 'product'])
        .populate({
            path: 'user',
            populate: {
                path: 'address'
            }
        })
        .lean();
    let data = new PostManage(allProduct.reverse(), unapprovedPost.reverse(), processingPost.reverse(), successPost.reverse())
    response.render('postManage', {
        data: data
    });
});
//duyệt bài viết
app.get('/confirmPost', async function (request, response) {
    let _id = request.query.idProduct;
    try {
        let product = await Product.find({_id: _id}).populate(['address', 'product'])
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .lean();
        //them cho moi image 1 truong id
        let listImages = product[0].product.information.image;
        let countListImages = listImages.length;
        let listObjectImages = [];
        for (let i = 0; i < countListImages; i++) {
            listObjectImages.push({
                id: i,
                path: listImages[i]
            })
        }
        //them cho moi tienich 1 truong id
        let listUtilities = product[0].product.utilities;
        let countListUtilities = listUtilities.length;
        let listObjectUtilities = [];
        for (let i = 0; i < countListUtilities; i++) {
            listObjectUtilities.push({
                id: i,
                utilities: listUtilities[i]
            })
        }
        let dataUtilities = [];
        let countUtilities = 6
        let utilities = ['Wifi', 'An ninh', 'Giờ giấc', 'Nhà ăn', 'Nhà vệ sinh', 'Phòng riêng', 'Giường', 'Để xe', 'Thú cưng', 'Trẻ em']
        for (let i = 0; i < countUtilities; i++) {
            dataUtilities.push({
                id: i,
                utilities: utilities[i]
            })
        }
        var now = new Date();
        var day = ("0" + now.getDate()).slice(-2);
        var month = ("0" + (now.getMonth() + 1)).slice(-2);

        var today = now.getFullYear() + "-" + (month) + "-" + (day);
        let info = {
            admin: nameDN,
            date: today
        }
        console.log(JSON.stringify(product[0].address));
        response.render('confirmPost', {
            info: info,
            product: product[0],
            utilities: dataUtilities,
            images: listObjectImages
        });
    } catch (e) {
        console.log('Lỗi: ' + e)
        response.render('login', {status: 'none', user: '', pass: ''});
    }

});

//quản lý người dùng

//API-WEB
// duyet bai dang
app.post('/confirm-product', async function (request, response) {
    let name = 'CONFIRM-PRODUCT'
    try {
        let adminId = request.body.adminId;
        let id = request.body.id;
        let utilities = request.body.utilities;
        let category = request.body.category;
        let information = request.body.information;
        if (checkData(adminId) &&
            checkData(id) &&
            checkData(utilities) &&
            checkData(category) &&
            checkData(information)) {
            let admin = await Admin.find({_id: adminId}).lean();
            if (admin.length > 0) {
                let oldProduct = await Product.find({_id: id}).lean();
                if (oldProduct.length > 0) {
                    oldProduct = oldProduct[0];
                    let product = await InforProduct.findByIdAndUpdate(oldProduct.product._id, {
                        category: category,
                        information: information,
                        utilities: utilities
                    });
                    // update data vao bang chinh
                    let updateAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                    let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                        status: '1',
                        updateAt: updateAt
                    });
                    if (updateProduct && product) {
                        let confirm = await ConfirmPost({
                            product: id,
                            admin: adminId,
                            user: null,
                            status: 'CONFIRM',
                            createAt: updateAt
                        })
                        let confirPrd = await confirm.save();

                        let res_body = {status: sttOK}
                        response.json(getResponse(name, 200, sttOK, res_body))
                    } else {
                        let res_body = {status: 'Fail'}
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    response.json(getResponse(name, 404, 'Product not found', null))
                }
            } else {
                response.json(getResponse(name, 404, 'Admin not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
// huy duyet bai dang
app.post('/cancel-product', async function (request, response) {
    let name = 'CANCEL-PRODUCT'
    try {
        let adminId = request.body.adminId;
        let id = request.body.id;
        let status = request.body.status;
        if (checkData(adminId) &&
            checkData(id) &&
            checkData(status)) {
            let admin = await Admin.find({_id: adminId}).lean();
            if (admin.length > 0) {
                let oldProduct = await Product.find({_id: id}).lean();
                if (oldProduct.length > 0) {
                    oldProduct = oldProduct[0];
                    // update data vao bang chinh
                    let updateAt = moment(Date.now()).format('YYYY-MM-DD hh:mm:ss');
                    let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                        status: status,
                        updateAt: updateAt
                    })
                    if (updateProduct) {
                        let confirm = await ConfirmPost({
                            product: id,
                            admin: adminId,
                            user: null,
                            status: 'CANCEL',
                            createAt: updateAt
                        })
                        let confirPrd = await confirm.save();
                        let res_body = {status: sttOK}
                        response.json(getResponse(name, 200, sttOK, res_body))
                    } else {
                        let res_body = {status: 'Fail'}
                        response.json(getResponse(name, 200, 'Fail', res_body))

                    }
                } else {
                    response.json(getResponse(name, 404, 'Product not found', null))
                }
            } else {
                response.json(getResponse(name, 404, 'Admin not found', null))
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});