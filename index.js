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
const commentSchema = require('./model/CommentSchema');
const Comment = db.model('Comment', commentSchema, 'comment');
const favoriteSchema = require('./model/FavoriteSchema');
const Favorite = db.model('Favorite', favoriteSchema, 'favorite');

const UserManage = require('./model/modelWeb/UserManage');
const PostManage = require('./model/modelWeb/PostManage');

const confirmPostSchema = require('./model/ConfirmPostSchema');
const ConfirmPost = db.model('ConfirmPost', confirmPostSchema, 'confirmPost');

const postSchema = require('./model/PostSchema');
const Product = db.model('Product', postSchema, 'products');

const userSchema = require('./model/UserSchema');
const User = db.model('User', userSchema, 'users');

const identityCardSchema = require('./model/IdentityCardSchema');
const IdentityCard = db.model('IdentityCard', identityCardSchema, 'identityCard');

const upgradeUserSchema = require('./model/UpgradeUserSchema');
const UpgradeUser = db.model('UpgradeUser', upgradeUserSchema, 'upgradeUser');

const adminSchema = require('./model/AdminSchema');
const Admin = db.model('Admin', adminSchema, 'admin');

const addressSchema = require('./model/AddressSchema');
const Address = db.model('Address', addressSchema, 'address');

const inforProductSchema = require('./model/InforProductSchema');
const InforProduct = db.model('InforProduct', inforProductSchema, 'product');

const informationSchema = require('./model/InformationSchema');
const Information = db.model('Information', informationSchema);

const postResponseSchema = require('./model/PostResponseSchema');
const PostResponseSchema = db.model('PostResponseSchema', postResponseSchema);

