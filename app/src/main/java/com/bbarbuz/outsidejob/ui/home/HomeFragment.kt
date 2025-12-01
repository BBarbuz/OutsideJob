package com.bbarbuz.outsidejob.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bbarbuz.outsidejob.R
import com.bbarbuz.outsidejob.databinding.FragmentHomeBinding

//My imports
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null // Przechowuje referencje dla obiektu View Binding dla layoutu
    // FragmentHomeBinding to klasa automatycznie stworzona przez gradle na podstawie fragment_home.xml
    // dwukropek po _binding oznacza określenie typu zmiennej
    // znak zapytania po FragmentHomeBinding oznacza że zmienna może przyjąć wartość null
    // ? po typie np <zmienna: typ?> pozwala zmiennej przyjąć wartość null jest typu nullable

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!! // bezpieczny skrót do referencji obiektu View Binding dla layoutu
    // dwa wykrzykniki wymuszają pobranie wartości null jako non-null dlatego przed użyciem konieczna jest deklaracja

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val textDesc = getString(R.string.home_fragment_description)
        binding.textDescription.text = textDesc

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}