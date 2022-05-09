package com.hvdevs.mydailytaxes

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hvdevs.mydailytaxes.constructor.Taxes
import com.hvdevs.mydailytaxes.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val items = listOf("Impuestos", "Prestamos", "Otros")
    private val loans = listOf("Personal", "Bancario", "Prestadora", "Otro")
    private val taxes = listOf("Luz", "Gas", "Agua", "Telefono", "Internet", "Otro")
    private val db = Firebase.database
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.typeInput as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.typeInput.setOnItemClickListener { adapterView, view, i, l ->
            binding.checkbox.visibility = View.GONE
            binding.quoteLayout.visibility = View.GONE
            binding.nameInput.setText("")
            when (i) {
                0 -> {
                    binding.checkbox.visibility = View.VISIBLE
                    val adapter2 = ArrayAdapter(requireContext(), R.layout.list_item, taxes)
                    (binding.nameInput as? AutoCompleteTextView)?.setAdapter(adapter2)
                }
                2 -> {
                    binding.checkbox.visibility = View.VISIBLE
                    val adapter3 = ArrayAdapter(requireContext(), R.layout.list_item, taxes)
                    (binding.nameInput as? AutoCompleteTextView)?.setAdapter(adapter3)
                }
                1 -> {
                    binding.quoteLayout.visibility = View.VISIBLE
                    val adapter4 = ArrayAdapter(requireContext(), R.layout.list_item, loans)
                    (binding.nameInput as? AutoCompleteTextView)?.setAdapter(adapter4)
                }
            }
        }

        binding.dateInput.setOnFocusChangeListener { view, b ->
            if (b) {
                setDate(view as TextInputEditText)
            }
        }
        binding.dateInput.setOnClickListener {
            setDate(it as TextInputEditText)
        }

        binding.add.setOnClickListener{
            if (binding.priceInput.text!!.isEmpty())
                Toast.makeText(context, "Complete los campos", Toast.LENGTH_SHORT).show()
            else {
                val quote = if (binding.quoteInput.text!!.isEmpty()) 0
                else Integer.parseInt(binding.quoteInput.text.toString())
                val tax = Taxes(
                    binding.descriptionInput.text.toString(),
                    binding.dateInput.text.toString(),
                    binding.monthly.isChecked,
                    binding.nameInput.text.toString(),
                    Integer.parseInt(binding.priceInput.text.toString()).toLong(),
                    quote,
                    binding.typeInput.text.toString()
                )
                addTax(tax)
                val result = true
                setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                removeFragment(R.id.add_taxes)
            }
        }

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    private fun setDate(textInput: TextInputEditText){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
        datePicker.addOnPositiveButtonClickListener {
            val date = getDate(datePicker.selection!!, "dd/MM/yyyy")
            textInput.setText(date.toString())
        }
        datePicker.show(requireActivity().supportFragmentManager, "tag")
    }

    private fun addTax(tax: Taxes){ //Con push creamos un nuevo documento
        db.getReference("myTaxes/users/userId/taxes/${LocalDate.now().month}-${LocalDate.now().year}/").push().setValue(tax)
    }

    private fun removeFragment(idFrag: Int){
        val transition = requireActivity().supportFragmentManager.beginTransaction()
        val frag = requireActivity().supportFragmentManager.findFragmentById(idFrag)
        transition
            .setCustomAnimations(
                R.anim.frag_down_to_up,
                R.anim.frag_up_to_down,
                R.anim.frag_down_to_up,
                R.anim.frag_up_to_down
            )
        transition
            .remove(frag!!)
            .commit()
    }

}