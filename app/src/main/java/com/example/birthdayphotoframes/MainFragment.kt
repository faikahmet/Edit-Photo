package com.example.birthdayphotoframes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birthdayphotoframes.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    lateinit var binding : FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listFrame :MutableList<Int> = arrayListOf(
            R.drawable.landscapeone,
            R.drawable.beac,
            R.drawable.outputone,
            R.drawable.outputwo,
            R.drawable.outputthree,
            R.drawable.outputfour,
            R.drawable.outputfive,
            R.drawable.outputsiz,
            )

        val adapter = FrameListAdapter(listFrame){
            println("recyclerview adapter id:$it")
           val action = MainFragmentDirections.actionMainFragmentToFrameFragment(listFrame[it])
           findNavController().navigate(action)
        }
        adapter.submitList(listFrame)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =LinearLayoutManager(this.context)
        println("52.satır recyclerview :${recyclerView.height} :${recyclerView.width}")
        toolbar.setOnClickListener {
            println("57.satır toolbar")

        }
    }
}