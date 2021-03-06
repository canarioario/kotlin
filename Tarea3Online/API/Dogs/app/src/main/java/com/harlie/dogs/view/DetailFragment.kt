package com.harlie.dogs.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.ajalt.timberkt.Timber
import com.harlie.dogs.R
import com.harlie.dogs.databinding.FragmentDetailBinding
import com.harlie.dogs.databinding.SendSmsDialogBinding
import com.harlie.dogs.model.DogBreed
import com.harlie.dogs.model.DogsApiService
import com.harlie.dogs.model.SmsInfo
import com.harlie.dogs.repository.DogDetailDataRepository
import com.harlie.dogs.util.GlideWrapper
import com.harlie.dogs.util.SharedPreferencesHelper
import com.harlie.dogs.viewmodel.DogDetailViewModel
import com.harlie.dogs.viewmodel.MyViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private val _tag = "LEE: <" + DetailFragment::class.java.simpleName + ">"

    private lateinit var dogDetailViewModel: DogDetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding
    private var dogBreedId = "1"
    private var deepLink = false
    private var startedSendSMS = false
    private var currentDog: DogBreed? = null

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.tag(_tag).d("onCreateView")
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.tag(_tag).d("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            dogBreedId = DetailFragmentArgs.fromBundle(bundle).dogBreedId
            deepLink = bundle.getBoolean("deep_link", false)
        }
        Timber.tag(_tag).d("dogBreedId=${dogBreedId}")
        Timber.tag(_tag).d("deepLink=${deepLink}")
        val apiService = DogsApiService()
        val prefHelper = SharedPreferencesHelper()
        val viewModelFactory = MyViewModelFactory(DogDetailDataRepository(dogBreedId, apiService, prefHelper))
        dogDetailViewModel =
            ViewModelProvider(this, viewModelFactory).get(DogDetailViewModel::class.java)
        dogDetailViewModel.isDeepLink = deepLink

        observeViewModel()
        refresh()
    }

    private fun observeViewModel() {
        Timber.tag(_tag).d("observeViewModel")
        dogDetailViewModel.dog.observe(viewLifecycleOwner, Observer { dog ->
            if (dog != null) {
                currentDog = dog
                Timber.tag(_tag).d("observeViewModel: observe dog_icon dog_icon=${dog}")
                dataBinding.dog = dog
                dog.breedImageUrl?.let {
                    GlideWrapper().setBackgroundColor(this, dataBinding, it)
                }
            }
            else {
                Timber.tag(_tag).d("observeViewModel: need to refresh()")
                refresh()
            }
        })
    }

    private fun refresh() {
        Timber.tag(_tag).d("refresh")
        uiScope.launch(Dispatchers.IO) {
            dogDetailViewModel.fetch()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Timber.tag(_tag).d("onCreateOptionsMenu")
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.tag(_tag).d("onOptionsItemSelected")
        when (item.itemId) {
            R.id.action_send_sms -> {
                view?.let {
                    Timber.tag(_tag).d("onOptionsItemSelected: send SMS")
                    startedSendSMS = true
                    (activity as MainActivity).checkSmsPermission()
                }
            }
            R.id.action_share -> {
                view?.let {
                    Timber.tag(_tag).d("onOptionsItemSelected: share")
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout this dog breed")
                    intent.putExtra(Intent.EXTRA_TEXT, "The ${currentDog?.breedName} is bred for ${currentDog?.breedPurpose}")
                    intent.putExtra(Intent.EXTRA_STREAM, currentDog?.breedImageUrl)
                    startActivity(Intent.createChooser(intent, "Share with"))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted: Boolean) {
        Timber.tag(_tag).d("onPermissionResult permissionGranted=${permissionGranted}")
        if (startedSendSMS && permissionGranted) {
            context?.let {
                val smsInfo = SmsInfo(
                    "",
                    "The ${currentDog?.breedName} is bred for ${currentDog?.breedPurpose}",
                    currentDog?.breedImageUrl,
                    currentDog?.breedId
                )
                val dialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(
                    LayoutInflater.from(it),
                    R.layout.send_sms_dialog,
                    null,
                    false
                )
                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton(R.string.send_sms) {dialog, which ->
                        if (! dialogBinding.smsDestination.text.isNullOrEmpty()) {
                            Timber.tag(_tag).d("-CLICK- send SMS")
                            smsInfo.to = dialogBinding.smsDestination.text.toString()
                            sendSms(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel") {dialog, which ->
                        Timber.tag(_tag).d("-CLICK- cancel SMS")
                    }
                    .show()
                dialogBinding.smsInfo = smsInfo
            }
        }
    }

    private fun sendSms(smsInfo: SmsInfo) {
        Timber.tag(_tag).d("sendSms smsInfo=${smsInfo}")
        Toast.makeText(activity, "Sending SMS..", Toast.LENGTH_LONG).show()
        uiScope.launch(Dispatchers.IO) {
            dogDetailViewModel.sendSms(smsInfo)
        }
    }

}
