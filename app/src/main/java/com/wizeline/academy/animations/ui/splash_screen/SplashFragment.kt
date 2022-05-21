package com.wizeline.academy.animations.ui.splash_screen

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimatorRes
import androidx.core.animation.doOnCancel
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wizeline.academy.animations.R
import com.wizeline.academy.animations.databinding.SplashFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runAnimationSet(R.animator.w_logo_animator)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(2000)
            goToHomeScreen()
        }
    }

    private fun goToHomeScreen() {
        val directions = SplashFragmentDirections.toMainFragment()
        findNavController().navigate(directions)
    }

    private fun runAnimationSet(@AnimatorRes animator: Int) {
        (AnimatorInflater.loadAnimator(this.context, animator) as? AnimatorSet).apply {
            this?.setTarget(binding.ivWizelineLogo)
            this?.doOnCancel {
                restoreOriginalValues()
            }

        }?.start()
    }

    private fun restoreOriginalValues() {
        binding.ivWizelineLogo.apply {
            translationX = 0f
            translationY = 0f
            rotationX = 0f
            rotationY = 0f
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }

}