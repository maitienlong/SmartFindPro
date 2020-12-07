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

//anh xa model
const postSchema = require('./model/PostSchema');
const Product = db.model('Product', postSchema, 'products');

const userSchema = require('./model/UserSchema');
const User = db.model('User', userSchema, 'users');

const adminSchema = require('./model/AdminSchema');
const Admin = db.model('Admin', adminSchema, 'admins');

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

//Khai bao bien
const sttOK = 'Succsess'
let nameDN = '', allAdmin = '';

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

    if (admins.length <= 0 && sm == 1) {
        response.render('login', {
            status: 'block',
            data: 'Không thể đăng nhập, kiểm tra lại tài khoản và mật khẩu của bạn.',
            user: '',
            pass: ''
        });
    } else {
        response.render('index', {
            status: 'none',
            user: nameDN,
            pass: pass,
        });
    }


});
// thêm sửa xóa tài khoản admin
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
// form update admin
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

app.get('/postManage', async function (request, response) {
    const PostManage = require('./model/confirmPost/PostManage');
    // try {
    //     if (request.query.idProduct != undefined) {
    //         let _id = request.query.idProduct
    //         console.log('_id: ' + _id)
    //         if (_id != undefined || _id != null || _id != '') {
    //             let dateNow = Date.now()
    //             let date = moment(dateNow).format('YYYY-MM-DD hh:mm:ss');
    //             console.log(date)
    //             let update = await Product.findByIdAndUpdate(_id, {
    //                 deleteAt: date,
    //             });
    //             if (update) {
    //                 console.log('[Delete] OK')
    //                 var unapprovedPost = await Product.find({deleteAt: '', status: '-1'}).lean();
    //                 var processingPost = await Product.find({deleteAt: '', status: '0'}).lean();
    //                 var successPost = await Product.find({deleteAt: '', status: '1'}).lean();
    //             } else {
    //                 console.log('[Delete] Fail')
    //             }
    //         }
    //     }
    //
    // } catch (e) {
    //     console.log('[Delete] Error: ' + e)
    // }

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
    let data = new PostManage(allProduct, unapprovedPost, processingPost, successPost)
    response.render('postManage', {
        data: data
    });
});

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
        console.log(product)
        response.render('confirmPost', {
            product: product[0]
        });
    } catch (e) {
        console.log('Lỗi: ' + e)
        response.render('login', {status: 'none', user: '', pass: ''});
    }

});
//nam23

// phần kết nối sever với app
app.get('/getDL', async function (request, response) {
    response.render('getDL');
});

// nhận thông tin khách hàng để tạo tài khoản
app.post('/postUser', async function (request, response) {
    let nPhone = request.body.phone;
    let nFullName = request.body.fullname;
    let nPassword = request.body.password;
    let nPassword2 = request.body.password2;


    let newUser = new User({
        phone: nPhone,
        fullName: nFullName,
        password: nPassword,
        password2: nPassword2
    });
    let status = await newUser.save();
    if (status) {
        response.send(newUser)
    } else {
        response.send('Them thất bại.')
    }


});
// nhận thông tin giỏ hàng để thêm vào database
app.post('/postUpdateUserPass', async function (request, response) {

    let nPhone = request.body.nPhone;
    let nPassword = request.body.nPassword;

    let searchUser = await User.find({phone: nPhone}).lean();

    let update = await User.findByIdAndUpdate(searchUser[0]._id, {
        password: nPassword,
    });


    let nUser = await User.find({}).lean();
    if (update) {
        response.send(nUser)
    } else {
        response.send('Update thất bại.' + nPhone + ' , ' + searchUser.fullName + ' , ' + nPassword + ' , ' + searchUser.password2)
    }

});


//post anh
app.post("/upload-photo", multer({storage: storage}).single('photo'), function (req, res) {

    var jsonResult = [];
    jsonResult.push(req.file.path)

    return res.status(200).json({
        addressImage: jsonResult
    })
});

app.post("/upload-photo-array", multer({storage: storage}).array('photo', 5), function (req, res) {

    var jsonResult = [];

    for (var i in req.files) {
        jsonResult.push(req.files[i].path)
    }

    return res.status(200).json({
        addressImage: jsonResult
    })
});