const formatDate = 'YYYY-MM-DD HH:mm:ss'
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
                    let createAt = moment(Date.now()).format(formatDate);
                    let confirm = await ConfirmPost({
                        product: id,
                        admin: null,
                        user: userByPhone,
                        status: 'LOGIN-APP',
                        createAt: createAt
                    });
                    let confirPrd = await confirm.save();
                    res_body = {type: 'PHONE_NUMBER', user: userByPhone}
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
            let createAt = moment(Date.now()).format(formatDate);
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
                createAt: createAt,
                status: true
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
// cap nhat mat khau nguoi dung
app.post('/update-user-password', async function (request, response) {
    let name = 'UPDATE-USER-PASSWORD'
    try {
        let userId = request.body.userId;
        let password = request.body.password;
        if (checkData(userId) && checkData(password)) {
            let user = await User.find({_id: userId}).populate(['address']).lean();
            console.log(JSON.stringify(user))
            let res_body = {status: null};
            if (user.length > 0) {
                user = user[0];
                if (user.status == true) {
                    let updateAt = moment(Date.now()).format(formatDate);
                    let updateUser = await User.findByIdAndUpdate(userId, {
                        password: password,
                        updateAt: updateAt,
                    })
                    if (updateUser) {
                        let confirm = await ConfirmPost({
                            product: null,
                            admin: null,
                            user: userId,
                            status: name,
                            createAt: updateAt
                        });
                        let confirPrd = await confirm.save();
                        res_body = {status: sttOK};
                        response.json(getResponse(name, 200, sttOK, res_body));
                    } else {
                        res_body = {status: "Fail"};
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    res_body = {status: "The account has been locked"};
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
// cap nhat nguoi dung
app.post('/update-user', async function (request, response) {
    let name = 'UPDATE-USER'
    try {
        let userId = request.body.userId;
        let mAddress = request.body.address;
        let avatar = request.body.avatar;
        let converImage = request.body.coverImage;
        let gender = request.body.gender;
        let birth = request.body.birth;
        let fullName = request.body.full_name;
        let phoneNumber = request.body.phone_number;
        if (checkData(userId)) {
            let user = await User.find({_id: userId}).populate(['address']).lean();
            console.log(JSON.stringify(user))
            let res_body = {status: null};
            if (user.length > 0) {
                user = user[0];
                if (user.status == true) {
                    console.log("USER_ADDRESS_ID: " + user.address._id);
                    let address = '';
                    if (checkData(mAddress)) {
                        address = await Address.findByIdAndUpdate(user.address._id, {
                            provinceCity: checkData(mAddress.provinceCity) ? mAddress.provinceCity : user.address.provinceCity,
                            districtsTowns: checkData(mAddress.districtsTowns) ? mAddress.districtsTowns : user.address.districtsTowns,
                            communeWardTown: checkData(mAddress.communeWardTown) ? mAddress.communeWardTown : user.address.communeWardTown,
                            detailAddress: checkData(mAddress.detailAddress) ? mAddress.detailAddress : user.address.detailAddress,
                            location: {
                                latitude: checkData(mAddress.location.latitude) ? mAddress.location.latitude : user.address.location.latitude,
                                longitude: checkData(mAddress.location.longitude) ? mAddress.location.longitude : user.address.location.longitude
                            }
                        });
                    }
                    // update data vao bang chinh
                    let updateAt = moment(Date.now()).format(formatDate);
                    let lvUp = 0;
                    if (user.level === 0 &&
                        checkData(address) &&
                        checkData(birth) &&
                        checkData(fullName) &&
                        checkData(gender)) {
                        lvUp = 1;
                    } else {
                        lvUp = user.level
                    }
                    let updateUser = await User.findByIdAndUpdate(userId, {
                        level: lvUp,
                        password: user.password,
                        address: checkData(address) ? address._id : user.address._id,
                        avatar: checkData(avatar) ? avatar : user.avatar,
                        coverImage: checkData(converImage) ? converImage : user.converImage,
                        gender: checkData(gender) ? gender : user.gender,
                        birth: checkData(birth) ? birth : user.birth,
                        full_name: checkData(fullName) ? fullName : user.full_name,
                        phone_number: checkData(phoneNumber) ? phoneNumber : user.phone_number,
                        deleteAt: user.deleteAt,
                        updateAt: updateAt,
                        createAt: user.createAt,
                        status: user.status
                    })
                    if (updateUser) {
                        let confirm = await ConfirmPost({
                            product: null,
                            admin: null,
                            user: userId,
                            status: 'UPDATE_USER',
                            createAt: updateAt
                        });
                        console.log(JSON.stringify(confirm));
                        let confirPrd = await confirm.save();
                        if (lvUp == 1) {
                            res_body = {status: 'Successfully upgraded account level 1'};
                        } else {
                            res_body = {status: sttOK};
                        }

                        response.json(getResponse(name, 200, sttOK, res_body));
                    } else {
                        res_body = {status: "Fail"};
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    res_body = {status: "The account has been locked"};
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
// nang cap quyen
app.post('/upgrade-user', async function (request, response) {
    let name = 'UPGRADE-USER'
    try {
        let userId = request.body.userId;
        if (checkData(userId)) {
            let user = await User.find({_id: userId}).lean();
            let res_body = {status: null};
            if (user.length > 0) {
                user = user[0];
                let createAt = moment(Date.now()).format(formatDate);
                if (user.status == true) {
                    if (user.level == 1) {
                        let type = request.body.type;
                        let code = request.body.code;
                        let full_name = request.body.name;
                        let date = request.body.date;
                        let gender = request.body.gender;
                        let issuedBy = request.body.issuedBy;
                        let expiryDate = request.body.expiryDate;
                        let homeTown = request.body.homeTown;
                        let resident = request.body.resident;
                        let nationality = request.body.nationality;
                        let previous = request.body.previous;
                        let behind = request.body.behind;
                        let hasFace = request.body.hasFace;
                        if (checkData(type) &&
                            checkData(code) &&
                            checkData(full_name) &&
                            checkData(date) &&
                            checkData(gender) &&
                            checkData(issuedBy) &&
                            checkData(expiryDate) &&
                            checkData(homeTown) &&
                            checkData(resident) &&
                            checkData(nationality) &&
                            checkData(previous) &&
                            checkData(behind) &&
                            checkData(hasFace)) {

                            name = name + '-LEVEL-2'
                            let image = {
                                previous: previous,
                                behind: behind,
                                hasFace: hasFace
                            }
                            let identityCard = new IdentityCard({
                                type: type,
                                code: code,
                                name: full_name,
                                date: date,
                                gender: gender,
                                issuedBy: issuedBy,
                                expiryDate: expiryDate,
                                image: image,
                                homeTown: homeTown,
                                resident: resident,
                                nationality: nationality,
                                createAt: createAt
                            });

                            let findUpgradeUser = await UpgradeUser.find({code: code}).lean();
                            if (findUpgradeUser.length > 0) {
                                findUpgradeUser = findUpgradeUser[0];
                                let initIdentityCard = await identityCard.save();
                                let updateIdentityCard = await UpgradeUser.findByIdAndUpdate(findUpgradeUser._id, {
                                    type: type,
                                    code: code,
                                    name: full_name,
                                    date: date,
                                    gender: gender,
                                    issuedBy: issuedBy,
                                    expiryDate: expiryDate,
                                    image: image,
                                    homeTown: homeTown,
                                    resident: resident,
                                    nationality: nationality,
                                    createAt: findUpgradeUser.createAt,
                                    updateAt: createAt
                                });
                                if (initIdentityCard) {
                                    let confirm = await ConfirmPost({
                                        product: null,
                                        admin: null,
                                        user: userId,
                                        status: 'UPDATE-' + name,
                                        createAt: createAt
                                    });
                                    let confirPrd = await confirm.save();
                                    res_body = {status: sttOK};
                                    response.json(getResponse(name, 200, sttOK, res_body));
                                } else {
                                    res_body = {status: "Fail"};
                                    response.json(getResponse(name, 200, 'Fail', res_body))
                                }
                                return
                            } else {
                                let initIdentityCard = await identityCard.save();
                                if (initIdentityCard) {
                                    let updateUser = new UpgradeUser({
                                        user: userId,
                                        identityCard: initIdentityCard._id,
                                        createAt: createAt,
                                        updateAt: '',
                                        deleteAt: ''
                                    });
                                    let initUpdateUser = await updateUser.save();
                                    if (initUpdateUser) {
                                        let confirm = await ConfirmPost({
                                            product: null,
                                            admin: null,
                                            user: userId,
                                            status: name,
                                            createAt: createAt
                                        });
                                        let confirPrd = await confirm.save();
                                        res_body = {status: sttOK};
                                        response.json(getResponse(name, 200, sttOK, res_body));
                                    } else {
                                        res_body = {status: "Fail"};
                                        response.json(getResponse(name, 200, 'Fail', res_body))
                                    }
                                } else {
                                    res_body = {status: "Fail"};
                                    response.json(getResponse(name, 200, 'Fail', res_body))
                                }
                            }
                        } else {
                            response.json(getResponse(name, 400, 'Bad request', null))
                        }
                    } else {
                        response.json(getResponse(name, 403, 'User not permissions', null))
                    }
                } else {
                    res_body = {status: "The account has been locked"};
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
// xoa nguoi dung
app.post('/delete-user-not-use', async function (request, response) {
    let name = 'DELETE-USER'
    try {
        let id = request.body.id;
        let userId = request.body.userId;
        let adminId = request.body.adminId;
        if (checkData(id)) {
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                user = user[0];
                if (checkData(userId)) {
                    if (user.user._id == userId) {
                        let createAt = moment(Date.now()).format(formatDate);
                        let deleteProduct = await Product.findByIdAndDelete(user._id);
                        let deleteInforProduct = await InforProduct.findByIdAndDelete(user.product._id);
                        let deleteAddress = await Address.findByIdAndDelete(user.address._id);
                        if (deleteProduct && deleteInforProduct && deleteAddress) {
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
                    let createAt = moment(Date.now()).format(formatDate);
                    let deleteUser = await User.findByIdAndDelete(user._id);
                    let deleteAddress = await Address.findByIdAndDelete(user.address._id);
                    if (deleteUser && deleteAddress) {
                        let confirm = await ConfirmPost({
                            product: null,
                            admin: adminId,
                            user: id,
                            status: 'DELETE_USER',
                            createAt: createAt
                        });
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
// khoa nguoi dung
app.post('/disable-user', async function (request, response) {
    let name = 'DISABLE-USER'
    try {
        let id = request.body.id;
        let adminId = request.body.adminId;
        if (checkData(id)) {
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                user = user[0];
                if (checkData(adminId)) {
                    let createAt = moment(Date.now()).format(formatDate);
                    let uSTT = user.status;
                    if (user.status == true) {
                        uSTT = false;
                        name = 'DISABLE-USER'
                    } else {
                        uSTT = true;
                        name = 'ENABLE-USER'
                    }
                    let disableUser = await User.findByIdAndUpdate(user._id, {
                        status: uSTT,
                        updateAt: createAt
                    });
                    if (disableUser) {
                        let confirm = await ConfirmPost({
                            product: null,
                            admin: adminId,
                            user: id,
                            status: name,
                            createAt: createAt
                        });
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
                let createAt = moment(Date.now()).format(formatDate);
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
                user = user[0];
                if (user.status == true) {
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
                    let createAt = moment(Date.now()).format(formatDate);
                    let status = '-1'
                    let newProduct = new Product({
                        product: product._id,
                        address: address._id,
                        user: id,
                        content: content,
                        status: status,
                        createAt: createAt,
                        total_people_lease: 0,
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
                    res_body = {status: "The account has been locked"};
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
                if (user.status == true) {
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
                        let updateAt = moment(Date.now()).format(formatDate);
                        let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                            product: product._id,
                            address: address._id,
                            user: oldProduct.user,
                            content: content,
                            status: oldProduct.status,
                            createAt: oldProduct.createAt,
                            updateAt: updateAt,
                            deleteAt: oldProduct.deleteAt,
                            linkProduct: oldProduct.linkProduct,
                            total_people_lease: oldProduct.total_people_lease
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
                        res_body = {status: "The account has been locked"};
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
// cap nhat so luong nguoi thue bai dang
app.post('/total-people-lease-product', async function (request, response) {
    let name = 'UPDATE-TOTAL-PEOPLE-LEASE'
    try {
        let userId = request.body.userId;
        let id = request.body.id;
        let total_people_lease = request.body.total_people_lease;
        if (checkData(userId) &&
            checkData(id) &&
            checkData(total_people_lease) &&
            !isNaN(total_people_lease)) {
            let oldProduct = await Product.find({_id: id}).populate(['address', 'product'])
                .populate({
                    path: 'user',
                    populate: {
                        path: 'address'
                    }
                }).lean();
            if (oldProduct.length > 0) {
                oldProduct = oldProduct[0];
                if (oldProduct.user._id == userId) {
                    if (oldProduct.user.status == true) {
                        if (oldProduct.product.information.amountPeople < total_people_lease) {
                            let res_body = {status: 'The number of tenants is full'};
                            response.json(getResponse(name, 200, 'Fail', res_body))
                        } else {
                            // update data vao bang chinh
                            let updateAt = moment(Date.now()).format(formatDate);
                            let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                                total_people_lease: total_people_lease,
                                updateAt: updateAt
                            })
                            if (updateProduct) {
                                let confirm = await ConfirmPost({
                                    product: id,
                                    admin: null,
                                    user: userId,
                                    status: name,
                                    createAt: updateAt
                                });
                                let confirPrd = await confirm.save();
                                let res_body = {status: sttOK};
                                response.json(getResponse(name, 200, sttOK, res_body))
                            } else {
                                let res_body = {status: 'Fail'};
                                response.json(getResponse(name, 200, 'Fail', res_body))
                            }
                        }
                    } else {
                        res_body = {status: "The account has been locked"};
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
                        let createAt = moment(Date.now()).format(formatDate);
                        let deleteProduct = await Product.findByIdAndDelete(product._id);
                        let deleteInforProduct = await InforProduct.findByIdAndDelete(product.product._id);
                        let deleteAddress = await Address.findByIdAndDelete(product.address._id);

                        if (deleteProduct && deleteInforProduct && deleteAddress) {
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
                    let createAt = moment(Date.now()).format(formatDate);
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
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                user = user[0];
                if (user.status == true) {
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
                    let products = [];
                    if (allProduct) {
                        if (allProduct.length > 0) {
                            allProduct = allProduct.reverse();
                            console.log(allProduct.length)
                            for (let i = 0; i < allProduct.length; i++) {
                                if (allProduct[i].total_people_lease < allProduct[i].product.information.amountPeople) {
                                    products.push(allProduct[i]);
                                }
                            }
                        }
                        res_body = {products: products};
                        response.json(getResponse(name, 200, sttOK, res_body));
                    } else {
                        res_body = {products: null};
                        response.json(getResponse(name, 200, 'Fail', res_body));
                    }
                } else {
                    res_body = {status: "The account has been locked"};
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
//trả ve danh sách bài đăng
app.post('/list-product', async function (request, response) {
    let name = 'LIST-PRODUCT'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            let user = await User.find({_id: id}).lean();
            if (user.length > 0) {
                user = user[0];
                if (user.status == true) {
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
                    let res_body = {products: null};
                    let products = [];
                    if (allProduct) {
                        if (allProduct.length > 0) {
                            allProduct = allProduct.reverse();
                            console.log(allProduct.length)
                            for (let i = 0; i < allProduct.length; i++) {
                                if (allProduct[i].total_people_lease < allProduct[i].product.information.amountPeople) {
                                    products.push(allProduct[i]);
                                }
                            }
                        }
                        res_body = {products: products};
                        response.json(getResponse(name, 200, sttOK, res_body));
                    } else {
                        res_body = {products: null};
                        response.json(getResponse(name, 200, 'Fail', res_body));
                    }
                } else {
                    res_body = {status: "The account has been locked"};
                    response.json(getResponse(name, 200, 'Fail', res_body))
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
        let listAmoutPost = []
        listAmoutPost.length = 10;
        let listAmoutFavorite = []
        listAmoutFavorite.length = 10;
        let listUserPost = [];
        let dataLease = 0;
        let dataNotLease = 0;
        let dataFindSuccess = 0;
        try {
            var allProduct = await Product.find({
                deleteAt: ''
            }).lean();
            var unapprovedPost = await Product.find({
                status: '-1',
                deleteAt: ''
            }).lean();
            var processingPost = await Product.find({
                status: '0',
                deleteAt: ''
            }).lean();
            var successPost = await Product.find({
                status: '1',
                deleteAt: ''
            }).populate(['address', 'product'])
                .populate({
                    path: 'user',
                    populate: {
                        path: 'address'
                    }
                }).lean();
            let allUser = await User.find({
                deleteAt: ''
            }).lean();
            let uLv0 = await User.find({
                deleteAt: '',
                level: 0
            }).lean();
            let uLv1 = await User.find({
                deleteAt: '',
                level: 1
            }).lean();
            let uLv2 = await User.find({
                deleteAt: '',
                level: 2
            }).lean();
            let ulv3 = await User.find({
                deleteAt: '',
                level: 3
            }).lean();
// lay danh sach nguoi dung dang nhieu bai nhat
            for (let i = 0; i < allUser.length; i++) {
                let count = 0;
                for (let j = 0; j < successPost.length; j++) {
                    console.log(allUser[i]._id + ', ' + successPost[j].user._id)
                    if (allUser[i]._id.toString() == successPost[j].user._id.toString()) {
                        count++;
                    }
                }
                if (count > 0) {
                    let item = {
                        user: allUser[i]._id,
                        count: count
                    }
                    listAmoutPost.push(item);
                }
            }
            listAmoutPost = (listAmoutPost.sort(function (a, b) {
                return b.count - a.count;

            }));
            for (let i = 0; i < listAmoutPost.length; i++) {
                if (checkData(listAmoutPost[i])) {
                    let uItem = await User.find({
                        deleteAt: '',
                        _id: listAmoutPost[i].user
                    }).populate('address').lean();
                    if (uItem.length > 0) {
                        listUserPost.push({
                            id: (i + 1),
                            user: uItem[0],
                            count: listAmoutPost[i].count
                        });
                    }
                }
            }
            console.log(listAmoutPost)
// lay danh sach bai dang duoc yeu thich nhat
            for (let i = 0; i < successPost.length; i++) {
                let count = 0;
                let findFavorite = await Favorite
                    .find({
                        product: successPost[i]._id,
                        status: 'POST'
                    }).populate('user').lean();
                if (findFavorite.length > 0) {
                    count = findFavorite.length;
                }
                if (count > 0) {
                    let item = {
                        id: (i + 1),
                        product: successPost[i],
                        count: count
                    }
                    listAmoutFavorite.push(item);
                }
            }
            try {
                for (let i = 0; i < successPost.length; i++) {
                    dataFindSuccess += successPost[i].total_people_lease
                    if (successPost[i].total_people_lease > 0) {
                        dataLease++;
                    } else if (successPost[i].total_people_lease == 0) {
                        dataNotLease++;
                    }
                }

            } catch (e) {
                console.log('loi ne: ' + e)
            }
            let dataProduct = new PostManage(allProduct.reverse(), unapprovedPost.reverse(), processingPost.reverse(), successPost.reverse())
            let dataUser = new UserManage(allUser.length, uLv0.length, uLv1.length, uLv2.length, ulv3.length)
            let dataAdmin = await Admin.find({}).lean();
            let dataComment = await Comment.find({deleteAt: ''}).lean();
            let dataShare = 0;
            let dataDownload = 0;
            dataAdmin = dataAdmin.length;
            let createAt = moment(Date.now()).format(formatDate);
            if (admins.length > 0) {
                let confirm = await ConfirmPost({
                    product: null,
                    admin: admins[0]._id,
                    user: null,
                    status: 'LOGIN-WEB-SERVER',
                    createAt: createAt
                });
                let confirPrd = await confirm.save();
            }
            response.render('index', {
                status: 'none',
                user: nameDN,
                dataProduct: dataProduct,
                dataUser: dataUser,
                dataAdmin: dataAdmin,
                listUserPost: listUserPost,
                listAmoutFavorite: listAmoutFavorite,
                dataComment: dataComment.length > 0 ? dataComment.length : 0,
                dataShare: dataShare,
                dataLease: dataLease,
                dataDownload: dataDownload,
                dataNotLease: dataNotLease,
                dataFindSuccess: dataFindSuccess
            });
        } catch (e) {
            let dataComment = 0;
            let dataShare = 0;
            let dataDownload = 0;

            let dataProduct = new PostManage([], [], [], [])
            let dataUser = new UserManage(0, 0, 0, 0, 0)
            let dataAdmin = await Admin.find({}).lean();
            response.render('index', {
                status: 'none',
                user: nameDN,
                dataProduct: dataProduct,
                dataUser: dataUser,
                dataAdmin: dataAdmin.length,
                listUserPost: listUserPost,
                listAmoutFavorite: listAmoutFavorite,
                dataComment: dataComment,
                dataShare: dataShare,
                dataDownload: dataDownload,
                dataLease: dataLease,
                dataNotLease: dataNotLease,
                dataFindSuccess: dataFindSuccess
            });
        }
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
    let mAddress = "";
    console.log(nAddress)
    let addressAD = await Address.find({_id: nAddress}).lean();   //dk
    console.log(addressAD)
    if (addressAD.length > 0) {
        addressAD = addressAD[0];
        mAddress = addressAD.detailAddress + ', ' + addressAD.communeWardTown + ', ' + addressAD.districtsTowns + ', ' + addressAD.provinceCity;
    } else {
        console.log('dasda');
    }
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
        address: mAddress,
    });

});

// Báo cáo
app.get('/reportProduct', async function (request, response) {
    //  let obj = await getArea('P', '');
    //console.log('object: ' + obj);
    try {
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
        successPost = successPost.reverse();
        let data = new PostManage(allProduct.reverse(), unapprovedPost.reverse(), processingPost.reverse(), successPost)

        for (let i = 0; i < data.lengthOfSuccessPost; i++) {
            let productFavorite = await Favorite.find({
                deleteAt: '', status: 'POST', product: successPost[i]._id
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();

            let allComments = await Comment.find({
                deleteAt: '', status: 'COMMENT', product: successPost[i]._id
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();
            let productShare = [];

            successPost[i] = {
                product: successPost[i],
                countOfProductComment: allComments.length,
                countOfProductFavorite: productFavorite.length,
                productShare: productShare.length
            }
        }
        console.log(JSON.stringify(successPost))
        response.render('reportProduct', {
            data: data
        });
    } catch (e) {
        let data = new PostManage([], [], [], []);
        response.render('reportProduct', {
            data: data
        });
    }
});
// quản lý bài viết
app.get('/postManage', async function (request, response) {
    //  let obj = await getArea('P', '');
    //console.log('object: ' + obj);
    try {
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
        successPost = successPost.reverse();
        let data = new PostManage(allProduct.reverse(), unapprovedPost.reverse(), processingPost.reverse(), successPost)

        for (let i = 0; i < data.lengthOfSuccessPost; i++) {
            let productFavorite = await Favorite.find({
                deleteAt: '', status: 'POST', product: successPost[i]._id
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();

            let allComments = await Comment.find({
                deleteAt: '', status: 'COMMENT', product: successPost[i]._id
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();
            let productShare = [];

            successPost[i] = {
                product: successPost[i],
                countOfProductComment: allComments.length,
                countOfProductFavorite: productFavorite.length,
                productShare: productShare.length
            }
        }
        console.log(JSON.stringify(successPost))
        response.render('postManage', {
            data: data
        });
    } catch (e) {
        let data = new PostManage([], [], [], []);
        response.render('postManage', {
            data: data
        });
    }
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
app.get('/userManage', async function (request, response) {
    try {
        var allUsers = await User.find({deleteAt: ''}).populate(['address']).lean();
        var userLV0 = await User.find({deleteAt: '', level: 0}).populate(['address']).lean();
        var userLV1 = await User.find({deleteAt: '', level: 1}).populate(['address']).lean();
        var userLV2 = await User.find({deleteAt: '', level: 2}).populate(['address']).lean();
        var userLV3 = await User.find({deleteAt: '', level: 3}).populate(['address']).lean();
        userLV2 = userLV2.reverse();
        for (let i = 0; i < userLV2.length; i++) {
            let prdPost = await Product.find({
                deleteAt: '',
                user: userLV2[i]._id,
                status: '1'
            }).lean();
            let prdLeased = await Product.find({
                deleteAt: '',
                user: userLV2[i]._id
            }).lean();
            let newPrdLeased = [];
            if (prdLeased.length > 0) {
                for (let j = 0; j < prdLeased.length; j++) {
                    if (prdLeased[j].total_people_lease > 0) {
                        newPrdLeased.push(prdLeased[j]);
                    }
                }
            }
            let prdBlocked = await Product.find({
                deleteAt: '',
                user: userLV2[i]._id,
                status: '0'
            }).lean();
            userLV2[i] = {
                product: userLV3[i],
                post: prdPost.length,
                leased: newPrdLeased.length,
                blocked: prdBlocked.length,
            }
        }
        userLV3 = userLV3.reverse();
        for (let i = 0; i < userLV3.length; i++) {
            let prdPost = await Product.find({
                deleteAt: '',
                user: userLV3[i]._id,
                status: '1'
            }).lean();
            let prdLeased = await Product.find({
                deleteAt: '',
                user: userLV3[i]._id
            }).lean();
            let newPrdLeased = [];
            if (prdLeased.length > 0) {
                for (let j = 0; j < prdLeased.length; j++) {
                    if (prdLeased[j].total_people_lease > 0) {
                        newPrdLeased.push(prdLeased[j]);
                    }
                }
            }
            let prdBlocked = await Product.find({
                deleteAt: '',
                user: userLV3[i]._id,
                status: '0'
            }).lean();
            userLV3[i] = {
                product: userLV3[i],
                post: prdPost.length,
                leased: newPrdLeased.length,
                blocked: prdBlocked.length,
            }
        }

        let data = new UserManage(allUsers.reverse(), userLV0.reverse(), userLV1.reverse(), userLV2, userLV3);
        response.render('userManage', {
            data: data
        });

    } catch (e) {
        let data = new UserManage([], [], [], [], []);
        response.render('userManage', {
            data: data
        });
    }
});

//duyệt bài viết
app.get('/confirmUser', async function (request, response) {
    try {
        var listUpgrade = await UpgradeUser.find({deleteAt: ''})
            .populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            })
            .populate({
                path: 'identityCard'
            })
            .lean();
        if (listUpgrade.length > 0) {
            let listLv1 = [];
            let listLv2 = [];
            for (let i = 0; i < listUpgrade.length; i++) {
                if (listUpgrade[i].user.level == 1) {
                    listLv1.push(listUpgrade[i]);
                } else if (listUpgrade[i].user.level == 2) {
                    listLv2.push(listUpgrade[i]);
                }
            }
            let lengthOfLv1 = listLv1.length;
            let lengthOfLv2 = listLv2.length;
            response.render('confirmUser', {
                data: {
                    listLv1: listLv1,
                    lengthOfLv1: lengthOfLv1,
                    lengthOfLv2: lengthOfLv2,
                    listLv2: listLv2
                }
            });
        } else {
            response.render('confirmUser', {
                data: {
                    listLv1: [],
                    lengthOfLv1: 0,
                    lengthOfLv2: 0,
                    listLv2: []
                }
            });
        }

    } catch (e) {
        console.log('Lỗi: ' + e)
        response.render('login', {status: 'none', user: '', pass: ''});
    }

});

//lịch sử
app.get('/history', async function (request, response) {
    var confirmPost = await ConfirmPost.find({})
        .populate(['admin'])
        .populate({
            path: 'product',
            populate: {
                path: 'address',
                path: 'product',
                populate: {
                    path: 'user',
                    populate: {
                        path: 'address'
                    }
                }
            }
        })
        .populate({
            path: 'user',
            populate: {
                path: 'address'
            }
        })
        .lean();
    console.log(JSON.stringify(confirmPost))
    response.render('history', {data: confirmPost.reverse()});
});
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
                    let updateAt = moment(Date.now()).format(formatDate);
                    let updateProduct = await Product.findByIdAndUpdate(oldProduct._id, {
                        status: '1',
                        total_people_lease: oldProduct.total_people_lease,
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
                    let updateAt = moment(Date.now()).format(formatDate);
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

// duyet nang quyen
app.post('/confirm-upgrade', async function (request, response) {
        let name = 'CONFIRM-UPGRADE'
        try {
            let adminId = request.body.adminId;
            let id = request.body.id;
            if (checkData(adminId) && checkData(id)) {
                let admin = await Admin.find({_id: adminId}).lean();
                if (admin.length > 0) {
                    let updatedAt = moment(Date.now()).format(formatDate);
                    let user = await User.find({_id: id}).lean();
                    let res_body = {status: null};
                    let upgradeUser = await UpgradeUser.findByIdAndUpdate(id, {
                        updatedAt: updatedAt
                    });
                    if (upgradeUser) {
                        let user = await User.find({_id: upgradeUser.user}).lean();
                        let identityCard = await IdentityCard.find({_id: upgradeUser.identityCard}).lean();
                        if (user.length > 0 && identityCard.length > 0) {
                            user = user[0];
                            identityCard = identityCard[0];
                            if (user.level == 1 || user.level == 2) {
                                let number = 0;
                                if (user.level == 1) {
                                    number = 2;
                                    name = name + '-LEVEL-2';
                                    let updateUser = await UpgradeUser.findByIdAndUpdate(id, {
                                        updateAt: updatedAt
                                    });
                                } else {
                                    number = 3;
                                    name = name + '-LEVEL-3';
                                    let deleteUpgradeUser = await UpgradeUser.findByIdAndDelete(id);
                                }
                                let updateUser = await User.findByIdAndUpdate(user._id, {
                                    level: number,
                                    updateAt: updatedAt
                                });
                                let updateIdentityCard = await IdentityCard.findByIdAndUpdate(identityCard._id, {
                                    user: user._id,
                                    updateAt: updatedAt
                                });
                                if (updateUser && updateIdentityCard) {
                                    let confirm = await ConfirmPost({
                                        product: null,
                                        admin: adminId,
                                        user: id,
                                        status: name,
                                        createAt: updatedAt
                                    });
                                    let confirPrd = await confirm.save();
                                    res_body = {status: sttOK};
                                    response.json(getResponse(name, 200, sttOK, res_body));
                                } else {
                                    res_body = {status: "Fail"};
                                    response.json(getResponse(name, 200, 'Fail', res_body))
                                }
                            } else {
                                response.json(getResponse(name, 403, 'User not permissions', null))
                            }
                        } else {
                            response.json(getResponse(name, 404, 'Data not found', null))
                        }
                    } else {
                        response.json(getResponse(name, 404, 'Upgrade user not found', null))
                    }

                } else {
                    response.json(getResponse(name, 404, 'Admin not found', null))
                }
            } else {
                response.json(getResponse(name, 400, 'Bad request', null))
            }
        } catch
            (e) {
            console.log('loi ne: \n' + e)
            response.status(500).json(getResponse(name, 500, 'Server error', null))
        }
    }
);
// huy duyet nang quyen
app.post('/cancel-upgrade', async function (request, response) {
        let name = 'CANCEL-UPGRADE'
        try {
            let adminId = request.body.adminId;
            let id = request.body.id;
            if (checkData(adminId) &&
                checkData(id)) {
                let admin = await Admin.find({_id: adminId}).lean();
                if (admin.length > 0) {
                    let res_body = {status: null};
                    let deleteAt = moment(Date.now()).format(formatDate);
                    let updateUser = await UpgradeUser.findByIdAndUpdate(id, {
                        deleteAt: deleteAt
                    });
                    if (updateUser) {
                        let confirm = await ConfirmPost({
                            product: null,
                            admin: adminId,
                            user: null,
                            status: name,
                            createAt: deleteAt
                        });
                        let confirPrd = await confirm.save();
                        res_body = {status: sttOK};
                        response.json(getResponse(name, 200, sttOK, res_body));
                    } else {
                        res_body = {status: "Fail"};
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                } else {
                    response.json(getResponse(name, 404, 'Admin not found', null))
                }
            } else {
                response.json(getResponse(name, 400, 'Bad request', null))
            }
        } catch
            (e) {
            console.log('loi ne: \n' + e)
            response.status(500).json(getResponse(name, 500, 'Server error', null))
        }
    }
);

//comment
app.post('/init-comment', async function (request, response) {
    let name = 'INIT-COMMENT'
    try {
        let user = request.body.user;
        let product = request.body.product;
        let title = request.body.title;
        let oldComment = request.body.oldComment;
        if (checkData(user) &&
            checkData(product) &&
            checkData(title)) {
            let res_body = {status: null};
            let savedComment = null, status = null, findOldComment = null;
            let findUser = await User.find({_id: user}).lean();
            if (findUser.length < 0) {
                res_body = {status: "User not found"};
                response.json(getResponse(name, 404, 'User not found', res_body))
                return
            }
            if (findUser[0].status == false) {
                res_body = {status: "The account has been locked"};
                response.json(getResponse(name, 200, 'Fail', res_body))
                return
            }
            let findProduct = await Product.find({_id: product}).lean();
            if (findProduct.length < 0) {
                res_body = {status: "Product not found"};
                response.json(getResponse(name, 404, 'Product not found', res_body))
                return
            }

            let createAt = moment(Date.now()).format(formatDate);
            if (checkData(oldComment)) {
                findOldComment = await Comment.find({_id: oldComment}).lean();
                if (findOldComment.length < 0) {
                    res_body = {status: "Comment not found"};
                    response.json(getResponse(name, 404, 'Comment not found', res_body))
                    return
                }
                findOldComment = findOldComment[0];
                status = "REPLY";
                savedComment = oldComment;
            } else {
                status = "COMMENT";
            }
            let newComment = new Comment({
                user: user,
                product: product,
                title: title,
                oldComment: savedComment,
                updateAt: "",
                deleteAt: "",
                status: status,
                createAt: createAt
            });

            let initComment = await newComment.save();
            if (initComment) {
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

//favorite
app.post('/init-favorite', async function (request, response) {
    let name = 'INIT-FAVORITE'
    try {
        let res_body = {status: null};
        let user = request.body.user;
        let product = request.body.product;
        let comment = request.body.comment;
        if (checkData(user) &&
            checkData(product)) {
            let status = null, findComment = null;
            let findUser = await User.find({_id: user}).lean();
            if (findUser.length < 0) {
                let res_body = {status: 'User not found'};
                response.json(getResponse(name, 404, 'User not found', res_body))
                return
            }
            let findProduct = await Product.find({_id: product}).lean();
            if (findProduct.length < 0) {
                let res_body = {status: 'Product not found'};
                response.json(getResponse(name, 404, 'Product not found', res_body))
                return
            }
            if (checkData(comment)) {
                findComment = await Comment.find({_id: comment}).lean();
                if (findComment.length < 0) {
                    let res_body = {status: 'Comment not found'};
                    response.json(getResponse(name, 404, 'Comment not found', res_body))
                    return
                } else {
                    findComment = findComment[0];
                    status = "COMMENT";
                }
            } else {
                status = "POST";
            }
            try {
                let findFavorite = await Favorite.find({user: user, product: product, comment: comment}).lean();
                if (findFavorite.length > 0) {
                    name = 'DELETE-FAVORITE'
                    findFavorite = findFavorite[0];
                    let deleteFavorite = await Favorite.findByIdAndDelete(findFavorite._id);
                    if (deleteFavorite) {
                        res_body = {status: sttOK}
                        response.json(getResponse(name, 200, sttOK, res_body))
                    } else {
                        res_body = {status: 'Fail'}
                        response.json(getResponse(name, 200, 'Fail', res_body))
                    }
                    return
                }
            } catch (e) {
                console.log("del favorite: " + e)
            }
            let createAt = moment(Date.now()).format(formatDate);
            let newFavorite = new Favorite({
                product: product,
                user: user,
                comment: findComment,
                status: status,
                updateAt: "",
                deleteAt: "",
                createAt: createAt
            });
            let initFavorite = await newFavorite.save();
            if (initFavorite) {
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

//trả ve danh sách comment cua bai dang
app.post('/product-comment', async function (request, response) {
    let name = 'PRODUCT-COMMENT'
    try {
        let res_body = {status: null};
        let user = request.body.user;
        let product = request.body.product;
        if (checkData(user) &&
            checkData(product)) {
            let findUser = await User.find({_id: user}).lean();
            if (findUser.length < 0) {
                let res_body = {status: 'User not found'};
                response.json(getResponse(name, 404, 'User not found', res_body))
                return
            }
            if (findUser[0].status == false) {
                res_body = {status: "The account has been locked"};
                response.json(getResponse(name, 200, 'Fail', res_body))
                return
            }
            let findProduct = await Product.find({_id: product}).lean();
            if (findProduct.length < 0) {
                let res_body = {status: 'Product not found'};
                response.json(getResponse(name, 404, 'Product not found', res_body))
                return
            }
            let allComments = await Comment.find({
                deleteAt: '', status: 'COMMENT', product: product
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();
            if (allComments.length > 0) {
                allComments = allComments.reverse();
                let listResponse = [];
                for (let i = 0; i < allComments.length; i++) {
                    let stt = false;
                    let replyCount = 0;
                    let favoriteCount = 0;
                    let reply = [];
                    let favorite = [];
                    try {
                        let findFavorite = await Favorite
                            .find({
                                user: user,
                                comment: allComments[i]._id,
                                status: 'COMMENT'
                            }).lean();
                        if (findFavorite.length > 0) {
                            stt = true;
                        } else {
                            stt = false;
                        }
                    } catch (e) {
                        console.log('findFavorite comment: ' + e)
                    }
                    try {
                        let is_favorite_reply = false;
                        let allReplyOfComment = await Comment.find({
                            deleteAt: '', status: 'REPLY', product: product, oldComment: allComments[i]._id
                        }).populate({
                            path: 'user',
                            populate: {
                                path: 'address'
                            }
                        }).lean();
                        if (allReplyOfComment.length > 0) {
                            allReplyOfComment = allReplyOfComment;
                            for (let j = 0; j < allReplyOfComment.length; j++) {
                                if (user === allReplyOfComment[j].user._id) {
                                    is_favorite_reply = true;
                                } else {
                                    is_favorite_reply = false;
                                }
                                let favoriteReplyComment = [];
                                let favoriteCountReplyComment = 0;
                                try {
                                    let allFavoriteOfReplyComment = await Favorite.find({
                                        deleteAt: '', status: 'COMMENT', comment: allReplyOfComment[j]._id
                                    }).lean();
                                    if (allFavoriteOfReplyComment.length > 0) {
                                        favoriteReplyComment = allFavoriteOfReplyComment.reverse();
                                        favoriteCountReplyComment = allFavoriteOfReplyComment.length;
                                    }
                                } catch (e) {
                                    console.log('allFavoriteOfComment: ' + e)
                                }
                                reply.push({
                                    is_favorite_reply: is_favorite_reply,
                                    favorites: {count: favoriteCountReplyComment, list: favoriteReplyComment},
                                    comment: allReplyOfComment[j],
                                })
                            }
                            replyCount = allReplyOfComment.length;
                        }
                    } catch (e) {
                        console.log('allReplyOfComment: ' + e)
                    }
                    try {
                        let allFavoriteOfComment = await Favorite.find({
                            deleteAt: '', status: 'COMMENT', product: product, comment: allComments[i]._id
                        }).lean();
                        if (allFavoriteOfComment.length > 0) {
                            favorite = allFavoriteOfComment.reverse();
                            favoriteCount = allFavoriteOfComment.length;
                        }
                    } catch (e) {
                        console.log('allFavoriteOfComment: ' + e)
                    }
                    let item = {
                        is_favorite: stt,
                        comment: allComments[i],
                        reply: {count: replyCount, list: reply},
                        favorites: {count: favoriteCount, list: favorite}
                    }
                    listResponse.push(item);
                }
                res_body = {count: listResponse.length, comments: listResponse};
                response.json(getResponse(name, 200, sttOK, res_body));
            } else {
                res_body = {count: listResponse.length, comments: null};
                response.json(getResponse(name, 200, 'Fail', res_body));
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
//trả ve yêu thích của bài đăng
app.post('/product-favorite', async function (request, response) {
    let name = 'PRODUCT-FAVORITE'
    try {
        let res_body = {status: null};
        let user = request.body.user;
        let product = request.body.product;
        if (checkData(user) &&
            checkData(product)) {
            let findUser = await User.find({_id: user}).lean();
            if (findUser.length < 0) {
                let res_body = {status: 'User not found'};
                response.json(getResponse(name, 404, 'User not found', res_body))
                return
            }
            if (findUser[0].status == false) {
                res_body = {status: "The account has been locked"};
                response.json(getResponse(name, 200, 'Fail', res_body))
                return
            }
            let findProduct = await Product.find({_id: product}).lean();
            if (findProduct.length < 0) {
                let res_body = {status: 'Product not found'};
                response.json(getResponse(name, 404, 'Product not found', res_body))
                return
            }
            let productFavorite = await Favorite.find({
                deleteAt: '', status: 'POST', product: product
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();
            listUserNew = [];
            if (productFavorite.length > 0) {
                productFavorite = productFavorite.reverse();
                let listUser = [];
                let stt = false;
                for (let i = 0; i < productFavorite.length; i++) {
                    try {
                        if (productFavorite[i].user._id == user) {
                            stt = true;
                        }
                        let count = 0;
                        try {
                            let findUser = await User.find({_id: productFavorite[i].user._id}).populate('address').lean();
                            if (findUser.length > 0) {
                                listUserNew.push(findUser[0])
                                count++;
                            }
                        } catch (e) {
                            console.log('findUser: ' + e)
                        }
                    } catch (e) {
                        console.log('productFavorite post: ' + e)
                    }
                }
                res_body = {count: productFavorite.length, is_favorite: stt, list_user: listUserNew};
                response.json(getResponse(name, 200, sttOK, res_body));
            } else {
                res_body = {count: productFavorite.length, is_favorite: false, list_user: listUserNew};
                response.json(getResponse(name, 200, 'Fail', res_body));
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});

//tim kiem comment
app.post('/find-comment', async function (request, response) {
    let name = 'FIND-COMMENT'
    try {
        let res_body = {status: null};
        let user = request.body.user;
        let comment = request.body.comment;
        if (checkData(user) &&
            checkData(comment)) {
            let findUser = await User.find({_id: user}).lean();
            if (findUser.length < 0) {
                let res_body = {status: 'User not found'};
                response.json(getResponse(name, 404, 'User not found', res_body))
                return
            }
            if (findUser[0].status == false) {
                res_body = {status: "The account has been locked"};
                response.json(getResponse(name, 200, 'Fail', res_body))
                return
            }
            let findComment = await Comment.find({_id: comment}).lean();
            if (findComment.length < 0) {
                let res_body = {status: 'Product not found'};
                response.json(getResponse(name, 404, 'Comment not found', res_body))
                return
            }
            let getComment = await Comment.find({
                deleteAt: '', status: 'COMMENT', _id: comment
            }).populate({
                path: 'user',
                populate: {
                    path: 'address'
                }
            }).lean();
            if (getComment.length > 0) {
                getComment = getComment[0];
                let listResponse = [];
                let stt = false;
                let replyCount = 0;
                let favoriteCount = 0;
                let reply = [];
                let favorite = [];
                try {
                    let findFavorite = await Favorite
                        .find({
                            user: user,
                            comment: getComment._id,
                            status: 'COMMENT'
                        }).lean();
                    if (findFavorite.length > 0) {
                        stt = true;
                    } else {
                        stt = false;
                    }
                } catch (e) {
                    console.log('findFavorite comment: ' + e)
                }
                try {
                    let is_favorite_reply = false;
                    let allReplyOfComment = await Comment.find({
                        deleteAt: '', status: 'REPLY', oldComment: getComment._id
                    }).populate({
                        path: 'user',
                        populate: {
                            path: 'address'
                        }
                    }).lean();
                    if (allReplyOfComment.length > 0) {
                        allReplyOfComment = allReplyOfComment;
                        for (let j = 0; j < allReplyOfComment.length; j++) {
                            if (user === allReplyOfComment[j].user._id) {
                                is_favorite_reply = true;
                            } else {
                                is_favorite_reply = false;
                            }
                            let favoriteReplyComment = [];
                            let favoriteCountReplyComment = 0;
                            try {
                                let allFavoriteOfReplyComment = await Favorite.find({
                                    deleteAt: '', status: 'COMMENT', comment: allReplyOfComment[j]._id
                                }).lean();
                                if (allFavoriteOfReplyComment.length > 0) {
                                    favoriteReplyComment = allFavoriteOfReplyComment.reverse();
                                    favoriteCountReplyComment = allFavoriteOfReplyComment.length;
                                }
                            } catch (e) {
                                console.log('allFavoriteOfComment: ' + e)
                            }
                            reply.push({
                                is_favorite_reply: is_favorite_reply,
                                favorites: {count: favoriteCountReplyComment, list: favoriteReplyComment},
                                comment: allReplyOfComment[j],
                            })
                        }
                        replyCount = allReplyOfComment.length;
                    }
                } catch (e) {
                    console.log('allReplyOfComment: ' + e)
                }
                try {
                    let allFavoriteOfComment = await Favorite.find({
                        deleteAt: '', status: 'COMMENT', comment: getComment._id
                    }).lean();
                    if (allFavoriteOfComment.length > 0) {
                        favorite = allFavoriteOfComment.reverse();
                        favoriteCount = allFavoriteOfComment.length;
                    }
                } catch (e) {
                    console.log('allFavoriteOfComment: ' + e)
                }
                let item = {
                    is_favorite: stt,
                    comment: getComment,
                    reply: {count: replyCount, list: reply},
                    favorites: {count: favoriteCount, list: favorite}
                }
                res_body = {comment: item};
                response.json(getResponse(name, 200, sttOK, res_body));
            } else {
                res_body = {comments: null};
                response.json(getResponse(name, 200, 'Fail', res_body));
            }
        } else {
            response.json(getResponse(name, 400, 'Bad request', null))
        }
    } catch (e) {
        console.log('loi ne: \n' + e)
        response.status(500).json(getResponse(name, 500, 'Server error', null))
    }
});
