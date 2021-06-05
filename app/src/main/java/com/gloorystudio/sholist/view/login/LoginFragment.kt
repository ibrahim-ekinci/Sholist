package com.gloorystudio.sholist.view.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gloorystudio.sholist.Go
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.data.firebase.getStaticToken
import com.gloorystudio.sholist.data.getJwt
import com.gloorystudio.sholist.data.setJwt
import com.gloorystudio.sholist.databinding.FragmentLoginBinding
import com.gloorystudio.sholist.isEmailTrue
import com.gloorystudio.sholist.isPasswordTrue
import com.gloorystudio.sholist.view.main.MainActivity
import com.gloorystudio.sholist.viewmodel.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {


    private val RC_SIGN_IN = 1
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.initialAuth()
        getStaticToken()
        binding.llRegister.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToRegisterFragment().Go(it)
            lifecycleScope.launch {
                setJwt(requireContext(),"sonTEst")
            }
        }
        binding.textViewForget.setOnClickListener {
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment().Go(it)
            lifecycleScope.launch {
                println(getJwt(requireContext()))
            }
        }
        binding.btnLogin.setOnClickListener {
            //TODO:REMOVE navigation
            if (isFieldsFilling()) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()

                viewModel.loginWithEmailAndPass(
                    requireContext(),
                    binding.etLoginEmail.text.toString(),
                    binding.etLoginPasword.text.toString()
                )

            }
        }
        binding.etLoginPasword.apply {
            doAfterTextChanged {
                isFieldsFilling()
            }
        }
        binding.etLoginEmail.apply {
            doAfterTextChanged {
                isFieldsFilling()
            }
        }


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.newGooglebtn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        binding.signInGoogleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


    }

    private fun isFieldsFilling(): Boolean {
        binding.tiLoginEmail.error = null
        binding.tiLoginPasword.error = null
        if (!binding.etLoginEmail.isEmailTrue()) {
            binding.tiLoginEmail.error = requireContext().getString(R.string.invalid_email_address)
            return false
        } else if (!binding.etLoginPasword.isPasswordTrue()) {
            binding.tiLoginPasword.error =
                requireContext().getString(R.string.password_must_be_more_then_six)
            return false
        } else return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                viewModel.loginWithGoogle(account.idToken!!, requireActivity())
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }


    //--Google Sing in Finish--


}