// trả về dữ liệu trong database
app.get('/getAlluser', async function (request, response) {
    let users = await User.find({});
    response.send(users);
});

//trả ve danh sách bài đăng cua nguoi dung
app.post('/user-product', async function (request, response) {
    let name = 'USER-PRODUCT'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            try {
                let userNo = await User.find({_id: id}).lean();
                try {
                    let allProduct = await Product.find({
                        deleteAt: ''
                    }).populate(['address', 'product'])
                        .populate({
                            path: 'user',
                            populate: {
                                path: 'address'
                            }
                        })
                        .lean();
                    console.log(allProduct)
                    let res_body = {products: allProduct}
                    response.json(getResponse(name, 200, sttOK, res_body))
                } catch (e) {
                    console.log('loi ne: \n' + e)
                    response.json(getResponse(name, 200, 'Fail', null))
                }
            } catch (e) {
                console.log('loi ne: \n' + e)
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
            try {
                let user = await User.find({_id: id}).lean();
                try {
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
                    let res_body = {products: allProduct}
                    response.json(getResponse(name, 200, sttOK, res_body))
                } catch (e) {
                    console.log('loi ne: \n' + e)
                    response.json(getResponse(name, 200, 'Fail', null))
                }
            } catch (e) {
                console.log('loi ne: \n' + e)
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

//tim kiem nguoi dung
app.post('/find-user', async function (request, response) {
    let name = 'FIND-USER'
    try {
        let id = request.body.id;
        if (checkData(id)) {
            try {
                let user = await User.find({_id: id}).populate(['address']).lean();
                let res_body = {user: user[0]}
                response.json(getResponse(name, 200, sttOK, res_body))
            } catch (e) {
                console.log('loi ne: \n' + e)
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
            try {
                let user = await User.find({_id: id}).lean();
                try {
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
                            latitude: mAddress.longitude,
                            longitude: mAddress.latitude
                        }
                    });
                    let product = await newInforProduct.save();
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
                    response.json(getResponse(name, 200, sttOK, null))
                } catch (e) {
                    console.log('loi ne: \n' + e)
                    response.json(getResponse(name, 200, 'Fail', null))
                }
            } catch (e) {
                console.log('loi ne: \n' + e)
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
            try {
                let user = await User.find({_id: userId}).lean();
                try {
                    let oldProduct = await Product.find({_id: id}).lean();
                    oldProduct = oldProduct[0];
                    try {
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
                                latitude: mAddress.longitude,
                                longitude: mAddress.latitude
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
                        response.json(getResponse(name, 200, sttOK, null))
                    } catch (e) {
                        console.log('loi ne: \n' + e)
                        response.json(getResponse(name, 200, 'Fail', null))
                    }
                } catch (e) {
                    console.log('loi ne: \n' + e)
                    response.json(getResponse(name, 404, 'Product not found', null))
                }
            } catch (e) {
                console.log('loi ne: \n' + e)
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
// xoa bai dang
app.post('/delete-product', async function (request, response) {
    let name = 'DELETE-PRODUCT'
    try {
        let id = request.body.id;
        let userId = request.body.userId;
        if (checkData(id) && checkData(userId)) {
            try {
                let user = await User.find({_id: userId}).lean();
                try {
                    let product = await Product.find({_id: id}).lean();
                    product = product[0];
                    try {
                        let deleteProduct = await Product.findByIdAndDelete(product._id);
                        let deleteInforProduct = await InforProduct.findByIdAndDelete(product.product._id);
                        let deleteAddress = await Address.findByIdAndDelete(product.address._id);
                        response.json(getResponse(name, 200, sttOK, null))
                    } catch (e) {
                        console.log('loi ne: \n' + e)
                        response.json(getResponse(name, 200, 'Fail', null))
                    }
                } catch (e) {
                    console.log('loi ne: \n' + e)
                    response.json(getResponse(name, 404, 'Product not found', null))
                }
            } catch (e) {
                console.log('loi ne: \n' + e)
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
