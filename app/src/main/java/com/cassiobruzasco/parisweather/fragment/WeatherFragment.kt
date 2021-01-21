package com.cassiobruzasco.parisweather.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cassiobruzasco.parisweather.R
import com.cassiobruzasco.parisweather.databinding.FragmentWeatherBinding
import com.cassiobruzasco.parisweather.fragment.adapter.WeatherRecyclerAdapter
import com.cassiobruzasco.parisweather.viewmodel.WeatherModel
import com.cassiobruzasco.parisweather.viewmodel.WeatherViewModel
import com.cassiobruzasco.util.ViewUtil
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * The Weather Fragment
 * Here are displayed the list containing 16 items with Paris Forecast
 */
class WeatherFragment: Fragment() {

    companion object {
        const val CITY = "Paris"
        const val DAYS = 16
    }

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

        configureObservables()
        mViewModel.initialize()
        mViewModel.getWeather(CITY, DAYS)
    }

    /**
     * After the request is done
     * The components that use the response objects are populated
     */
    private fun configureComponents() {
        mBinding.cityCountry.text = getString(
            R.string.weather_fragment_city_and_country_label,
            mViewModel.model.weatherOb.value!!.city.cityName,
            mViewModel.model.weatherOb.value!!.city.country
        )
        mBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recycler.adapter = WeatherRecyclerAdapter(mViewModel.dateUtil, mViewModel.model.weatherOb.value!!)
    }

    /**
     * The observables that are triggered based on it's value
     */
    private fun configureObservables() {
        mViewModel.model.weatherStateOb.observe(viewLifecycleOwner, Observer {
            handleState(it)
        })
        mViewModel.model.weatherLoadedOb.observe(viewLifecycleOwner, Observer {
            if (it) configureComponents()
        })
    }

    /**
     * This method handles the state based on what response the api returns
     * The loading animation starts when the request is initialized and ends after gets a result
     * 3 Customized snack bars to display possible erros
     */
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
                Snackbar.make(requireView(), getString(R.string.snack_bar_generic_error), Snackbar.LENGTH_LONG)
            }
            is WeatherModel.WeatherState.NotANumber -> {
                Snackbar.make(requireView(), getString(R.string.snack_bar_number_error), Snackbar.LENGTH_LONG)
            }
            is WeatherModel.WeatherState.CityNotFound -> {
                Snackbar.make(requireView(), getString(R.string.snack_bar_city_error), Snackbar.LENGTH_LONG)
            }
        }
    }
}