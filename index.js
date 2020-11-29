let express = require('express');
let hbs = require('express-handlebars');
let db = require('mongoose');
let multer = require('multer');
let body = require('body-parser');
let fs = require('fs');
var moment = require('moment');
var crypto = require('crypto');

let userSchema = require('./model/userSchema');
let adminSchema = require('./model/adminSchema');
let postSchema = require('./model/postSchema');
let addressSchema = require('./model/addressSchema')
let informationSchema = require('./model/informationSchema')
let identityCardSchema = require('./model/identityCardSchema')

let IdentityCard = db.model('IdentityCard', identityCardSchema);
let Information = db.model('Information', informationSchema);
let Address = db.model('Address', addressSchema);
let User = db.model('User', userSchema, 'users');
let Admin = db.model('Admin', adminSchema, 'admins');
let Product = db.model('Product', postSchema, 'postProduct');


db.connect('mongodb+srv://Nhom5qlda14351:quanlyduan123@cluster0-z9led.mongodb.net/TimtroDatabase?retryWrites=true&w=majority', {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false
}).then(function () {
    console.log('Mongoose is connected');
});

let app = express();

let path = require('path');
const { json } = require('body-parser');
app.use('/public', express.static(path.join(__dirname, 'public')))
app.use(body.json());
app.use(body.urlencoded({ extended: true }));
app.engine('.hbs', hbs({
    extname: 'hbs',
    defaultLayout: '',
    layoutsDir: ''
}))
app.set('view engine', '.hbs')

//chạy lên local host với port là 9090
let localNumber = 9090
app.listen(localNumber);
console.log('Localhost: ' + localNumber);
// phần sever

// var log = console.log;
// console.log = function () {
//     log.apply(console, arguments);
//     // Print the stack trace
//     console.trace();
// };

//tk Admin
let adminNow = ''
// đăng nhập


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
    response.render('login', { status: 'none', user: '', pass: '' });
});

let nameDN = '', allAdmin = '';
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

    let admins = await Admin.find({ username: user, password: pass }).lean();   //dk

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
        let seachAdmin = await Admin.find({ username: nameSP }).lean();
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

            let findAdmin = await Admin.find({ username: nUser }).lean();   //dk

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

            let admins = await Admin.find({ username: nUser, password: nPass }).lean();   //dk

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
    // let unapprovedPost = await Product.find({status: '-1'}).lean();
    // let processingPost = await Product.find({status: '0'}).lean();
    // let successPost = await Product.find({status: '1'}).lean();

    var product = await Product.find().lean();
    var a = product.filter(function (el) {
        return el.deleteAt === "";
    });

    var unapprovedPost = a.filter(function (el) {
        return el.status === '-1';
    });

    var processingPost = a.filter(function (el) {
        return el.status === '0';
    });
    var successPost = a.filter(function (el) {
        return el.status === '1';
    });
    console.log('a: \n' + JSON.stringify(a))
    console.log('unapprovedPost: \n' + JSON.stringify(unapprovedPost))
    console.log('processingPost: \n' + JSON.stringify(processingPost))
    console.log('successPost: \n' + JSON.stringify(successPost))
    try {

        if (request.query.idProduct != undefined) {
            let _id = request.query.idProduct
            console.log('_id: ' + _id)
            if (_id != undefined || _id != null || _id != '') {
                let dateNow = Date.now()
                let date = moment(dateNow).format('YYYY-MM-DD hh:mm:ss');
                console.log(date)
                let update = await Product.findByIdAndUpdate(_id, {
                    deleteAt: date,
                });
                if (update) {
                    console.log('[Delete] OK')
                    product = await Product.find().lean();
                    a = product.filter(function (el) {
                        return el.deleteAt === "";
                    });

                    unapprovedPost = a.filter(function (el) {
                        return el.status === '-1';
                    });

                    processingPost = a.filter(function (el) {
                        return el.status === '0';
                    });
                    successPost = a.filter(function (el) {
                        return el.status === '1';
                    });
                } else {
                    console.log('[Delete] Fail')
                }
            }
        }

    } catch (e) {
        console.log('[Delete] Error: ' + e)
    }
    let data = new PostManage(unapprovedPost, processingPost, successPost)
    response.render('postManage', {
        status: 'none',
        data: data,
    });
});

app.get('/confirmPost', async function (request, response) {
    let _id = request.query.idProduct;
    try {
        let productFind = await Product.find({ _id: _id }).lean();
        let product = productFind[0]
        let findUserProduct = await User.find({ _id: product.userId }).lean();
        let userProduct = findUserProduct[0]

        console.log('[GET LIST FIND] Product\n' + JSON.stringify(product) + '\n User \n' + JSON.stringify(userProduct) + '\n AdminNow: ' + adminNow + '\n------------------->')
        response.render('confirmPost', {
            product: product,
            userProduct: userProduct,
            nameDN: nameDN
        });
    } catch (e) {
        console.log('Lỗi: ' + e)
        response.render('login', { status: 'none', user: '', pass: '' });
    }

});


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

    let searchUser = await User.find({ phone: nPhone }).lean();

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

// trả về dữ liệu trong database
app.get('/getAlluser', async function (request, response) {
    let users = await User.find({});
    response.send(users);
});
//trả ve danh sách bài đăng
app.get('/getAllProduct', async function (request, response) {
    let successPost = await Product.find({ status: '1' });
    response.send(successPost);
});

//post anh
app.post("/upload-photo", multer({ storage: storage }).single('photo'), function (req, res) {

    var jsonResult = [];
    jsonResult.push(req.file.path)

    return res.status(200).json({
        addressImage: jsonResult
    })
});

app.post("/upload-photo-array", multer({ storage: storage }).array('photo', 5), function (req, res) {

    var jsonResult = [];

    for (var i in req.files) {
        jsonResult.push(req.files[i].path)
    }

    return res.status(200).json({
        addressImage: jsonResult
    })
});

// dang bai
app.post('/init-product', async function (request, response) {

    let category = request.body.category;
    let information = request.body.information;
    let mAddress = request.body.address;
    let utilities = request.body.utilities;
    let content = request.body.content;
    let idUser = request.body.idUser;
    let status = request.body.status;
    let createAt = request.body.createAt;
    let updateAt = request.body.updateAt;
    let deleteAt = request.body.deleteAt;
    let linkProduct = request.body.linkProduct;

    if (status == null || status == "") {
        status = ""
    }
    if (linkProduct == null || linkProduct == "") {
        linkProduct = ""
    }
    if (createAt == null || createAt == "") {
        createAt = ""
    }
    if (updateAt == null || updateAt == "") {
        updateAt = ""
    }
    if (deleteAt == null || deleteAt == "") {
        deleteAt = ""
    }
    try {
        let dateNow = Date.now()
        createAt = moment(dateNow).format('YYYY-MM-DD hh:mm:ss');
        status = '-1'
        let newProduct = new Product({
            category: category,
            information: information,
            address: mAddress,
            utilities: utilities,
            content: content,
            userId: idUser,
            status: status,
            createAt: createAt,
            updateAt: updateAt,
            deleteAt: deleteAt,
            linkProduct: linkProduct
        });
        console.log(newProduct);
        if (!newProduct) {
            response.status(500).json({
                message: 'Fail'
            })
        } else {
            let product = await newProduct.save();
            response.status(200).json({
                message: 'OK'
            })
        }
    } catch (e) {
        response.status(400).json({
            message: 'Lỗi: ' + e
        })
    }
});