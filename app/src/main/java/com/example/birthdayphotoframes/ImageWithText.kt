package com.example.birthdayphotoframes

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import kotlinx.android.synthetic.main.sample_image_with_text.view.*


class ImageWithText(
    context: Context,attrs:AttributeSet?
) : ConstraintLayout(context,attrs) {

  //  lateinit var binding :SampleImageWithTextBinding
    private val TAG=ImageWithText::class.java.simpleName
    init {
         /* binding =
            SampleImageWithTextBinding.inflate(LayoutInflater.from(context), this, true)*/
        inflate(context, R.layout.sample_image_with_text, this)
       // inflate(context,R.layout.sample_image_with_text,binding.root)
        attrs?.let {attributeSet->
            val attributes = context.obtainStyledAttributes(attributeSet,R.styleable.ImageWithText)
            try{
//                image_custom_view.setImageDrawable(attributes.getDrawable(R.styleable.ImageWithText_imageRef))
            //    image_inside_custom_view.setImageDrawable(attributes.getDrawable(R.styleable.ImageWithText_imageInside))
              text_custom_text.text = attributes.getString(R.styleable.ImageWithText_text)
                //    text_custom_text.text = "ali"


            }
            finally {
            attributes.recycle()
            }
        }
    }
    fun setImage(img: Int){
        image_custom_view.load(img)
    }
    fun setCustomInside(img: Bitmap){
        image_inside_custom_view.load(img)
    }
    fun textImage(string: String){
        Log.d(TAG, "textImage:$string")
        //  binding.textImage.text =string
        text_custom_text.bringToFront()
        text_custom_text.text =string
    }

 /*   override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d(TAG, "onLayout")
        super.onLayout(changed, left, top, right, bottom)
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(TAG, "onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        val widthWithoutPadding = measuredWidth - paddingLeft - paddingRight
        val heightWithoutPadding = measuredHeight- paddingTop - paddingBottom


        val size =if(widthWithoutPadding>heightWithoutPadding){
            heightWithoutPadding
        }else{
            widthWithoutPadding
        }
        setMeasuredDimension(size + paddingLeft + paddingRight,
            size + paddingTop + paddingBottom)

    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        Log.d(TAG, "dispatchDraw")
    }

    override fun draw(canvas: Canvas?) {
        Log.d(TAG, "inside draw")
        super.draw(canvas)
    }

    /*
    * For efficiency, layouts do not get their onDraw() method called.
    * To enable it, call setWillNotDrawEnabled(false)
    * https://groups.google.com/g/android-developers/c/oLccWfszuUo
    * */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "inside onDraw")
    }
*/
}