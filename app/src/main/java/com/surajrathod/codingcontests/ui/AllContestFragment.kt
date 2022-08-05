package com.surajrathod.codingcontests.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.surajrathod.codingcontests.R
import com.surajrathod.codingcontests.adapter.ContestAdapter
import com.surajrathod.codingcontests.db.ContestDatabase
import com.surajrathod.codingcontests.model.Site
import com.surajrathod.codingcontests.repo.ContestRepo
import com.surajrathod.codingcontests.viewmodel.ContestViewModel
import com.surajrathod.codingcontests.viewmodel.ContestViewModelFactory


class AllContestFragment : Fragment() {

    lateinit var viewModel : ContestViewModel
    val repo = ContestRepo()
    lateinit var rv : RecyclerView
    lateinit var contestdb : ContestDatabase
    lateinit var loading : LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contestdb = ContestDatabase.getDatabase(this.requireContext())
        viewModel = ViewModelProvider(this.viewModelStore,ContestViewModelFactory(repo)).get(ContestViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_contest, container, false)

        rv = view.findViewById(R.id.rv_All_Contest)
        loading = view.findViewById(R.id.loading)

        repo.mutableProgress.observe(viewLifecycleOwner,{
            if(!it){
                loading.visibility = View.GONE
            }
        })

        viewModel.getAllSites().observe(viewLifecycleOwner,{
            rv.adapter = ContestAdapter(it,this.requireContext(), contestdb)
        })

        return view
    }


}