package com.example.birthdayphotoframes

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.birthdayphotoframes.databinding.FragmentFrameBinding
import kotlinx.android.synthetic.main.fragment_frame.*
import kotlinx.android.synthetic.main.fragment_frame.view.*
import kotlinx.android.synthetic.main.sample_image_with_text.*
import kotlinx.android.synthetic.main.sample_image_with_text.view.*
import java.util.*


class FrameFragment : Fragment() {

    private lateinit var binding :FragmentFrameBinding

    private val textView: TextView? = null

    var lastEvent: FloatArray? = null
    var d = 0f
    var newRot = 0f
    private var isZoomAndRotate = false
    private var isOutSide = false
    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2
    private var mode = NONE
    private val start = PointF()
    private val mid = PointF()
    var oldDist = 1f
    private var xCoOrdinate = 0f
    private  var yCoOrdinate:kotlin.Float = 0f

    private val pickImage = 100
    private var imageUri: Uri? = null

    private val TAG="FrameFragment"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrameBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            if(data!=null){
           // imageUri = data?.data
              //  println("onactivityresult pickimage first string:$string data.data :${data.data}")
               // val imgg =data.getIntExtra(pickImage.toString(),0)


                println("onactivityresult pickimage:${data.data} ")

                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, data.data)

                binding.imageView.setCustomInside(bitmap)
              }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setBackgroundColor(Color.parseColor("#00000000"))
        binding.constraintlayout.setBackgroundColor(Color.parseColor("#00000000"))
        binding.imageView.setImage(arguments?.get("frameid").toString().toInt())

     binding.imageView.text_custom_text!!.setOnTouchListener { v, event ->
            val viewa = v as TextView
         println("143 setOnTouchListener width:${binding.imageView.text_custom_text.width} height:${binding.imageView.text_custom_text.height}")
         //  viewa.bringToFront()
            viewTransformation(viewa, event)
            true
        }
        binding.imageView.image_inside_custom_view!!.setOnTouchListener { v, event ->
            val viewa = v as ImageView
          //  viewa.bringToFront()
            viewTransformation(viewa, event)
            true
        }
        binding.textBtn.setOnClickListener {
            val inputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            view.requestFocus()
            inputMethodManager.showSoftInput(binding.textHeader, 0)
            binding.imageView.text_custom_text.bringToFront()
        }
        binding.textHeader.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
             binding.imageView.textImage(s.toString())
            }
        })

        media_btn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.downloadBtn.setOnClickListener {
      takeScreenshotOfView(300,300,binding.constraintlayout, (this.context as Activity).window){
            binding.mediaBtn.load(it)
        }
        }
        var frontBack = true
        binding.getFrontBtn.setOnClickListener {
            frontBack = !frontBack
            if(frontBack){
                image_custom_view.bringToFront()
                getFrontBtn.text = getString(R.string.front)
            }
            else{
                image_inside_custom_view.bringToFront()
                getFrontBtn.text = getString(R.string.back)
            }

        }

        val scaleXText = binding.imageView.text_custom_text.scaleX
        val scaleYText = binding.imageView.text_custom_text.scaleY
        val mBorderWidth = 0.0f

        val newScaleX: Float = (binding.imageView.text_custom_text.width
                / (binding.imageView.text_custom_text.width + mBorderWidth + mBorderWidth))
        val newScaleY: Float = (binding.imageView.text_custom_text.height
                / (binding.imageView.text_custom_text.height + mBorderWidth + mBorderWidth))
        binding.textBtnReset.setOnClickListener {
          /*  LayoutParams layoutParams = new LayoutParams(int width, int height);
            layoutParams.setMargins(int left, int top, int right, int bottom);
            imageView.setLayoutParams(layoutParams);*/
            println("143 width:${binding.imageView.text_custom_text.width} start:$scaleXText")
            println("143 height:${binding.imageView.text_custom_text.height} start:$scaleYText")
            //val iv = ImageView(requireContext())
            binding.imageView.text_custom_text.scaleX = scaleXText
            binding.imageView.text_custom_text.scaleY = scaleYText
            val centerX = binding.imageView.contraintsample.x + binding.imageView.contraintsample.width  / 2
            val centerY = binding.imageView.contraintsample.y + binding.imageView.contraintsample.height / 2


            binding.imageView.text_custom_text.animate().x(centerX).y(centerY);

         //   binding.imageView.text_custom_text.animate().translationX(13f)

        }
    }
    fun takeScreenshotOfView( height: Int, width: Int,view: View,window:Window, bitmapCallback: (Bitmap)->Unit): Bitmap? {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val location = IntArray(2)
            view.getLocationInWindow(location)
            PixelCopy.request(window,
                Rect(location[0], location[1], location[0] + view.width, location[1] + view.height),
                bitmap,
                {
                    if (it == PixelCopy.SUCCESS) {
                        bitmapCallback.invoke(bitmap)
                    }
                },
                Handler(Looper.getMainLooper()) )
            return null
        }
        else{
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
            return bitmap
        }
    }
    private fun viewTransformation(view: View, event: MotionEvent) {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.rawX
                yCoOrdinate = view.y - event.rawY
                start[event.x] = event.y
                isOutSide = false
                mode = DRAG
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
            }
            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
                if (mode === DRAG) {
                    val x = event.x
                    val y = event.y
                }
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_MOVE -> if (!isOutSide) {
                if (mode === DRAG) {
                    isZoomAndRotate = false
                    view.animate().x(event.rawX + xCoOrdinate).y(event.rawY + yCoOrdinate)
                        .setDuration(0).start()
                }
                if (mode === ZOOM && event.pointerCount == 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale = newDist1 / oldDist * view.scaleX
                        view.scaleX = scale
                        view.scaleY = scale
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event)
                        view.rotation = (view.rotation + (newRot - d))
                    }
                }
            }
        }
    }

    private fun rotation(event: MotionEvent): Float {
        val delta_x = (event.getX(0) - event.getX(1)).toDouble()
        val delta_y = (event.getY(0) - event.getY(1)).toDouble()
        val radians = Math.atan2(delta_y, delta_x)
        return Math.toDegrees(radians).toFloat()
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toInt().toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }

}

