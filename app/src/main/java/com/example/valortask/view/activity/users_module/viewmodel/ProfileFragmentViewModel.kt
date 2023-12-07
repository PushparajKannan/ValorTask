package com.example.valortask.view.activity.users_module.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.valortask.baseclass.BaseViewModel
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.database.tablemodel.UserListDbModel
import com.example.valortask.repository.ApiDataRepository
import com.example.valortask.repository.LocalDataRepository
import com.example.valortask.utilities.SharedPref
import com.example.valortask.utilities.ValidationUtils
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewState
import com.example.valortask.view.activity.users_module.viewstate.ProfileViewType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val networkRepo: ApiDataRepository,
    private val localDbRepo: LocalDataRepository,
    private val sharedPref: SharedPref
) : BaseViewModel() {


    var currentViewType = MutableLiveData<ProfileViewType>(ProfileViewType.VIEW)


    val userName = MutableLiveData<String>("")
    val userMail = MutableLiveData<String>("")
    val userPhone = MutableLiveData<String>("")
    val userDOB = MutableLiveData<String>("")


    fun updateProfileInfo(user : UserListDbModel?){
        userName.value = user?.userName ?: ""
        userMail.value = user?.userMail ?: ""
        userPhone.value = user?.phoneNumber ?: ""
        userDOB.value = user?.userDateOfBirth ?: ""
    }


    fun updateProfile(model: UserListDbModel){

        when{
            userName.value.isNullOrEmpty() ->{
                updateUiState(BaseViewState.ShowError("User name should not empty"))
            }

            ValidationUtils.isValidEmailPatterMatcher(userMail.value).not() -> {
                updateUiState(BaseViewState.ShowError("Enter valid email address"))
            }

            userDOB.value.isNullOrEmpty() ->{
                updateUiState(BaseViewState.ShowError("UserDOB name should not empty"))
            }

            (userName.value.isNullOrEmpty().not() &&
                    userMail.value.isNullOrEmpty().not() &&
                    userDOB.value.isNullOrEmpty().not()) ->{

                viewModelScope.launch(Dispatchers.IO) {
                    val data = model.copy(
                        userMail= userMail.value,
                        userDateOfBirth = userDOB.value,
                        userName = userName.value
                    )

                    localDbRepo.updateUser(data)
                    updateUiState(ProfileViewState.ProfileUpdated("Profile updated"))

                }
                    }

        }
    }

}