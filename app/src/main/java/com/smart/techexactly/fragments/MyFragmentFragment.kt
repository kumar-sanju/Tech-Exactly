package com.smart.techexactly.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.techexactly.*
import com.smart.techexactly.databinding.FragmentMyFragmentBinding


class MyFragmentFragment : Fragment() {

    private lateinit var binding: FragmentMyFragmentBinding
    lateinit var viewModel: MainViewModel
    lateinit var addDataAdapter: AddDataAdapter
    lateinit var VALUE: String
    lateinit var POSITION: String

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        POSITION = requireArguments().getString(POSITION).toString()
//        VALUE = requireArguments().getString(VALUE).toString()
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_fragment, container, false)
        val view: View = binding.getRoot()

        addDataAdapter = AddDataAdapter()

        val layoutManager = LinearLayoutManager(context)
        binding.listOfRV.layoutManager = layoutManager
        binding.listOfRV.adapter = addDataAdapter

        viewModel = ViewModelProvider(this,
            MainViewModelFactory())[MainViewModel::class.java]

        viewModel.getUserMutableLiveData()?.observe(this, userListUpdateObserver)

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addDataAdapter.setFilter(s.toString())
            }
        })

        return view
    }

//    fun newInstanceValue(position: String?, value: String?): MyFragmentFragment? {
//        val fragment: MyFragmentFragment = MyFragmentFragment()
//        val args = Bundle()
//        args.putString(POSITION, position)
//        args.putString(VALUE, value)
//        fragment.setArguments(args)
//        return fragment
//    }

    private var userListUpdateObserver: Observer<ArrayList<AllData>> =
        Observer<ArrayList<AllData>> {
            it -> addDataAdapter.updateArrayList(it)
        }
}