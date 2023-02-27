package com.quraanali.discoverPlaces.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quraanali.discoverPlaces.R
import com.quraanali.discoverPlaces.databinding.FragmentHomeBinding
import com.quraanali.discoverPlaces.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.uiState.collect {
                showProgress(it.isLoading)


                if (it.searchImagesResultBottomSheetUiModel != null) {
                    SearchImagesResultBottomSheet.newInstance(it.searchImagesResultBottomSheetUiModel)
                        .show(
                            parentFragmentManager,
                            SearchImagesResultBottomSheet.TAG
                        )

                    viewModel.clearShowSearchImagesResultBottomSheet()
                }


            }
        }


        try {
            binding.mapView.onCreate(savedInstanceState)
            binding.mapView.onResume()

            MapsInitializer.initialize(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }


        view.post {
            checkPermissionsAndGetMyLocation()
        }

    }

    private fun checkPermissionsAndGetMyLocation() {
        askForPermissions(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION) {
            if (it.isAllGranted(
                    Permission.ACCESS_FINE_LOCATION,
                    Permission.ACCESS_COARSE_LOCATION
                )
            ) {
                checkGpsStatus()
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    fun getMyLocation() {

        binding.mapView.getMapAsync { googleMap ->
            googleMap.setOnMapClickListener {
                // latitude and longitude
                val latitude = it.latitude
                val longitude = it.longitude
                // create marker
                val marker = MarkerOptions().position(
                    LatLng(latitude, longitude)
                ).title(getString(R.string.app_name))
                // Changing marker icon
                marker.icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                )

                // adding marker
                googleMap.addMarker(marker)

                getCityNameFromLatLng(
                    latitude,
                    longitude
                )
            }


            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            val locationResult = fusedLocationClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val cameraPosition =
                        CameraPosition.Builder().target(
                            LatLng(
                                task.result?.latitude ?: 31.722284363281922,
                                task.result?.longitude ?: 35.99713895566469
                            )
                        ).zoom(12f).build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(cameraPosition)
                    )

                    getCityNameFromLatLng(
                        task.result?.latitude ?: 31.722284363281922,
                        task.result?.longitude ?: 35.99713895566469
                    )


                } else {
                    val cameraPosition =
                        CameraPosition.Builder()
                            .target(LatLng(31.722284363281922, 35.99713895566469)).zoom(12f)
                            .build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newCameraPosition(cameraPosition)
                    )

                }
            }

        }
    }

    private fun checkGpsStatus() {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getMyLocation()
        } else {
            showGpsSettings()
        }
    }

    private fun showGpsSettings() {
        val intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent1)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        checkGpsStatus()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    private fun getCityNameFromLatLng(lat: Double, lng: Double) {
        val location = Location("providerNA")
        location.latitude = lat
        location.longitude = lng

        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        var addresses: List<Address>? = null

        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        } catch (e: Exception) {
            Log.e("", "Error in getting address for the location")
        }

        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "No address found for the location",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val address = addresses[0]

            viewModel.getImageForLocation(address)

            Toast.makeText(
                requireContext(),
                address.countryName + " " +
                        address.adminArea + " " +
                        address.subAdminArea + " " +
                        address.locality + " " +
                        address.featureName + " ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}