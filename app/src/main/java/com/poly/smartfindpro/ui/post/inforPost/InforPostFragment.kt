package com.poly.smartfindpro.ui.post.inforPost

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.poly.smartfindpro.R
import com.poly.smartfindpro.basedatabind.BaseDataBindFragment
import com.poly.smartfindpro.data.Config
import com.poly.smartfindpro.data.model.post.req.ImageInforPost
import com.poly.smartfindpro.data.model.post.req.Information
import com.poly.smartfindpro.data.model.post.req.PostRequest
import com.poly.smartfindpro.databinding.FragmentInforPostBinding
import com.poly.smartfindpro.ui.post.adapter.ImageInforPostAdapter
import com.poly.smartfindpro.ui.post.adapter.ShowImagePostAdapter
import com.poly.smartfindpro.ui.post.adressPost.AddressPostFragment
import java.util.*

class InforPostFragment : BaseDataBindFragment<FragmentInforPostBinding?, InforPostPresenter?>(), InforPostContract.ViewModel, OnTouchListener, View.OnClickListener {
    var category: String? = null
    var mAmountPeople = ""
    var mPrice = ""
    var mDeposit = ""
    var mGender = ""
    var mElectricityBill = ""
    var mWaterBill = ""
    var mDescription = ""
    var idPost: String? = null
    private var postRequest: PostRequest? = null
    private var showImagePostAdapter: ShowImagePostAdapter? = null
    private var information: Information? = null
    private var imageListPath: MutableList<ImageInforPost>? = null
    private val imagePostAdapter: ImageInforPostAdapter? = null
    private val urlReal = ""
    override fun getLayoutId(): Int {
        return R.layout.fragment_infor_post
    }

    override fun initView() {
        imageListPath = ArrayList()

        //chon the loai
        mBinding!!.btnNhaTro.setOnTouchListener(this)
        mBinding!!.btnOGhep.setOnTouchListener(this)
        mBinding!!.btnNguyenCan.setOnTouchListener(this)
        mBinding!!.btnChungCu.setOnTouchListener(this)
        mBinding!!.btnContinue.setOnClickListener(this)
        mBinding!!.imgAddImages.setOnClickListener(this)
        data
    }

    private val data: Unit
        private get() {
            if (arguments != null && arguments!!.getString(Config.POST_BUNDEL_RES) != null) {
            }
        }

    override fun initData() {
        mPresenter = InforPostPresenter(context, this)
        showImagePostAdapter = ShowImagePostAdapter(mActivity)
        mBinding!!.presenter = mPresenter
        mBinding!!.rvImages.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mBinding!!.rvImages.setHasFixedSize(true)
    }

