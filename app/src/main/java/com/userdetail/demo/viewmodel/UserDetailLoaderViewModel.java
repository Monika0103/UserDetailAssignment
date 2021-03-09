package com.userdetail.demo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.userdetail.demo.model.UserDetailBean;
import com.userdetail.demo.userrepository.UserDataRepository;

/**
 * ViewModel
 */
public class UserDetailLoaderViewModel extends ViewModel {

    MutableLiveData<UserDetailBean> userDetailBeanMutableLiveData;
    UserDataRepository userDataRepository;

    /**
     * ViewModel Constructor
     */
    public UserDetailLoaderViewModel() {
        userDetailBeanMutableLiveData = new MutableLiveData<>();
        init();
    }

    /**
     * Get Updated User Data
     */
    public MutableLiveData<UserDetailBean> getUserMutableLiveData() {
        return userDetailBeanMutableLiveData;
    }


    /**
     * Initialize Repository
     */
    public void init() {
        userDataRepository = new UserDataRepository();
        userDetailBeanMutableLiveData = userDataRepository.getUserResponseLiveData();
    }

    /**
     * Get More Page User Data
     */
    public void getData(int pageNo) {
        userDataRepository.getUserDetailData(pageNo);
    }

}
