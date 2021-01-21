package com.cassiobruzasco.parisweather.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cassiobruzasco.parisweather.R
import com.cassiobruzasco.parisweather.databinding.FragmentWeatherBinding
import com.cassiobruzasco.parisweather.viewmodel.WeatherModel
import com.cassiobruzasco.parisweather.viewmodel.WeatherViewModel
import com.cassiobruzasco.util.ViewUtil
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment: Fragment() {

    private val mViewModel by viewModel<WeatherViewModel>()
    private lateinit var mBinding: FragmentWeatherBinding
    private val loadingDialog: Dialog? by lazy {
        ViewUtil.getLoadingDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_weather,
            container, false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureComponents()
        configureObservables()
        mViewModel.initialize()
    }

    private fun configureComponents() {
        mBinding.button.setOnClickListener {
            mViewModel.getWeather("Paris", 2)
        }
    }

    private fun configureObservables() {
        mViewModel.model.weatherStateOb.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
    }

    private fun handleState(state: WeatherModel.WeatherState?) {
        when (state) {
            is WeatherModel.WeatherState.Loading -> {
                if (state.isLoading) {
                    loadingDialog?.show()
                } else {
                    loadingDialog?.dismiss()
                }
            }
            is WeatherModel.WeatherState.GenericError -> {
                Snackbar.make(requireView(), getString(R.string.snack_bar_error), Snackbar.LENGTH_LONG)
            }
        }
    }
}