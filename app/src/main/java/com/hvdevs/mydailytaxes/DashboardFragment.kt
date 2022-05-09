package com.hvdevs.mydailytaxes

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hvdevs.mydailytaxes.adapter.TaxesAdapter
import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.databinding.FragmentDashboardBinding
import com.hvdevs.mydailytaxes.mvvm.data.network.TaxesRepoImplement
import com.hvdevs.mydailytaxes.mvvm.domain.TaxesUseCaseImplement
import com.hvdevs.mydailytaxes.mvvm.presentation.viewmodel.TaxesViewModel
import com.hvdevs.mydailytaxes.mvvm.presentation.viewmodel.TaxesViewModelFactory
import com.hvdevs.mydailytaxes.resource.Resource
import com.hvdevs.mydailytaxes.utilities.Utilities

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private var rotate = false
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            TaxesViewModelFactory(TaxesUseCaseImplement(TaxesRepoImplement()))
        )[TaxesViewModel::class.java]
    }
    private var mainList: ArrayList<Taxes> = arrayListOf()
    private lateinit var mAdapter: TaxesAdapter
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)



//        binding.rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        getData()

        val fabRotate = AnimationUtils.loadAnimation(context, R.animator.fab_rotate)
        val fabRotateOriginPosition = AnimationUtils.loadAnimation(context, R.animator.fab_rotate_origin_position)

        setFragmentResultListener("requestKey") { requestKey, bundle -> //Se soluciona un problema de vida
            val result = bundle.getBoolean("bundleKey") //Obtenemos el resultado del fragment anterior
            if (result) option(binding.add, fabRotate, fabRotateOriginPosition) //Pasamos la funcion a ejecutar
        }

        binding.add.setOnClickListener {
            option(it, fabRotate, fabRotateOriginPosition)
        }

        binding.dark.setOnClickListener {
            option(binding.add, fabRotate, fabRotateOriginPosition)
        }

        return binding.root
    }

    private fun option(it: View, fabRotate: Animation, fabRotateOriginPosition: Animation){
        if (!rotate) {
            rotate = true
            binding.dark.visibility = View.VISIBLE
            it.startAnimation(fabRotate)
            it.background.setTint(Color.parseColor("#FF0000"))
            it.animate().scaleX(0.8f).scaleY(0.8f)
            Utilities.addFragment(requireFragmentManager(), R.id.add_taxes, DetailsFragment())
        }
        else {
            rotate = false
            it.startAnimation(fabRotateOriginPosition)
            it.background.setTint(Color.parseColor("#FF03DAC5"))
            it.animate().scaleX(1f).scaleY(1f)
            Utilities.removeFragment(requireFragmentManager(), R.id.add_taxes)
            binding.dark.visibility = View.GONE
        }
    }

    private fun getData(){ //Obtenemos los datos
        viewModel.fetchTaxesData.observe(viewLifecycleOwner){ result ->
            when (result){
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    if (result.data.isEmpty()){
                        binding.progressBar.visibility = View.GONE
                        binding.rootCl.visibility = View.VISIBLE
                    } else {
                        initRV(result.data)
                        binding.progressBar.visibility = View.GONE
                        binding.rootCl.visibility = View.GONE
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "Error ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRV(data: ArrayList<Taxes>) {
        mainList = data
        mAdapter = TaxesAdapter(mainList)
        mAdapter.notifyDataSetChanged()
        binding.rv.adapter = mAdapter
        GridLayoutManager(context, 3, RecyclerView.VERTICAL, false).apply {
            binding.rv.layoutManager = this
        }
        binding.rv.setHasFixedSize(true)
        mAdapter.setOnItemClickListener(object : TaxesAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("name", mainList[position].name)
                Utilities.addFragment(fragmentManager!!, R.id.add_taxes, DetailsFragment())
            }
        })
    }
}