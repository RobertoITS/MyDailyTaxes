package com.hvdevs.mydailytaxes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import com.hvdevs.mydailytaxes.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    //Controla la visibilidad de los botones en el panel de inicio
    private var isShown = false
    //Controla la expancion del appbar de la pantalla de inicio
    var misAppbarExpand = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val bundle = Bundle()
        val name = bundle.getString("name")
        binding.toolbarLayoutMain.title = name.toString()

        binding.appbarMain.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val scrollRange = appBarLayout.totalScrollRange
            val fraction = 1f * (scrollRange + verticalOffset) / scrollRange
            if (fraction < 0.5 && misAppbarExpand) {
                //Hide here
                misAppbarExpand = false
                //Se cambia la escala de la vista
                binding.fb.animate().scaleX(0f).scaleY(0f)
                binding.ins.animate().scaleX(0f).scaleY(0f)
                binding.tw.animate().scaleX(0f).scaleY(0f)
            }
            if (fraction > 0.8 && !misAppbarExpand) {
                //Show here
                misAppbarExpand = true
                binding.fb.animate().scaleX(1f).scaleY(1f)
                binding.ins.animate().scaleX(1f).scaleY(1f)
                binding.tw.animate().scaleX(1f).scaleY(1f)
            }
        })

        binding.logoFront.setOnClickListener {
            if (!isShown) {
                isShown = true
                binding.fb.animate().translationX(-130f).translationY(-130f)
                binding.ins.animate().translationX(0f).translationY(-182f)
                binding.tw.animate().translationX(130f).translationY(-130f)
            }
            else {
                isShown = false
                binding.fb.animate().translationX(0f).translationY(0f)
                binding.ins.animate().translationX(0f).translationY(0f)
                binding.tw.animate().translationX(0f).translationY(0f)
            }
        }

        return binding.root
    }

}