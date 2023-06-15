package com.smart.techexactly

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    private var userLiveData: MutableLiveData<ArrayList<AllData>> ?= null
    var userArrayList: ArrayList<AllData> ?= null

    init {
        userLiveData = MutableLiveData()
        populateList()
        userLiveData!!.value = userArrayList
    }

    fun getUserMutableLiveData(): MutableLiveData<ArrayList<AllData>> ?{
        return userLiveData
    }

    private fun populateList(){
        userArrayList = ArrayList()

        userArrayList?.add(AllData("Assistant", R.drawable.ic_assistant, true))
        userArrayList?.add(AllData("Calculator", R.drawable.ic_equal, false))
        userArrayList?.add(AllData("Calculator", R.drawable.ic_calculator_icon, false))
        userArrayList?.add(AllData("Calender", R.drawable.ic_calendar_icon, true))
        userArrayList?.add(AllData("Chrome", R.drawable.ic_chrome, true))
        userArrayList?.add(AllData("Test Assistant", R.drawable.ic_assistant, true))
        userArrayList?.add(AllData("Value Calculator", R.drawable.ic_equal, false))
        userArrayList?.add(AllData("New Calculator", R.drawable.ic_calculator_icon, false))
        userArrayList?.add(AllData("Old Calender", R.drawable.ic_calendar_icon, true))
        userArrayList?.add(AllData("Updated Chrome", R.drawable.ic_chrome, true))
    }

}