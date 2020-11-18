let express = require('express');
let hbs = require('express-handlebars');
let db = require('mongoose');
let multer = require('multer');
let body = require('body-parser');
let fs = require('fs');


let userSchema = require('./model/userSchema');
let adminSchema = require('./model/adminSchema');
let postSchema = require('./model/postSchema');

let User = db.model('User', userSchema, 'users');
let Admin = db.model('Admin', adminSchema, 'admins');
let Product = db.model('Product', postSchema, 'postProduct');

db.connect('mongodb+srv://Nhom5qlda14351:quanlyduan123@cluster0-z9led.mongodb.net/TimtroDatabase?retryWrites=true&w=majority', {}).then(function (res) {
    console.log('conected');
})

let app = express();

let path = require('path');
app.use('/public', express.static(path.join(__dirname, 'public')))
app.use(body.json());
app.use(body.urlencoded({extended: true}));
app.engine('.hbs', hbs({
    extname: 'hbs',
    defaultLayout: '',
    layoutsDir: ''
}))
app.set('view engine', '.hbs')
app.listen(9091);
// phần sever

// đăng nhập
app.get('/', function (request, response) {
    response.render('login', {status: 'none', user: '', pass: ''});
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
        name:nName,
        phone:nPhone,
        address:nAddress,
    });

});
app.get('/postManage', async  function (request, response) {
response.render('postManage')
})


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

// trả về dữ liệu trong database
app.get('/getAlluser', async function (request, response) {
    let users = await User.find({});
    response.send(users);
});// đăng nhập
app.get('/getAllproduct', async function (request, response) {
    let products = await Product.find({});
    response.send(products);
});// tìm kiếm các bài đăng


// dang bai
app.post('/init-product', async function (request, response) {
    let category = request.body.category;
    let information = request.body.information;
    let address = request.body.address;
    let utilities = request.body.utilities;
    let content = request.body.content;
    let idUser = request.body.idUser;
    let status = request.body.status;
    let time = request.body.time;
    let linkProduct = request.body.linkProduct;

    if (status == null && status != ""){
        status = null
    }

    if (linkProduct == null && linkProduct != ""){
        linkProduct = null
    }
    let newProduct = new Product({
        category: category,
        information: information,
        address: address,
        utilities: utilities,
        content: content,
        idUser: idUser,
        status: status,
        time: time,
        linkProduct: linkProduct
    });
    let product = await newProduct.save();
    if (product) {
        response.send(newProduct);
    } else {
        response.send('Them thất bại.',200);
    }

});