    //lay du lieu cua anh de hien thi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            if (data.clipData != null) {
                val totalItem = data.clipData!!.itemCount
                for (i in 0 until totalItem) {
                    // URI
                    val imageUri = data.clipData!!.getItemAt(i).uri

                    // File name
                    val imageName = getFileName(imageUri)

                    //  Lay duong dan thuc te
                    val realPath = RealPathUtil.getRealPath(mActivity, imageUri)

                    //  mPresenter.onDemoUri(realPath);
                    // them du lieu vao object Image
                    try {
                        val item = ImageInforPost(imageName, realPath, MediaStore.Images.Media.getBitmap(mActivity.contentResolver, imageUri))
                        imageListPath!!.add(item)
                    } catch (e: Exception) {
                    }

                    // show image
                    onShowImage(imageListPath)
                }
            } else if (data.data != null) {
                val imageUri = data.data

                // File name
                val imageName = getFileName(imageUri)

                //  Lay duong dan thuc te
                val realPath = RealPathUtil.getRealPath(mActivity, imageUri)

                //  mPresenter.onDemoUri(realPath);
                // them du lieu vao object Image
                try {
                    val item = ImageInforPost(imageName, realPath, MediaStore.Images.Media.getBitmap(mActivity.contentResolver, imageUri))
                    imageListPath!!.add(item)
                } catch (e: Exception) {
                    Log.d("CheckLoge", e.toString())
                }

                // show image
                onShowImage(imageListPath)
            }
        } else {
            showMessage("Bạn chưa chọn ảnh nào")
        }
    }

    fun getFileName(uri: Uri?): String? {
        var result: String? = null
        if (uri!!.scheme == "content") {
            val cursor = mActivity.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
            if (result == null) {
                result = uri.path
                val cut = result!!.lastIndexOf('/')
                if (cut != -1) {
                    result = result.substring(cut + 1)
                }
            }
        }
        return result
    }

    override fun onNextFragment() {
        postRequest = PostRequest()
        information = Information()
        information!!.amountPeople = Integer.valueOf(mAmountPeople)
        information!!.price = Integer.valueOf(mPrice)
        information!!.gender = mGender
        information!!.unit = "VNĐ"
        information!!.deposit = Integer.valueOf(mDeposit)
        information!!.electricBill = Integer.valueOf(mElectricityBill)
        information!!.electricUnit = "Số"
        information!!.waterBill = Integer.valueOf(mWaterBill)
        information!!.waterUnit = "Khối"
        information!!.describe = mDescription
        postRequest!!.category = category
        postRequest!!.information = information
        if (imageListPath != null && imageListPath!!.size > 0) {
            onNext(Gson().toJson(postRequest), Gson().toJson(imageListPath), Gson().toJson(idPost))
        } else {
            showMessage("Bạn phải có ít nhất 1 ảnh")
        }
    }

    override fun onErrorCategory() {
        Toast.makeText(context, R.string.text_category_empty, Toast.LENGTH_SHORT).show()
    }

    override fun onErrorInfor() {
        Toast.makeText(context, R.string.text_infor_error_empty, Toast.LENGTH_SHORT).show()
    }

    override fun onErrorGender() {
        Toast.makeText(context, R.string.text_gender_empty, Toast.LENGTH_SHORT).show()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (view.id) {
            R.id.btn_nha_tro -> {
                mBinding!!.btnNhaTro.isPressed = true
                mBinding!!.btnChungCu.isPressed = false
                mBinding!!.btnNguyenCan.isPressed = false
                mBinding!!.btnOGhep.isPressed = false
                category = mBinding!!.btnNhaTro.text.toString()
            }
            R.id.btn_chung_cu -> {
                mBinding!!.btnNhaTro.isPressed = false
                mBinding!!.btnChungCu.isPressed = true
                mBinding!!.btnNguyenCan.isPressed = false
                mBinding!!.btnOGhep.isPressed = false
                category = mBinding!!.btnChungCu.text.toString()
            }
            R.id.btn_nguyen_can -> {
                mBinding!!.btnNhaTro.isPressed = false
                mBinding!!.btnChungCu.isPressed = false
                mBinding!!.btnNguyenCan.isPressed = true
                mBinding!!.btnOGhep.isPressed = false
                category = mBinding!!.btnNguyenCan.text.toString()
            }
            R.id.btn_o_ghep -> {
                mBinding!!.btnNhaTro.isPressed = false
                mBinding!!.btnChungCu.isPressed = false
                mBinding!!.btnNguyenCan.isPressed = false
                mBinding!!.btnOGhep.isPressed = true
                category = mBinding!!.btnOGhep.text.toString()
            }
        }
        return true
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnContinue) {
            mAmountPeople = mBinding!!.edtAmountPerson.text.toString()
            mPrice = mBinding!!.edtPrice.text.toString()
            mDeposit = mBinding!!.edtDeposit.text.toString()

            //check The loai phong
            if (category == null) {
                onErrorCategory()
                return
            }

            //gioi tinh
            if (mBinding!!.rbFemale.isChecked) {
                mGender = mBinding!!.rbFemale.text.toString()
            } else if (mBinding!!.rbMale.isChecked) {
                mGender = mBinding!!.rbMale.text.toString()
            } else if (mBinding!!.rbAll.isChecked) {
                mGender = mBinding!!.rbAll.text.toString()
            }
            mElectricityBill = mBinding!!.edtElectricityBill.text.toString()
            mWaterBill = mBinding!!.edtWaterBill.text.toString()
            mDescription = mBinding!!.edtDescription.text.toString()
            mPresenter!!.handleData(category, mAmountPeople, mPrice, mDeposit, mGender, mElectricityBill, mWaterBill, mDescription)
        }
    }

    private fun onShowImage(imageList: List<ImageInforPost>?) {
        showImagePostAdapter!!.setItemView(imageList)
        val linearLayoutManager = LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        mBinding!!.rvImages.layoutManager = linearLayoutManager
        mBinding!!.rvImages.adapter = showImagePostAdapter
    }

    fun onNext(jsonData: String?, jsonPhoto: String?, idPost: String?) {
        Log.d("CheckLog", jsonData!!)
        val bundle = Bundle()
        bundle.putString(Config.POST_BUNDEL_RES, jsonData)
        bundle.putString(Config.POST_BUNDEL_RES_PHOTO, jsonPhoto)
        if (arguments != null) {
            bundle.putString(Config.POST_BUNDlE_RES_ID, arguments!!.getString(Config.POST_BUNDlE_RES_ID))
            Log.d("CheckBundle", idPost!!)
        } else {
            Toast.makeText(mActivity, "loi", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent()
        intent.putExtra(Config.DATA_CALL_BACK, "2")
        intent.putExtra(Config.POST_BUNDEL_RES, jsonData)
        intent.putExtra(Config.POST_BUNDEL_RES_PHOTO, jsonPhoto)
        setResult(Activity.RESULT_OK, intent)
        onBackData()
        baseActivity.goToFragmentCallBackData(R.id.fl_post, AddressPostFragment(), bundle, onFragmentDataCallBack)
    }

    private fun showImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onShowPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, MY_PERMISSIONS_REQUEST)
            } else {
                showImageGallery()
            }
        } else {
            showImageGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageGallery()
            } else {
                showMessage("Quyền truy cập đã được từ chối")
            }
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
        const val MY_PERMISSIONS_REQUEST = 1001
    }
}